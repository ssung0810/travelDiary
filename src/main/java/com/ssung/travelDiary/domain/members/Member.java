package com.ssung.travelDiary.domain.members;

import com.ssung.travelDiary.domain.BaseTimeEntity;
import com.ssung.travelDiary.file.FileDto;
import com.ssung.travelDiary.web.members.dto.MemberUpdateRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Embedded
    private FileDto imageFile;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(String username, String password, String email, FileDto imageFile, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.imageFile = imageFile;
        this.role = role;
    }

    public Member update(MemberUpdateRequestDto entity, FileDto imageFile) {
        this.username = entity.getUsername();
        this.password = entity.getPassword();
        this.email = entity.getEmail();

        if(imageFile != null) updateImage(imageFile);

        return this;
    }

    private void updateImage(FileDto imageFile) {
        this.imageFile = imageFile;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", imageFile=" + imageFile +
                ", role=" + role +
                '}';
    }
}
