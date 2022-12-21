package com.example.myapp.services.implement;

import com.example.myapp.dto.request.AddressRequestDTO;
import com.example.myapp.dto.response.ResponseObject;
import com.example.myapp.services.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {
    @Override
    public ResponseEntity<ResponseObject> createAddress(AddressRequestDTO addressRequestDTO) {
        return null;
    }
}
