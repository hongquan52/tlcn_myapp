package com.example.myapp.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {
    @Size(message = "Size is invalid.", max = 64, min = 1)
    private String name;

    @Min(value = 1, message = "Amount must be greater 1")
    private int quantity;
    @Min(value = 1, message = "Stand cost must be greater 1")
    private BigDecimal price;
    private int promotion;
    private String description;
    private MultipartFile image;

    private Long category;
    private Long brand;

    private MultipartFile[] images;

}
