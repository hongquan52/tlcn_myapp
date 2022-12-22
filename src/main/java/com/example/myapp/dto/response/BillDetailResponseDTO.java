package com.example.myapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BillDetailResponseDTO {
    private Long productId;
    private String productName;
    private String productImage;

    private Long billId;
    private int amount;
    private Date payDate;
    private String payMethod;
    private String status;
    private BigDecimal price;
    private UserResponseDTO userResponseDTO;
}
