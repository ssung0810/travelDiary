package com.ssung.travelDiary.web.share.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ShareSaveRequestDto {

    private String title;
    private String creator;
    private List<Long> members = new ArrayList<>();
    private List<Long> boards = new ArrayList<>();

    public ShareSaveRequestDto(String creator) {
        this.creator = creator;
    }
}
