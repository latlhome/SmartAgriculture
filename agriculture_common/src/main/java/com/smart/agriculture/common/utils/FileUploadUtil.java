package com.smart.agriculture.common.utils;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

import static cn.hutool.core.io.NioUtil.DEFAULT_BUFFER_SIZE;

public class FileUploadUtil {

    /**
     * 文本文件转换为指定编码的字符串
     *
     * @param file     文本文件
     * @param encoding 编码类型
     * @return 转换后的字符串
     * @throws IOException
     */
    public static String file2String(File file, String encoding) {
        InputStreamReader reader = null;
        StringWriter writer = new StringWriter();
        try {
            if (encoding == null || "".equals(encoding.trim())) {
                reader = new InputStreamReader(new FileInputStream(file));
            } else {
                reader = new InputStreamReader(new FileInputStream(file), encoding);

            }
            // 将输入流写入输出流
            char[] buffer = new char[DEFAULT_BUFFER_SIZE];
            int n = 0;
            while (-1 != (n = reader.read(buffer))) {
                writer.write(buffer, 0, n);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        // 返回转换结果
        if (writer != null)
            return writer.toString();
        else return null;
    }

    /**
     * 将字符串写入指定文件(当指定的父路径中文件夹不存在时，会最大限度去创建，以保证保存成功！)
     *
     * @param res      原字符串
     * @param filePath 文件路径
     * @return 成功标记
     */
    public static boolean string2File(String res, String filePath) {
        boolean flag = true;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {
            File distFile = new File(filePath);
            if (!distFile.getParentFile().exists()) distFile.getParentFile().mkdirs();
            bufferedReader = new BufferedReader(new StringReader(res));
            bufferedWriter = new BufferedWriter(new FileWriter(distFile));
            char buf[] = new char[1024];          // 字符缓冲区
            int len;
            while ((len = bufferedReader.read(buf)) != -1) {
                bufferedWriter.write(buf, 0, len);
            }
            bufferedWriter.flush();
            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            flag = false;
            return flag;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    /**
     * @param file     文件
     * @param path     文件存放路径
     * @param fileName 原文件名
     * @return
     */
    public static boolean upload(MultipartFile file, String path, String fileName) {
        // 生成新的文件名

        String destPath = new File(path).getAbsolutePath();
        String realPath = destPath+"/"+fileName;
        File dest = new File(realPath);

        // 判断文件父目录是否存在
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdir();
        }

        try {
            // 保存文件
            file.transferTo(dest);
            Thumbnails.of(realPath)
                    .size(250, 250)
                    .toFile(realPath);
            return true;
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**

     * 文件删除

     *

     * @param filePath 文件路径

     * @return false、true

     */

    public static Boolean delete(String filePath) {
        String absolutePath = new File(filePath).getAbsolutePath();
        File file = new File(absolutePath);

        if (file.exists()) {

            file.delete();

            System.out.println("===========删除成功=================");

            return true;

        } else {

            System.out.println("===============删除失败==============");

            return false;

        }

    }

}
