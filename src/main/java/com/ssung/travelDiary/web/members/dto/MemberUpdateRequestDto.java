package com.ssung.travelDiary.web.members.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Pattern;

@Getter @Setter
public class MemberUpdateRequestDto {

    private String username;
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}")
    private String password;

    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}")
    private String password_check;

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$")
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
