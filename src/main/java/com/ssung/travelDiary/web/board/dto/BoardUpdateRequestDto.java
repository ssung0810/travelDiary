package com.ssung.travelDiary.web.board.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter @Setter
public class BoardUpdateRequestDto {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private String location;
    private String image;

    @NotBlank
    private String date;

    public BoardUpdateRequestDto(String title, String content, String location, String image, String date) {
        this.title = title;
        this.content = content;
        this.location = location;
        this.image = image;
        this.date = date;
    }
}
