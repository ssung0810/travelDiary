package com.ssung.travelDiary.web.members;

import com.ssung.travelDiary.domain.members.Member;
import com.ssung.travelDiary.domain.members.MemberRepository;
import com.ssung.travelDiary.domain.members.Role;
import com.ssung.travelDiary.dto.members.MemberSaveRequestDto;
import com.ssung.travelDiary.service.members.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;

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

    @GetMapping("/")
    public String login() {
        return "members/login";
    }


    @PostMapping("/login")
    public String loginOk(@RequestParam String username,
                          @RequestParam String password,
                          HttpSession httpSession,
                          Model model) {

        log.info("===1==== username = {}, password = {}", username, password);
        String loginCheck = memberService.memberLogin(username, password);

        if(!password.equals(loginCheck)) {
            model.addAttribute("error", loginCheck);
            return "members/login";
        }

        return "/board/privateBoard";
    }
}
