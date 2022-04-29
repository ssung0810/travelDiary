package com.ssung.travelDiary;

import com.ssung.travelDiary.domain.image.Image;
import com.ssung.travelDiary.domain.image.ImageRepository;
import com.ssung.travelDiary.domain.members.Member;
import com.ssung.travelDiary.domain.members.MemberRepository;
import com.ssung.travelDiary.domain.members.Role;
import com.ssung.travelDiary.handler.FileHandler;
import com.ssung.travelDiary.service.members.MemberService;
import com.ssung.travelDiary.web.file.FileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final ImageRepository imageRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileHandler fileHandler;

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
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
