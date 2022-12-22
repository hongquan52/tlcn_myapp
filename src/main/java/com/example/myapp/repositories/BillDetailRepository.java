package com.example.myapp.repositories;

import com.example.myapp.entites.Bill;
import com.example.myapp.entites.BillDetail;
import com.example.myapp.entites.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BillDetailRepository extends JpaRepository<BillDetail, Long>{

    List<BillDetail> findBillDetailByBill(Bill bill);

    List<BillDetail> findBillDetailByProduct(Product product);

    Optional<BillDetail> findBillDetailByBillAndProduct(Bill bill, Product product);



}
