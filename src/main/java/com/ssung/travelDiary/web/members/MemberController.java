package com.ssung.travelDiary.web.members;

import com.ssung.travelDiary.domain.members.MemberRepository;
import com.ssung.travelDiary.file.FileDto;
import com.ssung.travelDiary.file.FileHandler;
import com.ssung.travelDiary.domain.members.Member;
import com.ssung.travelDiary.domain.members.Role;
import com.ssung.travelDiary.web.members.dto.MemberSaveRequestDto;
import com.ssung.travelDiary.service.members.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final FileHandler fileHandler;
    private final MemberRepository memberRepository;

    // 회원가입 화면
    @GetMapping("/sign")
    public String signForm(Model model) {
        model.addAttribute("sign", new MemberSaveRequestDto());
        return "members/sign";
    }

    @ResponseBody
    @GetMapping("/imageTest")
    public ResponseEntity<Resource> image(HttpSession httpSession) {
        String username = (String) httpSession.getAttribute("username");
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("없는 별명"));

        String path = new File("").getAbsolutePath() + "\\";
        String storedPath = member.getStored_file_path();

        Resource resource = new FileSystemResource(path+storedPath);

        HttpHeaders httpHeaders = new HttpHeaders();

        try {
            Path filePath = Paths.get(path + storedPath);
            httpHeaders.add("Content-Type", Files.probeContentType(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<Resource>(resource, httpHeaders, HttpStatus.OK);
    }

    // 회원가입 후 메인화면으로 이동
    @PostMapping("/sign")
    public String sign(@Valid @ModelAttribute("sign") MemberSaveRequestDto dto,
                       BindingResult bindingResult,
                       MultipartHttpServletRequest mRequest) throws Exception {

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "members/sign";
        }

        List<FileDto> fileDto = fileHandler.parseFileInfo(mRequest);
        FileDto image;
        if(fileDto.isEmpty()) {
            image = new FileDto("", "", 0L);
        } else {
            image = fileHandler.parseFileInfo(mRequest).get(0);
        }

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
