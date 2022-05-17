package com.ssung.travelDiary.handler;

import com.ssung.travelDiary.dto.file.FileDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class FileHandler {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String fileName) {
        return fileDir + fileName;
    }

    public List<FileDto> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        ArrayList<FileDto> resultDto = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                FileDto fileDto = storeFile(multipartFile);
                if(fileDto != null) resultDto.add(fileDto);
            }
        }

        return resultDto;
    }

    public FileDto storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String storedName = createStoreFileName(multipartFile.getOriginalFilename());
        String fullPath = getFullPath(storedName);
        multipartFile.transferTo(new File(fullPath));

        return new FileDto(multipartFile.getOriginalFilename(), storedName);
    }

    private String createStoreFileName(String originalName) {
        return Long.toString(System.nanoTime()) + originalName;
    }

    private String extractExt(String originalName) {
        int pos = originalName.lastIndexOf(".");
        return originalName.substring(pos + 1);
    }
}
