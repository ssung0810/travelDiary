package com.ssung.travelDiary.web.login;

import com.ssung.travelDiary.web.login.dto.MemberLoginRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller
public class LoginController {

    @GetMapping("/")
    public String loginForm(Model model) {
        model.addAttribute("login", new MemberLoginRequestDto());

        return "members/login";
    }


    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("login") MemberLoginRequestDto login,
                        BindingResult bindingResult,
                        HttpSession httpSession,
                        Model model) {



        // 검증 로직
        if (!StringUtils.hasText(login.getUsername())) {
            bindingResult.addError(new FieldError("login", "username", "아이디를 입력하세요."));
//            bindingResult.addError(new FieldError("login", "username", login.getUsername(), false, null, null, "아이디를 입력하세요."));
        }
        if (!StringUtils.hasText(login.getPassword())) {
            bindingResult.addError(new FieldError("login", "password", "비밀번호를 입력하세요."));
//            bindingResult.addError(new FieldError("login", "password", login.getPassword(), false, null, null, "비밀번호를 입력하세요."));
        }

        log.info("loginObject = {}", login);

        if(bindingResult.hasErrors()) {
            log.info("bindingResult = {}", bindingResult);
            return "members/login";
        }

//        return "redirect:/";
//        return "members/login";

//        String loginCheck = memberService.memberLogin(loginDto.getUsername(), loginDto.getPassword());

//        if(!loginDto.getPassword().equals(loginCheck)) {
//            model.addAttribute("error", loginCheck);
//            return "members/login";
//        }

        return "redirect:/board/privateBoard";
    }
}
