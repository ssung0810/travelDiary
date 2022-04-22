package com.ssung.travelDiary.web.board.dto;


import com.ssung.travelDiary.domain.board.Board;
import com.ssung.travelDiary.domain.members.Member;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ShareBoardSaveRequestDto {

    @NotBlank
    private String title;
    private List<Board> boardList = new ArrayList<>();
    private List<Member> members = new ArrayList<>();
}
