package com.hex.entity;


import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "image_details")
public class ImageColor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")  // It hold much more data than the default string type.
    private String hexColor;
    private String imagePath;
//    private String imageName;
//
//    public Long getId() {
//        return id;
//    }
//
//    public String getHexColor() {
//        return hexColor;
//    }
//
//    public String getImagePath() {
//        return imagePath;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public void setHexColor(String hexColor) {
//        this.hexColor = hexColor;
//    }
//
//    public void setImagePath(String imagePath) {
//        this.imagePath = imagePath;
//    }
}
