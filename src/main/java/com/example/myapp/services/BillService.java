package com.example.myapp.services;

import com.example.myapp.dto.request.BillRequestDTO;
import com.example.myapp.dto.response.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface BillService {

    ResponseEntity<?> getAllBill();
    ResponseEntity<?> getBillByUser(Long userId);

    ResponseEntity<?> getBillById(Long id);

    ResponseEntity<ResponseObject> createBill(Long userId, BillRequestDTO billRequestDTO);

    ResponseEntity<ResponseObject> updateBill(Long billId, BillRequestDTO billRequestDTO);
    ResponseEntity<ResponseObject> deleteBill(Long billId);

    ResponseEntity<Integer> getNumberOfBill();

    ResponseEntity<Double> getSales();

    ResponseEntity<Integer> getNumberOfBillWaitingConfirm();
    ResponseEntity<Integer> getNumberOfBillConfimed();
    ResponseEntity<Integer> getNumberOfBillReadyToDelivery();
    ResponseEntity<Integer> getNumberOfBillDelivering();
    ResponseEntity<Integer> getNumberOfBillPaid();
    ResponseEntity<Integer> getNumberOfBillCancel();
}
