package com.ssung.travelDiary.domain.share;

import com.ssung.travelDiary.domain.board.Board;
import com.ssung.travelDiary.domain.board.BoardRepository;
import com.ssung.travelDiary.domain.members.Member;
import com.ssung.travelDiary.domain.members.MemberRepository;
import com.ssung.travelDiary.domain.members.Role;
import com.ssung.travelDiary.dto.file.FileDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
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

    @Test
    void 공유폴더_생성() throws Exception {
        // given
        Share share = Share.builder().title("shareTitle").creator("shareCreator").build();
        ShareBoard shareBoard = createShareBoard("title", "content", "location", share);
        ShareMember shareMember1 = createShareMember("username1", "password1", "email1", share);
        ShareMember shareMember2 = createShareMember("username2", "password2", "email2", share);
        share = createShare(share, shareBoard, shareMember1, shareMember2);

        shareRepository.save(share);
        clear();

        // when
        Share findShare = shareRepository.findById(share.getId()).orElseThrow(IllegalArgumentException::new);

        // then
        assertThat(findShare.getShareBoard().size()).isEqualTo(1);
        assertThat(findShare.getShareMember().size()).isEqualTo(2);
    }

    @Test
    void 공유폴더_리스트_출력() throws Exception {
        // given
        Share share = Share.builder().title("shareTitle").creator("shareCreator").build();
        ShareBoard shareBoard = createShareBoard("title", "content", "location", share);
        ShareMember shareMember1 = createShareMember("username1", "password1", "email1", share);
        ShareMember shareMember2 = createShareMember("username2", "password2", "email2", share);
        share = createShare(share, shareBoard, shareMember1, shareMember2);

        shareRepository.save(share);
        clear();

        // when
        List<Share> shareList = shareRepository.findList(shareMember1.getMember().getId());

        // then
        assertThat(shareList.size()).isEqualTo(1);
        assertThat(shareList.get(0).getTitle()).isEqualTo("shareTitle");
    }

    @Test
    void 공유폴더_선택시_해당하는_게시글_출력() throws Exception {
        // given
        Share share = Share.builder().title("shareTitle").creator("shareCreator").build();
        ShareBoard shareBoard = createShareBoard("title", "content", "location", share);
        ShareMember shareMember1 = createShareMember("username1", "password1", "email1", share);
        ShareMember shareMember2 = createShareMember("username2", "password2", "email2", share);
        share = createShare(share, shareBoard, shareMember1, shareMember2);

        shareRepository.save(share);
        clear();

        // when
        List<Board> boardList = shareRepository.findShareBoard(share.getId(), LocalDate.now().toString());

        // then
        assertThat(boardList.size()).isEqualTo(1);
        assertThat(boardList.get(0).getTitle()).isEqualTo("title");
    }

    private void clear() {
        em.flush();
        em.clear();
    }

    private Share createShare(Share share, ShareBoard shareBoard, ShareMember... shareMembers) {
        share.getShareBoard().add(shareBoard);

        for (ShareMember shareMember : shareMembers) {
            share.getShareMember().add(shareMember);
        }

        return share;
    }

    private ShareMember createShareMember(String username, String password, String email, Share share) {
        Member member = Member.builder()
                .username(username)
                .password(password)
                .email(email)
                .image(null)
                .role(Role.USER)
                .build();

        memberRepository.save(member);
        clear();

        return ShareMember.createShareMember(member, share);
    }

    private ShareBoard createShareBoard(String title, String content, String location, Share share) {
        Board board = Board.builder()
                .title("title")
                .content("content")
                .location("location")
                .date(LocalDate.now().toString())
                .images(new ArrayList<>())
                .build();

        boardRepository.save(board);
        clear();

        return ShareBoard.createShareBoard(board, share);
    }
}