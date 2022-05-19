package com.ssung.travelDiary.web.board;

import com.ssung.travelDiary.domain.board.Board;
import com.ssung.travelDiary.service.board.BoardService;
import com.ssung.travelDiary.web.SessionConst;
import com.ssung.travelDiary.dto.board.BoardResponseDto;
import com.ssung.travelDiary.dto.board.BoardSaveRequestDto;
import com.ssung.travelDiary.dto.board.BoardUpdateRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class BoardControllerTest {

    @InjectMocks
    BoardController boardController;

    @Mock
    BoardService boardService;

    MockMvc mockMvc;

    private final String basicURL = "/board";

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(boardController).build();
    }

    @Test
    void 게시글_리스트_조회() throws Exception {
        // given
        String date = LocalDate.now().toString();
        given(boardService.findList(1L, date)).willReturn(List.of(new BoardResponseDto()));

        // when, then
        mockMvc.perform(get(basicURL + "/privateBoardList")
                        .sessionAttr(SessionConst.USER_ID, 1L)
                        .param("date", date))
                .andExpect(status().isOk())
                .andExpect(view().name("board/boardList"));

        verify(boardService).findList(1L, date);
    }

    @Test
    void 게시글_조회() throws Exception {
        // given
        given(boardService.findOne(1L)).willReturn(new BoardResponseDto());

        // when, then
        mockMvc.perform(get(basicURL + "/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("board/board"));

        verify(boardService).findOne(1L);
    }

    @Test
    void 게시글_저장_폼() throws Exception {
        // when, then
        mockMvc.perform(get(basicURL))
                .andExpect(status().isOk())
                .andExpect(view().name("board/boardSaveForm"));
    }

    @Test
    void 게시글_저장() throws Exception {
        // given
        String date = LocalDate.now().toString();
        Board board = Board.builder().date(date).build();
        given(boardService.save(any(BoardSaveRequestDto.class), anyLong())).willReturn(new BoardResponseDto(board));

        // when, then
        mockMvc.perform(post(basicURL)
                        .param("title", "title")
                        .param("content", "content")
                        .param("location", "location")
                        .param("date", date)
                        .sessionAttr(SessionConst.USER_ID, "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/board/privateBoardList?date=" + date));

        verify(boardService).save(any(BoardSaveRequestDto.class), anyLong());
    }

    @Test
    void 게시글_저장_검증에러() throws Exception {
        // when, then
        mockMvc.perform(post(basicURL)
                        .param("title", "title")
                        .sessionAttr(SessionConst.USER_ID, "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("board/boardSaveForm"));

        verify(boardService, never()).save(any(BoardSaveRequestDto.class), anyLong());
    }

    @Test
    void 게시글_수정_폼() throws Exception {
        // given
        given(boardService.findOne(1L)).willReturn(new BoardResponseDto());

        // when, then
        mockMvc.perform(get(basicURL + "/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("board/boardUpdateForm"));
    }

    @Test
    void 게시글_수정() throws Exception {
        // given
        BoardResponseDto responseDto = new BoardResponseDto();
        responseDto.setId(1L);
        given(boardService.update(any(BoardUpdateRequestDto.class), anyLong())).willReturn(responseDto);

        // when, then
        mockMvc.perform(patch(basicURL + "/1")
                        .param("title", "title")
                        .param("content", "content")
                        .param("location", "location")
                        .param("date", LocalDate.now().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/board/1"));

        verify(boardService).update(any(BoardUpdateRequestDto.class), anyLong());
    }

    @Test
    void 게시글_수정_검증에러() throws Exception {
        // when, then
        mockMvc.perform(patch(basicURL + "/1")
                        .param("title", "title"))
                .andExpect(status().isOk())
                .andExpect(view().name("board/boardUpdateForm"));

        verify(boardService, never()).update(any(BoardUpdateRequestDto.class), anyLong());
    }
}