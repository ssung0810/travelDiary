package com.ssung.travelDiary.web.board.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class BoardUpdateRequestDto {

    private String title;
    private String content;
    private String location;
    private String image;
    private LocalDateTime date;

    public BoardUpdateRequestDto(String title, String content, String location, String image, LocalDateTime date) {
        this.title = title;
        this.content = content;
        this.location = location;
        this.image = image;
        this.date = date;
    }
}
