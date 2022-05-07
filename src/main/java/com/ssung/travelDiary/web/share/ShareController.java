package com.ssung.travelDiary.web.share;

import com.ssung.travelDiary.domain.board.Board;
import com.ssung.travelDiary.domain.members.Member;
import com.ssung.travelDiary.domain.share.Share;
import com.ssung.travelDiary.service.board.BoardService;
import com.ssung.travelDiary.service.members.MemberService;
import com.ssung.travelDiary.service.share.ShareService;
import com.ssung.travelDiary.web.SessionConst;
import com.ssung.travelDiary.web.members.dto.MemberResponseDto;
import com.ssung.travelDiary.web.share.dto.ShareBoardResponseDto;
import com.ssung.travelDiary.web.share.dto.ShareSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ShareController {

    private final ShareService shareService;
    private final BoardService boardService;
    private final MemberService memberService;

    @GetMapping("/share")
    public String shareForm(Model model,
                            @SessionAttribute(name = SessionConst.USERNAME) String username) {
        model.addAttribute("share", new ShareSaveRequestDto(username));

        return "share/shareCreateForm";
    }

    @PostMapping("/share")
    public String shareForm(@Valid @ModelAttribute("share") ShareSaveRequestDto dto,
                            BindingResult bindingResult,
                            @SessionAttribute(name = SessionConst.USER_ID) Long memberId) {

        if (bindingResult.hasErrors()) {
            log.info("bindingResult = {}", bindingResult);
            return "share/shareCreateForm";
        }

        Share saveShare = shareService.save(dto, memberId);

        return "redirect:/sharePosts/"+saveShare.getId();
    }

    @GetMapping("/sharePosts/{shareId}")
    public String sharePostsList(Model model,
                                 @PathVariable Long shareId,
                                 @SessionAttribute(name = SessionConst.USER_ID) Long memberId) {

        List<ShareBoardResponseDto> shareBoard = shareService.findShareBoard(shareId);

        model.addAttribute("boards", shareBoard);
        model.addAttribute("date", LocalDate.now().toString());
        model.addAttribute("memberId", memberId);
        model.addAttribute("shareId", shareId);

        return "board/boardList";
    }

    @GetMapping("shareBoardList")
    public String shareBoardList(@SessionAttribute(name = SessionConst.USER_ID) Long memberId,
                                 @RequestParam String value,
                                 Model model) {

        List<ShareBoardResponseDto> boardList = boardService.addBoardSearch(memberId, value);
        model.addAttribute("boardList", boardList);

        return "/share/shareCreateForm :: #boardListBox";
    }

    @GetMapping("shareMemberList")
    public String shareMemberList(@SessionAttribute(name = SessionConst.USER_ID) Long memberId,
                                  @RequestParam String value,
                                  Model model) {

        List<MemberResponseDto> memberList = memberService.addMemberSearch(memberId, value);
//        List<Member> memberList = memberService.findShareMember2(memberId);
        model.addAttribute("memberList", memberList);
//        model.addAttribute("member", "");

        return "/share/shareCreateForm :: #memberListBox";
    }
}
