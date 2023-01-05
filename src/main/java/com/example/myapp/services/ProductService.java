package com.example.myapp.services;

import com.example.myapp.dto.request.ProductRequestDTO;
import com.example.myapp.dto.response.ResponseObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ProductService {
    ResponseEntity<?> getAllProductOnTrading(Pageable pageable);
    ResponseEntity<ResponseObject> createProduct(ProductRequestDTO productRequestDTO);
    ResponseEntity<ResponseObject> updateProduct(ProductRequestDTO productRequestDTO, Long id);
    ResponseEntity<ResponseObject> deleteProduct(Long id);

    ResponseEntity<?> getProductById(Long id);
    ResponseEntity<?> search(String search, int page, int size);

    ResponseEntity<Integer> getNumberOfProduct();

    ResponseEntity<?> getProductByBrand(Long brandId);

    ResponseEntity<?> getProductByCategory(Long categoryId);

}
