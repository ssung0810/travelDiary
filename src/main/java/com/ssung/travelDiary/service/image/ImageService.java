package com.ssung.travelDiary.service.image;

import com.ssung.travelDiary.domain.image.Image;
import com.ssung.travelDiary.domain.image.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    public List<Image> findByBoardId(Long boardId) {
        return imageRepository.findByBoard_id(boardId);
    }
}
