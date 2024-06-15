package com.hex.entity;


import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data   // not working for getter and setter
@ToString
@Entity
@Table(name = "image_details")
public class ImageColor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageName;
    private String mostFrequentColor;
    @Column(name = "hexColorFrequencyString", columnDefinition = "TEXT")  // It hold much more data than the default string type.
    private String hexColor;
    private Long timeTaken;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getMostFrequentColor() {
        return mostFrequentColor;
    }

    public void setMostFrequentColor(String mostFrequentColor) {
        this.mostFrequentColor = mostFrequentColor;
    }

    public String getHexColor() {
        return hexColor;
    }

    public void setHexColor(String hexColor) {
        this.hexColor = hexColor;
    }

    public Long getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(Long timeTaken) {
        this.timeTaken = timeTaken;
    }
}
