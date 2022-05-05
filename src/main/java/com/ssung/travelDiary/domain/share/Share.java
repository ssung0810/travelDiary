package com.ssung.travelDiary.domain.share;

import com.ssung.travelDiary.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Share extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "share_id")
    private Long id;

    private String title;
    private String creator;

    @OneToMany(mappedBy = "share", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<ShareMember> shareMember = new ArrayList<>();

    @OneToMany(mappedBy = "share", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<ShareBoard> shareBoard = new ArrayList<>();

    @Builder
    public Share(Long id, String title, String creator, List<ShareMember> shareMember, List<ShareBoard> shareBoard) {
        this.id = id;
        this.title = title;
        this.creator = creator;
        this.shareMember = shareMember;
        this.shareBoard = shareBoard;
    }

    public static Share saveShare(String title, String creator) {
        Share share = new Share();
        share.title = title;
        share.creator = creator;

        return share;
    }

}
