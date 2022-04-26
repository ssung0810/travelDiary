package com.ssung.travelDiary.domain.board;

import com.ssung.travelDiary.domain.BaseTimeEntity;
import com.ssung.travelDiary.domain.image.Image;
import com.ssung.travelDiary.domain.members.Member;
import com.ssung.travelDiary.domain.share.Share;
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

//    @Column(nullable = false)
//    private String username;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "board")
    private List<Image> images = new ArrayList<>();

//    @OneToMany(mappedBy = "board")
//    private List<Share> shares = new ArrayList<>();

    private String date;

    @Builder
    public Board(String title, String content, String location, String date, Member member) {
        this.title = title;
        this.content = content;
        this.location = location;
        this.date = date;
        this.member = member;
    }

//    private void addShares(Share shares) {
//        this.shares.add(shares);
//        shares.addShares(this);
//    }

    public Board update(String title, String content, String location, String date) {
        this.title = title;
        this.content = content;
        this.location = location;
        this.date = date;

        return this;
    }
}
