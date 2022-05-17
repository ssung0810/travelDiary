package com.ssung.travelDiary.web.members;

import com.ssung.travelDiary.domain.members.Member;
import com.ssung.travelDiary.service.members.MemberService;
import com.ssung.travelDiary.web.SessionConst;
import com.ssung.travelDiary.dto.member.MemberResponseDto;
import com.ssung.travelDiary.dto.member.MemberSaveRequestDto;
import com.ssung.travelDiary.dto.member.MemberUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    // 회원가입 화면
    @GetMapping
    public String signForm(Model model) {
        model.addAttribute("member", new MemberSaveRequestDto());
        return "members/sign";
    }

    // 회원가입 후 메인화면으로 이동
    @PostMapping
    public String sign(@Valid @ModelAttribute("member") MemberSaveRequestDto dto,
                       BindingResult bindingResult) throws Exception {

        if(passwordValidation(dto.getPassword(), dto.getPassword_check())) {
            bindingResult.rejectValue("password_check", "passwordValidation", "");
        }
        if(dto.getUsername_validation() == 0) {
            bindingResult.reject("usernameValidation", null);
        }

        if (bindingResult.hasErrors()) {
            return "members/sign";
        }

        memberService.sign(dto);

        return "redirect:/login";
    }

    @GetMapping("/profileForm")
    public String profileForm(@SessionAttribute(name = SessionConst.USER_ID) Long userId,
                              Model model) {

        MemberResponseDto member = memberService.findOne(userId);

        model.addAttribute("member", member);

        return "/members/profileForm";
    }

    @GetMapping("/profile")
    public String profileUpdateForm(@SessionAttribute(name = SessionConst.USER_ID) Long userId,
                                    Model model) {

        MemberUpdateRequestDto member = new MemberUpdateRequestDto(memberService.findOne(userId));
        model.addAttribute("member", member);

        return "/members/profileUpdateForm";
    }

    @PostMapping("/profile")
    public String profileUpdate(@Valid @ModelAttribute("member") MemberUpdateRequestDto dto,
                                BindingResult bindingResult,
                                HttpSession httpSession) throws IOException {

        if(passwordValidation(dto.getPassword(), dto.getPassword_check())) {
            bindingResult.rejectValue("password_check", "passwordValidation", "");
        }

        if (bindingResult.hasErrors()) {
            return "/members/profileUpdateForm";
        }

        Member member = memberService.update(dto);

        if(!dto.getImage().isEmpty())
            httpSession.setAttribute("imageName", member.getImage().getStoredFileName());

        return "/members/profileForm";
    }

    private boolean passwordValidation(String password, String password_check) {
        return !password.equals(password_check);
    }
}
