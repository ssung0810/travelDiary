package com.ssung.travelDiary.web.image;

import com.ssung.travelDiary.handler.FileHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

@RequiredArgsConstructor
@Slf4j
@RestController
public class ImageController {

    private final FileHandler fileHandler;

    @GetMapping("image/{imageName}")
    public Resource downloadImage(@PathVariable String imageName) throws MalformedURLException {
        log.info("imageName = {}", imageName);
        return new UrlResource("file:" + fileHandler.getFullPath(imageName));
    }
}
