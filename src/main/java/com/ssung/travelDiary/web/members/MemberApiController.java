package com.ssung.travelDiary.web.members;

import com.ssung.travelDiary.domain.members.Member;
import com.ssung.travelDiary.service.members.MemberService;
import com.ssung.travelDiary.web.members.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("member/api")
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/duplicate")
    public boolean duplicateUsername(@RequestBody String username) {
        MemberResponseDto member = memberService.findByUsername(username);

        if (member.getId() != null) return false;

        return true;
    }
}
