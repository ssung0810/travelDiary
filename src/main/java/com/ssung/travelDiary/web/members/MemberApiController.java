package com.ssung.travelDiary.web.members;

import com.ssung.travelDiary.domain.members.Member;
import com.ssung.travelDiary.service.members.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("member/api")
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/duplicate")
    public ModelAndView duplicateUsername(@RequestBody String username) {
        log.info("username = {}", username);

        Member member = memberService.findByUsername(username);

        log.info("member = {}", member);

        ModelAndView vie = new ModelAndView();
        vie.setViewName("members/sign");
        if (member != null) {
            vie.addObject("result", "no");
            return vie;
        }

        vie.addObject("result", "ok");
        return vie;
    }
}
