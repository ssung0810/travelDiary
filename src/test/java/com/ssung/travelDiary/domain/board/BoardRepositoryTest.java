package com.ssung.travelDiary.domain.board;

import com.ssung.travelDiary.domain.members.Member;
import com.ssung.travelDiary.domain.members.MemberRepository;
import com.ssung.travelDiary.domain.members.Role;
import com.ssung.travelDiary.exception.board.BoardNotFountException;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Transactional
public class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;

    private final String boardDate = LocalDate.now().toString();

    @Test
    void 게시글_저장() throws Exception {
        // given
        Board board = createBoard();
        boardRepository.save(board);
        clear();

        // when
        Board findBoard = boardRepository.findById(board.getId())
                .orElseThrow(() -> new BoardNotFountException(""));

        // then
        assertThat(board.getTitle()).isEqualTo(findBoard.getTitle());
    }

    @Test
    void 게시글_수정() throws Exception {
        // given
        Board board = createBoard();
        boardRepository.save(board);
        clear();

        // when
        String updateTitle = "title2";
        String updateContent = "content2";
        String updateLocation = "location2";
        Board findBoard = boardRepository.findById(board.getId()).orElseThrow(() -> new BoardNotFountException(""));
        Board updateBoard = findBoard.update(updateTitle, updateContent, updateLocation, boardDate, new ArrayList<>());
        clear();

        // then
        assertThat(findBoard.getTitle()).isEqualTo(updateBoard.getTitle());
    }

    @Test
    void 게시글_삭제() throws Exception {
        // given
        Board board = createBoard();
        boardRepository.save(board);
        clear();

        // when
        boardRepository.delete(board);
        clear();
        Optional<Board> findBoard = boardRepository.findById(board.getId());

        // then
        assertThat(findBoard).isEmpty();
    }

    @Test
    void member_id와_날짜로_조회() throws Exception {
        // given
        Board board = createBoard();
        boardRepository.save(board);
        clear();

        // when
        List<Board> boardList = boardRepository.findByMember_idAndDate(board.getMember().getId(), boardDate);

        // then
        assertThat(boardList.size()).isEqualTo(1);
    }

    @Test
    void member_id와_게시글_제목으로_조회() throws Exception {
        // given
        Board board = createBoard();
        boardRepository.save(board);
        clear();

        // when
        List<Board> boardList = boardRepository.findByMemberIdAndMoreType(board.getMember().getId(), board.getTitle());

        // then
        assertThat(boardList.size()).isEqualTo(1);
    }

    private void clear() {
        em.flush();
        em.clear();
    }

    private Board createBoard() {
        return Board.builder()
                .title("title")
                .content("content")
                .location("location")
                .date(boardDate)
                .member(createMember())
                .build();
    }

    private Board createBoard(String title, String content, String location) {
        return Board.builder()
                .title(title)
                .content(content)
                .location(location)
                .date(boardDate)
                .member(createMember())
                .build();
    }

    private Member createMember() {
        Member member = Member.builder()
                .username("username")
                .password("password")
                .email("email")
                .role(Role.USER)
                .build();

        memberRepository.save(member);
        clear();
        return member;
    }
}
