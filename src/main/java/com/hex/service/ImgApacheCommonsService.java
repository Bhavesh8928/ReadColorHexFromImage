package com.hex.service;

import com.hex.repository.ImageColorRepository;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ImgApacheCommonsService {

    @Autowired
    private ImageColorRepository imageColorRepository;

    //    HEX CODE FIND METHOD WITH TIME TAKEN AND MOST FREQUENT COLOR
    public Map<String, Object> hexUsingApacheCommon(String imagePath) throws IOException, ImageReadException {
        long startTime = System.currentTimeMillis();

        BufferedImage image = Imaging.getBufferedImage(new File(imagePath));
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

//        String mostFrequentColor = colorFrequency.entrySet().stream()
//                .filter(entry -> entry.getValue() > 1)  // Only consider colors that occur more than once
//                .max(Map.Entry.comparingByValue())
//                .map(Map.Entry::getKey)
//                .orElse("No repeated color");  // Default value if no color occurs more than once

        // Find the most frequent color
//        Map.Entry<String, Integer> mostFrequentEntry = colorFrequency.entrySet().stream()
//                .max(Map.Entry.comparingByValue())
//                .orElse(null);

//        Map<String, String> colorFrequencyWithOccurrences = new HashMap<>();
//        for (Map.Entry<String, Integer> entry : colorFrequency.entrySet()) {
//            colorFrequencyWithOccurrences.put(entry.getKey(), entry.getValue() + " times occurred");
//        }
//        String mostFrequentColor = mostFrequentEntry != null ? mostFrequentEntry.getKey() : "No color found";

        Map<String, Object> result = new HashMap<>();
        result.put("timeTaken", timeTaken);
        result.put("mostFrequentColor", mostFrequentColor);
        result.put("hexColors", hexColors);
//        result.put("colorFrequency", colorFrequency);   // To get the frequency of each color
//        result.put("colorFrequency", colorFrequencyWithOccurrences);


        return result;
    }

    //    BASIC HEX CODE FIND METHOD
//    public Set<String> hexUsingApacheCommon(String imagePath) throws IOException, ImageReadException {
//        Long startTime = System.currentTimeMillis();
//        BufferedImage image = Imaging.getBufferedImage(new File(imagePath));
//        Set<String> hexColors = new HashSet<>();
//        for (int x = 0; x < image.getWidth(); x++) {
//            for (int y = 0; y < image.getHeight(); y++) {
//                int rgb = image.getRGB(x, y);
//                String hex = String.format("#%02x%02x%02x", (rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF);
//                hexColors.add(hex);
//            }
//        }
//        return hexColors;
//    }


//    //    public String hexUsingApacheCommon(String imagePath) throws IOException, ImageReadException {
//    public Set<String> hexUsingApacheCommon(String imagePath) throws IOException, ImageReadException {
//        Long startTime = System.currentTimeMillis();
//        BufferedImage image = Imaging.getBufferedImage(new File(imagePath));
//        Set<String> hexColors = new HashSet<>();
//        Map<String, Integer> colorFrequency = new HashMap<>();
//
//        for (int x = 0; x < image.getWidth(); x++) {
//            for (int y = 0; y < image.getHeight(); y++) {
//                int rgb = image.getRGB(x, y);
//                String hex = String.format("#%02x%02x%02x", (rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF);
//                hexColors.add(hex);
//                colorFrequency.put(hex, colorFrequency.getOrDefault(hex, 0) + 1);
//            }
//        }
//
////        TO SAVE THE HEX CODE TO DATABASE UNCOMMENT THE FOLLOWING CODE
//        // Convert the set of colors to a comma-separated string
////        String hexColorsString = String.join(",", hexColors);
//        // Save the colors and image path to the database
//        ImageColorApacheCommons imageColor = new ImageColorApacheCommons();
//        imageColor.setHexColor(hexColorsString);
//        imageColor.setImagePath(imagePath);
//        imageColorRepository.save(imageColor);
//        return hexColorsString;
//        return hexColors;
//    }


}
