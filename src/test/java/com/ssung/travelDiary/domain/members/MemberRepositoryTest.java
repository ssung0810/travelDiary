package com.ssung.travelDiary.domain.members;

import com.ssung.travelDiary.dto.file.FileDto;
import com.ssung.travelDiary.dto.member.MemberUpdateRequestDto;
import com.ssung.travelDiary.exception.member.MemberNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired private MemberRepository memberRepository;
    @PersistenceContext EntityManager em;

    @Test
    public void 회원_저장() throws Exception {
        // given
        Member member = createMember();
        memberRepository.save(member);
        clear();

        // when
        Member findMember = memberRepository.findById(member.getId())
                .orElseThrow(MemberNotFoundException::new);

        // then
        assertThat(findMember.getId()).isEqualTo(member.getId());
    }
    
    @Test
    public void 회원_수정() throws Exception {
        // given
        Member member = createMember();
        memberRepository.save(member);
        clear();

        Member findMember = memberRepository.getById(member.getId());
        MemberUpdateRequestDto requestDto = new MemberUpdateRequestDto();
        String updateUsername = "username2";
        String updatePassword = "password2";
        String updateEmail = "email2";
        requestDto.setUsername(updateUsername);
        requestDto.setPassword(updatePassword);
        requestDto.setEmail(updateEmail);

        // when
        Member updateMember = findMember.update(requestDto, null);
        clear();
        Member resultMember = memberRepository.getById(updateMember.getId());

        // then
        assertThat(resultMember.getUsername()).isEqualTo(updateUsername);
    }

    @Test
    public void 회원_삭제() throws Exception {
        // given
        Member member = memberRepository.save(createMember());
        clear();

        // when
        memberRepository.delete(member);
        Optional<Member> findMember = memberRepository.findById(member.getId());

        // then
        assertThat(findMember.isEmpty()).isEqualTo(true);
    }

    @Test
    public void 별명으로_조회() throws Exception {
        // given
        Member member = createMember();
        memberRepository.save(member);
        clear();

        // when
        Member findMember = memberRepository.findByUsername("username")
                .orElseThrow(MemberNotFoundException::new);

        // then
        assertThat(findMember.getId()).isEqualTo(member.getId());
    }
    
    @Test
    public void 해당_별명이_존재하는지_여부확인() throws Exception {
        // given
        Member member = createMember();
        memberRepository.save(member);
        clear();

        // when
        boolean username = memberRepository.existsByUsername("username");
        boolean username2 = memberRepository.existsByUsername("usernameTest");

        // then
        assertThat(username).isTrue();
        assertThat(username2).isFalse();
    }

    @Test
    public void 해당_이메일이_존재하는지_여부확인() throws Exception {
        // given
        Member member = createMember();
        memberRepository.save(member);
        clear();

        // when
        boolean email = memberRepository.existsByEmail("email");
        boolean email2 = memberRepository.existsByUsername("email2");

        // then
        assertThat(email).isTrue();
        assertThat(email2).isFalse();
    }

    @Test
    void 본인을_제외하고_별명으로_검색() throws Exception {
        // given
        Member member = createMember();
        memberRepository.save(member);
        clear();

        // when
        List<Member> EmptyMembers = memberRepository.findByMemberIdAndMoreType(1L, "");
        List<Member> members = memberRepository.findByMemberIdAndMoreType(2L, "");

        // then
        assertThat(EmptyMembers.size()).isEqualTo(0);
        assertThat(members.size()).isEqualTo(1);
    }


    @Test
    public void 별명_유니크키_확인() throws Exception {
        // given
        memberRepository.save(createMember());
        clear();

        // when
        Member member2 = createMember("username", "password2", "email2");

        // then
        assertThatThrownBy(() -> memberRepository.save(member2)).isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    public void 이메일_유니크키_확인() throws Exception {
        // given
        memberRepository.save(createMember());
        clear();

        // when
        Member member2 = createMember("username2", "password2", "email");

        // then
        assertThatThrownBy(() -> memberRepository.save(member2)).isInstanceOf(DataIntegrityViolationException.class);
    }

    public void clear() {
        em.flush();
        em.clear();
    }

    public Member createMember() {
        return Member.builder()
                .username("username")
                .password("password")
                .email("email")
                .role(Role.USER)
                .build();
    }
    public Member createMember(String username, String password, String email) {
        return Member.builder()
                .username(username)
                .password(password)
                .email(email)
                .role(Role.USER)
                .build();
    }
}