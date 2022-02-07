package com.ssung.travelDiary.service.members;

import com.ssung.travelDiary.domain.members.Member;
import com.ssung.travelDiary.domain.members.Role;
import com.ssung.travelDiary.dto.members.MemberUpdateRequestDto;
import org.assertj.core.api.Assertions;
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

    @Test
    public void 회원가입() throws Exception {
        // given
        Member member = Member.builder()
                .nickname("nickname")
                .password("password")
                .email("email")
                .picture("picture")
                .role(Role.GUEST)
                .build();

        // when
        Long findId = memberService.sign(member);
        Member findMember = memberService.findOne(findId);

        // then
        assertThat(findMember.getId()).isEqualTo(findId);
    }

    @Test
    public void 회원정보_변경() throws Exception {
        // given
        Member member = Member.builder()
                .nickname("nickname")
                .password("password")
                .email("email")
                .picture("picture")
                .role(Role.GUEST)
                .build();

        Long memberId = memberService.sign(member);
        Member findMember = memberService.findOne(memberId);

        MemberUpdateRequestDto requestDto = new MemberUpdateRequestDto("nickname2", "password2", "email2", "picture2");
        String updateNickname = "nickname2";

        // when
        Member updateMember = memberService.update(memberId, requestDto);

        // then
        assertThat(updateMember.getEmail()).isEqualTo("email2");
    }
}