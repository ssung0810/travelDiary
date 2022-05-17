package com.ssung.travelDiary.dto.share;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class ShareSaveRequestDto {

    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String creator;
    @NotEmpty
    private List<Long> members = new ArrayList<>();
    @NotEmpty
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
