package com.ssung.travelDiary.dto.members;

import com.ssung.travelDiary.domain.members.Member;
import lombok.Getter;

@Getter
public class MemberUpdateRequestDto {

    private String username;
    private String password;
    private String email;
    private String picture;

    public MemberUpdateRequestDto(String username, String password, String email, String picture) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.picture = picture;
    }
}
