package com.ssung.travelDiary.web.members.dto;

import com.ssung.travelDiary.domain.members.Member;
import lombok.Getter;

@Getter
public class MemberUpdateRequestDto {

    private String username;
    private String password;
    private String email;
    private String image;

    public MemberUpdateRequestDto(String username, String password, String email, String image) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.image = image;
    }
}
