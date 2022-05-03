package com.ssung.travelDiary.domain.board;

import com.ssung.travelDiary.web.share.dto.ShareBoardResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByMember_idAndDate(@Param("memberId") Long memberId, @Param("date") String date);

    List<ShareBoardResponseDto> findByMember_id(@Param("member_id") Long member_id);
}
