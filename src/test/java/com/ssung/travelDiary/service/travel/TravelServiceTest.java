package com.ssung.travelDiary.service.travel;

import com.ssung.travelDiary.domain.travel.Travel;
import com.ssung.travelDiary.domain.travel.TravelCategory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class TravelServiceTest {

    @Autowired
    private TravelService travelService;

    @Test
    public void 여행일지_등록() throws Exception {
        // given
        Travel travel = Travel.builder()
                .username("username")
                .location("location")
                .content("content")
                .category(TravelCategory.TRAVEL)
                .build();

        travelService.save(travel);

        // when
        Travel findTravel = travelService.findAll().get(0);

        // then
        Assertions.assertThat(findTravel.getContent()).isEqualTo("content");
    }
}