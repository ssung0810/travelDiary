package com.ssung.travelDiary.domain.share;

import com.ssung.travelDiary.domain.board.Board;
import com.ssung.travelDiary.domain.members.Member;
import com.ssung.travelDiary.web.board.dto.BoardSaveRequestDto;
import com.ssung.travelDiary.web.share.dto.ShareBoardResponseDto;
import com.ssung.travelDiary.web.share.dto.ShareListResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShareRepository extends JpaRepository<Share, Long> {
    @Query("select s from Share s join s.shareMember sm on sm.member = :member")
    List<Share> findList(@Param("member") Member member);

    @Query("select b from Share s join s.shareBoard sb on s.id = :share_id join sb.board b")
    List<Board> findShareBoard(@Param("share_id") Long share_id);

//    @Query("select b from Board b where b.title like %:value% and b.member = :member")
//    List<Board> shareBoardList(@Param("member") Member member, @Param("value") String value);
}
