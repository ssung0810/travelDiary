package com.ssung.travelDiary.web.share;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class ShareController {

    @GetMapping("/share")
    public String shareForm() {
        return "shareCreateForm";
    }
}
