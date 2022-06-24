package com.ssung.travelDiary.domain.members;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.ssung.travelDiary.domain.members.QMember.*;

public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Member> findByMemberIdAndMoreType(Long member_id, String value) {
        return queryFactory
                .selectFrom(member)
                .where(member.id.eq(member_id).not()
                        .and(member.username.contains(value))
                )
                .fetch();
    }
}
