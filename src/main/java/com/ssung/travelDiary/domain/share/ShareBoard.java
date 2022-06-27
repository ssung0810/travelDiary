package com.ssung.travelDiary.domain.share;

import com.ssung.travelDiary.domain.board.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class ShareBoard {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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
