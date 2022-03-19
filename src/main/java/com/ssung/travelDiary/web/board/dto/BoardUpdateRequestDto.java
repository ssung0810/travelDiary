package com.ssung.travelDiary.web.board.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

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

}
