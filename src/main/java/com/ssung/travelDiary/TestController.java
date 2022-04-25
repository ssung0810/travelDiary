package com.ssung.travelDiary;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/maps")
    public String maps() {
        return "maps";
    }

}
