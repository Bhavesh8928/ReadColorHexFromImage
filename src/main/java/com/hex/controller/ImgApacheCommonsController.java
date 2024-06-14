package com.hex.controller;

import com.hex.repository.ImageColorRepository;
import com.hex.service.ImgApacheCommonsService;
import org.apache.commons.imaging.ImageReadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/image/apache")
public class ImgApacheCommonsController {

    @Autowired
    private ImgApacheCommonsService imageService;

    @Autowired
    private ImageColorRepository imageColorRepository;

    @GetMapping("/home")
    public String home() {
        return "Welcome to Hex!";
    }

    @PostMapping("/hex")
//    public ResponseEntity<Set<String>> hexUsingApacheCommon(@RequestParam("image") MultipartFile image) {
//    public ResponseEntity<String> hexUsingApacheCommon(@RequestParam("image") MultipartFile image) {
    public ResponseEntity<Map<String, Object>> hexUsingApacheCommon(@RequestParam("image") MultipartFile image) {
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
//        Set<String> colors;
//        String colors;
        Map<String, Object> result;
        try {
//            colors = imageService.hexUsingApacheCommon(tempFile.getAbsolutePath());
            result = imageService.hexUsingApacheCommon(tempFile.getAbsolutePath());
        } catch (IOException | ImageReadException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        tempFile.delete();
//        return new ResponseEntity<>(colors, HttpStatus.OK);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

//    @PostMapping("/hex/commons")
//    public ResponseEntity<Map<String, Object>> uploadImageCommons(@RequestParam("image") MultipartFile image) {
//        if (image.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//        try {
//            Map<String, Object> result = imageService.hexUsingApacheCommon(image);
//            return new ResponseEntity<>(result, HttpStatus.OK);
//        } catch (IOException e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }


}
