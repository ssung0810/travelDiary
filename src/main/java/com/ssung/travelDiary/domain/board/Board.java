package com.ssung.travelDiary.domain.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Board {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    private String location;
    private String image;

    private String date;

//    @Column(nullable = false)
//    @Enumerated(EnumType.STRING)
//    private TravelCategory category;

    @Builder
    public Board(String username, String title, String content, String location, String image, String date) {
        this.username = username;
        this.title = title;
        this.content = content;
        this.location = location;
        this.image = image;
        this.date = date;
    }

    public Board update(String title, String content, String location, String image, String date) {
        this.title = title;
        this.content = content;
        this.location = location;
        this.image = image;
        this.date = date;

        return this;
    }
}
