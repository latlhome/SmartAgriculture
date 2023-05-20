package com.smart.agriculture.service;

import org.springframework.web.multipart.MultipartFile;

public interface IImagesService {

    /**
     * 上传文件图片
     * @param file 图片文件
     * @return 操作状态
     */
    String fileUpload(MultipartFile file);

    /**
     * 删除上传图片
     * @param path 图片路径
     * @return 操作状态
     */
    Boolean deleteFile(String path);
}