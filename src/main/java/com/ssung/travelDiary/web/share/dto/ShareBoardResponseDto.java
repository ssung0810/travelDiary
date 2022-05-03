package com.ssung.travelDiary.web.share.dto;

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

    public ShareBoardResponseDto(Long id, String title, String content, String location, String date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.location = location;
        this.date = date;
    }

    @Override
    public String toString() {
        return "ShareBoardResponseDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", location='" + location + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
