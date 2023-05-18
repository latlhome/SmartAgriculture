package com.smart.agriculture.service.impl;

import com.smart.agriculture.common.utils.FileNameUtil;
import com.smart.agriculture.common.utils.FileUploadUtil;
import com.smart.agriculture.service.IImagesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CmsImagesServiceImpl implements IImagesService {
    @Value("${web.images}")
    private String localPath;


    @Override
    public String fileUpload(MultipartFile file) {
        if (file == null){
            return null;
        }
        // 获取新文件名
        String fileName = file.getOriginalFilename();
        fileName = FileNameUtil.getFileName(fileName);
        // 拼接文件路径，上传到服务器
        if (FileUploadUtil.upload(file,localPath,fileName)){
            return fileName;
        }
        return "上传失败";
    }

    public Boolean deleteFile(String path){
        return FileUploadUtil.delete(localPath+path);
    }


}