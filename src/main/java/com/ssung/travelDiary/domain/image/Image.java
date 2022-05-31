package com.ssung.travelDiary.domain.image;

import com.ssung.travelDiary.domain.board.Board;
import com.ssung.travelDiary.dto.file.FileDto;
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
    private FileDto image;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    public Image(FileDto image, Board board) {
        this.image = image;
        this.board = board;
    }
}

