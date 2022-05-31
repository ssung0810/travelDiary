package com.ssung.travelDiary.web.share;

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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ShareApiControllerTest {

    @InjectMocks
    ShareApiController shareApiController;

    @Mock
    ShareService shareService;

    MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(shareApiController).build();
    }

    @Test
    void 선택가능한_공유폴더_리스트_출력() throws Exception {
        // given
        given(shareService.findList(anyLong())).willReturn(anyList());

        // when, then
        mockMvc.perform(get("/share/api/shareList")
                        .sessionAttr(SessionConst.USER_ID, "1"))
                .andExpect(status().isOk());

        verify(shareService).findList(anyLong());
    }

}