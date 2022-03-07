package com.ssung.travelDiary.service.travel;

import com.ssung.travelDiary.domain.travel.Travel;
import com.ssung.travelDiary.domain.travel.TravelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TravelService {

    private final TravelRepository travelRepository;

    @Transactional
    public Long save(Travel travel) {
        travelRepository.save(travel);
        return travel.getId();
    }

    public List<Travel> findAll() {
        return travelRepository.findAll();
    }

//    public TravelResponseDto findOne(Long travelId) {
//        Travel travel = travelRepository.findById(travelId)
//                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
//
//        return new TravelResponseDto(travel);
//    }
}
