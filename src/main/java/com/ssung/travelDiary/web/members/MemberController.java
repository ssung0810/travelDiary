package com.ssung.travelDiary.web.members;

import com.ssung.travelDiary.domain.members.Member;
import com.ssung.travelDiary.domain.members.MemberRepository;
import com.ssung.travelDiary.domain.members.Role;
import com.ssung.travelDiary.dto.members.MemberLoginRequestDto;
import com.ssung.travelDiary.dto.members.MemberSaveRequestDto;
import com.ssung.travelDiary.service.members.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public String login(Model model) {
        model.addAttribute("loginDto", new MemberLoginRequestDto());

        return "members/login";
    }


    @PostMapping("/login")
    public String loginOk(@ModelAttribute MemberLoginRequestDto loginDto,
                          BindingResult bindingResult,
                          HttpSession httpSession,
                          Model model) {

        // 검증 로직
        if (!StringUtils.hasText(loginDto.getUsername())) {
//            bindingResult.rejectValue("username", "login");
            bindingResult.addError(new FieldError("loginDto", "username", "아이디를 입력하세요."));
        }
        if (!StringUtils.hasText(loginDto.getPassword())) {
            bindingResult.addError(new FieldError("loginDto", "password", "비밀번호를 입력하세요."));
//            bindingResult.rejectValue("password", "login");
        }

        if(bindingResult.hasErrors()) {
            log.info("bindingResult = {}", bindingResult);
            return "redirect:/";
        }

//        String loginCheck = memberService.memberLogin(loginDto.getUsername(), loginDto.getPassword());

//        if(!loginDto.getPassword().equals(loginCheck)) {
//            model.addAttribute("error", loginCheck);
//            return "members/login";
//        }

        return "/board/privateBoard";
    }
}
