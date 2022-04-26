package com.ssung.travelDiary.domain.share;

import com.ssung.travelDiary.domain.board.Board;
import com.ssung.travelDiary.domain.members.Member;
import lombok.Builder;

import javax.persistence.*;

@Entity
public class Share {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String creator;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
//    private Member member;

//    public Share createShare(String creator, Member member) {
//        this.creator = creator;
//        this.member = member;
//
//        return this;
//    }
}
