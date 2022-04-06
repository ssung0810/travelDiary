package com.ssung.travelDiary.web.board;

import com.fasterxml.jackson.core.JsonParser;
import com.ssung.travelDiary.domain.board.Board;
import com.ssung.travelDiary.domain.image.Image;
import com.ssung.travelDiary.domain.image.ImageRepository;
import com.ssung.travelDiary.file.FileDto;
import com.ssung.travelDiary.file.FileHandler;
import com.ssung.travelDiary.service.board.BoardService;
import com.ssung.travelDiary.web.board.dto.BoardSaveRequestDto;
import com.ssung.travelDiary.web.board.dto.BoardUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final ImageRepository imageRepository;
    private final FileHandler fileHandler;

    @GetMapping("/privateBoardList")
    public String privateBoardList(Model model,
                                   @SessionAttribute String username) {

        List<Board> board = boardService.findByUsername(username);
        model.addAttribute("boards", board);
//        List<Image> images = imageRepository.findByBoard_id(board.stream().filter(board -> ));
//        List<Image> images = board.stream().filter(b -> imageRepository.findByBoard_id(b.getId()));


        return "board/privateBoardList";
    }

    @GetMapping("/{boardId}")
    public String boardSearch(@PathVariable Long boardId,
                              Model model) {
        Board board = boardService.findOne(boardId);

//        List<Image> images = imageRepository.findByBoard_id(board.getId());

//        model.addAttribute("boardId", boardId);
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

        List<Image> images = createImage(fileHandler.storeFiles(dto.getImages()));
        Board board = createBoard(dto, username);

        Long saveId = boardService.save(board, images);

        return "redirect:/board/privateBoardList";
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

    private List<Image> createImage(List<FileDto> dtos) {
        List<Image> images = new ArrayList<>();

        for (FileDto fileDto : dtos) {
            images.add(Image.builder().images(fileDto).build());
        }

        return images;
    }
}
