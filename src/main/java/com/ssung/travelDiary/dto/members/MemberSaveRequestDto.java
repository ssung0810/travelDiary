package com.ssung.travelDiary.dto.members;

import com.ssung.travelDiary.domain.members.Role;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class MemberSaveRequestDto {

    @NotEmpty(message = "별명을 입력해주세요")
    private String username;
    private String password;
    private String email;
    private String picture;
    private Role role;

    @Builder
    public MemberSaveRequestDto(String username, String password, String email, String picture, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }
}
