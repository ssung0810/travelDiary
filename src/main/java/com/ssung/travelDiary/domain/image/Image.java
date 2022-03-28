package com.ssung.travelDiary.domain.image;

import com.ssung.travelDiary.domain.board.Board;
import com.ssung.travelDiary.file.FileDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor
@Entity
public class Image {

    @Embedded
    private FileDto images;

//    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Board board;
}
