package com.ssung.travelDiary.domain.members;

import com.ssung.travelDiary.dto.members.MemberUpdateRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;
    private String picture;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(String nickname, String password, String email, String picture, Role role) {
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public Member update(MemberUpdateRequestDto entity) {
        this.nickname = entity.getNickname();
        this.password = entity.getPassword();
        this.email = entity.getEmail();
        this.picture = entity.getPicture();

        return this;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", picture='" + picture + '\'' +
                ", role=" + role +
                '}';
    }
}
