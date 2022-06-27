package com.ssung.travelDiary.service.share;

import com.ssung.travelDiary.domain.board.Board;
import com.ssung.travelDiary.domain.board.BoardRepository;
import com.ssung.travelDiary.domain.members.Member;
import com.ssung.travelDiary.domain.members.MemberRepository;
import com.ssung.travelDiary.domain.members.Role;
import com.ssung.travelDiary.domain.share.Share;
import com.ssung.travelDiary.domain.share.ShareBoard;
import com.ssung.travelDiary.domain.share.ShareMember;
import com.ssung.travelDiary.domain.share.ShareRepository;
import com.ssung.travelDiary.dto.share.ShareBoardResponseDto;
import com.ssung.travelDiary.dto.share.ShareListResponseDto;
import com.ssung.travelDiary.dto.share.ShareResponseDto;
import com.ssung.travelDiary.dto.share.ShareSaveRequestDto;
import com.ssung.travelDiary.exception.board.BoardNotFountException;
import com.ssung.travelDiary.exception.member.MemberNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShareServiceUnitTest {

    @InjectMocks ShareService shareService;
    @Mock ShareRepository shareRepository;
    @Mock MemberRepository memberRepository;
    @Mock BoardRepository boardRepository;

    @Test
    void 공유폴더_저장_성공() throws Exception {
        // given
        Member myMember = createMember();
        Board myBoard = createBoard(myMember);
        Member otherMember = createMember();

        given(memberRepository.findById(1L)).willReturn(Optional.of(myMember));
        given(memberRepository.findById(2L)).willReturn(Optional.of(otherMember));
        given(boardRepository.findById(1L)).willReturn(Optional.of(myBoard));

        ShareSaveRequestDto requestDto = new ShareSaveRequestDto("title", "creator", Arrays.asList(2L), Arrays.asList(1L));

        // when
        ShareResponseDto result = shareService.save(requestDto, 1L);

        // then
        assertThat(result.getTitle()).isEqualTo(requestDto.getTitle());
        verify(memberRepository, times(2)).findById(anyLong());
        verify(boardRepository).findById(anyLong());
        verify(shareRepository).save(any());
    }

    @Test
    void 공유폴더_저장_내_memberId_없음() throws Exception {
        // given
        given(memberRepository.findById(1L)).willThrow(MemberNotFoundException.class);

        ShareSaveRequestDto requestDto = new ShareSaveRequestDto("title", "creator", Arrays.asList(2L), Arrays.asList(1L));

        // when, then
        assertThatThrownBy(() -> shareService.save(requestDto, 1L)).isInstanceOf(MemberNotFoundException.class);
        verify(memberRepository, times(1)).findById(anyLong());
        verify(boardRepository, never()).findById(anyLong());
        verify(shareRepository, never()).save(any());
    }

    @Test
    void 공유폴더_저장_공유할_memberId_없음() throws Exception {
        // given
        Member myMember = createMember();
        Member otherMember = createMember();

        given(memberRepository.findById(1L)).willReturn(Optional.of(myMember));
        given(memberRepository.findById(2L)).willReturn(Optional.of(otherMember));
        given(boardRepository.findById(1L)).willThrow(BoardNotFountException.class);

        ShareSaveRequestDto requestDto = new ShareSaveRequestDto("title", "creator", Arrays.asList(2L), Arrays.asList(1L));

        // when, then
        assertThatThrownBy(() -> shareService.save(requestDto, 1L)).isInstanceOf(BoardNotFountException.class);
        verify(memberRepository, times(2)).findById(anyLong());
        verify(boardRepository).findById(anyLong());
        verify(shareRepository, never()).save(any());
    }

    @Test
    void 공유폴더_저장_공유할_boardId_없음() throws Exception {
        // given
        Member myMember = createMember();

        given(memberRepository.findById(1L)).willReturn(Optional.of(myMember));
        given(memberRepository.findById(2L)).willThrow(MemberNotFoundException.class);

        ShareSaveRequestDto requestDto = new ShareSaveRequestDto("title", "creator", Arrays.asList(2L), Arrays.asList(1L));

        // when, then
        assertThatThrownBy(() -> shareService.save(requestDto, 1L)).isInstanceOf(MemberNotFoundException.class);
        verify(memberRepository, times(2)).findById(anyLong());
        verify(boardRepository, never()).findById(anyLong());
        verify(shareRepository, never()).save(any());
    }

    @Test
    void 공유폴더_리스트_출력() throws Exception {
        // given
        List<Share> shareList = List.of(createShare(), createShare());

        given(shareRepository.findList(any())).willReturn(shareList);

        // when
        List<ShareListResponseDto> result = shareService.findList(anyLong());

        // then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getTitle()).isEqualTo(shareList.get(0).getTitle());
        verify(shareRepository).findList(any());
    }

    @Test
    void 공유폴더_선택_시_게시글_리스트() throws Exception {
        // given
        Member member = createMember();
        List<Board> boardList = List.of(createBoard(member), createBoard(member));

        given(shareRepository.findShareBoard(anyLong(), anyString())).willReturn(boardList);

        // when
        List<ShareBoardResponseDto> result = shareService.findShareBoard(anyLong(), anyString());

        // then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getTitle()).isEqualTo(boardList.get(0).getTitle());
        verify(shareRepository).findShareBoard(anyLong(), anyString());
    }

    private Share createShare() {
        Share share = Share.builder()
                .title("title")
                .creator("creator")
                .build();

        Member myMember = createMember();
        Board myBoard = createBoard(myMember);
        Member otherMember = createMember();

        share.getShareMember().add(ShareMember.createShareMember(myMember, share));
        share.getShareMember().add(ShareMember.createShareMember(otherMember, share));
        share.getShareBoard().add(ShareBoard.createShareBoard(myBoard, share));

        return share;
    }

    private Member createMember() {
        return Member.builder()
                .username("username")
                .password("password")
                .email("email")
                .role(Role.USER)
                .build();
    }

    private Board createBoard(Member member) {
        return Board.builder()
                .title("title")
                .content("content")
                .location("location")
                .date(LocalDate.now().toString())
                .member(member)
                .build();
    }
}
