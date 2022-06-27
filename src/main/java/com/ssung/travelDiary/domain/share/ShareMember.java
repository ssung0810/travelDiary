package com.ssung.travelDiary.domain.share;

import com.ssung.travelDiary.domain.members.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class ShareMember {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "share_id")
    private Share share;

    public static ShareMember createShareMember(Member member, Share share) {
        ShareMember shareMember = new ShareMember();
        shareMember.member = member;
        shareMember.share = share;

        return shareMember;
    }
}
