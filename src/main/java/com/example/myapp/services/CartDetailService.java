package com.example.myapp.services;

import com.example.myapp.dto.response.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface CartDetailService {

    ResponseEntity<ResponseObject> addProductToCart(Long cartId, Long productId, int amount);

    ResponseEntity<ResponseObject> deleteProductToCart(Long cartId, Long productId);

    ResponseEntity<?> getProductToCart(Long cartId);

    ResponseEntity<ResponseObject> clearCartDetail(Long cartId);
}
