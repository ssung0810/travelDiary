package com.ssung.travelDiary.web.share;

import com.ssung.travelDiary.service.share.ShareService;
import com.ssung.travelDiary.web.share.dto.ShareSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class ShareController {

    private final ShareService shareService;

    @GetMapping("/share")
    public String shareForm(Model model,
                            @SessionAttribute String username) {
        model.addAttribute("share", new ShareSaveRequestDto(username));

        return "share/shareCreateForm";
    }

    @PostMapping("/share")
    public String shareSave(@ModelAttribute("share") ShareSaveRequestDto dto,
                            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "board/shareBoardCreateForm";
        }

        return "redirection:/board/privateBoardList";
    }

    @GetMapping("/sharePostsList/{shareId}")
    public String sharePostsList(Model model,
                                 @PathVariable Long shareId) {

        return "board/boardList";
    }
}
