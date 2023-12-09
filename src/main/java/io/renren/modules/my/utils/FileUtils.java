package io.renren.modules.my.utils;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @Author yiz
 * @Date 2023/12/9 12:48
 * @Description
 */
public class FileUtils {
    public static String uploadFile(@RequestParam("file") MultipartFile file, String filePath) throws IOException {
        String fileName = file.getOriginalFilename();
        File coverFolder = new File(filePath);
        if (!coverFolder.exists()) {
            coverFolder.mkdirs();
        }
        String finalVideoPath = filePath + fileName;
        File dest = new File(finalVideoPath);
        file.transferTo(dest);
        return fileName;
    }
}
