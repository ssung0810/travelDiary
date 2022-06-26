package com.ssung.travelDiary.domain.share;

import com.ssung.travelDiary.domain.board.Board;
import com.ssung.travelDiary.domain.members.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShareRepository extends JpaRepository<Share, Long>, ShareRepositoryCustom {

//    @Query("select s from Share s join s.shareMember sm join sm.member m where m.id = :memberId")
//    List<Share> findList(@Param("memberId") Long memberId);

//    @Query("select b from Share s join s.shareBoard sb on s.id = :share_id join sb.board b")
//    List<Board> findShareBoard(@Param("share_id") Long share_id);
}
