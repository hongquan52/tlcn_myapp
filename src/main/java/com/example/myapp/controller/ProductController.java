package com.example.myapp.controller;

import com.example.myapp.services.ProductService;
import com.example.myapp.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/product")
public class ProductController {
    @Autowired private ProductService productService;

    @GetMapping(value = "")
    public ResponseEntity<?> getAllProductOnTrading(
            @RequestParam(name = "page", required = false, defaultValue = Utils.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(name = "size", required = false, defaultValue = Utils.DEFAULT_PAGE_SIZE) int size){
        Pageable pageable = PageRequest.of(page - 1, size);
        return productService.getAllProductOnTrading(pageable);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getProductById(@PathVariable(name = "id") Long id) {
        return productService.getProductById(id);
    }
}
