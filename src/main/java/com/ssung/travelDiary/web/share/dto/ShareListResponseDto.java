package com.ssung.travelDiary.web.share.dto;

import lombok.Getter;

@Getter
public class ShareListResponseDto {

    private Long id;
    private String title;

    public ShareListResponseDto(Long id, String title) {
        this.id = id;
        this.title = title;
    }
}
