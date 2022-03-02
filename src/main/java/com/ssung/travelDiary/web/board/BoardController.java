package com.ssung.travelDiary.web.board;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board")
public class BoardController {

    @GetMapping("/privateBoard")
    public String privateBoard() {
        return "board/privateBoard";
    }
}
