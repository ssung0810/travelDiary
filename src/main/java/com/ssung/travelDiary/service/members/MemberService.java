package com.ssung.travelDiary.service.members;

import com.ssung.travelDiary.constancy.ErrorMessageConst;
import com.ssung.travelDiary.domain.members.Member;
import com.ssung.travelDiary.domain.members.MemberRepository;
import com.ssung.travelDiary.domain.members.Role;
import com.ssung.travelDiary.dto.file.FileDto;
import com.ssung.travelDiary.dto.member.MemberResponseDto;
import com.ssung.travelDiary.dto.member.MemberSaveRequestDto;
import com.ssung.travelDiary.dto.member.MemberUpdateRequestDto;
import com.ssung.travelDiary.exception.member.MemberEmailAlreadyExistException;
import com.ssung.travelDiary.exception.member.MemberNotFoundException;
import com.ssung.travelDiary.exception.member.MemberUsernameAlreadyExistException;
import com.ssung.travelDiary.service.file.FileUploadService;
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
    private final FileUploadService fileUploadService;

    /**
     * 회원가입
     */
    @Transactional
    public Long save(MemberSaveRequestDto dto) throws IOException, MemberEmailAlreadyExistException {
        MemberUsernameValidation(dto.getUsername());
        MemberEmailValidation(dto.getEmail());

        FileDto fileDto = fileUploadService.storeFile(dto.getImage());
        Member member = createMember(dto, fileDto);
        memberRepository.save(member);

        return member.getId();
    }

    /**
     * 유저 검색
     */
    public MemberResponseDto findOne(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException(ErrorMessageConst.MemberNotFoundException));
        return new MemberResponseDto(member);
    }

    /**
     * 유저 별명 검색
     */
    public MemberResponseDto findByUsername(String username) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new MemberNotFoundException(ErrorMessageConst.MemberNotFoundException));

        return new MemberResponseDto(member);
    }

    /**
     * 프로필 정보 변경
     */
    @Transactional
    public MemberResponseDto update(MemberUpdateRequestDto requestDto, Long memberId) throws IOException {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorMessageConst.MemberNotFoundException));

        if(!requestDto.getUsername().equals(member.getUsername())) MemberUsernameValidation(requestDto.getUsername());
        if(!requestDto.getEmail().equals(member.getEmail())) MemberEmailValidation(requestDto.getEmail());

        FileDto fileDto = fileUploadService.storeFile(requestDto.getImage());
//        FileDto fileDto = fileHandler.storeFile(requestDto.getImage());

        requestDto.setPassword(encodePassword(requestDto.getPassword()));

        return new MemberResponseDto(member.update(requestDto, fileDto));
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

    /**
     * 공유폴더 등록할 회원 조회
     */
    public List<MemberResponseDto> addMemberSearch(Long memberId, String value) {
        return memberRepository.findByMemberIdAndMoreType(memberId, value).stream()
                .map(m -> new MemberResponseDto(m)).collect(Collectors.toList());
    }

    private void MemberUsernameValidation(String username) {
        if(memberRepository.existsByUsername(username)) throw new MemberUsernameAlreadyExistException(ErrorMessageConst.MemberUsernameAlreadyExistException);
    }

    private void MemberEmailValidation(String email) {
        if(memberRepository.existsByEmail(email)) throw new MemberEmailAlreadyExistException(ErrorMessageConst.MemberEmailAlreadyExistException);
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
