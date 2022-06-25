package com.ssung.travelDiary.domain.board;

import com.ssung.travelDiary.domain.members.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("select b from Board b left join fetch b.images join b.member m where b.date = :date and m.id = :memberId")
    List<Board> findByMember_idAndDate(@Param("memberId") Long memberId, @Param("date") String date);

    @Query("select b from Board b join b.member m where b.title like %:title% and m.id = :memberId")
    List<Board> findByMemberIdAndMoreType(@Param("memberId") Long memberId, @Param("title") String title);
}
