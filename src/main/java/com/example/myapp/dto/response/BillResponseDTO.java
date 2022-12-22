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
public class BillResponseDTO {
    private String userName;
    private Long billId;
    private String status;
    private String paymentMethod;
    private BigDecimal totalPrice;
    private Date payDate;
}
