package com.ssung.travelDiary.service.share;

import com.ssung.travelDiary.domain.members.Role;
import com.ssung.travelDiary.domain.share.Share;
import com.ssung.travelDiary.domain.share.ShareBoard;
import com.ssung.travelDiary.domain.share.ShareMember;
import com.ssung.travelDiary.dto.share.ShareResponseDto;
import com.ssung.travelDiary.service.board.BoardService;
import com.ssung.travelDiary.service.members.MemberService;
import com.ssung.travelDiary.dto.board.BoardResponseDto;
import com.ssung.travelDiary.dto.board.BoardSaveRequestDto;
import com.ssung.travelDiary.dto.member.MemberSaveRequestDto;
import com.ssung.travelDiary.dto.share.ShareBoardResponseDto;
import com.ssung.travelDiary.dto.share.ShareListResponseDto;
import com.ssung.travelDiary.dto.share.ShareSaveRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
//@Rollback(value = false)
class ShareServiceTest {

    @Autowired MemberService memberService;
    @Autowired BoardService boardService;
    @Autowired ShareService shareService;

    private Long memberId;

    @Test
    public void 공유폴더_저장() throws Exception {
        // given
        ShareResponseDto share = createShare();

        // when
        List<ShareBoard> shareBoard = share.getShareBoard();
        List<ShareMember> shareMember = share.getShareMember();

        // then
        assertThat(share.getTitle()).isEqualTo("shareTitle");
        assertThat(shareBoard.size()).isEqualTo(2);
        assertThat(shareMember.size()).isEqualTo(2);
        assertThat(shareBoard.get(0).getShare().getId()).isEqualTo(share.getId());
        assertThat(shareMember.get(0).getShare().getId()).isEqualTo(share.getId());
    }

    @Test
    void 공유폴더_리스트_출력() throws Exception {
        // given
        createShare();

        // when
        ShareListResponseDto responseDto = shareService.findList(memberId).get(0);

        // then
        assertThat(responseDto.getTitle()).isEqualTo("shareTitle");
    }

    @Test
    void 공유폴더_리스트_출력_member없음() throws Exception {
        // given
        MockMultipartFile multipartFile = new MockMultipartFile("null", new byte[]{});
        MemberSaveRequestDto dto = new MemberSaveRequestDto("username3", "password3", "email3", multipartFile, Role.USER);
        Long findId = memberService.save(dto);

        // when
        List<ShareListResponseDto> list = shareService.findList(findId);

        // then
        assertThat(list.size()).isEqualTo(0);
    }

    @Test
    void 공유된_게시글_전체조회() throws Exception {
        // given
        ShareResponseDto share = createShare();

        // when
        List<ShareBoardResponseDto> shareBoard = shareService.findShareBoard(share.getId());

        // then
        assertThat(shareBoard.size()).isEqualTo(2);
        assertThat(shareBoard.get(0).getTitle()).isEqualTo("title");
    }

    private ShareResponseDto createShare() throws IOException {
        MockMultipartFile multipartFile = new MockMultipartFile("null", new byte[]{});

        MemberSaveRequestDto dto = new MemberSaveRequestDto("username", "password", "email", multipartFile, Role.USER);
        MemberSaveRequestDto dto2 = new MemberSaveRequestDto("username2", "password2", "email2", multipartFile, Role.USER);
        Long memberId = memberService.save(dto);
        Long memberId2 = memberService.save(dto2);
        this.memberId = memberId;

        BoardSaveRequestDto boardSaveRequestDto = new BoardSaveRequestDto("title", "content", "location", new ArrayList<>(), LocalDate.now().toString());
        BoardSaveRequestDto boardSaveRequestDto2 = new BoardSaveRequestDto("title2", "content2", "location2", new ArrayList<>(), LocalDate.now().toString());
        BoardResponseDto board = boardService.save(boardSaveRequestDto, memberId);
        BoardResponseDto board2 = boardService.save(boardSaveRequestDto2, memberId);

        String title = "shareTitle";
        String creator = "test";

        ArrayList<Long> members = new ArrayList<>();
        members.add(memberId2);

        ArrayList<Long> boards = new ArrayList<>();
        boards.add(board.getId());
        boards.add(board2.getId());

        ShareSaveRequestDto shareSaveRequestDto = new ShareSaveRequestDto(title, creator, members, boards);
        ShareSaveRequestDto shareSaveRequestDto2 = new ShareSaveRequestDto(title, creator, members, boards);

        ShareResponseDto share = shareService.save(shareSaveRequestDto, memberId);
        shareService.save(shareSaveRequestDto2, memberId);

        return share;
    }
}