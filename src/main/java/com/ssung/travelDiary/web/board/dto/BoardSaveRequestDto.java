package com.ssung.travelDiary.web.board.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter @Setter
public class BoardSaveRequestDto {

    @NotBlank
    private String username;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private String location;

    private String image;

    @NotBlank
    private LocalDateTime date;
}