package com.hex.service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import org.springframework.stereotype.Service;

@Service
public class ImgJavaAWTService {

    public Map<String, Object> hexUsingJavaAWT(String imagePath) throws IOException {
        long startTime = System.currentTimeMillis();

        BufferedImage image = ImageIO.read(new File(imagePath));
        Set<String> hexColors = new HashSet<>();
        Map<String, Integer> colorFrequency = new HashMap<>();

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color color = new Color(image.getRGB(x, y));
                String hex = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
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

        return result;
    }
}