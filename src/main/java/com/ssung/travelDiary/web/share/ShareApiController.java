package com.ssung.travelDiary.web.share;

import com.ssung.travelDiary.service.share.ShareService;
import com.ssung.travelDiary.web.SessionConst;
import com.ssung.travelDiary.dto.share.ShareBoardResponseDto;
import com.ssung.travelDiary.dto.share.ShareListResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ShareApiController {

    private final ShareService shareService;

    @GetMapping("/shareList")
    public List<ShareListResponseDto> shareList(@SessionAttribute(name = SessionConst.USER_ID) Long memberId) {
        return shareService.findList(memberId);
    }

    @GetMapping("/shareBoard/{shareId}")
    public List<ShareBoardResponseDto> shareBoard(@PathVariable Long shareId) {
        return shareService.findShareBoard(shareId);
    }
}
