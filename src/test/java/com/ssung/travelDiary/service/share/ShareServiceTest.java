package com.ssung.travelDiary.service.share;

import com.ssung.travelDiary.domain.members.Role;
import com.ssung.travelDiary.service.board.BoardService;
import com.ssung.travelDiary.service.members.MemberService;
import com.ssung.travelDiary.web.members.dto.MemberSaveRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class ShareServiceTest {

    @Autowired MemberService memberService;
    @Autowired BoardService boardService;
    @Autowired ShareService shareService;


    @Test
    public void 공유폴더_저장() throws Exception {
        // given
        MockMultipartFile multipartFile = new MockMultipartFile("null", new byte[]{});
        MemberSaveRequestDto dto = new MemberSaveRequestDto("username", "password", "email", multipartFile, Role.USER);
        MemberSaveRequestDto dto2 = new MemberSaveRequestDto("username2", "password2", "email", multipartFile, Role.USER);
        Long findId = memberService.sign(dto);
        Long findId2 = memberService.sign(dto2);



        // when

        // then
    }
}