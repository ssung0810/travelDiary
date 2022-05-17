package com.ssung.travelDiary.web.login;

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

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    @InjectMocks
    LoginController loginController;

    @Mock
    MemberService memberService;

    MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }

    @Test
    void 로그인_페이지() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("members/login"));
    }

    @Test
    void 로그인_성공() throws Exception {
        // given
        String username = "username";
        String password = "password123!";

        MemberResponseDto responseDto = new MemberResponseDto();
        responseDto.setId(1L);
        responseDto.setUsername("username");
        responseDto.setImage(null);

        given(memberService.loginValidation(username, password)).willReturn(responseDto);

        // when, then
        mockMvc.perform(post("/login")
                        .param("username", username)
                        .param("password", password))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/board/privateBoardList"));

        verify(memberService).loginValidation(username, password);
    }

    @Test
    void 로그인_실패_검증에러() throws Exception {
        // given
        String username = "username";
        String password = "";

        // when, then
        mockMvc.perform(post("/login")
                        .param("username", username)
                        .param("password", password))
                .andExpect(status().isOk())
                .andExpect(view().name("members/login"));

        verify(memberService, never()).loginValidation(username, password);
    }

    @Test
    void 로그인_실페_아이디_및_비밀번호_부적합() throws Exception {
        // given
        String username = "username";
        String password = "password123!";

        given(memberService.loginValidation(username, password)).willReturn(null);

        // when, then
        mockMvc.perform(post("/login")
                        .param("username", username)
                        .param("password", password))
                .andExpect(status().isOk())
                .andExpect(view().name("members/login"));

        verify(memberService).loginValidation(username, password);
    }
}