package com.ssung.travelDiary.dto.members;

import com.ssung.travelDiary.domain.members.Role;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class MemberSaveRequestDto {

    @NotEmpty(message = "별명을 입력해주세요")
    private String nickname;
    private String password;
    private String email;
    private String picture;
    private Role role;

    @Builder
    public MemberSaveRequestDto(String nickname, String password, String email, String picture, Role role) {
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }
}
