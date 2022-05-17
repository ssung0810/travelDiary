package com.ssung.travelDiary.dto.login;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class MemberLoginRequestDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public MemberLoginRequestDto() {

    }

    public MemberLoginRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
