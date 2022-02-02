package com.ssung.travelDiary.dto.members;

import com.ssung.travelDiary.domain.members.Member;
import lombok.Getter;

@Getter
public class MemberUpdateRequestDto {

    private String nickname;
    private String password;
    private String email;
    private String picture;

    public MemberUpdateRequestDto(String nickname, String password, String email, String picture) {
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.picture = picture;
    }
}
