package com.ssung.travelDiary.web.login.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
public class MemberLoginRequestDto {

    private String username;
    private String password;

    public MemberLoginRequestDto() {

    }

    public MemberLoginRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "MemberLoginRequestDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
