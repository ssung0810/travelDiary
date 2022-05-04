package com.ssung.travelDiary.service.members;

import com.ssung.travelDiary.domain.members.Member;
import com.ssung.travelDiary.domain.members.MemberRepository;
import com.ssung.travelDiary.domain.members.Role;
import com.ssung.travelDiary.handler.FileHandler;
import com.ssung.travelDiary.web.file.FileDto;
import com.ssung.travelDiary.web.members.dto.MemberResponseDto;
import com.ssung.travelDiary.web.members.dto.MemberSaveRequestDto;
import com.ssung.travelDiary.web.members.dto.MemberUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileHandler fileHandler;

    /**
     * 회원가입
     */
    @Transactional
    public Long sign(MemberSaveRequestDto dto) throws IOException {
        FileDto fileDto = fileHandler.storeFile(dto.getImage());

        Member member = createMember(dto, fileDto);
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

        FileDto fileDto = fileHandler.storeFile(requestDto.getImage());

        requestDto.setPassword(encodePassword(requestDto.getPassword()));

        return member.update(requestDto, fileDto);
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

    /**
     * 공유폴더 등록할 회원 조회
     */
    public List<MemberResponseDto> findShareMember(Long memberId) {
        return memberRepository.findByIdNot(memberId).stream()
                .map(m -> new MemberResponseDto(m)).collect(Collectors.toList());
    }
    public List<Member> findShareMember2(Long memberId) {
        return memberRepository.findByIdNot(memberId);
    }

    private boolean validationPassword(String password, String encodedPassword) {
        return passwordEncoder.matches(password, encodedPassword);
    }

    private Member createMember(MemberSaveRequestDto dto, FileDto image) {
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
