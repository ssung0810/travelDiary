package com.ssung.travelDiary.domain.board;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssung.travelDiary.domain.image.Image;
import com.ssung.travelDiary.domain.image.QImage;
import com.ssung.travelDiary.domain.members.QMember;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.ssung.travelDiary.domain.board.QBoard.board;
import static com.ssung.travelDiary.domain.image.QImage.image1;
import static com.ssung.travelDiary.domain.members.QMember.member;

@Repository
public class BoardRepositoryImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public BoardRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<Board> findByMember_idAndDate(Long memberId, String date) {
        return queryFactory
                .selectFrom(board)
                .distinct()
                .leftJoin(board.images, image1).fetchJoin()
                .join(board.member, member)
                .where(
                        board.date.eq(date)
                        .and(member.id.eq(memberId))
                )
                .fetch();
    }

    @Override
    public List<Board> findByMemberIdAndMoreType(Long memberId, String title) {
        return queryFactory
                .selectFrom(board)
                .join(board.member, member)
                .where(board.title.contains(title).and(member.id.eq(memberId)))
                .fetch();
    }

}
