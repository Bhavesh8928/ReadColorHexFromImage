package com.hex.controller;

import com.hex.service.ImgImageIOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/image/imageio")
public class ImgImageIOController {

    @Autowired
    private ImgImageIOService imageService;

    @PostMapping("/hex")
    public ResponseEntity<Map<String, Object>> uploadImage(@RequestParam("image") MultipartFile image) {
        if (image.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
       //  File tempFile; s necessary if the method that processes the image requires a File or a file path as input. In the case he method takes
        //  a MultipartFile as input, not a File or a file path. Therefore, this step of creating a temporary file is not necessary.
        try {
            Map<String, Object> result = imageService.extractColorsUsingImageIO(image);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @PostMapping("/hex")
//    public ResponseEntity<Map<String, Object>> uploadImage(@RequestParam("image") MultipartFile image) {
//        if (image.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        File tempFile;
//        try {
//            tempFile = File.createTempFile("uploaded-", image.getOriginalFilename());
//            image.transferTo(tempFile);
//        } catch (IOException e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//        Map<String, Object> result = imageService.extractColorsUsingImageIO(tempFile.getAbsolutePath());
//        tempFile.delete();
//
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }


}