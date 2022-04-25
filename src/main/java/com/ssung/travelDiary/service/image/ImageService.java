package com.ssung.travelDiary.service.image;

import com.ssung.travelDiary.domain.board.Board;
import com.ssung.travelDiary.domain.image.Image;
import com.ssung.travelDiary.domain.image.ImageRepository;
import com.ssung.travelDiary.web.file.FileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    public void saveBoard(List<FileDto> dtos, Board board) {
        for (FileDto dto : dtos) {
            imageRepository.save(Image.builder()
                            .images(dto)
                            .board(board)
                            .build());
        }
    }

    public List<Image> findByBoardId(Long boardId) {

        return imageRepository.findByBoard_id(boardId);
    }
}
