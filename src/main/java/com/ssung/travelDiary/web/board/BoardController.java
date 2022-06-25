package com.ssung.travelDiary.web.board;

import com.ssung.travelDiary.service.board.BoardService;
import com.ssung.travelDiary.constancy.SessionConst;
import com.ssung.travelDiary.dto.board.BoardResponseDto;
import com.ssung.travelDiary.dto.board.BoardSaveRequestDto;
import com.ssung.travelDiary.dto.board.BoardUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/privateBoardList")
    public String privateBoardList(Model model,
                                   @SessionAttribute(name = SessionConst.USER_ID) Long memberId,
                                   @RequestParam(required = false) String date) {

        if(date == null) date = LocalDate.now().toString();

        List<BoardResponseDto> board = boardService.findList(memberId, date);
        model.addAttribute("boards", board);
        model.addAttribute("date", date);
        model.addAttribute("shareId", 0);

        return "board/boardList";
    }

    @GetMapping("/{boardId}")
    public String boardSearch(@PathVariable Long boardId,
                              Model model) {
        BoardResponseDto board = boardService.findOne(boardId);

        model.addAttribute("board", board);
        model.addAttribute("images", board.getImages());

        return "board/board";
    }

    @GetMapping
    public String boardSaveForm(Model model) {

        model.addAttribute("board", new BoardSaveRequestDto());

        return "board/boardSaveForm";
    }

    @PostMapping
    public String boardSave(@Valid @ModelAttribute("board") BoardSaveRequestDto dto,
                            BindingResult bindingResult,
                            @SessionAttribute(name = SessionConst.USER_ID) Long memberId) throws IOException {

        if (bindingResult.hasErrors()) {
            return "board/boardSaveForm";
        }

        BoardResponseDto board = boardService.save(dto, memberId);

        return "redirect:/board/privateBoardList?date=" + board.getDate();
    }

    @GetMapping("/{boardId}/update")
    public String updateForm(@PathVariable Long boardId,
                             Model model) {

        BoardResponseDto board = boardService.findOne(boardId);
        model.addAttribute("board", board);

        return "board/boardUpdateForm";
    }

    @PutMapping("/{boardId}")
    public String updateBoard(@PathVariable Long boardId,
                              @Valid @ModelAttribute("board") BoardUpdateRequestDto dto,
                              BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            return "board/boardUpdateForm";
        }

        BoardResponseDto board = boardService.update(dto, boardId);

        return "redirect:/board/"+board.getId();
    }
}
