package com.ssung.travelDiary.service.board;

import com.ssung.travelDiary.domain.board.Board;
import com.ssung.travelDiary.domain.members.Role;
import com.ssung.travelDiary.service.members.MemberService;
import com.ssung.travelDiary.web.board.dto.BoardSaveRequestDto;
import com.ssung.travelDiary.web.members.dto.MemberSaveRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
class BoardServiceTest {

    @Autowired BoardService boardService;
    @Autowired MemberService memberService;

    @Test
    public void 여행일지_등록() throws Exception {
        // given
        BoardSaveRequestDto boardSaveRequestDto = new BoardSaveRequestDto(
                "title", "content", "location", new ArrayList<>(), "2022-04-29"
        );

        MockMultipartFile multipartFile = new MockMultipartFile("null", new byte[]{});
        MemberSaveRequestDto dto = new MemberSaveRequestDto("username", "password", "email", multipartFile, Role.USER);
        Long memberId = memberService.sign(dto);

        boardService.save(boardSaveRequestDto, memberId);

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