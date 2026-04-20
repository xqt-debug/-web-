package com.xqt.apprepositories.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@PropertySource("classpath:application.yaml")
class DownloadController {

    @Value("${spring.repolocation.url}")
    private String basePath;

    @RequestMapping("/download/{name}")
    public ResponseEntity<Resource> downloadByName(@PathVariable String name) {
        try{
            Path file = Paths.get(basePath).resolve(name).normalize();
            File physicalFile = file.toFile();

            System.out.println("绝对路径: " + physicalFile.getAbsolutePath());
            System.out.println("是否存在: " + physicalFile.exists());
            System.out.println("是否可读: " + physicalFile.canRead());
            System.out.println("文件字节数: " + physicalFile.length());

            Resource resource = new FileSystemResource(file.toFile());

        if (resource.exists() || resource.isReadable()) {
            return  ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        }else{
            return ResponseEntity.notFound().build();
        }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
