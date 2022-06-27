package com.ssung.travelDiary;

import com.ssung.travelDiary.domain.members.Member;
import com.ssung.travelDiary.domain.members.MemberRepository;
import com.ssung.travelDiary.domain.members.Role;
import com.ssung.travelDiary.dto.board.BoardSaveRequestDto;
import com.ssung.travelDiary.dto.share.ShareSaveRequestDto;
import com.ssung.travelDiary.service.board.BoardService;
import com.ssung.travelDiary.service.share.ShareService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final BoardService boardService;
    private final ShareService shareService;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() throws IOException {

        for(int i=1; i<=2; i++) {
            Member member = Member.builder()
                    .username("test"+i)
                    .password(passwordEncoder.encode("test!"))
                    .email("qqq"+i+"@www.com")
                    .image(null)
                    .role(Role.USER)
                    .build();

            memberRepository.save(member);
        }


        for (int i = 1; i <= 15; i++) {
            boardService.save(new BoardSaveRequestDto("title" + i, "content" + i, "location" + i, new ArrayList<>(), LocalDate.now().toString()), 1L);
        }

        for (int i = 16; i <= 20; i++) {
            boardService.save(new BoardSaveRequestDto("title" + i, "content" + i, "location" + i, new ArrayList<>(), "2022-06-24"), 1L);
        }


        ShareSaveRequestDto shareSaveRequestDto = new ShareSaveRequestDto("첫 공유폴더", "test1", List.of(2L), List.of(1L, 3L, 18L));
        shareService.save(shareSaveRequestDto, 1L);
        ShareSaveRequestDto shareSaveRequestDto2 = new ShareSaveRequestDto("두 번째 공유폴더", "test1", List.of(2L), List.of(1L, 3L, 18L));
        shareService.save(shareSaveRequestDto2, 1L);
    }
}
