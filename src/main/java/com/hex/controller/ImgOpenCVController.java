package com.hex.controller;

import com.hex.repository.ImageColorRepository;
import com.hex.service.ImgOpenCVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

@RestController
@RequestMapping("/image/opencv")
public class ImgOpenCVController {

    @Autowired
    private ImgOpenCVService imageService;

    @Autowired
    private ImageColorRepository imageColorRepository;

    @GetMapping("/home")
    public String home() {
        return "Welcome to Hex!";
    }

    @PostMapping("/hex")
    public ResponseEntity<Set<String>> uploadImage(@RequestParam("image") MultipartFile file) {
        try {
            Set<String> hexColors = imageService.extractColorsUsingJavaCV(file);
            return ResponseEntity.ok(hexColors);
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }

//    @PostMapping("/hex")
//    public ResponseEntity<Set<String>> extractColors(@RequestParam("image") MultipartFile image) {
//        if (image.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        // Save the uploaded file to a temporary location
//        File tempFile;
//        try {
//            tempFile = File.createTempFile("uploaded-", image.getOriginalFilename());
//            image.transferTo(tempFile);
//        } catch (IOException e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        // Extract colors from the image
//        Set<String> colors = imageService.extractColorsUsingJavaCV(tempFile.getAbsolutePath());
//        // Delete the temporary file
//        tempFile.delete();
//        return new ResponseEntity<>(colors, HttpStatus.OK);
//    }


}
