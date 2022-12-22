package com.example.myapp.repositories;

import com.example.myapp.entites.Bill;
import com.example.myapp.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    List<Bill> findBillByUser(User user);
}
