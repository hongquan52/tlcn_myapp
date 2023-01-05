package com.example.myapp.controller;

import com.example.myapp.services.BillService;
import com.example.myapp.services.ProductService;
import com.example.myapp.services.StatisticService;
import com.example.myapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/statistic")
public class StatisticController {

    @Autowired private ProductService productService;
    @Autowired private UserService userService;

    @Autowired private BillService billService;

    @Autowired private StatisticService statisticService;

    @GetMapping(value = "/product")
    public ResponseEntity<Integer> getNumberOfProduct() {
        return productService.getNumberOfProduct();
    }

    @GetMapping(value = "/customer")
    public ResponseEntity<Integer> getNumberOfCustomer() {
        return userService.getNumberOfCustomer();
    }

    @GetMapping(value = "/bill")
    public ResponseEntity<Integer> getNumberOfBill() {
        return billService.getNumberOfBill();
    }

    @GetMapping(value = "sales")
    public ResponseEntity<Double> getSales() {
        return billService.getSales();
    }

    @GetMapping(value = "/bill/product")
    public ResponseEntity<Integer> getNumberProductOfAllBill() {
        return statisticService.getNumberProductOfAllBill();
    }

    @GetMapping(value = "/bill/product/sales")
    public ResponseEntity<?> getNumberProductOfBill() {
        return statisticService.getNumberProductOfBill();
    }

    @GetMapping(value = "/user/day")
    public ResponseEntity<?> getUserByDay() {
        return statisticService.getAllUserByDay();
    }

    @GetMapping(value = "/bill/day")
    public ResponseEntity<?> getRevenueByDay() {
        return statisticService.getRevenueByDay();
    }

    @GetMapping(value = "/bill/status/waitingConfirm")
    public ResponseEntity<Integer> getNumberOfBillWaitingConfirm() {
        return billService.getNumberOfBillWaitingConfirm();
    }

    @GetMapping(value = "/bill/status/confirmed")
    public ResponseEntity<Integer> getNumberOfBillConfirmed() {
        return billService.getNumberOfBillConfimed();
    }

    @GetMapping(value = "/bill/status/readyToDelivery")
    public ResponseEntity<Integer> getNumberOfBillReadyToDelivery() {
        return billService.getNumberOfBillReadyToDelivery();
    }

    @GetMapping(value = "/bill/status/delivering")
    public ResponseEntity<Integer> getNumberOfBillDelivering() {
        return billService.getNumberOfBillDelivering();
    }

    @GetMapping(value = "/bill/status/paid")
    public ResponseEntity<Integer> getNumberOfBillPaid() {
        return billService.getNumberOfBillPaid();
    }

    @GetMapping(value = "/bill/status/cancel")
    public ResponseEntity<Integer> getNumberOfBillCancel() {
        return billService.getNumberOfBillCancel();
    }
}
