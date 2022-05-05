package com.ssung.travelDiary.service.board;

import com.ssung.travelDiary.domain.board.Board;
import com.ssung.travelDiary.domain.board.BoardRepository;
import com.ssung.travelDiary.domain.members.Member;
import com.ssung.travelDiary.domain.members.MemberRepository;
import com.ssung.travelDiary.web.file.FileDto;
import com.ssung.travelDiary.handler.FileHandler;
import com.ssung.travelDiary.service.image.ImageService;
import com.ssung.travelDiary.web.board.dto.BoardSaveRequestDto;
import com.ssung.travelDiary.web.board.dto.BoardUpdateRequestDto;
import com.ssung.travelDiary.web.share.dto.ShareBoardResponseDto;
import com.ssung.travelDiary.web.share.dto.ShareSaveRequestDto;
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
    private final ImageService imageService;
    private final FileHandler fileHandler;

    /**
     * 게시글 저장
     */
    @Transactional
    public Board save(BoardSaveRequestDto dto, Long memberId) throws IOException {
        Member member = memberRepository.findById(memberId).orElse(null);

        Board board = createBoard(dto, member);
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
     * 개인 게시글 조회
     */
    public List<Board> findList(Long member_id, String date) {
//        return boardRepository.findBoardList(member_id, date);
        return boardRepository.findByMember_idAndDate(member_id, date);
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

    /**
     * 공유폴더 저장 시 게시글 등록 조회
     */
    public List<ShareBoardResponseDto> addBoardSearch(Long memberId, String value) {
        Member member = memberRepository.findById(memberId).orElse(null);

        return boardRepository.findByMemberIdAndMoreType(member, value).stream()
                .map(b -> new ShareBoardResponseDto(b)).collect(Collectors.toList());
    }
//    public List<Board> findByMemberIdAndMoreType(Long memberId, String value) {
//        return boardRepository.findByMember_id(memberId);
//    }

    private Board createBoard(BoardSaveRequestDto dto, Member member) {
        return Board.builder()
                .date(dto.getDate())
                .title(dto.getTitle())
                .location(dto.getLocation())
                .content(dto.getContent())
                .member(member)
                .build();
    }
}
