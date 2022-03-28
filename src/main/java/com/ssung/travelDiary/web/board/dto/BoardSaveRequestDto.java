package com.ssung.travelDiary.web.board.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter @Setter
public class BoardSaveRequestDto {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private String location;

    private List<MultipartFile> images;

    @NotBlank
    private String date;

    public BoardSaveRequestDto() {
    }
}
