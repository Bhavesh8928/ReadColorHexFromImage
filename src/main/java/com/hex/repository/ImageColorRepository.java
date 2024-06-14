package com.hex.repository;


import com.hex.entity.ImageColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageColorRepository extends JpaRepository<ImageColor, Long> {
    ImageColor findByHexColor(String hexColor);
}