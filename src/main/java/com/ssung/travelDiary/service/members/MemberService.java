package com.ssung.travelDiary.service.members;

import com.ssung.travelDiary.domain.members.Member;
import com.ssung.travelDiary.domain.members.MemberRepository;
import com.ssung.travelDiary.domain.members.Role;
import com.ssung.travelDiary.dto.members.MemberSaveRequestDto;
import com.ssung.travelDiary.dto.members.MemberUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public Long sign(@Valid MemberSaveRequestDto dto) {
        Member member = Member.builder()
                .nickname(dto.getNickname())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .picture(dto.getPicture())
                .role(Role.GUEST)
                .build();

        memberRepository.save(member);
        return member.getId();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다."));
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Member update(Long memberId, MemberUpdateRequestDto requestDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다."));

        Member updateMember = member.update(requestDto);

        return updateMember;
    }

    public void delete(Long memberId) {
        Member member = findOne(memberId);
        memberRepository.delete(member);
    }

    public String loginCheck(String nickname, String password) {
        String findPassword = memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다"));

        if(!password.equals(findPassword)) return "비밀번호가 잘못되었습니다.";

        return findPassword;
    }
}
