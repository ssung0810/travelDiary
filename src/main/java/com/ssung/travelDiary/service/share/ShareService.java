package com.ssung.travelDiary.service.share;

import com.ssung.travelDiary.constancy.ErrorMessageConst;
import com.ssung.travelDiary.domain.board.Board;
import com.ssung.travelDiary.domain.board.BoardRepository;
import com.ssung.travelDiary.domain.members.Member;
import com.ssung.travelDiary.domain.members.MemberRepository;
import com.ssung.travelDiary.domain.share.Share;
import com.ssung.travelDiary.domain.share.ShareBoard;
import com.ssung.travelDiary.domain.share.ShareMember;
import com.ssung.travelDiary.domain.share.ShareRepository;
import com.ssung.travelDiary.dto.share.ShareBoardResponseDto;
import com.ssung.travelDiary.dto.share.ShareListResponseDto;
import com.ssung.travelDiary.dto.share.ShareResponseDto;
import com.ssung.travelDiary.dto.share.ShareSaveRequestDto;
import com.ssung.travelDiary.exception.board.BoardNotFountException;
import com.ssung.travelDiary.exception.member.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ShareService {

    private final ShareRepository shareRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    /**
     * 공유폴더 저장
     */
    @Transactional
    public ShareResponseDto save(ShareSaveRequestDto dto,
                                 Long memberId) {

//        Share share = Share.saveShare(dto.getTitle(), dto.getCreator());
        Share share = Share.builder().title(dto.getTitle()).creator(dto.getCreator()).build();

        share.getShareMember().add(
                ShareMember.createShareMember(
                        memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException(ErrorMessageConst.MemberNotFoundException)), share
                )
        );

        List<Long> members = dto.getMembers();
        for (Long m : members) {
            Member member = memberRepository.findById(m).orElseThrow(() -> new MemberNotFoundException(ErrorMessageConst.MemberNotFoundException));
            share.getShareMember().add(ShareMember.createShareMember(member, share));
        }

        List<Long> boards = dto.getBoards();
        for (Long b : boards) {
            Board board = boardRepository.findById(b).orElseThrow(() -> new BoardNotFountException(ErrorMessageConst.BoardNotFoundException));
            share.getShareBoard().add(ShareBoard.createShareBoard(board, share));
        }

        shareRepository.save(share);

        return new ShareResponseDto(share);
    }

    /**
     * 공유폴더 리스트 출력
     */
    public List<ShareListResponseDto> findList(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException(ErrorMessageConst.MemberNotFoundException));

        return shareRepository.findList(member).stream()
            .map(s -> new ShareListResponseDto(s.getId(), s.getTitle())).collect(Collectors.toList());
    }

    /**
     * 특정 공유폴더 내부 게시글 조회
     */
    public List<ShareBoardResponseDto> findShareBoard(Long shareId) {
        return shareRepository.findShareBoard(shareId).stream()
                .map(b -> new ShareBoardResponseDto(b)).collect(Collectors.toList());
    }
}
