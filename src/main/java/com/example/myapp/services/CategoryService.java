package com.example.myapp.services;

import com.example.myapp.dto.request.CategoryRequestDTO;
import com.example.myapp.dto.response.CategoryResponseDTO;
import com.example.myapp.dto.response.ResponseObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface CategoryService {
    ResponseEntity<?> getAllCategoryOnTrading(Pageable pageable);

    ResponseEntity<?> getAllCategory();

    ResponseEntity<ResponseObject> createCategory(CategoryRequestDTO categoryRequestDTO);

    ResponseEntity<ResponseObject> updateCategory(CategoryRequestDTO categoryRequestDTO, Long id);

    ResponseEntity<ResponseObject> deleteCategory(Long id);

    CategoryResponseDTO getCategoryById(Long id);
}
