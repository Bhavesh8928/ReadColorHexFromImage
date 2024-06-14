package com.hex.service;

import com.hex.repository.ImageColorRepository;

//import org.bytedeco.opencv.opencv_core.Mat;
//import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class ImgOpenCVService {

    @Autowired
    private ImageColorRepository imageColorRepository;

    //    ("/ hex-javacv1 " CODE)
    static {
//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//        OR
//        System.load("C:\\Program Files\\opencv\\build\\java\\x64\\opencv_java490.dll");
//        OR
        System.setProperty("java.library.path", "C:\\Program Files\\opencv\\build\\java\\x64");
        System.loadLibrary("opencv_java490");
    }

    //    ("/ hex-javacv " CODE)
    public Set<String> extractColorsUsingJavaCV(MultipartFile file) throws IOException {
        java.io.File tempFile = java.io.File.createTempFile("temp", null);
        file.transferTo(tempFile);

        Mat image = Imgcodecs.imread(tempFile.getAbsolutePath());
        Set<String> hexColors = new HashSet<>();

        for (int x = 0; x < image.cols(); x++) {
            for (int y = 0; y < image.rows(); y++) {
                double[] rgb = image.get(y, x);
                String hex = String.format("#%02x%02x%02x", (int) rgb[2], (int) rgb[1], (int) rgb[0]);
                hexColors.add(hex);
            }
        }
        tempFile.delete();
        return hexColors;
    }


}
