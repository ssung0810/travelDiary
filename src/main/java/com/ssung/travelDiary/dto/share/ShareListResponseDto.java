package com.ssung.travelDiary.dto.share;

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
