package com.ssung.travelDiary.web.members;

import com.ssung.travelDiary.file.FileDto;
import com.ssung.travelDiary.file.FileHandler;
import com.ssung.travelDiary.domain.members.Member;
import com.ssung.travelDiary.domain.members.Role;
import com.ssung.travelDiary.web.members.dto.MemberSaveRequestDto;
import com.ssung.travelDiary.service.members.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final FileHandler fileHandler;

    // 회원가입 화면
    @GetMapping("/sign")
    public String signForm(Model model) {
        model.addAttribute("sign", new MemberSaveRequestDto());
        return "members/sign";
    }

    // 회원가입 후 메인화면으로 이동
    @PostMapping("/sign")
    public String sign(@ModelAttribute("sign") MemberSaveRequestDto dto,
                       BindingResult bindingResult,
                       MultipartHttpServletRequest mRequest) throws Exception {

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "members/sign";
        }

        FileDto image = fileHandler.parseFileInfo(mRequest).get(0);

        Member member = Member.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .original_file_name(image.getOriginal_file_name())
                .stored_file_path(image.getStored_file_path())
                .file_size(image.getFile_size())
                .role(Role.USER)
                .build();
        memberService.sign(member);

        return "redirect:/";
    }




}
