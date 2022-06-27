package com.ssung.travelDiary.domain.share;

import com.ssung.travelDiary.domain.board.Board;

import java.util.List;

public interface ShareRepositoryCustom {

    List<Share> findList(Long memberId);

    List<Board> findShareBoard(Long share_id, String date);
}
