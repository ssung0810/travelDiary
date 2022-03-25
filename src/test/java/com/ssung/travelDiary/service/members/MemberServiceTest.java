package com.ssung.travelDiary.service.members;

import com.ssung.travelDiary.domain.members.Member;
import com.ssung.travelDiary.domain.members.Role;
import com.ssung.travelDiary.file.FileDto;
import com.ssung.travelDiary.web.members.dto.MemberSaveRequestDto;
import com.ssung.travelDiary.web.members.dto.MemberUpdateRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @BeforeEach
    void clean() {

    }

//    @Test
//    public void 회원가입() throws Exception {
//        // given
//        MemberSaveRequestDto dto = createSaveDto();
//        FileDto fileDto = createFileDto();
//
//        // when
//        Long findId = memberService.sign(dto, fileDto);
//        Member findMember = memberService.findOne(findId);
//
//        // then
//        assertThat(findMember.getId()).isEqualTo(findId);
//    }
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