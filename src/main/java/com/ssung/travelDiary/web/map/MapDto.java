package com.ssung.travelDiary.web.map;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter @Setter
public class MapDto {
    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    private List<HashMap<String, String>> items = new ArrayList<>();

    @Override
    public String toString() {
        return "MapDto{" +
                "lastBuildDate='" + lastBuildDate + '\'' +
                ", total=" + total +
                ", start=" + start +
                ", display=" + display +
                ", items=" + items +
                '}';
    }
}
