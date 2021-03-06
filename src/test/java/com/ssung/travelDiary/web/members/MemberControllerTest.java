package com.ssung.travelDiary.web.members;

import com.ssung.travelDiary.domain.members.Member;
import com.ssung.travelDiary.exception.member.MemberEmailAlreadyExistException;
import com.ssung.travelDiary.exception.member.MemberUsernameAlreadyExistException;
import com.ssung.travelDiary.service.members.MemberService;
import com.ssung.travelDiary.constancy.SessionConst;
import com.ssung.travelDiary.dto.member.MemberResponseDto;
import com.ssung.travelDiary.dto.member.MemberSaveRequestDto;
import com.ssung.travelDiary.dto.member.MemberUpdateRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
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

    MockMvc mockMvc;

    private final String baseUrl = "/member";

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
    }

    @Test
    void 회원가입_폼() throws Exception {
        mockMvc.perform(get(baseUrl))
                .andExpect(status().isOk());
    }

    @Test
    void 회원가입_성공() throws Exception {
        // when, then
        mockMvc.perform(multipart(baseUrl)
                        .file("image", new byte[]{})
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "username")
                        .param("username_validation", "1")
                        .param("password", "password123!")
                        .param("password_check", "password123!")
                        .param("email", "email@naver.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        verify(memberService).save(any(MemberSaveRequestDto.class));
    }

    @Test
    void 회원가입_검증에러_발생() throws Exception {
        mockMvc.perform(post(baseUrl)
                        .param("username", "username")
                        .param("username_validation", "0")
                        .param("password", "password")
                        .param("password_check", "password1")
                        .param("email", "email"))
                .andExpect(status().isOk())
                .andExpect(view().name("members/sign"));

        verify(memberService, never()).save(any(MemberSaveRequestDto.class));
    }

    @Test
    void 회원가입_이메일_중복에러_발생() throws Exception {
        given(memberService.save(any(MemberSaveRequestDto.class))).willThrow(MemberEmailAlreadyExistException.class);

        mockMvc.perform(post(baseUrl)
                        .param("username", "username")
                        .param("username_validation", "1")
                        .param("password", "password123!")
                        .param("password_check", "password123!")
                        .param("email", "email@naver.com"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("members/sign"));

        verify(memberService).save(any(MemberSaveRequestDto.class));
    }
    
    @Test
    void 프로필_폼() throws Exception {
        mockMvc.perform(get(baseUrl + "/profileForm")
                        .sessionAttr(SessionConst.USER_ID, 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("members/profileForm"));

        verify(memberService).findOne(1L);
    }

    @Test
    void 프로필수정_폼() throws Exception {
        Member member = Member.builder().username("username").build();
        given(memberService.findOne(1L)).willReturn(new MemberResponseDto(member));

        mockMvc.perform(get(baseUrl + "/profile")
                        .sessionAttr(SessionConst.USER_ID, 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("members/profileUpdateForm"));

        verify(memberService).findOne(1L);
    }

    @Test
    void 프로필수정() throws Exception {
        // given
        given(memberService.update(any(MemberUpdateRequestDto.class), anyLong())).willReturn(new MemberResponseDto());

        // when, then
        MockMultipartHttpServletRequestBuilder builder = multipart(baseUrl + "/profile");
        builder.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setMethod("PUT");
                return request;
            }
        });

        mockMvc.perform(builder
                        .file("image", new byte[]{})
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "username")
                        .param("password", "password123!")
                        .param("password_check", "password123!")
                        .param("email", "email@naver.com")
                        .sessionAttr(SessionConst.USER_ID, 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("members/profileForm"));

        verify(memberService).update(any(MemberUpdateRequestDto.class), anyLong());
    }

    @Test
    void 프로필수정_검증에러() throws Exception {
        // when, then
        mockMvc.perform(put(baseUrl + "/profile")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "username")
                        .param("password", "password")
                        .param("password_check", "password1")
                        .param("email", "email"))
                .andExpect(status().isOk())
                .andExpect(view().name("members/profileUpdateForm"));

        verify(memberService, never()).update(any(MemberUpdateRequestDto.class), anyLong());
    }

    @Test
    void 프로필수정_이메일_중복_예외처리() throws Exception {
        // given
        given(memberService.update(any(MemberUpdateRequestDto.class), anyLong())).willThrow(MemberEmailAlreadyExistException.class);

        // when, then
        mockMvc.perform(put(baseUrl + "/profile")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "username")
                        .param("password", "password123!")
                        .param("password_check", "password123!")
                        .param("email", "email@naver.com")
                        .sessionAttr(SessionConst.USER_ID, 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("members/profileUpdateForm"));

        verify(memberService).update(any(MemberUpdateRequestDto.class), anyLong());
    }

    @Test
    void 프로필수정_별명_중복_예외처리() throws Exception {
        // given
        given(memberService.update(any(MemberUpdateRequestDto.class), anyLong())).willThrow(MemberUsernameAlreadyExistException.class);

        // when, then
        mockMvc.perform(put(baseUrl + "/profile")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "username")
                        .param("password", "password123!")
                        .param("password_check", "password123!")
                        .param("email", "email@naver.com")
                        .sessionAttr(SessionConst.USER_ID, 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("members/profileUpdateForm"));

        verify(memberService).update(any(MemberUpdateRequestDto.class), anyLong());
    }
}