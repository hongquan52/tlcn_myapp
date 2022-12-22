package com.example.myapp.repositories;

import com.example.myapp.entites.Cart;
import com.example.myapp.entites.CartDetail;
import com.example.myapp.entites.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {

    List<CartDetail> findCartDetailByCart(Cart cart);

    Optional<CartDetail> findCartDetailByCartAndProduct(Cart cart, Product product);
}
