package com.hex.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

@Service
public class ImgImageIOService {

    public Map<String, Object> extractColorsUsingImageIO(MultipartFile file) throws IOException {
        long startTime = System.currentTimeMillis();

        java.io.File tempFile = java.io.File.createTempFile("temp", null);
        file.transferTo(tempFile);

        BufferedImage image = ImageIO.read(tempFile);
        Set<String> hexColors = new HashSet<>();
        Map<String, Integer> colorFrequency = new HashMap<>();

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int rgb = image.getRGB(x, y);
                String hex = String.format("#%02x%02x%02x", (rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF);
                hexColors.add(hex);
                colorFrequency.put(hex, colorFrequency.getOrDefault(hex, 0) + 1);
            }
        }

        long endTime = System.currentTimeMillis();
        long timeTaken = endTime - startTime;

        String mostFrequentColor = colorFrequency.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .get()
                .getKey();

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("mostFrequentColor", mostFrequentColor);
        result.put("hexColors", hexColors);
        result.put("timeTaken", timeTaken);
        result.put("colorFrequency", colorFrequency);

        tempFile.delete();
        return result;
    }
}