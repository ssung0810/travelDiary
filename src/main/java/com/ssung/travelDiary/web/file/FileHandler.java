package com.ssung.travelDiary.web.file;

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

        return new FileDto(multipartFile.getOriginalFilename(), storedName, multipartFile.getSize());
    }

    private String createStoreFileName(String originalName) {
        return Long.toString(System.nanoTime()) + originalName + "." + extractExt(originalName);
    }

    private String extractExt(String originalName) {
        int pos = originalName.lastIndexOf(".");
        return originalName.substring(pos + 1);
    }

//    public List<FileDto> parseFileInfo(MultipartHttpServletRequest mRequest) throws Exception {
//
//        List<FileDto> fileList = new ArrayList<>();
//
//        Iterator<String> fileNames = mRequest.getFileNames();
//
////        if (fileNames.) {
////            return fileList;
////        }
//
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
//        String current_date = simpleDateFormat.format(new Date());
//
//        String absolutePath = new File("").getAbsolutePath() + "\\";
//
//        String path = "images/" + current_date;
//        File file = new File(path);
//
//        if (!file.exists()) {
//            file.mkdirs();
//        }
//
//        MultipartFile multipartFile;
//
//        while(fileNames.hasNext()) {
//            multipartFile = mRequest.getFile(fileNames.next());
//
//            if (!multipartFile.isEmpty()) {
//                String contentType = multipartFile.getContentType();
//                String originFileExtension;
//
//                if (ObjectUtils.isEmpty(contentType)) {
//                    break;
//                } else {
//                    if (contentType.contains("image/jpeg") || contentType.contains("image/jpg")) {
//                        originFileExtension = ".jpg";
//                    } else if (contentType.contains("image/png")) {
//                        originFileExtension = ".png";
//                    } else {
//                        break;
//                    }
//                }
//
//                String newFileName = Long.toString(System.nanoTime()) + originFileExtension;
//
//                FileDto fileDto = new FileDto(multipartFile.getOriginalFilename(), path + "/" + newFileName, multipartFile.getSize());
//                fileList.add(fileDto);
//
//                file = new File(absolutePath + path + "/" + newFileName);
//                log.info("absolutePath = {} / path = {} / newFileName = {}", absolutePath, path, newFileName);
//                multipartFile.transferTo(file);
//            }
//        }
//
//        return fileList;
//    }
}
