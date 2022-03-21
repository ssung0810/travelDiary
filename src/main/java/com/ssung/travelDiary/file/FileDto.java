package com.ssung.travelDiary.file;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FileDto {

    private String original_file_name;
    private String stored_file_path;
    private Long file_size;

    public FileDto(String original_file_name, String stored_file_path, Long file_size) {
        this.original_file_name = original_file_name;
        this.stored_file_path = stored_file_path;
        this.file_size = file_size;
    }

    @Override
    public String toString() {
        return "FileDto{" +
                "original_file_name='" + original_file_name + '\'' +
                ", stored_file_path='" + stored_file_path + '\'' +
                ", file_size=" + file_size +
                '}';
    }
}
