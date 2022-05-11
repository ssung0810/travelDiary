package com.ssung.travelDiary.web.members;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssung.travelDiary.domain.members.Role;
import com.ssung.travelDiary.service.members.MemberService;
import com.ssung.travelDiary.web.members.dto.MemberSaveRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class MemberControllerTest {

    @InjectMocks
    MemberController memberController;

    @Mock
    MemberService memberService;

    @Autowired
    private TestRestTemplate restTemplate;

    ObjectMapper objectMapper = new ObjectMapper();

    MockMvc mockMvc;

    private final String baseUrl = "/member";

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
    }

    @Test
    void 회원가입_폼() throws Exception {
        mockMvc.perform(get(baseUrl + "/"))
                .andExpect(status().isOk());
    }

    @Test
    void 회원가입() throws Exception {
        // given\
        MockMultipartFile multipartFile = new MockMultipartFile("image", "test.png", MediaType.IMAGE_PNG_VALUE, "test".getBytes());
//        MemberSaveRequestDto requestDto = new MemberSaveRequestDto("username", 1, "password", "password", "email", null, Role.USER);

//        given(memberService.sign(requestDto)).willReturn(1L);

        // when, then
        mockMvc.perform(multipart(baseUrl + '/')
                        .file(multipartFile)
//                        .content(objectMapper.writeValueAsString(requestDto)))
                        .param("username", "username")
                        .param("username_validation", "1")
                        .param("password", "password123!")
                        .param("password_check", "password123!")
                        .param("email", "email@naver.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

//        verify(memberService).sign(requestDto);
    }

    @Test
    void 회원가입_검증에러_발생() throws Exception {
        // given
        mockMvc.perform(post(baseUrl + "/")
                        .param("username", "username")
                        .param("username_validation", "0")
                        .param("password", "password")
                        .param("password_check", "password1")
                        .param("email", "email"))
                .andExpect(status().isOk())
                .andExpect(view().name("members/sign"))
                .andDo(print());

        // when

        // then
    }
}