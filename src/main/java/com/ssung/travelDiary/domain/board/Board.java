package com.ssung.travelDiary.domain.board;

import com.ssung.travelDiary.domain.image.Image;
import com.ssung.travelDiary.file.FileDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Board {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    private String location;

    @OneToMany(mappedBy = "board")
    private List<Image> images;

    private String date;

//    @Column(nullable = false)
//    @Enumerated(EnumType.STRING)
//    private TravelCategory category;

    @Builder
    public Board(String username, String title, String content, String location, List<Image> images, String date) {
        this.username = username;
        this.title = title;
        this.content = content;
        this.location = location;
        this.images = images;
        this.date = date;
    }

    public Board update(String title, String content, String location, List<Image> images, String date) {
        this.title = title;
        this.content = content;
        this.location = location;
        this.images = images;
        this.date = date;

        return this;
    }
}
