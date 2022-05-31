package com.ssung.travelDiary.domain.share;

import com.ssung.travelDiary.domain.board.BoardRepository;
import com.ssung.travelDiary.domain.members.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class ShareRepositoryTest {

    @Autowired
    ShareRepository shareRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;

//    private ShareMember createShareMember() {
//
//    }
}