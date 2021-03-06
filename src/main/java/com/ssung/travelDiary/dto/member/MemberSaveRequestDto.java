package com.ssung.travelDiary.dto.member;

import com.ssung.travelDiary.domain.members.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Pattern;

@Getter @Setter
public class MemberSaveRequestDto {

    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{4,10}$")
    private String username;

    private int username_validation;

    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}")
    private String password;

    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}")
    private String password_check;

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$")
    private String email;

    private MultipartFile image;

    private Role role;

    public MemberSaveRequestDto() {
    }

    public MemberSaveRequestDto(String username, String password, String email, MultipartFile image, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.image = image;
        this.role = role;
    }
}
