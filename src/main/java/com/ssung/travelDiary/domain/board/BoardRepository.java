package com.ssung.travelDiary.domain.board;

import com.ssung.travelDiary.domain.members.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByMember_idAndDate(@Param("memberId") Long memberId, @Param("date") String date);


    @Query("select b from Board b where b.title like %:title% and b.member = :member")
    List<Board> findByMemberIdAndMoreType(@Param("member") Member member, @Param("title") String title);
//    List<Board> findByMember_id(@Param("member_id") Long member_id);
}
