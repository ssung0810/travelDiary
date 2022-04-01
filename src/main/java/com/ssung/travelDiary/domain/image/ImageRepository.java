package com.ssung.travelDiary.domain.image;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Image findByBoard_id(Long board_id);
}
