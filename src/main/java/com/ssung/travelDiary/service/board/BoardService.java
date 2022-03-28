package com.ssung.travelDiary.service.board;

import com.ssung.travelDiary.domain.board.Board;
import com.ssung.travelDiary.domain.board.BoardRepository;
import com.ssung.travelDiary.file.FileDto;
import com.ssung.travelDiary.file.FileHandler;
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
    private final FileHandler fileHandler;

    /**
     * 게시글 저장
     */
    @Transactional
    public Long save(Board board) {
        boardRepository.save(board);

//        if(board.getImages() != null) {
//            log.info("images.size = {} / images.stordName = {}", board.getImages().size(), board.getImages().get(0).getStoredFilePath());
//        }

        return board.getId();
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
    public List<Board> findByUsername(String username) {
        return boardRepository.findByUsername(username);
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

        return board.update(dto.getTitle(), dto.getContent(), dto.getLocation(), images, dto.getDate().substring(0, 10));
    }

    /**
     * 게시글 삭제
     */
    @Transactional
    public void delete(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        boardRepository.delete(board);
    }

//    public TravelResponseDto findOne(Long travelId) {
//        Travel travel = travelRepository.findById(travelId)
//                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
//
//        return new TravelResponseDto(travel);
//    }
}
