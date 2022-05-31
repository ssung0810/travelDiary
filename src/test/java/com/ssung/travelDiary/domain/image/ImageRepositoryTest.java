package com.ssung.travelDiary.domain.image;

import com.ssung.travelDiary.domain.board.Board;
import com.ssung.travelDiary.domain.board.BoardRepository;
import com.ssung.travelDiary.domain.members.Member;
import com.ssung.travelDiary.domain.members.MemberRepository;
import com.ssung.travelDiary.domain.members.Role;
import com.ssung.travelDiary.dto.file.FileDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
class ImageRepositoryTest {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;

    private final String boardDate = LocalDate.now().toString();
    private final String originalName = "file";

    @Test
    void Board의_cascade를_이용한_이미지_저장() throws Exception {
        // given
        Board board = createBoard();
        boardRepository.save(board);
        clear();

        // when
        List<Image> images = board.getImages();

        // then
        assertThat(images.size()).isEqualTo(1);
        assertThat(images.get(0).getImage().getOriginalFileName()).isEqualTo(originalName);
    }

    @Test
    void Board의_cascade를_이용한_이미지_삭제() throws Exception {
        // given
        Board board = createBoard();
        boardRepository.save(board);
        clear();

        List<Image> images = board.getImages();
        assertThat(images.size()).isEqualTo(1);

        Image image = images.get(0);

        // when
        boardRepository.delete(board);
        clear();
        Optional<Image> findImage = imageRepository.findById(image.getId());

        // then
        assertThat(findImage).isEmpty();
    }

    private void clear() {
        em.flush();
        em.clear();
    }

    private Board createBoard() {
        List<FileDto> fileDtoList = List.of(new FileDto(originalName, "storedFile"));

        return Board.builder()
                .title("title")
                .content("content")
                .location("location")
                .date(boardDate)
                .member(createMember())
                .images(fileDtoList)
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