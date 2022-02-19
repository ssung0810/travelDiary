package com.ssung.travelDiary.service.members;

import com.ssung.travelDiary.domain.members.Member;
import com.ssung.travelDiary.domain.members.MemberRepository;
import com.ssung.travelDiary.domain.members.Role;
import com.ssung.travelDiary.dto.members.MemberSaveRequestDto;
import com.ssung.travelDiary.dto.members.MemberUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원가입
     */
    public Long sign(@Valid MemberSaveRequestDto dto) {
        Member member = Member.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .picture(dto.getPicture())
                .role(Role.GUEST)
                .build();

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
     * 유저 삭제
     */
    public void delete(Long memberId) {
        Member member = findOne(memberId);
        memberRepository.delete(member);
    }

    /**
     * 로그인
     */
    public String memberLogin(String username, String password) {
        log.info("====2===== username = {}, password = {}", username, password);
        String findPassword = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다."));

        if(!password.equals(findPassword)) return "비밀번호가 잘못되었습니다.";

        return findPassword;
    }
}
