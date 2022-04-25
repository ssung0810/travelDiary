package com.ssung.travelDiary.domain.image;

import com.ssung.travelDiary.domain.board.Board;
import com.ssung.travelDiary.web.file.FileDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor
@Entity
public class Image {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @Embedded
    private FileDto images;

//    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    private void addBoard(Board board) {
        board.getImages().add(this);
    }

    @Builder
    public Image(FileDto images, Board board) {
        this.images = images;
        this.board = board;

        addBoard(board);
    }
}
