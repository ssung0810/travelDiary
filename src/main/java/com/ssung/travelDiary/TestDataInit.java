package com.ssung.travelDiary;

import com.ssung.travelDiary.domain.members.Member;
import com.ssung.travelDiary.domain.members.Role;
import com.ssung.travelDiary.service.members.MemberService;
import com.ssung.travelDiary.web.members.dto.MemberSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberService memberService;

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        Member member = Member.builder()
                .username("test")
                .password("test!")
                .email("qqq@www.com")
                .image("")
                .role(Role.USER)
                .build();

        memberService.sign(member);
    }
}
