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

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;

    @OneToMany(mappedBy = "member")
    private List<Board> boards = new ArrayList<>();

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(String username, String password, String email, Image image, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.image = image;
        this.role = role;

//        image.addMember(this);
    }

    public Member update(MemberUpdateRequestDto entity, Image image) {
        this.username = entity.getUsername();
        this.password = entity.getPassword();
        this.email = entity.getEmail();

        if(image != null) this.image = image;

        return this;
    }

//    private void updateImage(FileDto imageFile) {
//        this.imageFile = imageFile;
//    }

    public String getRoleKey() {
        return this.role.getKey();
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
//                ", imageFile=" + imageFile +
                ", role=" + role +
                '}';
    }
}
