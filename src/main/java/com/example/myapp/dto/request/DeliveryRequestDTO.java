package com.example.myapp.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryRequestDTO {
    @NotNull(message = "Status of Delivery is required")
    private String status;
    private Long addressId;
    @NotNull(message = "Bill is required")
    private Long billId;
    private Long shipperId;
    private String billStatus;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date payDate;
}
