package com.example.myapp.services;

import com.example.myapp.dto.request.BrandRequestDTO;
import com.example.myapp.dto.response.BrandResponseDTO;
import com.example.myapp.dto.response.ResponseObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface BrandService {
    ResponseEntity<?> getAllBrandOnTrading(Pageable pageable);

    ResponseEntity<?> getAllBrand();

    ResponseEntity<ResponseObject> createBrand(BrandRequestDTO brandRequestDTO);

    ResponseEntity<ResponseObject> updateBrand(BrandRequestDTO brandRequestDTO, Long id);

    ResponseEntity<ResponseObject> deleteBrand(Long id);

    BrandResponseDTO getBrandById(Long id);
}
