package com.ssung.travelDiary.web.board;

import com.ssung.travelDiary.domain.board.Board;
import com.ssung.travelDiary.domain.board.BoardRepository;
import com.ssung.travelDiary.service.board.BoardService;
import com.ssung.travelDiary.web.board.dto.BoardUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/privateBoard")
    public String privateMainBoard() {
        return "board/privateBoard";
    }

    @GetMapping("/{boardId}")
    public String boardSearch(@PathVariable Long boardId,
                              Model model) {
        Board board = boardService.findOne(boardId);

        model.addAttribute("board", board);

        return "";
    }

    @PostMapping
    public String boardSave(@ModelAttribute Board board) {

        Long saveId = boardService.save(board);

        return "redirect:/board/privateBoard";
    }

    @PatchMapping("/{boardId}")
    public String updateBoard(@PathVariable Long boardId,
                              BoardUpdateRequestDto dto) {

        Long updateId = boardService.update(boardId, dto);

        return "";
    }
}
