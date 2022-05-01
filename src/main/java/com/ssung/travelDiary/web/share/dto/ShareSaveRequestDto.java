package com.ssung.travelDiary.web.share.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ShareSaveRequestDto {

    private String title;
    private String creator;
    private List<Long> members = new ArrayList<>();
    private List<Long> boards = new ArrayList<>();

    public ShareSaveRequestDto(String title, String creator, List<Long> members, List<Long> boards) {
        this.title = title;
        this.creator = creator;
        this.members = members;
        this.boards = boards;
    }

    public ShareSaveRequestDto(String creator) {
        this.creator = creator;
    }
}
