package com.example.myapp.repositories;

import com.example.myapp.entites.User;
import com.example.myapp.models.IListShipper;
import com.example.myapp.models.IStatisticDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findUserByVerificationCode(String verifyCode);
    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByPhone(String phone);

    //Optional<User> findUserByVerificationCode(String verifyCode);

    // static
    @Query(value = "select count(u) from User u where u.role.id = 2")
    Optional<Integer> getNumberOfCustomer();

    @Query(value = "select u.id, u.name from User u where u.role.id = 3")
    List<IListShipper> getAllShipper();

    /*
    @Query(value = "select weekday(create_date) as weekDay, count(id) as totalValue from tbl_user"
    + "where create_date <= current_date() and create_date > date_sub(current_date(), interval 7 Day)"
    + "group by weekday(create_date)", nativeQuery = true)
    */
    @Query(value = "select weekday(create_date) as weekDay, count(id) as totalValue  from tbl_user "
            + "where create_date <= current_date() and create_date > date_sub(current_date(), interval 7 Day) "
            + "group by weekday(create_date)", nativeQuery = true)
    List<IStatisticDay> findAllUserByDay();
}
