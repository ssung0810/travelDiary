package com.ssung.travelDiary.web.login;

import com.ssung.travelDiary.domain.members.Member;
import com.ssung.travelDiary.service.members.MemberService;
import com.ssung.travelDiary.web.SessionConst;
import com.ssung.travelDiary.web.login.dto.MemberLoginRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
@Controller
public class LoginController {

    private final MemberService memberService;

    @GetMapping("/")
    public String loginForm(Model model) {
        model.addAttribute("login", new MemberLoginRequestDto());

        return "members/login";
    }


    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("login") MemberLoginRequestDto login,
                        BindingResult bindingResult,
                        HttpSession httpSession) {

        if(bindingResult.hasErrors()) {
            log.info("bindingResult = {}", bindingResult);
            return "members/login";
        }

        Member member = memberService.memberLogin(login.getUsername(), login.getPassword());
        if (member == null) {
            bindingResult.reject("loginFail", "* 아이디 및 비밀번호가 잘못되었습니다.");
            return "members/login";
        }

        httpSession.setAttribute(SessionConst.LOGIN_MEMBER, member.getId());
        httpSession.setAttribute("username", member.getUsername());
        httpSession.setAttribute("imageName", member.getImageFile().getStoredFileName());

        return "redirect:/board/privateBoardList";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        if (httpSession != null) {
            httpSession.invalidate();
        }

        return "redirect:/";
    }
}
