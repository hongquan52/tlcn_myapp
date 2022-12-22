package com.example.myapp.services;

import com.example.myapp.dto.response.CartResponseDTO;
import org.springframework.http.ResponseEntity;

public interface CartService {
    ResponseEntity<CartResponseDTO> getCartByUser(Long userId);
}
