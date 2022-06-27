package com.ssung.travelDiary.handler;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ssung.travelDiary.handler.FileHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@RequiredArgsConstructor
@Component
public class FileHandlerS3 implements FileHandler {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${awsFile.dir}")
    private String filePath;

    @Override
    public String uploadFile(InputStream inputStream, ObjectMetadata objectMetadata, String storedName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, filePath+storedName, inputStream, objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, filePath+storedName).toString();
    }

    @Override
    public String getFileUrl(String storedName) {
        return amazonS3Client.getUrl(bucket, filePath+storedName).toString();
    }
}
