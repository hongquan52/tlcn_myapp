package com.example.myapp.repositories;

import com.example.myapp.entites.Bill;
import com.example.myapp.entites.BillDetail;
import com.example.myapp.entites.Product;
import com.example.myapp.models.IProductQuantity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BillDetailRepository extends JpaRepository<BillDetail, Long>{

    List<BillDetail> findBillDetailByBill(Bill bill);

    List<BillDetail> findBillDetailByProduct(Product product);

    Optional<BillDetail> findBillDetailByBillAndProduct(Bill bill, Product product);

    @Query(value = "select sum (b.quantity) from BillDetail b where b.bill.status = 'paid'")
    Optional<Integer> numberProductOfAllBill();

    @Query(value = "select b.product as product, sum (b.quantity) as quantity from BillDetail b where b.bill.status = 'paid' group by b.product")
    List<IProductQuantity> numberProductOfBill(Sort sort);

}
