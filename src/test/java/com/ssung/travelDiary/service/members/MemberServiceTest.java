package com.ssung.travelDiary.service.members;

import com.ssung.travelDiary.domain.members.Member;
import com.ssung.travelDiary.domain.members.Role;
import com.ssung.travelDiary.web.members.dto.MemberSaveRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Value("${file.dir}")
    private String fileDir;

    @BeforeEach
    void clean() {

    }

    @Test
    void 프로필_이미지없는_회원가입() throws Exception {
        // given
        MockMultipartFile multipartFile = new MockMultipartFile("null", new byte[]{});
        MemberSaveRequestDto dto = new MemberSaveRequestDto("username", "password", "email", null, Role.USER);

        // when
        Long findId = memberService.sign(dto);
        Member findMember = memberService.findOne(findId);

        // then
        assertThat(findMember.getId()).isEqualTo(findId);
    }

    @Test
    void 프로필_이미지_저장여부() throws Exception {
        // given
        MockMultipartFile multipartFile = new MockMultipartFile("image", "testImage", "image/png", new FileInputStream(fileDir));
        MemberSaveRequestDto dto = new MemberSaveRequestDto("username", "password", "email", null, Role.USER);

        // when
        Long findId = memberService.sign(dto);
        Member findMember = memberService.findOne(findId);

        // then
        assertThat(findMember.getImageFile().getOriginalFileName()).isEqualTo(multipartFile.getOriginalFilename());
    }
//
//    @Test
//    public void 회원정보_변경() throws Exception {
//        // given
//        Member member = Member.builder()
//                .username("username")
//                .password("password")
//                .email("email")
//                .role(Role.USER)
//                .build();
//
//        Long memberId = memberService.sign(member);
//        Member findMember = memberService.findOne(memberId);
//
//        MemberUpdateRequestDto requestDto = new MemberUpdateRequestDto();
//        requestDto.setEmail("email2");
//
//        // when
//        Member updateMember = memberService.update(memberId, requestDto);
//
//        // then
//        assertThat(updateMember.getEmail()).isEqualTo("email2");
//    }
//
//    @Test
//    public void 로그인_성공() throws Exception {
//        // given
//        Member member = Member.builder()
//                .username("username")
//                .password("password")
//                .email("email")
//                .role(Role.USER)
//                .build();
//
//        Long findId = memberService.sign(member);
//
//        String username = "username";
//        String password = "password";
//
//        // when
//        Member findMember = memberService.memberLogin(username, password);
//
//        // then
//        assertThat(member).isEqualTo(findMember);
//    }
//
//    @Test
//    public void 로그인_실패_비밀번호_오류() throws Exception {
//        // given
//        Member member = Member.builder()
//                .username("username")
//                .password("password")
//                .email("email")
//                .role(Role.USER)
//                .build();
//
//        Long findId = memberService.sign(member);
//
//        String username = "username";
//        String password = "password2";
//
//        // when
//        Member findMember = memberService.memberLogin(username, password);
//
//        // then
//        assertThat(findMember).isEqualTo(null);
//    }
//
//    @Test
//    public void 로그인_실패_아이디_오류() throws Exception {
//        // given
//        Member member = Member.builder()
//                .username("username")
//                .password("password")
//                .email("email")
//                .role(Role.USER)
//                .build();
//
//        Long findId = memberService.sign(member);
//
//        String username = "nickname2";
//        String password = "password";
//
//        // when
//        Member findMember = memberService.memberLogin(username, password);
////        assertThrows(IllegalArgumentException.class, () -> memberService.memberLogin(username, password));
//
//        // then
//        assertThat(findMember).isEqualTo(null);
////        fail();
//    }
//
//    private MemberSaveRequestDto createSaveDto() {
//        return new MemberSaveRequestDto("username", "password", "email", Role.USER);
//    }
//
//    private FileDto createFileDto() {
//        return new FileDto("", "", 0L);
//    }
}