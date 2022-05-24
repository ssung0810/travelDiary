package com.ssung.travelDiary.service.members;

import com.ssung.travelDiary.domain.members.Member;
import com.ssung.travelDiary.domain.members.MemberRepository;
import com.ssung.travelDiary.domain.members.Role;
import com.ssung.travelDiary.exception.member.MemberEmailAlreadyExistException;
import com.ssung.travelDiary.exception.member.MemberNotFoundException;
import com.ssung.travelDiary.exception.member.MemberUsernameAlreadyExistException;
import com.ssung.travelDiary.handler.FileHandler;
import com.ssung.travelDiary.dto.file.FileDto;
import com.ssung.travelDiary.dto.member.MemberResponseDto;
import com.ssung.travelDiary.dto.member.MemberSaveRequestDto;
import com.ssung.travelDiary.dto.member.MemberUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
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
    public Long sign(MemberSaveRequestDto dto) throws IOException, MemberEmailAlreadyExistException {
        Member member = null;

        FileDto fileDto = fileHandler.storeFile(dto.getImage());

        member = createMember(dto, fileDto);

        MemberEmailValidation(dto.getEmail());
        memberRepository.save(member);

        return member.getId();
    }

    /**
     * 유저 검색
     */
    public MemberResponseDto findOne(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원입니다."));
        return new MemberResponseDto(member);
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
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원입니다."));

        FileDto fileDto = fileHandler.storeFile(requestDto.getImage());

        requestDto.setPassword(encodePassword(requestDto.getPassword()));
//        if(requestDto.getPassword() == null) requestDto.setPassword(member.getPassword());
//        else requestDto.setPassword(encodePassword(requestDto.getPassword()));

        return member.update(requestDto, fileDto);
    }

    /**
     * 아이디 및 비밀번호 체크
     */
    public MemberResponseDto loginValidation(String username, String password) {

        Member member = memberRepository.findByUsername(username)
                .orElse(null);

        if(member == null) return null;

        if(!validationPassword(password, member.getPassword())) return null;

        return new MemberResponseDto(member);
    }

    private void MemberUsernameValidation(String username) {
        if(memberRepository.existsByUsername(username)) throw new MemberUsernameAlreadyExistException("이미 존재하는 회원입니다.");
    }

    private void MemberEmailValidation(String email) {
        if(memberRepository.existsByEmail(email)) throw new MemberEmailAlreadyExistException("이미 존재하는 이메일입니다.");
    }

    /**
     * 공유폴더 등록할 회원 조회
     */
    public List<MemberResponseDto> addMemberSearch(Long memberId, String value) {
        return memberRepository.findByMemberIdAndMoreType(memberId, value).stream()
                .map(m -> new MemberResponseDto(m)).collect(Collectors.toList());
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

    private boolean validationPassword(String password, String encodedPassword) {
        return passwordEncoder.matches(password, encodedPassword);
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }


}
