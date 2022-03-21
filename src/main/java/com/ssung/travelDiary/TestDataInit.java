package com.ssung.travelDiary;

import com.ssung.travelDiary.domain.board.Board;
import com.ssung.travelDiary.domain.members.Member;
import com.ssung.travelDiary.domain.members.Role;
import com.ssung.travelDiary.service.board.BoardService;
import com.ssung.travelDiary.service.members.MemberService;
import com.ssung.travelDiary.web.members.dto.MemberSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberService memberService;
    private final BoardService boardService;

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        Member member = Member.builder()
                .username("test")
                .password("test!")
                .email("qqq@www.com")
                .role(Role.USER)
                .build();

        memberService.sign(member);

        Board board = Board.builder()
                .username("test")
                .title("qq")
                .location("ww")
                .content("ee")
                .date("2022-03-06")
                .build();

        boardService.save(board);
    }
}
