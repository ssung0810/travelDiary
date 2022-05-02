package com.ssung.travelDiary.web.share.dto;

import lombok.Getter;

@Getter
public class ShareBoardResponseDto {

    private Long id;
    private String title;
    private String content;
    private String location;
    private String date;

    public ShareBoardResponseDto(Long id, String title, String content, String location, String date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.location = location;
        this.date = date;
    }
}
