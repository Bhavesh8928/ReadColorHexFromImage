package com.hex.service;

import org.bytedeco.javacpp.indexer.UByteRawIndexer;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ImgJavaCVService {

    public Map<String, Object> hexUsingOpenCV(String imagePath) {
        long startTime = System.currentTimeMillis();

        Mat image = opencv_imgcodecs.imread(imagePath);
        Set<String> hexColors = new HashSet<>();
        Map<String, Integer> colorFrequency = new HashMap<>();

        UByteRawIndexer indexer = image.createIndexer();
        for (int y = 0; y < image.rows(); y++) {
            for (int x = 0; x < image.cols(); x++) {
                int red = indexer.get(y, x, 2);
                int green = indexer.get(y, x, 1);
                int blue = indexer.get(y, x, 0);
                String hex = String.format("#%02x%02x%02x", red, green, blue);
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