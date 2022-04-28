package com.ssung.travelDiary.web.share.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ShareSaveRequestDto {

    private String title;
    private String creator;
    private List<String> member_id = new ArrayList<>();
    private List<String> board_id = new ArrayList<>();
}
