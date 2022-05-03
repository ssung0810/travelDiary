package com.ssung.travelDiary.web.share;

import com.ssung.travelDiary.service.board.BoardService;
import com.ssung.travelDiary.service.share.ShareService;
import com.ssung.travelDiary.web.share.dto.ShareBoardResponseDto;
import com.ssung.travelDiary.web.share.dto.ShareSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ShareController {

    private final ShareService shareService;
    private final BoardService boardService;

    @GetMapping("/share")
    public String shareForm(Model model,
                            @SessionAttribute String username) {
        model.addAttribute("share", new ShareSaveRequestDto(username));

        return "share/shareCreateForm";
    }

    @PostMapping("/share")
    public String shareForm(@ModelAttribute("share") ShareSaveRequestDto dto,
                            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "board/shareBoardCreateForm";
        }

        return "redirection:/board/privateBoardList";
    }

    @GetMapping("/sharePosts/{shareId}")
    public String sharePostsList(Model model,
                                 @PathVariable Long shareId,
                                 @SessionAttribute Long memberId) {

        List<ShareBoardResponseDto> shareBoard = shareService.findShareBoard(shareId);

        log.info("shareBoard = {}", shareBoard);

        model.addAttribute("boards", shareBoard);
        model.addAttribute("date", LocalDate.now().toString());
        model.addAttribute("memberId", memberId);
        model.addAttribute("shareId", shareId);

        return "board/boardList";
    }

    @GetMapping("shareBoardList")
    public String shareBoardList(@SessionAttribute Long memberId,
                                 Model model) {

        List<ShareBoardResponseDto> boardList = boardService.findByMember(memberId);
        model.addAttribute("boardList", boardList);

        return "/share/shareCreateForm :: #boardListBox";
    }
}
