package com.example.myapp.services;

import com.example.myapp.dto.response.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface BillDetailService {
    ResponseEntity<ResponseObject> addProductToBill(Long billId, Long productId, int amount);

    ResponseEntity<ResponseObject> deleteProductToBill(Long billId, Long productId, int amount);

    ResponseEntity<?> getProductByBill(Long billId);

    ResponseEntity<?> getAllProductByBillPayed();
}
