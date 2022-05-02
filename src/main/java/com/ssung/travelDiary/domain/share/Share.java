package com.ssung.travelDiary.domain.share;

import com.ssung.travelDiary.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Share extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "share_id")
    private Long id;

    private String title;
    private String creator;

    @OneToMany(mappedBy = "share", cascade = CascadeType.PERSIST)
    private List<ShareMember> shareMember = new ArrayList<>();

    @OneToMany(mappedBy = "share", cascade = CascadeType.PERSIST)
    private List<ShareBoard> shareBoard = new ArrayList<>();

    @Builder
    public Share(String title, String creator) {
        this.title = title;
        this.creator = creator;
    }
}
