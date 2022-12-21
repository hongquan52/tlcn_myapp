package com.example.myapp.controller;

import com.example.myapp.dto.request.AddressRequestDTO;
import com.example.myapp.dto.response.ResponseObject;
import com.example.myapp.services.AddressDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/address")
public class AddressController {
    @Autowired private AddressDetailService addressDetailService;

    @PostMapping(value = "/user")
    public ResponseEntity<ResponseObject> createAddressUser(@ModelAttribute AddressRequestDTO addressRequestDTO, @RequestParam(name = "userId") Long userId){
        return addressDetailService.createAddressDetail(addressRequestDTO, userId);
    }

    @PutMapping(value = "/user")
    public ResponseEntity<ResponseObject> updateAddressUser(@ModelAttribute AddressRequestDTO addressRequestDTO,
                                                            @RequestParam(name = "userId") Long userId,
                                                            @RequestParam(name = "addressId") Long addressId){
        return addressDetailService.updateAddressDetail(addressId, userId, addressRequestDTO);
    }

    @GetMapping(value = "/user")
    public ResponseEntity<?> getAddressByUser(@RequestParam(name = "userId") Long userId){
        return addressDetailService.getAddressByUser(userId);
    }

    @DeleteMapping(value = "/user")
    public ResponseEntity<ResponseObject> deleteAddressUser(@RequestParam(name = "userId") Long userId,
                                                            @RequestParam(name = "addressId") Long addressId){
        return addressDetailService.deleteAddressDetail(addressId, userId);
    }
}
