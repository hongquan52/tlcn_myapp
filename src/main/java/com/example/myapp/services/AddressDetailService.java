package com.example.myapp.services;

import com.example.myapp.dto.request.AddressRequestDTO;
import com.example.myapp.dto.response.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface AddressDetailService {
    ResponseEntity<ResponseObject> createAddressDetail(
            AddressRequestDTO addressRequestDTO,
            Long userId
    );

    ResponseEntity<?> getAddressByUser(Long id);

    ResponseEntity<ResponseObject> updateAddressDetail(Long addressId, Long userId, AddressRequestDTO addressRequestDTO);

    ResponseEntity<ResponseObject> deleteAddressDetail(Long addressId, Long userId);
}
