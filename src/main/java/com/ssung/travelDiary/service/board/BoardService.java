package com.ssung.travelDiary.service.board;

import com.ssung.travelDiary.domain.board.Board;
import com.ssung.travelDiary.domain.board.BoardRepository;
import com.ssung.travelDiary.web.file.FileDto;
import com.ssung.travelDiary.web.file.FileHandler;
import com.ssung.travelDiary.service.image.ImageService;
import com.ssung.travelDiary.web.board.dto.BoardSaveRequestDto;
import com.ssung.travelDiary.web.board.dto.BoardUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final ImageService imageService;
    private final FileHandler fileHandler;

    /**
     * 게시글 저장
     */
    @Transactional
    public Board save(BoardSaveRequestDto dto, String username) throws IOException {

        Board board = createBoard(dto, username);
        boardRepository.save(board);

        imageService.saveBoard(fileHandler.storeFiles(dto.getImages()), board);

        return board;
    }

    /**
     * 게시글 전체 조회
     */
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    /**
     * 게시글 개인 조회
     */
    public List<Board> findPrivateList(String username, String date) {
        return boardRepository.findByUsernameAndDate(username, date);
    }

    /**
     * 게시글 단일 조회
     */
    public Board findOne(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
    }

    /**
     * 게시글 수정
     */
    @Transactional
    public Board update(Long boardId, BoardUpdateRequestDto dto) throws IOException {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        List<FileDto> images = fileHandler.storeFiles(dto.getImages());

        return board.update(dto.getTitle(), dto.getContent(), dto.getLocation(), dto.getDate());
    }

    /**
     * 게시글 삭제
     */
    @Transactional
    public Long delete(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        boardRepository.delete(board);

        return board.getId();
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
}
