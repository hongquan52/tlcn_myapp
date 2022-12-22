package com.example.myapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DeliveryResponseDTO {
    private Long id;
    private String status;
    private Long billId;
    private Long shipperId;
    private String shipperName;
    private String shipperPhone;
}
