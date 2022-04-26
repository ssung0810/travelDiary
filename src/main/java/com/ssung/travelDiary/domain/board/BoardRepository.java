package com.ssung.travelDiary.domain.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
//    @Query("SELECT b FROM Board b WHERE username=:username")
    List<Board> findByMember_idAndDate(@Param("memberId") Long memberId, @Param("date") String date);
}
