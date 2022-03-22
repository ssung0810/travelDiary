package com.ssung.travelDiary.file;

import com.ssung.travelDiary.web.members.dto.MemberSaveRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Component
public class FileHandler {

    public List<FileDto> parseFileInfo(MultipartHttpServletRequest mRequest) throws Exception {

        List<FileDto> fileList = new ArrayList<>();

        Iterator<String> fileNames = mRequest.getFileNames();

//        if (fileNames.) {
//            return fileList;
//        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String current_date = simpleDateFormat.format(new Date());

        String absolutePath = new File("").getAbsolutePath() + "\\";

        String path = "images/" + current_date;
        File file = new File(path);

        if (!file.exists()) {
            file.mkdirs();
        }

        MultipartFile multipartFile;

        while(fileNames.hasNext()) {
            multipartFile = mRequest.getFile(fileNames.next());

            if (!multipartFile.isEmpty()) {
                String contentType = multipartFile.getContentType();
                String originFileExtension;

                if (ObjectUtils.isEmpty(contentType)) {
                    break;
                } else {
                    if (contentType.contains("image/jpeg") || contentType.contains("image/jpg")) {
                        originFileExtension = ".jpg";
                    } else if (contentType.contains("image/png")) {
                        originFileExtension = ".png";
                    } else {
                        break;
                    }
                }

                String newFileName = Long.toString(System.nanoTime()) + originFileExtension;

                FileDto fileDto = new FileDto(multipartFile.getOriginalFilename(), path + "/" + newFileName, multipartFile.getSize());
                fileList.add(fileDto);

                file = new File(absolutePath + path + "/" + newFileName);
                log.info("absolutePath = {} / path = {} / newFileName = {}", absolutePath, path, newFileName);
                multipartFile.transferTo(file);
            }
        }

        return fileList;
    }
}
