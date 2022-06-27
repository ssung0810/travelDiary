package com.ssung.travelDiary.handler;

import com.amazonaws.services.s3.model.ObjectMetadata;

import java.io.InputStream;

public interface FileHandler {

    String uploadFile(InputStream inputStream, ObjectMetadata objectMetadata, String storedName);

    String getFileUrl(String storedName);
}
