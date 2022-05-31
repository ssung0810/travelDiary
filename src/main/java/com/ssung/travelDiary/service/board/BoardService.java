package com.ssung.travelDiary.service.board;

import com.ssung.travelDiary.domain.board.Board;
import com.ssung.travelDiary.domain.board.BoardRepository;
import com.ssung.travelDiary.domain.image.Image;
import com.ssung.travelDiary.domain.image.ImageRepository;
import com.ssung.travelDiary.domain.members.Member;
import com.ssung.travelDiary.domain.members.MemberRepository;
import com.ssung.travelDiary.dto.board.BoardResponseDto;
import com.ssung.travelDiary.dto.board.BoardSaveRequestDto;
import com.ssung.travelDiary.dto.board.BoardUpdateRequestDto;
import com.ssung.travelDiary.dto.share.ShareBoardResponseDto;
import com.ssung.travelDiary.exception.board.BoardNotFountException;
import com.ssung.travelDiary.exception.member.MemberNotFoundException;
import com.ssung.travelDiary.handler.FileHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final ImageRepository imageRepository;
    private final FileHandler fileHandler;

    /**
     * 게시글 저장
     */
    @Transactional
    public BoardResponseDto save(BoardSaveRequestDto dto, Long memberId) throws IOException {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원입니다."));

        Board board = createBoard(dto, member);
        boardRepository.save(board);

        return new BoardResponseDto(board);
    }

    /**
     * 게시글 전체 조회
     */
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    /**
     * 개인 게시글 리스트 조회
     */
    public List<BoardResponseDto> findList(Long member_id, String date) {
        return boardRepository.findByMember_idAndDate(member_id, date).stream()
                .map(b -> new BoardResponseDto(b)).collect(Collectors.toList());
    }

    /**
     * 게시글 단일 조회
     */
    public BoardResponseDto findOne(Long boardId) {
        return new BoardResponseDto(boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFountException("게시글이 존재하지 않습니다.")));
    }

    /**
     * 게시글 수정
     */
    @Transactional
    public BoardResponseDto update(BoardUpdateRequestDto dto, Long boardId) throws IOException {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFountException("게시글이 존재하지 않습니다."));

        for (Image image : board.getImages()) {
            imageRepository.delete(image);
        }

        return new BoardResponseDto(board.update(dto.getTitle(), dto.getContent(), dto.getLocation(), dto.getDate(), fileHandler.storeFiles(dto.getImages())));
    }

    /**
     * 게시글 삭제
     */
    @Transactional
    public Long delete(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFountException("게시글이 존재하지 않습니다."));

        boardRepository.delete(board);

        return board.getId();
    }

    /**
     * 공유폴더 저장 시 게시글 등록 조회
     */
    public List<ShareBoardResponseDto> addBoardSearch(Long memberId, String value) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원입니다."));

        return boardRepository.findByMemberIdAndMoreType(member, value).stream()
                .map(b -> new ShareBoardResponseDto(b)).collect(Collectors.toList());
    }

    private Board createBoard(BoardSaveRequestDto dto, Member member) throws IOException {
        return Board.builder()
                .date(dto.getDate())
                .title(dto.getTitle())
                .location(dto.getLocation())
                .content(dto.getContent())
                .member(member)
                .images(fileHandler.storeFiles(dto.getImages()))
                .build();
    }
}
