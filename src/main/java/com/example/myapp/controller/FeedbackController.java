package com.example.myapp.controller;

import com.example.myapp.dto.request.FeedbackRequestDTO;
import com.example.myapp.dto.response.FeedbackResponseDTO;
import com.example.myapp.dto.response.ResponseObject;
import com.example.myapp.services.FeedbackService;
import com.example.myapp.utils.Utils;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping(value = "/api/v1/feedback")
public class FeedbackController {
    @Autowired private FeedbackService feedbackService;

    @GetMapping(value = "")
    public ResponseEntity<?> getAllFeedbackOnTrading(
            @RequestParam(name = "page", required = false, defaultValue = Utils.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(name = "size", required = false, defaultValue = Utils.DEFAULT_PAGE_SIZE) int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        return feedbackService.getAllFeedbackOnTrading(pageable);
    }

    @PostMapping
    public ResponseEntity<ResponseObject> createFeedback(@ModelAttribute @Valid FeedbackRequestDTO feedbackRequestDTO) {
        return feedbackService.createFeedback(feedbackRequestDTO);
    }

    @DeleteMapping(value = "")
    public ResponseEntity<ResponseObject> deleteFeedback(@RequestParam(name = "id") Long id) {
        return feedbackService.deleteFeedback(id);
    }

    @GetMapping(value = "/{feedbackId}")
    public FeedbackResponseDTO getFeedbackById(@PathVariable(name = "feedbackId") Long feedbackId) {
        return feedbackService.getFeedbackById(feedbackId);
    }

    @GetMapping(value = "/product")
    public ResponseEntity<?> getAllFeedbackByProduct(
            @RequestParam(name = "page", required = false, defaultValue = Utils.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(name = "size", required = false, defaultValue = Utils.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(name = "productId") Long productId
    ) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return feedbackService.getAllFeedbackByProduct(pageable, productId);
    }
}
