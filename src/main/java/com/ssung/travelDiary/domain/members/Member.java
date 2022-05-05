package com.ssung.travelDiary.domain.members;

import com.ssung.travelDiary.domain.BaseTimeEntity;
import com.ssung.travelDiary.domain.board.Board;
import com.ssung.travelDiary.domain.image.Image;
import com.ssung.travelDiary.domain.share.Share;
import com.ssung.travelDiary.web.file.FileDto;
import com.ssung.travelDiary.web.members.dto.MemberUpdateRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Embedded
    private FileDto image;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Board> boards = new ArrayList<>();

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(String username, String password, String email, FileDto image, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.image = image;
        this.role = role;
    }

    public Member update(MemberUpdateRequestDto entity, FileDto image) {
        this.username = entity.getUsername();
        this.password = entity.getPassword();
        this.email = entity.getEmail();

        if(image != null) this.image = image;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
