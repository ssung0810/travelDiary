package com.ssung.travelDiary.handler;

import com.ssung.travelDiary.service.file.FileUploadService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FileHandlerUnitTest {

    @InjectMocks
    FileUploadService fileUploadService;

//    @Value("${file.dir}")
    private String fileDir;

//    @Test
//    void 파일_전체경로_가져오기() throws Exception {
//        // given
//        String fullPath = fileHandler.getFullPath("file");
//
//        // when, then
//        assertThat(fullPath).isEqualTo(fileDir + "file");
//    }

//    @Test
//    void 단일_파일_저장_파일O() throws Exception {
//        // given
//        String originalFileName = "originalFile";
//        MockMultipartFile multipartFile = new MockMultipartFile("file", originalFileName, MediaType.IMAGE_PNG_VALUE, "file".getBytes());
//        System.out.println("fileDir = " + fileDir);
//
//        // when
//        FileDto fileDto = fileHandler.storeFile(multipartFile);
//
//        // then
//        assertThat(fileDto.getOriginalFileName()).isEqualTo(originalFileName);
//    }
//
//    @Test
//    void 단일_파일_저장_파일X() throws Exception {
//        // given
//        MockMultipartFile multipartFile = new MockMultipartFile("null", new byte[]{});
//
//        // when
//        FileDto fileDto = fileHandler.storeFile(multipartFile);
//
//        // then
//        assertThat(fileDto).isNull();
//    }
//
//    @Test
//    void 다중_파일_저장_파일o() throws Exception {
//        // given
//        MockMultipartFile multipartFile1 = new MockMultipartFile("file1", "originalFile1", MediaType.IMAGE_PNG_VALUE, "file1".getBytes());
//        MockMultipartFile multipartFile2 = new MockMultipartFile("file2", "originalFile2", MediaType.IMAGE_PNG_VALUE, "file2".getBytes());
//        List<MultipartFile> multipartFiles = List.of(multipartFile1, multipartFile2);
//
//        // when
//        List<FileDto> fileDtoList = fileHandler.storeFiles(multipartFiles);
//
//        // then
//        assertThat(fileDtoList.size()).isEqualTo(2);
//        assertThat(fileDtoList.get(0).getOriginalFileName()).isEqualTo("originalFile1");
//    }
//
//    @Test
//    void 다중_파일_저장_파일X() throws Exception {
//        // given
//        List<MultipartFile> multipartFiles = new ArrayList<>();
//
//        // when
//        List<FileDto> fileDtoList = fileHandler.storeFiles(multipartFiles);
//
//        // then
//        assertThat(fileDtoList.isEmpty()).isTrue();
//    }
}
