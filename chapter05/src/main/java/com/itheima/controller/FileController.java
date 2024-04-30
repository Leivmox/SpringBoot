package com.itheima.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Controller
public class FileController {
    @GetMapping("/toUpload")
    public String toUpload() {
        return "upload";
    }

    @PostMapping("/uploadFile")
    public String uploadFile(MultipartFile[] fileUpload, Model model) {
        model.addAttribute("uploadStatus", "上传成功");
        for(MultipartFile file : fileUpload) {
            String fileName = file.getOriginalFilename();
            fileName = UUID.randomUUID() + "_" + fileName;
            String dirPath = "D:/chapter05/";
            File filePath = new File(dirPath);
            if(!filePath.exists()) {
                filePath.mkdirs();
            }
            try{
                file.transferTo(new File(dirPath + fileName));

            } catch (Exception e) {
                e.printStackTrace();
                model.addAttribute("uploadStatus", "上传失败: " + e.getMessage());
            }
        }
        return "upload";
    }
}
