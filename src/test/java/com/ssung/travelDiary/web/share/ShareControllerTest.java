package com.ssung.travelDiary.web.share;

import com.ssung.travelDiary.domain.board.Board;
import com.ssung.travelDiary.dto.share.ShareBoardResponseDto;
import com.ssung.travelDiary.dto.share.ShareResponseDto;
import com.ssung.travelDiary.dto.share.ShareSaveRequestDto;
import com.ssung.travelDiary.service.board.BoardService;
import com.ssung.travelDiary.service.members.MemberService;
import com.ssung.travelDiary.service.share.ShareService;
import com.ssung.travelDiary.constancy.SessionConst;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ShareControllerTest {

    @InjectMocks
    ShareController shareController;

    @Mock
    ShareService shareService;

    @Mock
    BoardService boardService;

    @Mock
    MemberService memberService;

    MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(shareController).build();
    }
    
    @Test
    void 공유폴더_생성_폼() throws Exception {
        // when, then
        mockMvc.perform(get("/share")
                        .sessionAttr(SessionConst.USERNAME, "username"))
                .andExpect(status().isOk())
                .andExpect(view().name("share/shareCreateForm"));
    }

    @Test
    void 공유폴더_생성_성공() throws Exception {
        // given
        ShareResponseDto responseDto = new ShareResponseDto();
        responseDto.setId(1L);
        given(shareService.save(any(ShareSaveRequestDto.class), anyLong())).willReturn(responseDto);

        // when, then
        mockMvc.perform(post("/share")
                        .param("title", "title")
                        .param("creator", "creator")
                        .param("members", "1")
                        .param("members", "2")
                        .param("boards", "1")
                        .param("boards", "2")
                        .sessionAttr(SessionConst.USER_ID, "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/share/" + 1));

        verify(shareService).save(any(ShareSaveRequestDto.class), anyLong());
    }

    @Test
    void 공유폴더_생성_검증에러() throws Exception {
        // when, then
        mockMvc.perform(post("/share")
                        .param("title", "title")
                        .param("members", "1")
                        .param("members", "2")
                        .param("boards", "1")
                        .param("boards", "2")
                        .sessionAttr(SessionConst.USER_ID, "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("share/shareCreateForm"));

        verify(shareService, never()).save(any(ShareSaveRequestDto.class), anyLong());
    }

    @Test
    void 공유폴더_조회() throws Exception {
        // given
        Board board = Board.builder().title("boardTitle").build();
        given(shareService.findShareBoard(anyLong())).willReturn(List.of(new ShareBoardResponseDto(board)));

        // when, then
        mockMvc.perform(get("/share/1")
                        .sessionAttr(SessionConst.USER_ID, "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("board/boardList"));

        verify(shareService).findShareBoard(anyLong());
    }

    @Test
    void 공유폴더_생성_시_게시글_조회() throws Exception {
        // when, then
        mockMvc.perform(get("/share/shareBoard")
                        .sessionAttr(SessionConst.USER_ID, "1")
                        .param("value", "title"))
                .andExpect(status().isOk())
                .andExpect(view().name("share/shareCreateForm :: #boardListBox"));

        verify(boardService).addBoardSearch(anyLong(), anyString());
    }

    @Test
    void 공유폴더_생성_시_회원_조회() throws Exception {
        // when, then
        mockMvc.perform(get("/share/shareMember")
                        .sessionAttr(SessionConst.USER_ID, "1")
                        .param("value", "title"))
                .andExpect(status().isOk())
                .andExpect(view().name("share/shareCreateForm :: #memberListBox"));

        verify(memberService).addMemberSearch(anyLong(), anyString());
    }
}