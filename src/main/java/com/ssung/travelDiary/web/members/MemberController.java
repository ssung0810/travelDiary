package com.ssung.travelDiary.web.members;

import com.ssung.travelDiary.domain.members.Member;
import com.ssung.travelDiary.domain.members.MemberRepository;
import com.ssung.travelDiary.domain.members.Role;
import com.ssung.travelDiary.dto.members.MemberSaveRequestDto;
import com.ssung.travelDiary.service.members.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    // 회원가입 화면
    @GetMapping("/sign")
    public String Sign() {
        return "members/sign";
    }

    // 회원가입 후 메인화면으로 이동
    @PostMapping("/member/save")
    public String sign(@Valid MemberSaveRequestDto dto,
                       Role role) {

        memberService.sign(dto);

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "members/login";
    }

    @PostMapping("/api/login")
    public String loginOk(@RequestParam String nickname,
                          @RequestParam String password,
                          HttpSession httpSession,
                          Model model) {

        String loginCheck = memberService.loginCheck(nickname, password);

        if(!password.equals(loginCheck)) {
            model.addAttribute("error", loginCheck);
            return "members/login";
        }

        return "redirect:/";
    }
}
