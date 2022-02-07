package com.ssung.travelDiary.web.members;

import com.ssung.travelDiary.domain.members.Member;
import com.ssung.travelDiary.domain.members.Role;
import com.ssung.travelDiary.dto.members.MemberSaveRequestDto;
import com.ssung.travelDiary.service.members.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/sign")
    public String Sign() {
        return "members/sign";
    }

    @PostMapping("/member/save")
    public String sign(@Valid MemberSaveRequestDto dto,
                       Role role) {

        Member member = Member.builder()
                .nickname(dto.getNickname())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .picture(dto.getPicture())
                .role(role.GUEST)
                .build();

        memberService.sign(member);

        return "redirect:/";
    }
}
