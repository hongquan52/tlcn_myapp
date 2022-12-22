package com.example.myapp.repositories;

import com.example.myapp.entites.Feedback;
import com.example.myapp.entites.Product;
import com.example.myapp.entites.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    Page<Feedback> findFeedbackByProduct(Pageable pageable, Product product);

    List<Feedback> findFeedbacksByProduct(Product product);

    List<Feedback> findFeedbackByUser(User user);
}
