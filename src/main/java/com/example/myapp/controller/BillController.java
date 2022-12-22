package com.example.myapp.controller;

import com.example.myapp.dto.request.BillRequestDTO;
import com.example.myapp.dto.response.ResponseObject;
import com.example.myapp.services.BillDetailService;
import com.example.myapp.services.BillService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/bill")
public class BillController {
    @Autowired private BillService billService;
    @Autowired private BillDetailService billDetailService;

    @GetMapping(value = "")
    public ResponseEntity<?> getAllBill() {
        return billService.getAllBill();
    }

    @GetMapping(value = "/user")
    public ResponseEntity<?> getBillByUser(@RequestParam(name = "userId") Long userId){
        return billService.getBillByUser(userId);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getBillById(@PathVariable(name = "id") Long id) {
        return billService.getBillById(id);
    }

    @PostMapping(value = "")
    public ResponseEntity<?> createBill(@RequestParam(name = "userId") Long userId,
                                        @ModelAttribute @Valid BillRequestDTO billRequestDTO ) {
        return billService.createBill(userId, billRequestDTO);
    }

    @PutMapping(value = "")
    public ResponseEntity<?> updateBill (@RequestParam(name = "billId") Long billId, @ModelAttribute @Valid BillRequestDTO billRequestDTO) {
        return billService.updateBill(billId, billRequestDTO);
    }

    @DeleteMapping(value = "")
    public ResponseEntity<ResponseObject> deleteBill(@RequestParam(name = "billId") Long billId){
        return billService.deleteBill(billId);
    }

    @GetMapping(value = "/product")
    public ResponseEntity<?> getBillProduct(@RequestParam(name = "billId") Long billId) {
        return billDetailService.getProductByBill(billId);
    }
}
