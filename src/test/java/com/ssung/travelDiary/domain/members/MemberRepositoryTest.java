package com.ssung.travelDiary.domain.members;

import com.ssung.travelDiary.exception.MemberNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

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
//        findMember.

        // when
        
        // then
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