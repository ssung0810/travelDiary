package com.ssung.travelDiary.service.members;

import com.ssung.travelDiary.domain.members.Member;
import com.ssung.travelDiary.domain.members.MemberRepository;
import com.ssung.travelDiary.domain.members.Role;
import com.ssung.travelDiary.dto.member.MemberResponseDto;
import com.ssung.travelDiary.dto.member.MemberSaveRequestDto;
import com.ssung.travelDiary.dto.member.MemberUpdateRequestDto;
import com.ssung.travelDiary.exception.member.MemberEmailAlreadyExistException;
import com.ssung.travelDiary.exception.member.MemberNotFoundException;
import com.ssung.travelDiary.exception.member.MemberUsernameAlreadyExistException;
import com.ssung.travelDiary.service.file.FileUploadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MemberServiceUnitTest {

    @InjectMocks MemberService memberService;
    @Mock MemberRepository memberRepository;
    @Mock FileUploadService fileUploadService;
    @Mock PasswordEncoder passwordEncoder;

    MockMvc mockMvc;

    @BeforeEach
    void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(memberService).build();
    }

    @Test
    void 회원가입_성공() throws Exception {
        // given
        MockMultipartFile multipartFile = new MockMultipartFile("image", "originalImage", MediaType.IMAGE_PNG_VALUE, "image".getBytes());
        MemberSaveRequestDto saveRequestDto = new MemberSaveRequestDto("username", "password", "email", multipartFile, null);

        // when
        memberService.save(saveRequestDto);

        // then
        verify(memberRepository).existsByEmail(saveRequestDto.getEmail());
        verify(memberRepository).existsByUsername(saveRequestDto.getUsername());
        verify(fileUploadService).storeFile(any(MultipartFile.class));
        verify(passwordEncoder).encode(saveRequestDto.getPassword());
        verify(memberRepository).save(any());
    }

    @Test
    void 회원가입_별명_중복() throws Exception {
        // given
        MockMultipartFile multipartFile = new MockMultipartFile("image", "originalImage", MediaType.IMAGE_PNG_VALUE, "image".getBytes());
        MemberSaveRequestDto saveRequestDto = new MemberSaveRequestDto("username", "password", "email", multipartFile, null);

        given(memberRepository.existsByUsername(anyString())).willThrow(MemberUsernameAlreadyExistException.class);

        // when, then
        assertThatThrownBy(() -> memberService.save(saveRequestDto)).isInstanceOf(MemberUsernameAlreadyExistException.class);
    }

    @Test
    void 회원가입_이메일_중복() throws Exception {
        // given
        MockMultipartFile multipartFile = new MockMultipartFile("image", "originalImage", MediaType.IMAGE_PNG_VALUE, "image".getBytes());
        MemberSaveRequestDto saveRequestDto = new MemberSaveRequestDto("username", "password", "email", multipartFile, null);

        given(memberRepository.existsByEmail(anyString())).willThrow(MemberEmailAlreadyExistException.class);

        // when, then
        assertThatThrownBy(() -> memberService.save(saveRequestDto)).isInstanceOf(MemberEmailAlreadyExistException.class);
    }

    @Test
    void 유저_단일_검색_성공() throws Exception {
        // given
        Member member = createMember();
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));

        // when
        MemberResponseDto dto = memberService.findOne(anyLong());

        // then
        assertThat(dto.getUsername()).isEqualTo(member.getUsername());
    }

    @Test
    void 유저_단일_검색_실패() throws Exception {
        // given
        given(memberRepository.findById(anyLong())).willThrow(MemberNotFoundException.class);

        // when, then
        assertThatThrownBy(() -> memberService.findOne(anyLong())).isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    void 별명으로_검색_성공() throws Exception {
        // give
        Member member = createMember();
        given(memberRepository.findByUsername(anyString())).willReturn(Optional.of(member));

        // when
        MemberResponseDto dto = memberService.findByUsername("username");

        // then
        assertThat(dto.getUsername()).isEqualTo(member.getUsername());
    }

    @Test
    void 별명으로_검색_실패() throws Exception {
        // give
        Member member = createMember();
        given(memberRepository.findByUsername(anyString())).willThrow(MemberNotFoundException.class);

        // when, then
        assertThatThrownBy(() -> memberService.findByUsername("username")).isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    void 회원정보_수정_성공() throws Exception {
        // given
        Member member = createMember();
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
        given(memberRepository.existsByEmail(anyString())).willReturn(false);

        MemberUpdateRequestDto dto = new MemberUpdateRequestDto();
        dto.setUsername("username");
        dto.setPassword("password");
        dto.setEmail("email2");

        // when
        MemberResponseDto resultDto = memberService.update(dto, anyLong());

        // then
        assertThat(resultDto.getEmail()).isEqualTo("email2");
        verify(passwordEncoder).encode(anyString());
        verify(fileUploadService).storeFile(any());
    }

    @Test
    void 회원정보_수정_수정할려는_회원_없는경우() throws Exception {
        // given
        given(memberRepository.findById(anyLong())).willThrow(MemberNotFoundException.class);

        MemberUpdateRequestDto dto = new MemberUpdateRequestDto();
        dto.setUsername("username");
        dto.setPassword("password");
        dto.setEmail("email2");

        // when, then
        assertThatThrownBy(() -> memberService.update(dto, anyLong())).isInstanceOf(MemberNotFoundException.class);
        verify(passwordEncoder, never()).encode(anyString());
        verify(fileUploadService, never()).storeFile(any());
    }

    @Test
    void 회원정보_수정_별명중복() throws Exception {
        // given
        Member member = createMember();
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
        given(memberRepository.existsByUsername(anyString())).willThrow(MemberUsernameAlreadyExistException.class);

        MemberUpdateRequestDto dto = new MemberUpdateRequestDto();
        dto.setUsername("username2");
        dto.setPassword("password");
        dto.setEmail("email2");

        // when, then
        assertThatThrownBy(() -> memberService.update(dto, anyLong())).isInstanceOf(MemberUsernameAlreadyExistException.class);
        verify(passwordEncoder, never()).encode(anyString());
        verify(fileUploadService, never()).storeFile(any());
    }

    @Test
    void 회원정보_수정_이메일중복() throws Exception {
        // given
        Member member = createMember();
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
        given(memberRepository.existsByUsername(anyString())).willThrow(MemberEmailAlreadyExistException.class);

        MemberUpdateRequestDto dto = new MemberUpdateRequestDto();
        dto.setUsername("username2");
        dto.setPassword("password");
        dto.setEmail("email2");

        // when, then
        assertThatThrownBy(() -> memberService.update(dto, anyLong())).isInstanceOf(MemberEmailAlreadyExistException.class);
        verify(passwordEncoder, never()).encode(anyString());
        verify(fileUploadService, never()).storeFile(any());
    }

    @Test
    void 아이디_비밀번호_확인_성공() throws Exception {
        // given
        Member member = createMember();
        given(memberRepository.findByUsername(anyString())).willReturn(Optional.of(member));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);

        // when
        MemberResponseDto dto = memberService.loginValidation("username", "password");

        // then
        assertThat(dto.getUsername()).isEqualTo("username");
        verify(passwordEncoder).matches(anyString(), anyString());
    }

    @Test
    void 아이디_비밀번호_확인_별명없음() throws Exception {
        // given
        given(memberRepository.findByUsername(anyString())).willReturn(Optional.ofNullable(null));

        // when
        MemberResponseDto dto = memberService.loginValidation("username", "password");

        // then
        assertThat(dto).isNull();
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    @Test
    void 아이디_비밀번호_확인_비밀번호_다름() throws Exception {
        // given
        Member member = createMember();
        given(memberRepository.findByUsername(anyString())).willReturn(Optional.of(member));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(false);

        // when
        MemberResponseDto dto = memberService.loginValidation("username", "password");

        // then
        assertThat(dto).isNull();
        verify(passwordEncoder).matches(anyString(), anyString());
    }

    @Test
    void 공유폴더_등록시_추가할_회원조회_결과O() throws Exception {
        // given
        Member member = createMember();
        given(memberRepository.findByMemberIdAndMoreType(anyLong(), anyString())).willReturn(List.of(member));

        // when
        List<MemberResponseDto> dtoList = memberService.addMemberSearch(anyLong(), anyString());

        // then
        assertThat(dtoList.size()).isEqualTo(1);
        assertThat(dtoList.get(0).getUsername()).isEqualTo("username");
    }

    @Test
    void 공유폴더_등록시_추가할_회원조회_결과X() throws Exception {
        // given
        Member member = createMember();
        given(memberRepository.findByMemberIdAndMoreType(anyLong(), anyString())).willReturn(new ArrayList<>());

        // when
        List<MemberResponseDto> dtoList = memberService.addMemberSearch(anyLong(), anyString());

        // then
        assertThat(dtoList.size()).isEqualTo(0);
    }

    private Member createMember() {
        return Member.builder()
                .username("username")
                .password("password")
                .email("email")
                .role(Role.USER)
                .build();
    }
}
