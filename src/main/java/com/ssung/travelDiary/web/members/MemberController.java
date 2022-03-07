package com.ssung.travelDiary.web.members;

import com.ssung.travelDiary.domain.members.Member;
import com.ssung.travelDiary.domain.members.Role;
import com.ssung.travelDiary.web.members.dto.MemberSaveRequestDto;
import com.ssung.travelDiary.service.members.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    // 회원가입 화면
    @GetMapping("/sign")
    public String signForm(Model model) {
        model.addAttribute("sign", new MemberSaveRequestDto());
        return "members/sign";
    }

    // 회원가입 후 메인화면으로 이동
    @PostMapping("/sign")
    public String sign(@Valid @ModelAttribute("sign") MemberSaveRequestDto dto,
                       BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "members/sign";
        }

        Member member = Member.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .image(dto.getImage())
                .role(Role.GUEST)
                .build();
        memberService.sign(member);

        return "redirect:/";
    }




}
