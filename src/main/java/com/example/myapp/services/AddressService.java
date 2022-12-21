package com.example.myapp.services;

import com.example.myapp.dto.request.AddressRequestDTO;
import com.example.myapp.dto.response.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface AddressService {
    ResponseEntity<ResponseObject> createAddress(AddressRequestDTO addressRequestDTO);
}
