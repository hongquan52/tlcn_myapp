package com.example.myapp.repositories;

import com.example.myapp.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByPhone(String phone);

    //Optional<User> findUserByVerificationCode(String verifyCode);
}
