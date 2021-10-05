package com.example.bigdaddy.controller;



import com.example.bigdaddy.model.UserFile;
import com.example.bigdaddy.model.User;
import com.example.bigdaddy.repository.FileRepository;
import com.example.bigdaddy.repository.UserRepository;
import com.example.bigdaddy.util.EncodeHex;
import com.example.bigdaddy.util.FileHostUrl;
import com.example.bigdaddy.util.response.DataResponse;

import lombok.SneakyThrows;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HashMap;

@RequestMapping("/file")
@RestController
@CrossOrigin
public class FileController {

    final UserRepository userRepository;

    final FileRepository fileRepository;

    public FileController(FileRepository fileRepository, UserRepository userRepository) {
        this.fileRepository = fileRepository;
        this.userRepository = userRepository;
    }

    @SneakyThrows
    @PostMapping("/upload")
    @ResponseBody
    public DataResponse<String> fileUpload(
            @RequestAttribute("mailbox") String mailbox,
            @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new DataResponse<String>(0, "EmptyFile", null);
        }
        String fileName = file.getOriginalFilename();  // 图片名称 包括后缀
        if (fileName == null || !fileName.contains(".")) {
            return new DataResponse<String>(0, "EmptyFile", null);
        }
        String[] fileNameSplitWithPoint = fileName.split("\\.");
        String filePostfix = fileNameSplitWithPoint[fileNameSplitWithPoint.length - 1];
        long fileSize = file.getSize();
        String path = System.getProperty("user.dir") + File.separatorChar + "file";
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");  //日常工厂
        messageDigest.update((fileName+mailbox).getBytes(StandardCharsets.UTF_8)); // 把文件名和邮箱拼一起哈希算了 真是麻烦
        String encodeFileName = EncodeHex.encode(messageDigest.digest());
        String fileSavePath = path + File.separatorChar + encodeFileName + '.' + filePostfix;
        File tempFile = new File(fileSavePath);
        if (!tempFile.exists()){
            tempFile.mkdirs();
            tempFile.createNewFile();
            file.transferTo(tempFile);
            if (userRepository.findById(mailbox).isPresent()) {
                User fileHost = userRepository.findById(mailbox).get();
                fileHost.setCounts(fileHost.getCounts() + 1);  // 更新持有的数量
                userRepository.save(fileHost);
                fileRepository.save(new UserFile(fileName, fileSavePath, fileSize, fileHost));
                HashMap<String, String> data = new HashMap<>();
                data.put("fileUrl", FileHostUrl.prefixUrl+"/file/download/" + encodeFileName + '.' + filePostfix);
                return new DataResponse<>(1, "Succeed", data);
            } else {
                return new DataResponse<>(0, "UserUnExist", null);
            }
        }else{
            HashMap<String, String> data = new HashMap<>();
            data.put("fileUrl", FileHostUrl.prefixUrl+"/file/download/" + encodeFileName + '.' + filePostfix);
            return new DataResponse<>(1,"fileExist",data);
        }
    }

    @SneakyThrows
    @GetMapping("/download/{file}")
    @ResponseBody
    public ResponseEntity<InputStreamResource> fileDownload(
            @PathVariable String file
           ) {
        String path = System.getProperty("user.dir") + File.separatorChar + "file" + File.separatorChar + file;
        File tempFile = new File(path);
        if (tempFile.exists()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file));
            FileSystemResource fileSystemResource = new FileSystemResource(path);
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentLength(fileSystemResource.contentLength())
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(new InputStreamResource(fileSystemResource.getInputStream()));
        }
        return null;
    }

}
