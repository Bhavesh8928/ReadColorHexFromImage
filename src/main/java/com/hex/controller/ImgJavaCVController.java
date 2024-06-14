package com.hex.controller;

import com.hex.service.ImgJavaCVService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/image/javacv")
public class ImgJavaCVController {

    @Autowired
    private ImgJavaCVService imageService;

    @GetMapping("/home")
    public String home() {
        return "Welcome to Hex!";
    }

    @PostMapping("/hex")
    public ResponseEntity<Map<String, Object>> hexUsingOpenCV(@RequestParam("image") MultipartFile image) {
        if (image.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        File tempFile;
        try {
            tempFile = File.createTempFile("uploaded-", image.getOriginalFilename());
            image.transferTo(tempFile);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Map<String, Object> result = imageService.hexUsingOpenCV(tempFile.getAbsolutePath());
        tempFile.delete();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}