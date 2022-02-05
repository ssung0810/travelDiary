package com.ssung.travelDiary.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {

    @GetMapping("/sign")
    public String Sign() {
        return "members/sign";
    }
}
