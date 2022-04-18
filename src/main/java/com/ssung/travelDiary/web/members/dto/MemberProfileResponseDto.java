package com.ssung.travelDiary.web.members.dto;

import com.ssung.travelDiary.domain.members.Member;
import com.ssung.travelDiary.file.FileDto;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberProfileResponseDto {

    private Long id;
    private String password;
    private String username;
    private String email;
    private FileDto image;

    public MemberProfileResponseDto(Member entity) {
        this.id = entity.getId();
        this.password = entity.getPassword();
        this.username = entity.getUsername();
        this.email = entity.getEmail();
        this.image = entity.getImageFile();
    }
}
