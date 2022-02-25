package com.ssung.travelDiary.dto.members;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberLoginRequestDto {

    private String username;
    private String password;

    public MemberLoginRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
