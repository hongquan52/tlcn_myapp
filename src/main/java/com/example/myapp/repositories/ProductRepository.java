package com.example.myapp.repositories;

import com.example.myapp.entites.Brand;
import com.example.myapp.entites.Category;
import com.example.myapp.entites.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ProductRepository  extends JpaRepository<Product, Long>,
        JpaSpecificationExecutor<Product> {
    Optional<Product> findProductByName(String name);

    List<Product> findProductByBrand(Brand brand);
    List<Product> findProductByCategory(Category category);
}
