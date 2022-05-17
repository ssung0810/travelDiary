package com.ssung.travelDiary.web.board;

import com.ssung.travelDiary.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BoardApiController {

    private final BoardService boardService;

    @DeleteMapping("/board/{boardId}")
    public Long delete(@PathVariable Long boardId) {
        return boardService.delete(boardId);
    }
}
