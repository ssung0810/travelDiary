package com.ssung.travelDiary.service.members;

import com.ssung.travelDiary.domain.members.Member;
import com.ssung.travelDiary.domain.members.MemberRepository;
import com.ssung.travelDiary.domain.members.Role;
import com.ssung.travelDiary.web.members.dto.MemberResponseDto;
import com.ssung.travelDiary.web.members.dto.MemberSaveRequestDto;
import com.ssung.travelDiary.web.members.dto.MemberUpdateRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired private MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void clean() {

    }

    @Test
    void 프로필_이미지없는_회원가입() throws Exception {
        // given
        MockMultipartFile multipartFile = new MockMultipartFile("null", new byte[]{});
        MemberSaveRequestDto dto = new MemberSaveRequestDto("username", "password", "email", multipartFile, Role.USER);
        Long findId = memberService.sign(dto);

        // when
        MemberResponseDto findMember = memberService.findOne(findId);

        // then
        assertThat(findMember.getId()).isEqualTo(findId);
    }

    @Test
    void 프로필_이미지_저장여부() throws Exception {
        // given
        MockMultipartFile multipartFile = new MockMultipartFile("image", "test.png", MediaType.IMAGE_PNG_VALUE, "test".getBytes());
        MemberSaveRequestDto dto = new MemberSaveRequestDto("username", "password", "email", multipartFile, Role.USER);
        Long findId = memberService.sign(dto);

        // when
        MemberResponseDto findMember = memberService.findOne(findId);

        // then
        assertThat(findMember.getImage().getOriginalFileName()).isEqualTo(multipartFile.getOriginalFilename());
    }

    @Test
    void 별명_중복확인() throws Exception {
        // given
        MockMultipartFile multipartFile = new MockMultipartFile("null", new byte[]{});
        MemberSaveRequestDto dto = new MemberSaveRequestDto("username", "password", "email", multipartFile, Role.USER);

        Long findId = memberService.sign(dto);

        // when
        String validationUsername = "validation";
        MemberResponseDto result = memberService.findByUsername(validationUsername);

        // then
        assertThat(result.getId()).isEqualTo(null);
    }

    @Test
    void 유저검색() throws Exception {
        // given
        MockMultipartFile multipartFile = new MockMultipartFile("null", new byte[]{});
        MemberSaveRequestDto dto = new MemberSaveRequestDto("username", "password", "email", multipartFile, Role.USER);

        Long findId = memberService.sign(dto);

        // when
        MemberResponseDto findMember = memberService.findOne(findId);

        // then
        assertThat(findMember.getUsername()).isEqualTo(dto.getUsername());
    }

    @Test
    void 로그인_아이디_오류() throws Exception {
        // given
        MockMultipartFile multipartFile = new MockMultipartFile("null", new byte[]{});
        MemberSaveRequestDto dto = new MemberSaveRequestDto("username", "password", "email", multipartFile, Role.USER);

        Long findId = memberService.sign(dto);

        String username = "username2";
        String password = "password";

        // when
        Member member = memberService.loginValidation(username, password);

        // then
        assertThat(member).isEqualTo(null);
    }

    @Test
    void 로그인_비밀번호_오류() throws Exception {
        // given
        MockMultipartFile multipartFile = new MockMultipartFile("null", new byte[]{});
        MemberSaveRequestDto dto = new MemberSaveRequestDto("username", "password", "email", multipartFile, Role.USER);

        Long findId = memberService.sign(dto);

        String username = "username";
        String password = "password2";

        // when
        Member member = memberService.loginValidation(username, password);

        // then
        assertThat(member).isEqualTo(null);
    }

    @Test
    void 로그인_성공() throws Exception {
        // given
        MockMultipartFile multipartFile = new MockMultipartFile("null", new byte[]{});
        MemberSaveRequestDto dto = new MemberSaveRequestDto("username", "password", "email", multipartFile, Role.USER);

        Long findId = memberService.sign(dto);

        String username = "username";
        String password = "password";

        // when
        Member member = memberService.loginValidation(username, password);

        // then
        assertThat(member).isNotEqualTo(null);
    }

    @Test
    public void 회원정보_변경() throws Exception {
        // given
        MockMultipartFile multipartFile = new MockMultipartFile("image", "test.png", MediaType.IMAGE_PNG_VALUE, "test".getBytes());
        MemberSaveRequestDto dto = new MemberSaveRequestDto("username", "password", "email", multipartFile, Role.USER);

        Long findId = memberService.sign(dto);

        String updateEmail = "email2";
        MockMultipartFile updateMultipartFile = new MockMultipartFile("image", "test2.png", MediaType.IMAGE_PNG_VALUE, "test".getBytes());

        MemberUpdateRequestDto requestDto = new MemberUpdateRequestDto();
        requestDto.setUsername("username");
        requestDto.setPassword("password");
        requestDto.setEmail(updateEmail);
        requestDto.setImage(updateMultipartFile);

        // when
        Member updateMember = memberService.update(requestDto);

        // then
        assertThat(updateMember.getEmail()).isEqualTo("email2");
        assertThat(updateMember.getImage().getOriginalFileName()).isEqualTo("test2.png");
    }

    @Test
    void 공유폴더_추가할_회원조회() throws Exception {
        // given
        MockMultipartFile multipartFile = new MockMultipartFile("null", new byte[]{});
        MemberSaveRequestDto dto = new MemberSaveRequestDto("username", "password", "email", multipartFile, Role.USER);
        MemberSaveRequestDto dto2 = new MemberSaveRequestDto("username2", "password2", "email2", multipartFile, Role.USER);
        Long myMemberId = memberService.sign(dto);
        memberService.sign(dto2);

        // when
        List<MemberResponseDto> members = memberService.addMemberSearch(myMemberId, "");

        // then
        assertThat(members.size()).isEqualTo(1);
        assertThat(members.get(0).getUsername()).isEqualTo("username2");
    }
}