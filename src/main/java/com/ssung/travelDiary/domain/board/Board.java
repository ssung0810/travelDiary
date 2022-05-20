package com.ssung.travelDiary.domain.board;

import com.ssung.travelDiary.domain.BaseTimeEntity;
import com.ssung.travelDiary.domain.image.Image;
import com.ssung.travelDiary.domain.members.Member;
import com.ssung.travelDiary.dto.file.FileDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@NoArgsConstructor
@Entity
public class Board extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    public Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(
            mappedBy = "board",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL, CascadeType.REMOVE}
    )
    private List<Image> images = new ArrayList<>();

    private String date;

    @Builder
    public Board(String title, String content, String location, String date, Member member, List<FileDto> fileDtoList) {
        this.title = title;
        this.content = content;
        this.location = location;
        this.date = date;
        this.member = member;

        if(!fileDtoList.isEmpty()) {
            addImage(fileDtoList);
        }
    }

    public Board update(String title, String content, String location, String date, List<FileDto> fileDtoList) {
        this.title = title;
        this.content = content;
        this.location = location;
        this.date = date;

        if(!fileDtoList.isEmpty()) {
            addImage(fileDtoList);
        }

        return this;
    }

    private void addImage(List<FileDto> fileDtoList) {
        images = new ArrayList<>();

        for (FileDto fileDto : fileDtoList) {
            images.add(Image.builder().images(fileDto).board(this).build());
        }
    }
}
