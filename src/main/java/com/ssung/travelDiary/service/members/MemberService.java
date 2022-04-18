package com.ssung.travelDiary.service.members;

import com.ssung.travelDiary.domain.members.Member;
import com.ssung.travelDiary.domain.members.MemberRepository;
import com.ssung.travelDiary.domain.members.Role;
import com.ssung.travelDiary.file.FileDto;
import com.ssung.travelDiary.file.FileHandler;
import com.ssung.travelDiary.web.members.dto.MemberSaveRequestDto;
import com.ssung.travelDiary.web.members.dto.MemberUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

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
    public Long sign(MemberSaveRequestDto dto) throws IOException {
        Member member = createMember(dto);

        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 유저 검색
     */
    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다."));
    }

    /**
     * 유저 별명 검색
     */
    public Member findByUsername(String username) {
        Member member = memberRepository.findByUsername(username)
                .orElse(null);
        return member;
    }

    /**
     * 전체 유저 검색
     */
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    /**
     * 프로필 정보 변경
     */
    public Member update(Long memberId, MemberUpdateRequestDto requestDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다."));

        Member updateMember = member.update(requestDto);

        return updateMember;
    }

    /**
     * 아이디 및 비밀번호 체크
     */
    public Member memberLogin(String username, String password) {

        Member member = memberRepository.findByUsername(username)
                .orElse(null);

        if(member == null) return null;

        boolean validation = validationPassword(password, member.getPassword());

        return member;
    }

    private boolean validationPassword(String password, String encodedPassword) {
        return passwordEncoder.matches(password, encodedPassword);
    }

    private Member createMember(MemberSaveRequestDto dto) throws IOException {
        FileDto image = fileHandler.storeFile(dto.getImage());

        return Member.builder()
                .username(dto.getUsername())
                .password(encodePassword(dto.getPassword()))
                .email(dto.getEmail())
                .imageFile(image)
                .role(Role.USER)
                .build();
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
