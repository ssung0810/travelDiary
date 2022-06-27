package com.ssung.travelDiary.domain.share;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssung.travelDiary.domain.board.Board;
import com.ssung.travelDiary.domain.board.QBoard;
import com.ssung.travelDiary.domain.image.QImage;
import com.ssung.travelDiary.domain.members.QMember;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.ssung.travelDiary.domain.board.QBoard.board;
import static com.ssung.travelDiary.domain.image.QImage.image1;
import static com.ssung.travelDiary.domain.members.QMember.member;
import static com.ssung.travelDiary.domain.share.QShare.share;
import static com.ssung.travelDiary.domain.share.QShareBoard.shareBoard;
import static com.ssung.travelDiary.domain.share.QShareMember.shareMember;

@Repository
public class ShareRepositoryImpl implements ShareRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ShareRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public List<Share> findList(Long memberId) {
        return queryFactory
                .selectFrom(share)
                .join(share.shareMember, shareMember)
                .join(shareMember.member, member)
                .where(member.id.eq(memberId))
                .fetch();
    }

    @Override
    public List<Board> findShareBoard(Long share_id, String date) {
        return queryFactory
                .select(board)
                .distinct()
                .from(share)
                .join(share.shareBoard, shareBoard).on(share.id.eq(share_id))
                .join(shareBoard.board, board)
                .leftJoin(board.images, image1).fetchJoin()
                .where(board.date.eq(date))
                .fetch();
    }
}
