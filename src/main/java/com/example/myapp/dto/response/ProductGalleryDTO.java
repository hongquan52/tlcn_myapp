package com.example.myapp.dto.response;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductGalleryDTO {
    private Long id;
    private String name;
    private int promotion;
    private BigDecimal price;
    private String image;

}
