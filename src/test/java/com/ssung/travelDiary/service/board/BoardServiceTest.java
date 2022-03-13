package com.ssung.travelDiary.service.board;

import com.ssung.travelDiary.domain.board.Board;
import com.ssung.travelDiary.domain.board.TravelCategory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Test
    public void 여행일지_등록() throws Exception {
        // given
        Board board = Board.builder()
                .username("username")
                .title("title")
                .content("content")
                .location("location")
                .image("image")
                .date(LocalDateTime.now())
                .build();

        boardService.save(board);

        // when
        Board findTravel = boardService.findAll().get(0);

        // then
        Assertions.assertThat(findTravel.getContent()).isEqualTo("content");
    }

    @Test
    public void 여행일지_수정() throws Exception {
        // given
        Board board = Board.builder()
                .username("username")
                .title("title")
                .content("content")
                .location("location")
                .image("image")
                .date(LocalDateTime.now())
                .build();

        boardService.save(board);

        // when

        // then
    }
}