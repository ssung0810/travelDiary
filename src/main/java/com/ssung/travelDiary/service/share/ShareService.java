package com.ssung.travelDiary.service.share;

import com.ssung.travelDiary.domain.board.Board;
import com.ssung.travelDiary.domain.board.BoardRepository;
import com.ssung.travelDiary.domain.members.Member;
import com.ssung.travelDiary.domain.members.MemberRepository;
import com.ssung.travelDiary.domain.share.Share;
import com.ssung.travelDiary.domain.share.ShareBoard;
import com.ssung.travelDiary.domain.share.ShareMember;
import com.ssung.travelDiary.domain.share.ShareRepository;
import com.ssung.travelDiary.web.share.dto.ShareSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public Share save(ShareSaveRequestDto dto,
                      Long memberId) {

        Share share = Share.builder().title(dto.getTitle()).creator(dto.getCreator()).build();

        List<Long> members = dto.getMembers();
        for (Long m : members) {
            Member member = memberRepository.findById(m).orElse(null);
            share.getShareMember().add(ShareMember.createShareMember(member, share));
        }

        List<Long> boards = dto.getBoards();
        for (Long b : boards) {
            Board board = boardRepository.findById(b).orElse(null);
            share.getShareBoard().add(ShareBoard.createShareBoard(board, share));
        }

        shareRepository.save(share);

        return share;
    }

    /**
     * 공유폴더 리스트 출력
     */
    public List<Share> findList(Long memberId) {
        Member member = memberRepository.findById(memberId).orElse(null);

        return shareRepository.findList(member);
    }
}
