package com.ssung.travelDiary.dto.file;

import lombok.Getter;

import javax.persistence.Embeddable;

@Getter
@Embeddable
public class FileDto {

    private String originalFileName;
    private String storedFileName;
    public FileDto() {
    }

    public FileDto(String originalFileName, String storedFileName) {
        this.originalFileName = originalFileName;
        this.storedFileName = storedFileName;
    }
}
