package com.ssung.travelDiary.web.members;

import com.ssung.travelDiary.domain.members.Member;
import com.ssung.travelDiary.exception.member.MemberNotFoundException;
import com.ssung.travelDiary.service.members.MemberService;
import com.ssung.travelDiary.dto.member.MemberResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class MemberApiControllerTest {

    @InjectMocks
    MemberApiController memberApiController;

    @Mock
    MemberService memberService;

    MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(memberApiController).build();
    }

    @Test
    void 아이디_중복확인_중복없음() throws Exception {
        // given
        given(memberService.findByUsername(anyString())).willReturn(new MemberResponseDto(null));

        // when, then
        mockMvc.perform(get("/member/api/duplicate")
                        .param("username", "username"))
                .andDo(print())
                .andExpect(content().string("false"))
                .andExpect(status().isOk());

        verify(memberService).findByUsername(anyString());
    }

    @Test
    void 아이디_중복확인_중복존재() throws Exception {
        // given
        given(memberService.findByUsername(anyString())).willThrow(MemberNotFoundException.class);

        // when, then
        mockMvc.perform(get("/member/api/duplicate")
                        .param("username", "username"))
                .andDo(print())
                .andExpect(content().string("true"))
                .andExpect(status().isOk());

        verify(memberService).findByUsername(anyString());
    }
}