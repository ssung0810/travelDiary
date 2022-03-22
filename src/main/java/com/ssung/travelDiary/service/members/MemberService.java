package com.ssung.travelDiary.service.members;

import com.ssung.travelDiary.domain.members.Member;
import com.ssung.travelDiary.domain.members.MemberRepository;
import com.ssung.travelDiary.domain.members.Role;
import com.ssung.travelDiary.web.members.dto.MemberSaveRequestDto;
import com.ssung.travelDiary.web.members.dto.MemberUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원가입
     */
    public Long sign(Member member) {

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
        return memberRepository.findByUsername(username)
                .orElse(null);
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
     * 아이디 및 비밀번호 체크
     */
    public Member memberLogin(String username, String password) {

        return memberRepository.findByUsername(username)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
}
