package com.ssung.travelDiary.web.members.dto;

import com.ssung.travelDiary.domain.members.Member;
import com.ssung.travelDiary.web.file.FileDto;
import lombok.Getter;

@Getter
public class MemberResponseDto {

    private Long id;
    private String password;
    private String username;
    private String email;
    private FileDto image;

    public MemberResponseDto(Member entity) {
        if (entity == null) return;

        this.id = entity.getId();
        this.password = entity.getPassword();
        this.username = entity.getUsername();
        this.email = entity.getEmail();

        if(entity.getImage() != null) this.image = entity.getImage();
    }
}
