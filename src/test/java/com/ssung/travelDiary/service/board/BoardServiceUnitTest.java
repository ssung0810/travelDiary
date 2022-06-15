package com.ssung.travelDiary.service.board;

import com.ssung.travelDiary.domain.board.Board;
import com.ssung.travelDiary.domain.board.BoardRepository;
import com.ssung.travelDiary.domain.image.ImageRepository;
import com.ssung.travelDiary.domain.members.Member;
import com.ssung.travelDiary.domain.members.MemberRepository;
import com.ssung.travelDiary.domain.members.Role;
import com.ssung.travelDiary.dto.board.BoardResponseDto;
import com.ssung.travelDiary.dto.board.BoardSaveRequestDto;
import com.ssung.travelDiary.dto.board.BoardUpdateRequestDto;
import com.ssung.travelDiary.dto.file.FileDto;
import com.ssung.travelDiary.dto.member.MemberSaveRequestDto;
import com.ssung.travelDiary.dto.share.ShareBoardResponseDto;
import com.ssung.travelDiary.exception.board.BoardNotFountException;
import com.ssung.travelDiary.exception.member.MemberNotFoundException;
import com.ssung.travelDiary.handler.FileHandler;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BoardServiceUnitTest {

    @InjectMocks BoardService boardService;
    @Mock BoardRepository boardRepository;
    @Mock MemberRepository memberRepository;
    @Mock ImageRepository imageRepository;
    @Mock FileHandler fileHandler;

    @Test
    void 게시글_저장_성공() throws Exception {
        // given
        BoardSaveRequestDto saveRequestDto = new BoardSaveRequestDto("title", "content", "location", new ArrayList<>(), LocalDate.now().toString());

        Member member = createMember();
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));

        Board board = createBoard(saveRequestDto, member);

        // when
        BoardResponseDto saveBoard = boardService.save(saveRequestDto, anyLong());

        // then
        assertThat(saveBoard.getTitle()).isEqualTo(board.getTitle());
        verify(boardRepository).save(any());
    }

    @Test
    void 게시글_저장_회원없음() throws Exception {
        // given
        BoardSaveRequestDto saveRequestDto = new BoardSaveRequestDto("title", "content", "location", new ArrayList<>(), LocalDate.now().toString());
        given(memberRepository.findById(anyLong())).willThrow(MemberNotFoundException.class);

        // when, then
        assertThatThrownBy(() -> boardService.save(saveRequestDto, anyLong())).isInstanceOf(MemberNotFoundException.class);
        verify(memberRepository, never()).save(any());
    }

    @Test
    void 게시글_전체조회() throws Exception {
        // given
        Member member = createMember();
        Board board = createBoard(member);
        given(boardRepository.findAll()).willReturn(List.of(board));

        // when
        List<Board> boardList = boardService.findAll();

        // then
        assertThat(boardList.size()).isEqualTo(1);
        assertThat(boardList.get(0).getTitle()).isEqualTo("title");
        verify(boardRepository).findAll();
    }

    @Test
    void 개인_게시글_리스트_조회() throws Exception {
        // given
        Member member = createMember();
        Board board = createBoard(member);
        given(boardRepository.findByMember_idAndDate(anyLong(), anyString())).willReturn(List.of(board));

        // when
        List<BoardResponseDto> resultList = boardService.findList(anyLong(), anyString());

        // then
        assertThat(resultList.size()).isEqualTo(1);
        assertThat(resultList.get(0).getTitle()).isEqualTo("title");
        verify(boardRepository).findByMember_idAndDate(anyLong(), anyString());
    }

    @Test
    void 게시글_단일_조회_성공() throws Exception {
        // given
        Member member = createMember();
        Board board = createBoard(member);
        given(boardRepository.findById(anyLong())).willReturn(Optional.of(board));

        // when
        BoardResponseDto result = boardService.findOne(anyLong());

        // then
        assertThat(result.getTitle()).isEqualTo(board.getTitle());
    }

    @Test
    void 게시글_단일_조회_게시글없음() throws Exception {
        // given
        given(boardRepository.findById(anyLong())).willThrow(BoardNotFountException.class);

        // when, then
        assertThatThrownBy(() -> boardService.findOne(anyLong())).isInstanceOf(BoardNotFountException.class);
    }

    @Test
    void 게시글_수정_성공() throws Exception {
        // given
        Member member = createMember();
        Board board = createBoard(member);
        given(boardRepository.findById(anyLong())).willReturn(Optional.of(board));

        BoardUpdateRequestDto dto = new BoardUpdateRequestDto("title2", "content2", "location2", new ArrayList<>(), LocalDate.now().toString());

        // when
        BoardResponseDto updateDto = boardService.update(dto, anyLong());

        // then
        assertThat(board.getTitle()).isEqualTo("title2");
        verify(imageRepository).delete(any());
        verify(fileHandler).storeFiles(any());
    }

    @Test
    void 게시글_수정_게시글없음() throws Exception {
        // given
        given(boardRepository.findById(anyLong())).willThrow(BoardNotFountException.class);
        BoardUpdateRequestDto dto = new BoardUpdateRequestDto("title2", "content2", "location2", new ArrayList<>(), LocalDate.now().toString());

        // when, then
        assertThatThrownBy(() -> boardService.update(dto, anyLong())).isInstanceOf(BoardNotFountException.class);
        verify(imageRepository, never()).delete(any());
        verify(fileHandler, never()).storeFiles(any());
    }

    @Test
    void 게시글_삭제_성공() throws Exception {
        // given
        Member member = createMember();
        Board board = createBoard(member);
        given(boardRepository.findById(anyLong())).willReturn(Optional.of(board));

        // when
        boardService.delete(anyLong());

        // then
        verify(boardRepository).findById(anyLong());
    }

    @Test
    void 게시글_삭제_게시글없음() throws Exception {
        // given
        given(boardRepository.findById(anyLong())).willThrow(BoardNotFountException.class);

        // when, then
        assertThatThrownBy(() -> boardService.delete(anyLong())).isInstanceOf(BoardNotFountException.class);
    }
    
    @Test
    void 공유폴더_저장시_공유할_게시글_조회_성공() throws Exception {
        // given
        Member member = createMember();
        Board board = createBoard(member);
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
        given(boardRepository.findByMemberIdAndMoreType(any(), anyString())).willReturn(List.of(board));

        // when
        List<ShareBoardResponseDto> resultDtoList = boardService.addBoardSearch(anyLong(), LocalDate.now().toString());

        // then
        assertThat(resultDtoList.size()).isEqualTo(1);
        assertThat(resultDtoList.get(0).getTitle()).isEqualTo(board.getTitle());
        verify(memberRepository).findById(anyLong());
        verify(boardRepository).findByMemberIdAndMoreType(any(), anyString());
    }

    @Test
    void 공유폴더_저장시_공유할_게시글_조회_회원없음() throws Exception {
        // given
        given(memberRepository.findById(anyLong())).willThrow(MemberNotFoundException.class);

        // when, then
        assertThatThrownBy(() -> boardService.addBoardSearch(anyLong(), LocalDate.now().toString())).isInstanceOf(MemberNotFoundException.class);
        verify(memberRepository).findById(anyLong());
        verify(boardRepository, never()).findByMemberIdAndMoreType(any(), anyString());
    }

    private Member createMember() {
        FileDto fileDto = new FileDto("original1", "stored1");
        return Member.builder().username("username").password("password").email("email").image(fileDto).role(Role.USER).build();
    }

    private Board createBoard(Member member) {
        FileDto fileDto = new FileDto("original1", "stored1");
        List<FileDto> fileDtoList = List.of(fileDto);
        return Board.builder().title("title").content("content").location("location").date(LocalDate.now().toString()).images(fileDtoList).member(member).build();
    }

    private Board createBoard(BoardSaveRequestDto dto, Member member) {
        return Board.builder().title(dto.getTitle()).content(dto.getContent()).location(dto.getLocation()).date(dto.getDate()).images(new ArrayList<>()).member(member).build();
    }
}
