package com.ssung.travelDiary.web.board;

import com.ssung.travelDiary.service.board.BoardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BoardApiControllerTest {

    @InjectMocks
    BoardApiController boardApiController;

    @Mock
    BoardService boardService;

    MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(boardApiController).build();
    }

    @Test
    void 게시글_삭제() throws Exception {
        // given
        given(boardService.delete(1L)).willReturn(1L);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.delete("/board/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }
}