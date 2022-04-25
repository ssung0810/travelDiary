package com.ssung.travelDiary.web.file;

import lombok.Getter;

import javax.persistence.Embeddable;

@Getter
@Embeddable
public class FileDto {

    private String originalFileName;
    private String storedFileName;
    private Long file_size;

    public FileDto() {
    }

    public FileDto(String originalFileName, String storedFileName, Long file_size) {
        this.originalFileName = originalFileName;
        this.storedFileName = storedFileName;
        this.file_size = file_size;
    }
}
