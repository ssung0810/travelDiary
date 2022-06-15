package com.ssung.travelDiary.handler;

import com.ssung.travelDiary.dto.file.FileDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest
//@Transactional
class FileHandlerTest {

//    @Autowired
//    FileHandler fileHandler;
//
//    @Value("${file.dir}")
//    private String fileDir;
//
//    @Test
//    void 파일의_전체경로_가져오기() throws Exception {
//        // given
//        MockMultipartFile multiFile = createMultiFile();
//
//        // when
//        String fullPath = fileHandler.getFullPath(multiFile.getOriginalFilename());
//
//        // then
//        assertThat(fullPath).isEqualTo(fileDir + multiFile.getOriginalFilename());
//    }
//
//    @Test
//    void 단일_파일_저장() throws Exception {
//        // given
//        MockMultipartFile multiFile = createMultiFile();
//
//        // when
//        FileDto fileDto = fileHandler.storeFile(multiFile);
//
//        // then
//        assertThat(fileDto.getOriginalFileName()).isEqualTo(multiFile.getOriginalFilename());
//    }
//
//    @Test
//    void 단일_파일_저장_빈_값() throws Exception {
//        // given
//        MockMultipartFile emptyMultiFile = createEmptyMultiFile();
//
//        // when
//        FileDto fileDto = fileHandler.storeFile(emptyMultiFile);
//
//        // then
//        assertThat(fileDto).isNull();
//    }
//
//    @Test
//    void 다중_파일_저장() throws Exception {
//        // given
//        List<MultipartFile> multipartFiles = List.of(createMultiFile(), createMultiFile());
//
//        // when
//        List<FileDto> fileDtos = fileHandler.storeFiles(multipartFiles);
//
//        // then
//        assertThat(fileDtos.size()).isEqualTo(2);
//        assertThat(fileDtos.get(0).getOriginalFileName()).isEqualTo(multipartFiles.get(0).getOriginalFilename());
//    }
//
//    @Test
//    void 다중_파일_저장_빈_값() throws Exception {
//        // given
//        List<MultipartFile> multipartFiles = List.of(createEmptyMultiFile(), createEmptyMultiFile());
//
//        // when
//        List<FileDto> fileDtos = fileHandler.storeFiles(multipartFiles);
//
//        // then
//        assertThat(fileDtos.size()).isEqualTo(0);
//    }
//
//    private MockMultipartFile createMultiFile() {
//        return new MockMultipartFile("image1", "test.png", MediaType.IMAGE_PNG_VALUE, "test".getBytes());
//    }
//
//    private MockMultipartFile createEmptyMultiFile() {
//        return new MockMultipartFile("null", new byte[]{});
//    }
}