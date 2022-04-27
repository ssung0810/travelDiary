package com.ssung.travelDiary.service.members;

import com.ssung.travelDiary.domain.image.Image;
import com.ssung.travelDiary.domain.image.ImageRepository;
import com.ssung.travelDiary.domain.members.Member;
import com.ssung.travelDiary.domain.members.MemberRepository;
import com.ssung.travelDiary.domain.members.Role;
import com.ssung.travelDiary.handler.FileHandler;
import com.ssung.travelDiary.web.members.dto.MemberResponseDto;
import com.ssung.travelDiary.web.members.dto.MemberSaveRequestDto;
import com.ssung.travelDiary.web.members.dto.MemberUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static com.ssung.travelDiary.domain.image.Image.createMemberImage;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final ImageRepository imageRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileHandler fileHandler;

    /**
     * 회원가입
     */
    @Transactional
    public Long sign(MemberSaveRequestDto dto) throws IOException {
        Image image = createMemberImage(fileHandler.storeFile(dto.getImage()));

        imageRepository.save(image);
        Member member = createMember(dto, image);

        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 유저 검색
     */
    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
    }

    /**
     * 유저 별명 검색
     */
    public MemberResponseDto findByUsername(String username) {
        Member member = memberRepository.findByUsername(username)
                .orElse(null);

        return new MemberResponseDto(member);
    }

    /**
     * 프로필 정보 변경
     */
    @Transactional
    public Member update(MemberUpdateRequestDto requestDto) throws IOException {
        Member member = memberRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        Image image = createMemberImage(fileHandler.storeFile(requestDto.getImage()));
        imageRepository.save(image);

        requestDto.setPassword(encodePassword(requestDto.getPassword()));

        return member.update(requestDto, image);
    }

    /**
     * 아이디 및 비밀번호 체크
     */
    public Member loginValidation(String username, String password) {

        Member member = memberRepository.findByUsername(username)
                .orElse(null);

        if(member == null) return null;

        if(!validationPassword(password, member.getPassword())) return null;

        return member;
    }

    private boolean validationPassword(String password, String encodedPassword) {
        return passwordEncoder.matches(password, encodedPassword);
    }

    private Member createMember(MemberSaveRequestDto dto, Image image) {
        return Member.builder()
                .username(dto.getUsername())
                .password(encodePassword(dto.getPassword()))
                .email(dto.getEmail())
                .image(image)
                .role(Role.USER)
                .build();
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
