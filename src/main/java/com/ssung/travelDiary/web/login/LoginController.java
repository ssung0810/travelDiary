package com.ssung.travelDiary.web.login;

import com.ssung.travelDiary.constancy.SessionConst;
import com.ssung.travelDiary.dto.login.MemberLoginRequestDto;
import com.ssung.travelDiary.dto.member.MemberResponseDto;
import com.ssung.travelDiary.service.members.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Enumeration;

@Slf4j
@RequiredArgsConstructor
@Controller
public class LoginController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("login", new MemberLoginRequestDto());

        return "members/login";
    }


    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("login") MemberLoginRequestDto login,
                        BindingResult bindingResult,
                        @RequestParam(name = "redirectURL", defaultValue = "/board/privateBoardList") String redirectURL,
                        HttpSession httpSession) {

        if(bindingResult.hasErrors()) {
            return "members/login";
        }

        MemberResponseDto member = memberService.loginValidation(login.getUsername(), login.getPassword());
        if (member == null) {
            bindingResult.reject("loginFail", "* 아이디 및 비밀번호가 잘못되었습니다.");
            return "members/login";
        }

        httpSession.setAttribute(SessionConst.USER_ID, member.getId());
        httpSession.setAttribute(SessionConst.USERNAME, member.getUsername());

        if(member.getImage() == null)
            httpSession.setAttribute(SessionConst.USER_IMAGE, null);
        else
            httpSession.setAttribute(SessionConst.USER_IMAGE, member.getImage().getStoredFileName());

        return "redirect:" + redirectURL;
    }

    @PostMapping("/logout")
    public String logout(HttpSession httpSession) {
        if (httpSession != null) {
            httpSession.invalidate();
        }

        return "redirect:/";
    }
}
