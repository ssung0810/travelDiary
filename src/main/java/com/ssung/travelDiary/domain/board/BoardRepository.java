package com.ssung.travelDiary.domain.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
//    @Query("SELECT b FROM Board b WHERE username=:username")
//    @Query("select b from Board b where b.member_id = :member_id and b.date = :date order by b.created_date desc")
//    public List<Board> findBoardList(Long member_id, String date);
    List<Board> findByMember_idAndDate(@Param("memberId") Long memberId, @Param("date") String date);
}
