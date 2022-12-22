package com.example.myapp.controller;

import com.example.myapp.dto.request.DeliveryRequestDTO;
import com.example.myapp.dto.response.DeliveryResponseDTO;
import com.example.myapp.dto.response.ResponseObject;
import com.example.myapp.services.DeliveryService;
import com.example.myapp.utils.Utils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/delivery")
public class DeliveryController {
    @Autowired private DeliveryService deliveryService;

    @GetMapping(value = "")
    public ResponseEntity<?> getAllDeliveryOnTrading(@RequestParam(name = "page", required = false, defaultValue = Utils.DEFAULT_PAGE_NUMBER) int page,
                                                     @RequestParam(name = "size", required = false, defaultValue = Utils.DEFAULT_PAGE_SIZE) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return deliveryService.getAllDeliveryOnTrading(pageable);
    }

    @PostMapping(value = "")
    public ResponseEntity<ResponseObject> createDelivery(@ModelAttribute @Valid DeliveryRequestDTO deliveryRequestDTO) {
        return deliveryService.createDelivery(deliveryRequestDTO);
    }

    @PutMapping(value = "")
    public ResponseEntity<ResponseObject> updateDelivery(@ModelAttribute @Valid DeliveryRequestDTO deliveryRequestDTO,@RequestParam(name = "deliveryId") Long deliveryId) {
        return deliveryService.updateDelivery(deliveryRequestDTO, deliveryId);
    }

    @DeleteMapping(value = "")
    public ResponseEntity<ResponseObject> deleteDelivery(@RequestParam(name = "deliveryId") Long deliveryId) {
        return deliveryService.deleteDelivery(deliveryId);
    }

    @GetMapping(value = "/{id}")
    public DeliveryResponseDTO getDeliveryById(@PathVariable(name = "id") Long id) {
        return deliveryService.getDeliveryById(id);
    }

    @GetMapping(value = "/status")
    public ResponseEntity<?> getDeliveryByStatus(
            @RequestParam(name = "status") String status ) {
        return deliveryService.getDeliveryByStatus(status);
    }

    @GetMapping(value = "/shipper")
    public ResponseEntity<?> getDeliveryByShipper(
            @RequestParam(name = "shipperId") Long shipperId ) {
        return deliveryService.getDeliveryByShipper(shipperId);
    }

    @GetMapping(value = "/shipper/status")
    public ResponseEntity<?> getDeliveryByStatusAndShipper(@RequestParam(name = "shipperId") Long shipperId, @RequestParam(name = "status") String status) {
        return deliveryService.getDeliveryByStatusAndShipper(status, shipperId);
    }
}
