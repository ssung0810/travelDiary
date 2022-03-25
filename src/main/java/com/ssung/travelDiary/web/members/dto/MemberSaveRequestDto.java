package com.ssung.travelDiary.web.members.dto;

import com.ssung.travelDiary.domain.members.Role;
import com.ssung.travelDiary.file.FileDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Pattern;
import java.util.List;

@Getter @Setter
public class MemberSaveRequestDto {

    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{2,10}$")
    private String username;

    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}")
    private String password;

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$")
    private String email;

    private MultipartFile multipartFile;

    private Role role;

    public MemberSaveRequestDto() {
    }

    public MemberSaveRequestDto(String username, String password, String email, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }
}
