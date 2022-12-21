package com.example.myapp.dto.response;

import com.example.myapp.entites.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {
    private Long id;
    private String name;
    private int quantity;
    private int promotion;
    private BigDecimal price;
    private String description;
    private String image;
    private String category;
    private String brand;
    private String[] images;
}
