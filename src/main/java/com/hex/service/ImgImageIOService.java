package com.growlogic.eeko.masterdata.util;

import com.hex.entity.ImageColor;
import com.hex.repository.ImageColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ImgImageIOService {

    @Autowired
    private ImageColorRepository imageColorRepository;

    public boolean isColorLight(String hexColor) {
        // Remove the '#' from the beginning of the hex color
        hexColor = hexColor.substring(1);

        // Parse the red, green, and blue color values from the hex color
        int r = Integer.parseInt(hexColor.substring(0, 2), 16);
        int g = Integer.parseInt(hexColor.substring(2, 4), 16);
        int b = Integer.parseInt(hexColor.substring(4, 6), 16);

        // Calculate the perceived brightness of the color
        double brightness = (299 * r + 587 * g + 114 * b) / 1000.0;

        // Return whether the color is light or dark
        return brightness >= 128;
    }

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

                // Determine whether the color is light or dark
                boolean isLight = isColorLight(hex);
                System.out.println("Color " + hex + " is " + (isLight ? "light" : "dark"));

            }
        }

        long endTime = System.currentTimeMillis();
        long timeTaken = endTime - startTime;

        String mostFrequentColor = colorFrequency.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .get()
                .getKey();

        // Sort the colorFrequency map in descending order based on the frequency
        Map<String, Integer> sortedColorFrequency = colorFrequency.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())   // Remove the reverse() method to sort in ascending order
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new));

        // Determine the limit based on the size of the sorted map
        int limit = Math.min(sortedColorFrequency.size(), 100);

        // Take the first 'limit' entries from the sorted map and convert them into a string separated by commas
        String topColors = sortedColorFrequency.entrySet().stream()
                .limit(limit)
//                .map(entry -> entry.getKey() + ":" + entry.getValue())
//                .map(entry -> entry.getKey() + ":" + entry.getValue() + ":" + (isColorLight(entry.getKey()) ? "light" : "dark"))
//                .collect(Collectors.joining(",  "));
                .map(entry -> "(" + entry.getKey() + ": " + entry.getValue() + " : " + (isColorLight(entry.getKey()) ? "light" : "dark") + ")")
                .collect(Collectors.joining(", "));

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("mostFrequentColor", mostFrequentColor);
        result.put("hexColors", hexColors);
        result.put("timeTaken", timeTaken);
        // result.put("colorFrequency", colorFrequency);   // mixed result
        result.put("colorFrequency", sortedColorFrequency);   // sorted descending order result
        result.put("topColors", topColors);   // upto top 100 colors result

        // Create a new ImageColor entity and set its fields
        ImageColor imageColor = new ImageColor();
        imageColor.setImageName(file.getOriginalFilename()); // replace with your image name
        imageColor.setMostFrequentColor(mostFrequentColor);
        imageColor.setHexColor(topColors);
        imageColor.setTimeTaken(timeTaken);

        // Save the ImageColor entity to the database and capture the returned entity
        ImageColor savedImageColor = imageColorRepository.save(imageColor);

        // Print the saved entity to the console
        System.out.println("Image Name : " + savedImageColor.getImageName());
        System.out.println("Most Frequent Color : " + savedImageColor.getMostFrequentColor());
        System.out.println("Hex Colors : " + savedImageColor.getHexColor());
        System.out.println("Time Taken : " + savedImageColor.getTimeTaken());

        tempFile.delete();
        return result;
    }
}