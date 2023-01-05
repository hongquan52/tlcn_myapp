package com.example.myapp.controller;

import com.example.myapp.dto.request.BrandRequestDTO;
import com.example.myapp.dto.response.BrandResponseDTO;
import com.example.myapp.dto.response.ResponseObject;
import com.example.myapp.services.BrandService;
import javax.validation.Valid;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/brand")
public class BrandController {
    @Autowired private BrandService brandService;

    @GetMapping(value = "")
    public ResponseEntity<?> getAllBrand() {
        return brandService.getAllBrand();
    }

    @PostMapping(value = "")
    public ResponseEntity<ResponseObject> createBrand(@ModelAttribute @Valid BrandRequestDTO brandRequestDTO) {
        return brandService.createBrand(brandRequestDTO);
    }

    @PutMapping(value = "")
    public ResponseEntity<ResponseObject> updateBrand(@ModelAttribute @Valid BrandRequestDTO brandRequestDTO, @RequestParam(name = "id") Long id) {
        return brandService.updateBrand(brandRequestDTO, id);
    }

    @DeleteMapping(value = "")
    public ResponseEntity<ResponseObject> deleteBrand(@RequestParam(name = "id") Long id) {
        return brandService.deleteBrand(id);
    }

    @GetMapping(value = "/{id}")
    public BrandResponseDTO getBrandById(@PathVariable(name = "id") Long id) {
        return brandService.getBrandById(id);
    }
}
