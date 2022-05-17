package com.ssung.travelDiary.web.members;

import com.ssung.travelDiary.service.members.MemberService;
import com.ssung.travelDiary.dto.member.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("member/api")
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/duplicate")
    public boolean duplicateUsername(@RequestParam String username) {
        MemberResponseDto member = memberService.findByUsername(username);

        if (member.getId() != null) return false;

        return true;
    }
}
