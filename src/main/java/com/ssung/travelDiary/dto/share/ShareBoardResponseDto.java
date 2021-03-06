package com.ssung.travelDiary.dto.share;

import com.ssung.travelDiary.domain.board.Board;
import com.ssung.travelDiary.domain.image.Image;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ShareBoardResponseDto {

    private Long id;
    private String title;
    private String content;
    private String location;
    private String date;

    private List<Image> images = new ArrayList<>();

    public ShareBoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.location = board.getLocation();
        this.date = board.getDate();
        this.images = board.getImages();
    }
}
