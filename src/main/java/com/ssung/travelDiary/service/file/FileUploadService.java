package com.ssung.travelDiary.service.file;



import com.amazonaws.services.s3.model.ObjectMetadata;
import com.ssung.travelDiary.dto.file.FileDto;
import com.ssung.travelDiary.handler.FileHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FileUploadService {

    private final FileHandler FileHandlerS3;

    public FileDto storeFile(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String storedName = createStoreFileName(multipartFile.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());
        try (InputStream inputStream = multipartFile.getInputStream()) {
            FileHandlerS3.uploadFile(inputStream, objectMetadata, storedName);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }

        return new FileDto(multipartFile.getOriginalFilename(), storedName);
    }

    public List<FileDto> storeFiles(List<MultipartFile> multipartFiles) {
        ArrayList<FileDto> resultDto = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                FileDto fileDto = storeFile(multipartFile);
                if(fileDto != null) resultDto.add(fileDto);
            }
        }

        return resultDto;
    }

    private String createStoreFileName(String originalName) {
        return Long.toString(System.nanoTime()) + originalName;
    }
}
