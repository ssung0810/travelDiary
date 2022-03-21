package com.ssung.travelDiary.web.members.dto;

import com.ssung.travelDiary.domain.members.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
public class MemberUpdateRequestDto {

    private String username;
    private String password;
    private String email;
    private MultipartFile image;

    public MemberUpdateRequestDto() {
    }

    public MemberUpdateRequestDto(String username, String password, String email, MultipartFile image) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.image = image;
    }
}
