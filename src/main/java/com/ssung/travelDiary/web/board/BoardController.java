package com.ssung.travelDiary.web.board;

import com.ssung.travelDiary.domain.board.Board;
import com.ssung.travelDiary.domain.image.Image;
import com.ssung.travelDiary.file.FileDto;
import com.ssung.travelDiary.file.FileHandler;
import com.ssung.travelDiary.service.board.BoardService;
import com.ssung.travelDiary.service.image.ImageService;
import com.ssung.travelDiary.web.board.dto.BoardSaveRequestDto;
import com.ssung.travelDiary.web.board.dto.BoardSearchDto;
import com.ssung.travelDiary.web.board.dto.BoardUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final ImageService imageService;
    private final FileHandler fileHandler;

    @GetMapping("/privateBoardList")
    public String privateBoardList(Model model,
                                   @SessionAttribute String username,
                                   @ModelAttribute BoardSearchDto dateDto) {

        log.info("searchDate = {}", dateDto.getDate());

        if(dateDto.getDate() == null || dateDto.getDate().equals("")) dateDto.setDate(LocalDate.now().toString());

        List<Board> board = boardService.findPrivateList(username, dateDto.getDate());
        model.addAttribute("boards", board);
        model.addAttribute("date", dateDto.getDate());

        return "board/privateBoardList";
    }

    @GetMapping("/{boardId}")
    public String boardSearch(@PathVariable Long boardId,
                              Model model) {
        Board board = boardService.findOne(boardId);

        model.addAttribute("board", board);
        model.addAttribute("images", board.getImages());

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
                            @SessionAttribute String username) throws IOException {

        if (bindingResult.hasErrors()) {
            log.info("bindingResult = {}", bindingResult);
            return "board/boardSaveForm";
        }

        Board board = createBoard(dto, username);

        Long saveId = boardService.save(board);
        imageService.saveBoard(fileHandler.storeFiles(dto.getImages()), board);

        return "redirect:/board/privateBoardList?date=" + board.getDate();
    }

    @GetMapping("/{boardId}/update")
    public String updateForm(@PathVariable Long boardId,
                             Model model) {

        Board board = boardService.findOne(boardId);
        model.addAttribute("board", board);

        return "board/boardUpdateForm";
    }

    @PostMapping("/{boardId}/update")
    public String updateBoard(@PathVariable Long boardId,
                              @Valid @ModelAttribute("board") BoardUpdateRequestDto dto,
                              BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            log.info("bindingResult(update) = {}", bindingResult);
            return "board/boardUpdateForm";
        }

        Board board = boardService.update(boardId, dto);

        return "redirect:/board/"+board.getId();
    }

    @ResponseBody
    @DeleteMapping("/{boardId}")
    public Long delete(@PathVariable Long boardId) {
        return boardService.delete(boardId);
//        boardService.delete(boardId);


//        return "board/privateBoardList";
    }

    private Board createBoard(BoardSaveRequestDto dto, String username) {
        return Board.builder()
                .date(dto.getDate().substring(0, 10))
                .username(username)
                .title(dto.getTitle())
                .location(dto.getLocation())
                .content(dto.getContent())
                .build();
    }

    private List<Image> createImage(List<FileDto> dtos, Board board) {
        List<Image> images = new ArrayList<>();

        for (FileDto fileDto : dtos) {
            images.add(Image.builder().images(fileDto).build());
        }

        return images;
    }
}
