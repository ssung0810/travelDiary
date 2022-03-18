package com.ssung.travelDiary.web.board;

import com.ssung.travelDiary.domain.board.Board;
import com.ssung.travelDiary.domain.board.BoardRepository;
import com.ssung.travelDiary.service.board.BoardService;
import com.ssung.travelDiary.web.board.dto.BoardSaveRequestDto;
import com.ssung.travelDiary.web.board.dto.BoardUpdateRequestDto;
import com.ssung.travelDiary.web.members.dto.MemberSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/privateBoardList")
    public String privateBoardList(Model model) {

        List<Board> board = boardService.findAll();
        model.addAttribute("boards", board);

        return "board/privateBoardList";
    }

    @GetMapping("/{boardId}")
    public String boardSearch(@PathVariable Long boardId,
                              Model model) {
        Board board = boardService.findOne(boardId);

        model.addAttribute("boardId", boardId);
        model.addAttribute("board", board);

        return "board/board";
    }

    @GetMapping("/save")
    public String saveForm(Model model) {

        model.addAttribute("board", new BoardSaveRequestDto());

        return "board/boardSaveForm";
    }

    @PostMapping("/save")
    public String boardSave(@Valid @ModelAttribute("board") BoardSaveRequestDto dto,
                            BindingResult bindingResult,
                            HttpSession session) {

        if (bindingResult.hasErrors()) {
            log.info("bindingResult = {}", bindingResult);
            return "board/boardSaveForm";
        }

        Board board = Board.builder()
                .date(dto.getDate())
                .username((String) session.getAttribute("username"))
                .title(dto.getTitle())
                .location(dto.getLocation())
                .image(dto.getImage())
                .content(dto.getContent())
                .build();

        Long saveId = boardService.save(board);

        return "redirect:/board/privateBoardList";
    }

    @GetMapping("/{boardId}/update")
    public String updateForm() {
        return "board/updateBoardForm";
    }

    @PatchMapping("/{boardId}/update")
    public String updateBoard(@PathVariable Long boardId,
                              BoardUpdateRequestDto dto) {

        Long updateId = boardService.update(boardId, dto);

        return "redirect:/board/"+boardId;
    }

    @DeleteMapping("/{boardId}/delete")
    public String delete(@PathVariable Long boardId) {
        boardService.delete(boardId);

        return "redirect:/board/privateBoardList";
    }
}
