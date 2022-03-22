package com.ssung.travelDiary.web.board;

import com.ssung.travelDiary.domain.board.Board;
import com.ssung.travelDiary.domain.board.BoardRepository;
import com.ssung.travelDiary.domain.members.Member;
import com.ssung.travelDiary.service.board.BoardService;
import com.ssung.travelDiary.web.board.dto.BoardSaveRequestDto;
import com.ssung.travelDiary.web.board.dto.BoardUpdateRequestDto;
import com.ssung.travelDiary.web.members.dto.MemberSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/privateBoardList")
    public String privateBoardList(Model model,
                                   HttpSession httpSession) {

        String username = (String) httpSession.getAttribute("username");
        List<Board> board = boardService.findByUsername(username);
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
                .date(dto.getDate().substring(0, 10))
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
    public String updateForm(@PathVariable Long boardId,
                             Model model) {

        Board board = boardService.findOne(boardId);
        model.addAttribute("board", board);

        return "board/boardUpdateForm";
    }

    @PostMapping("/{boardId}/update")
    public String updateBoard(@PathVariable Long boardId,
                              @Valid @ModelAttribute("board") BoardUpdateRequestDto dto,
                              BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("bindingResult(update) = {}", bindingResult);
            return "board/boardUpdateForm";
        }

        Board board = boardService.update(boardId, dto);

        return "redirect:/board/"+board.getId();
    }

    @DeleteMapping("/{boardId}/delete")
    public String delete(@PathVariable Long boardId) {
        boardService.delete(boardId);

        return "redirect:/board/privateBoardList";
    }

//    @ResponseBody
//    @GetMapping("/image/{boardId}")
//    public ResponseEntity<Resource> image(@PathVariable Long boardId,
//                                          HttpSession httpSession) {
//
//        Member member = memberRepository.findByUsername(username)
//                .orElseThrow(() -> new IllegalArgumentException("없는 별명"));
//
//        Board board = boardService.findOne(boardId);
//
//        String path = new File("").getAbsolutePath() + "\\";
//        String storedPath = member.getStored_file_path();
//
//        Resource resource = new FileSystemResource(path+storedPath);
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//
//        try {
//            Path filePath = Paths.get(path + storedPath);
//            httpHeaders.add("Content-Type", Files.probeContentType(filePath));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return new ResponseEntity<Resource>(resource, httpHeaders, HttpStatus.OK);
//    }
}
