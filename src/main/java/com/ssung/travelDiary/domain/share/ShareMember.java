package com.ssung.travelDiary.domain.share;

import com.ssung.travelDiary.domain.members.Member;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
public class ShareMember {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "share_id")
    private Share share;

    public static ShareMember createShareMember(Member member, Share share) {
        ShareMember shareMember = new ShareMember();
        shareMember.member = member;
        shareMember.share = share;

        return shareMember;
    }
}
