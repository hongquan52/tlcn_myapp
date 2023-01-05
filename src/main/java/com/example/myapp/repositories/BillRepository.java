package com.example.myapp.repositories;

import com.example.myapp.entites.Bill;
import com.example.myapp.entites.User;
import com.example.myapp.models.IStatisticDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    List<Bill> findBillByUser(User user);

    @Query(value = "select count(b) from Bill b")
    Optional<Integer> getNumberOfBill();

    @Query(value = "select sum(b.totalPrice) as sales from Bill b where b.status = 'paid'")
    Optional<Double> getSales();

    @Query(value = "select weekday(create_date) as weekDay, SUM(total_price) as totalValue  from tbl_bill \n"
            + "      where status = 'paid' and create_date < now() and create_date > date_sub(current_date(), interval 5 Day) \n"
            + "      group by weekday(create_date)", nativeQuery = true)
    List<IStatisticDay> findRevenueByDay();

    @Query(value = "select count(b) from Bill b where b.status='waiting_confirm' ")
    Optional<Integer> getNumberOfBillWaitingConfirm();
    @Query(value = "select count(b) from Bill b where b.status='confirmed' ")
    Optional<Integer> getNumberOfBillConfirmed();
    @Query(value = "select count(b) from Bill b where b.status='ready_to_delivery'")
    Optional<Integer> getNumberOfBillReadyToDelivery();
    @Query(value = "select count(b) from Bill b where b.status='delivering'")
    Optional<Integer> getNumberOfBillDelivering();
    @Query(value = "select count(b) from Bill b where b.status='paid'")
    Optional<Integer> getNumberOfBillPaid();
    @Query(value = "select count(b) from Bill b where b.status='cancel'")
    Optional<Integer> getNumberOfBillCancel();
}
