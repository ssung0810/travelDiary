package com.ssung.travelDiary.web.board;

import com.ssung.travelDiary.service.board.BoardService;
import com.ssung.travelDiary.web.SessionConst;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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
    void 게시글_리스트_출력() throws Exception {
        // given
        String date = LocalDate.now().toString();
        given(boardService.findList(1L, date)).willReturn(any());

        // when, then
        mockMvc.perform(get(basicURL + "/privateBoardList")
                        .sessionAttr(SessionConst.USER_ID, 1L)
                        .param("date", date))
                .andExpect(status().isOk())
                .andExpect(view().name("board/boardList"))
                .andDo(print());
    }
}