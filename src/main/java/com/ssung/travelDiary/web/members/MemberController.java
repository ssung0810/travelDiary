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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
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
    private final PasswordEncoder passwordEncoder;

    // 회원가입 화면
    @GetMapping("/sign")
    public String signForm(Model model) {
        model.addAttribute("sign", new MemberSaveRequestDto());
        return "members/sign";
    }

//    @ResponseBody
//    @GetMapping("/imageTest")
//    public ResponseEntity<Resource> image(HttpSession httpSession) {
//        String username = (String) httpSession.getAttribute("username");
//        Member member = memberRepository.findByUsername(username)
//                .orElseThrow(() -> new IllegalArgumentException("없는 별명"));
//
//        String path = new File("").getAbsolutePath() + "\\";
//        String storedPath = member.getStored_file_path();
//
//        Resource resource = new FileSystemResource(path+storedPath);
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//
//        try {
//            Path filePath = Paths.get(path + storedPath);
//            httpHeaders.add("Content-Type", Files.probeContentType(filePath));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return new ResponseEntity<Resource>(resource, httpHeaders, HttpStatus.OK);
//    }

    // 회원가입 후 메인화면으로 이동
    @PostMapping("/sign")
    public String sign(@Valid @ModelAttribute("sign") MemberSaveRequestDto dto,
                       BindingResult bindingResult,
                       HttpSession httpSession) throws Exception {

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "members/sign";
        }

        Member member = createMember(dto);

        memberService.sign(member);

        return "redirect:/";
    }

    private Member createMember(MemberSaveRequestDto dto) throws IOException {
        FileDto image = fileHandler.storeFile(dto.getImage());

        return Member.builder()
                .username(dto.getUsername())
                .password(encodePassword(dto.getPassword()))
                .email(dto.getEmail())
                .imageFile(image)
                .role(Role.USER)
                .build();
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

//    private FileDto getImage(MultipartFile multipartFile) throws Exception {
//        List<FileDto> fileDto = fileHandler.storeFiles(multipartFile);
//        FileDto image;
//        if(fileDto.isEmpty()) {
//            return new FileDto("", "", 0L);
//        } else {
//            return fileHandler.parseFileInfo(mRequest).get(0);
//        }
//    }
}
