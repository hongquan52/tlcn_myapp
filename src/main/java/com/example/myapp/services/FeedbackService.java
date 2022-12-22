package com.example.myapp.services;

import com.example.myapp.dto.request.FeedbackRequestDTO;
import com.example.myapp.dto.response.FeedbackResponseDTO;
import com.example.myapp.dto.response.ResponseObject;
import com.example.myapp.entites.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface FeedbackService {
    ResponseEntity<?> getAllFeedbackOnTrading(Pageable pageable);

    ResponseEntity<?> getAllFeedbackByProduct(Pageable pageable, Long productId);

    ResponseEntity<ResponseObject> createFeedback(FeedbackRequestDTO feedbackRequestDTO);

    ResponseEntity<ResponseObject> updateFeedback(FeedbackRequestDTO feedbackRequestDTO);

    ResponseEntity<ResponseObject> deleteFeedback(Long feedbackId);

    FeedbackResponseDTO getFeedbackById(Long feedbackId);
}
