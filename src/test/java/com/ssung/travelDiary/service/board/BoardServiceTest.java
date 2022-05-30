package com.ssung.travelDiary.service.board;

import com.ssung.travelDiary.domain.board.Board;
import com.ssung.travelDiary.domain.image.Image;
import com.ssung.travelDiary.domain.image.ImageRepository;
import com.ssung.travelDiary.domain.members.Role;
import com.ssung.travelDiary.exception.board.BoardNotFountException;
import com.ssung.travelDiary.service.members.MemberService;
import com.ssung.travelDiary.dto.board.BoardResponseDto;
import com.ssung.travelDiary.dto.board.BoardSaveRequestDto;
import com.ssung.travelDiary.dto.board.BoardUpdateRequestDto;
import com.ssung.travelDiary.dto.member.MemberSaveRequestDto;
import com.ssung.travelDiary.dto.share.ShareBoardResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class BoardServiceTest {

    @Autowired BoardService boardService;
    @Autowired MemberService memberService;

    static Long memberId;

    @Test
    public void 여행일지_등록_이미지없음() throws Exception {
        // given
        BoardSaveRequestDto boardSaveRequestDto = new BoardSaveRequestDto(
                "title", "content", "location", new ArrayList<>(), LocalDate.now().toString()
        );

        MockMultipartFile multipartFile = new MockMultipartFile("null", new byte[]{});
        MemberSaveRequestDto dto = new MemberSaveRequestDto("username", "password", "email", multipartFile, Role.USER);
        Long memberId = memberService.save(dto);

        boardService.save(boardSaveRequestDto, memberId);

        // when
        Board findBoard = boardService.findAll().get(0);

        // then
        assertThat(findBoard.getContent()).isEqualTo("content");
        assertThat(findBoard.getImages().size()).isEqualTo(0);
    }

    @Test
    public void 여행일지_등록_이미지있음() throws Exception {
        // given
        List<MultipartFile> images = new ArrayList<>();
        images.add(new MockMultipartFile("image1", "image.png", MediaType.IMAGE_PNG_VALUE, "image".getBytes()));
        images.add(new MockMultipartFile("image2", "image.png", MediaType.IMAGE_PNG_VALUE, "image".getBytes()));

        BoardSaveRequestDto boardSaveRequestDto = new BoardSaveRequestDto(
                "title", "content", "location", images, LocalDate.now().toString()
        );

        MockMultipartFile multipartFile = new MockMultipartFile("null", new byte[]{});
        MemberSaveRequestDto dto = new MemberSaveRequestDto("username", "password", "email", multipartFile, Role.USER);
        Long memberId = memberService.save(dto);

        boardService.save(boardSaveRequestDto, memberId);

        // when
        Board findBoard = boardService.findAll().get(0);
        List<Image> findImages = findBoard.getImages();

        // then
        assertThat(findBoard.getContent()).isEqualTo("content");
        assertThat(findBoard.getImages().size()).isEqualTo(2);
    }

    @Test
    void 개인_게시글_리스트_조회() throws Exception {
        // given
        BoardResponseDto board = createBoard();
        String date = LocalDate.now().toString();

        // when
        List<BoardResponseDto> boards = boardService.findList(memberId, date);

        // then
        assertThat(boards.size()).isEqualTo(1);
        assertThat(boards.get(0).getContent()).isEqualTo("content");
    }

    @Test
    void 게시글_단일조회() throws Exception {
        // given
        BoardResponseDto board = createBoard();

        // when
        BoardResponseDto findBoard = boardService.findOne(board.getId());

        // then
        assertThat(findBoard.getContent()).isEqualTo("content");
    }

    @Test
    void 게시글_수정() throws Exception {
        // given
        BoardResponseDto board = createBoard();
        List<MultipartFile> multipartFiles = List.of(
                new MockMultipartFile("image3", "image3", MediaType.IMAGE_PNG_VALUE, "image3".getBytes()),
                new MockMultipartFile("image4", "image4", MediaType.IMAGE_PNG_VALUE, "image4".getBytes())
        );
        BoardUpdateRequestDto updateRequestDto = new BoardUpdateRequestDto(
                "title2", "content2", "location2", multipartFiles, LocalDate.now().toString()
        );

        // when
        BoardResponseDto updateBoard = boardService.update(updateRequestDto, board.getId());
        BoardResponseDto findBoard = boardService.findOne(updateBoard.getId());

        // then
        assertThat(findBoard.getTitle()).isEqualTo("title2");
        assertThat(findBoard.getImages().size()).isEqualTo(2);
        assertThat(findBoard.getImages().get(0).getImages().getOriginalFileName()).isEqualTo("image3");
    }

    @Test
    void 게시글_삭제() throws Exception {
        // given
        BoardResponseDto board = createBoard();

        // when
        boardService.delete(board.getId());

        // then
        assertThatThrownBy(() -> boardService.findOne(board.getId())).isInstanceOf(BoardNotFountException.class);
    }

    @Test
    void 생성자에게_속한_공유_게시글_조회() throws Exception {
        // given
        BoardResponseDto board = createBoard();

        // when
        List<ShareBoardResponseDto> boardList = boardService.addBoardSearch(memberId, "");

        // then
        assertThat(boardList.get(0).getTitle()).isEqualTo("title");
    }

    private BoardResponseDto createBoard() throws IOException {
        List<MultipartFile> multipartFiles = List.of(
                new MockMultipartFile("image1", "image1", MediaType.IMAGE_PNG_VALUE, "image1".getBytes()),
                new MockMultipartFile("image2", "image2", MediaType.IMAGE_PNG_VALUE, "image2".getBytes())
        );

        BoardSaveRequestDto boardSaveRequestDto = new BoardSaveRequestDto(
                "title", "content", "location", multipartFiles, LocalDate.now().toString()
        );

        MockMultipartFile multipartFile = new MockMultipartFile("null", new byte[]{});
        MemberSaveRequestDto dto = new MemberSaveRequestDto("username", "password", "email", multipartFile, Role.USER);
        memberId = memberService.save(dto);

        return boardService.save(boardSaveRequestDto, memberId);
    }

}