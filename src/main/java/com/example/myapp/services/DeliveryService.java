package com.example.myapp.services;

import com.example.myapp.dto.request.DeliveryRequestDTO;
import com.example.myapp.dto.response.DeliveryResponseDTO;
import com.example.myapp.dto.response.ResponseObject;
import com.example.myapp.entites.User;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface DeliveryService {
    ResponseEntity<?> getAllDeliveryOnTrading(Pageable pageable);

    ResponseEntity<ResponseObject> createDelivery(DeliveryRequestDTO deliveryRequestDTO);

    ResponseEntity<ResponseObject> updateDelivery(DeliveryRequestDTO deliveryRequestDTO, Long id);

    ResponseEntity<ResponseObject> deleteDelivery(Long id);

    DeliveryResponseDTO getDeliveryById(Long id);

    ResponseEntity<?> getDeliveryByStatus(String status);

    ResponseEntity<?> getDeliveryByShipper(Long shipperId);

    ResponseEntity<?> getDeliveryByStatusAndShipper(String status, Long shipperId);

    DeliveryResponseDTO getDeliveryByBill(Long billId);
}
