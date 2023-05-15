package com.visual.disease.core.utils;

import java.io.*;


public class PathUtil {

    public String getUploadResource(String fileName) {
        // 返回读取指定资源的输入流
        InputStream is = this.getClass().getResourceAsStream("/onnxmodel/" + fileName);

        // 若文件已存在，则返回的filePath中含有"EXIST"，则不需再重写文件
        String filePath = createFile(fileName);

        // 文件不存在，则创建流输入默认数据到新文件
        if (!filePath.contains("EXIST")) {
            File file = new File(filePath);
            inputStreamToFile(is, file);
            return filePath;
        }
        return filePath.substring(5);
    }


    public String createFile(String filename) {
        String path = System.getProperty("user.dir");
        // create folder
        String dirPath = path + File.separator + "resources";
        File dir = new File(dirPath);
        dir.mkdirs();

        // create file
        String filePath = dirPath + File.separator + filename;
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return filePath;
        }
        return "EXIST" + filePath;
    }
    public void inputStreamToFile(InputStream ins, File file) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);

            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


