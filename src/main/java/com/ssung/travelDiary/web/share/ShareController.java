package com.ssung.travelDiary.web.share;

import com.ssung.travelDiary.web.board.dto.ShareBoardSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@RequiredArgsConstructor
@Controller
public class ShareController {

//    @ModelAttribute("")

    @GetMapping("/share")
    public String shareForm(Model model) {
        model.addAttribute("share", new ShareBoardSaveRequestDto());

        return "share/shareCreateForm";
    }
}
