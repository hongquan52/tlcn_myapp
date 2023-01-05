package com.example.myapp.controller;

import com.example.myapp.dto.response.CategoryResponseDTO;
import com.example.myapp.dto.response.ResponseObject;
import com.example.myapp.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/category")
public class CategoryController {
    @Autowired private CategoryService categoryService;

    @GetMapping(value = "")
    public ResponseEntity<?> getAllCategory() {
        return categoryService.getAllCategory();
    }

    @GetMapping(value = "/{id}")
    public CategoryResponseDTO getCategoryById (@PathVariable(name = "id") Long id) {
        return categoryService.getCategoryById(id);
    }

    @DeleteMapping(value = "")
    public ResponseEntity<ResponseObject> deleteCategory(@RequestParam(name = "categoryId") Long id) {
        return categoryService.deleteCategory(id);
    }
}
