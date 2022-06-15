package com.ssung.travelDiary;

import com.ssung.travelDiary.domain.members.Member;
import com.ssung.travelDiary.domain.members.MemberRepository;
import com.ssung.travelDiary.domain.members.Role;
import com.ssung.travelDiary.handler.FileHandler;
import com.ssung.travelDiary.service.board.BoardService;
import com.ssung.travelDiary.service.members.MemberService;
import com.ssung.travelDiary.service.share.ShareService;
import com.ssung.travelDiary.dto.board.BoardResponseDto;
import com.ssung.travelDiary.dto.board.BoardSaveRequestDto;
import com.ssung.travelDiary.dto.file.FileDto;
import com.ssung.travelDiary.dto.share.ShareSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

//@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberService memberService;
    private final BoardService boardService;
    private final ShareService shareService;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileHandler fileHandler;

    /**
     * 테스트용 데이터 추가
     */
//    @PostConstruct
    public void init() throws IOException {

        FileDto fileDto = new FileDto("01. 왕이신하나님.png", "1013784853220000.png");

        Member member = Member.builder()
                .username("test")
                .password(passwordEncoder.encode("test!"))
                .email("qqq@www.com")
                .image(null)
                .role(Role.USER)
                .build();

        memberRepository.save(member);

//        Member member1 = Member.builder()
//                .username("username")
//                .password(passwordEncoder.encode("password"))
//                .email("email")
//                .image(null)
//                .role(Role.USER)
//                .build();
//        Member member2 = Member.builder()
//                .username("username2")
//                .password(passwordEncoder.encode("password2"))
//                .email("email2")
//                .image(null)
//                .role(Role.USER)
//                .build();
//
//        memberRepository.save(member1);
//        memberRepository.save(member2);
//
//        BoardSaveRequestDto boardSaveRequestDto = new BoardSaveRequestDto("title", "content", "location", new ArrayList<>(), LocalDate.now().toString());
//        BoardSaveRequestDto boardSaveRequestDto2 = new BoardSaveRequestDto("title2", "content2", "location2", new ArrayList<>(), LocalDate.now().toString());
//        BoardResponseDto board = boardService.save(boardSaveRequestDto, 1L);
//        BoardResponseDto board2 = boardService.save(boardSaveRequestDto2, 1L);
//
//        String title = "shareTitle";
//        String creator = "test";
//
//        ArrayList<Long> members = new ArrayList<>();
//        members.add(2L);
//        members.add(3L);
//
//        ArrayList<Long> boards = new ArrayList<>();
//        boards.add(board.getId());
//        boards.add(board2.getId());
//
//        ShareSaveRequestDto shareSaveRequestDto = new ShareSaveRequestDto(title, creator, members, boards);
//        ShareSaveRequestDto shareSaveRequestDto2 = new ShareSaveRequestDto(title, creator, members, boards);
//
//        shareService.save(shareSaveRequestDto, 1L);
//        shareService.save(shareSaveRequestDto2, 1L);
    }
}
