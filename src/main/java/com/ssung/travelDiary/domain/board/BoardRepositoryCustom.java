package com.ssung.travelDiary.domain.board;

import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepositoryCustom {

    List<Board> findByMember_idAndDate(@Param("memberId") Long memberId, @Param("date") String date);

    List<Board> findByMemberIdAndMoreType(@Param("memberId") Long memberId, @Param("title") String title);
}
