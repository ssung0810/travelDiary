package com.ssung.travelDiary;

import com.ssung.travelDiary.domain.board.Board;
import com.ssung.travelDiary.domain.image.Image;
import com.ssung.travelDiary.domain.members.Member;
import com.ssung.travelDiary.domain.members.Role;
import com.ssung.travelDiary.file.FileDto;
import com.ssung.travelDiary.service.board.BoardService;
import com.ssung.travelDiary.service.members.MemberService;
import com.ssung.travelDiary.web.members.dto.MemberSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberService memberService;
    private final BoardService boardService;
    private final PasswordEncoder passwordEncoder;

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() throws IOException {

        FileDto fileDto = new FileDto("01. 왕이신하나님.png", "1013784853220000.png", 201848L);

        Member member = Member.builder()
                .username("test")
                .password(passwordEncoder.encode("test!"))
                .email("qqq@www.com")
                .imageFile(fileDto)
                .role(Role.USER)
                .build();

        memberService.sign(member);

//        for(int i=0; i<3; i++) {
//            Board board = Board.builder()
//                    .username("test")
//                    .title("qq")
//                    .location("ww")
//                    .content("ee")
//                    .date("2022-03-06")
//                    .build();
//
//            boardService.save(board, new ArrayList<Image>());
//        }
    }
}
