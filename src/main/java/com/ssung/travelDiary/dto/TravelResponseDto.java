package com.ssung.travelDiary.dto;

import com.ssung.travelDiary.domain.travel.Travel;
import com.ssung.travelDiary.domain.travel.TravelCategory;
import lombok.Getter;

@Getter
public class TravelResponseDto {

    private Long id;
    private String content;
    private String location;
    private String image;
    private TravelCategory category;

    public TravelResponseDto(Travel entity) {
        this.id = entity.getId();
        this.content = entity.getContent();
        this.location = entity.getLocation();
        this.image = entity.getImage();
        this.category = entity.getCategory();
    }
}
