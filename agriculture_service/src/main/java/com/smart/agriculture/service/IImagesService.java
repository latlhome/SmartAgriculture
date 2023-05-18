package com.smart.agriculture.service;

import org.springframework.web.multipart.MultipartFile;

public interface IImagesService {


    String fileUpload(MultipartFile file);

    Boolean deleteFile(String path);
}