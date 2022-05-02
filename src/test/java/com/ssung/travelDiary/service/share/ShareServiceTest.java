package com.ssung.travelDiary.service.share;

import com.ssung.travelDiary.domain.board.Board;
import com.ssung.travelDiary.domain.members.Role;
import com.ssung.travelDiary.domain.share.Share;
import com.ssung.travelDiary.domain.share.ShareBoard;
import com.ssung.travelDiary.domain.share.ShareMember;
import com.ssung.travelDiary.service.board.BoardService;
import com.ssung.travelDiary.service.members.MemberService;
import com.ssung.travelDiary.web.board.dto.BoardSaveRequestDto;
import com.ssung.travelDiary.web.members.dto.MemberSaveRequestDto;
import com.ssung.travelDiary.web.share.dto.ShareSaveRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
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


    @Test
    public void 공유폴더_저장() throws Exception {
        // given
        Share share = createShare();

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
        Share share = shareService.findList(1L).get(0);

        // then
        assertThat(share.getTitle()).isEqualTo("shareTitle");
    }

    @Test
    void 공유폴더_리스트_출력_member없음() throws Exception {
        // given
        createShare();

        MockMultipartFile multipartFile = new MockMultipartFile("null", new byte[]{});
        MemberSaveRequestDto dto = new MemberSaveRequestDto("username3", "password3", "email3", multipartFile, Role.USER);
        Long findId = memberService.sign(dto);

        // when
        List<Share> list = shareService.findList(findId);

        // then
        assertThat(list.size()).isEqualTo(0);
    }

    @Test
    void 공유된_게시글_전체조회() throws Exception {
        // given
        Share share = createShare();

        // when
        List<Board> shareBoard = shareService.findShareBoard(share.getId());

        // then
        assertThat(shareBoard.size()).isEqualTo(2);
        assertThat(shareBoard.get(0).getTitle()).isEqualTo("title");
    }

    private Share createShare() throws IOException {
        MockMultipartFile multipartFile = new MockMultipartFile("null", new byte[]{});

        MemberSaveRequestDto dto = new MemberSaveRequestDto("username", "password", "email", multipartFile, Role.USER);
        MemberSaveRequestDto dto2 = new MemberSaveRequestDto("username2", "password2", "email2", multipartFile, Role.USER);
        Long findId = memberService.sign(dto);
        Long findId2 = memberService.sign(dto2);

        BoardSaveRequestDto boardSaveRequestDto = new BoardSaveRequestDto("title", "content", "location", new ArrayList<>(), LocalDate.now().toString());
        BoardSaveRequestDto boardSaveRequestDto2 = new BoardSaveRequestDto("title2", "content2", "location2", new ArrayList<>(), LocalDate.now().toString());
        Board board = boardService.save(boardSaveRequestDto, findId);
        Board board2 = boardService.save(boardSaveRequestDto2, findId);


        String title = "shareTitle";
        String creator = "username";

        ArrayList<Long> members = new ArrayList<>();
        members.add(findId);
        members.add(findId2);

        ArrayList<Long> boards = new ArrayList<>();
        boards.add(board.getId());
        boards.add(board2.getId());

        ShareSaveRequestDto shareSaveRequestDto = new ShareSaveRequestDto(title, creator, members, boards);
        ShareSaveRequestDto shareSaveRequestDto2 = new ShareSaveRequestDto(title, creator, members, boards);

        Share share = shareService.save(shareSaveRequestDto, findId);
        shareService.save(shareSaveRequestDto2, findId);

        return share;
    }
}