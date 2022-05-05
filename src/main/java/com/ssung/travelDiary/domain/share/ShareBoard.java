package com.ssung.travelDiary.domain.share;

import com.ssung.travelDiary.domain.board.Board;
import com.ssung.travelDiary.domain.members.Member;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class ShareBoard {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "share_id")
    private Share share;

    public static ShareBoard createShareBoard(Board board, Share share) {
        ShareBoard shareBoard = new ShareBoard();
        shareBoard.board = board;
        shareBoard.share = share;

        return shareBoard;
    }
}
