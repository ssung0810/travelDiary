package com.ssung.travelDiary.service.board;

import com.ssung.travelDiary.domain.board.Board;
import com.ssung.travelDiary.domain.board.BoardRepository;
import com.ssung.travelDiary.web.board.dto.BoardUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    /**
     * 게시글 저장
     */
    @Transactional
    public Long save(Board board) {
        boardRepository.save(board);
        return board.getId();
    }

    /**
     * 게시글 전체 조회
     */
    public List<Board> findAll() {
        return boardRepository.findAll();
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
    public Long update(Long boardId, BoardUpdateRequestDto dto) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        Board updateBoard = board.update(dto.getTitle(), dto.getContent(), dto.getLocation(), dto.getImage(), dto.getDate());

        return updateBoard.getId();
    }

    /**
     * 게시글 삭제
     */
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
