package com.ssung.travelDiary.web.board.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter @Setter
public class BoardUpdateRequestDto {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private String location;
    private List<MultipartFile> images;

    @NotBlank
    private String date;

    public BoardUpdateRequestDto(String title, String content, String location, List<MultipartFile> images, String date) {
        this.title = title;
        this.content = content;
        this.location = location;
        this.images = images;
        this.date = date;
    }
}
