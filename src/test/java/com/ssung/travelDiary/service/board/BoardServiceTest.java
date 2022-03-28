package com.ssung.travelDiary.service.board;

import com.ssung.travelDiary.domain.board.Board;
import com.ssung.travelDiary.domain.board.TravelCategory;
import com.ssung.travelDiary.web.board.dto.BoardUpdateRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

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
                .date("2022-03-09")
                .build();

        boardService.save(board);

        // when
        Board findTravel = boardService.findAll().get(0);

        // then
        assertThat(findTravel.getContent()).isEqualTo("content");
    }

//    @Test
//    public void 여행일지_수정() throws Exception {
//        // given
//        Board board = Board.builder()
//                .username("username")
//                .title("title")
//                .content("content")
//                .location("location")
//                .date("2022-03-09")
//                .build();
//
//        Long boardId = boardService.save(board);
//        BoardUpdateRequestDto dto = new BoardUpdateRequestDto("title2", "content2", "location2", "2022-03-10");
//
//        // when
//        Board updateBoard = boardService.update(boardId, dto);
//
//        // then
//        assertThat(updateBoard.getTitle()).isEqualTo("title2");
//    }
}