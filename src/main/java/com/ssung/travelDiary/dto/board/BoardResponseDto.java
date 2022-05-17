package com.ssung.travelDiary.dto.board;

import com.ssung.travelDiary.domain.board.Board;
import com.ssung.travelDiary.domain.image.Image;
import com.ssung.travelDiary.domain.members.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class BoardResponseDto {

    private Long id;
    private String title;
    private String content;
    private String location;
    private Member member;
    private List<Image> images = new ArrayList<>();
    private String date;

    public BoardResponseDto(Board entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.location = entity.getLocation();
        this.member = entity.getMember();
        this.images = entity.getImages();
        this.date = entity.getDate();
    }
}
