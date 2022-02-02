package com.ssung.travelDiary.domain.travel;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Travel {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;
    private String location;

    @Column(nullable = false)
    private String content;

    private String image;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TravelCategory category;

    @Builder
    public Travel(String username, String location, String content, String image, TravelCategory category) {
        this.username = username;
        this.location = location;
        this.content = content;
        this.image = image;
        this.category = category;
    }
}
