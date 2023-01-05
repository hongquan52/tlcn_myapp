package com.example.myapp.services.implement;

import com.example.myapp.dto.request.FeedbackRequestDTO;
import com.example.myapp.dto.response.FeedbackResponseDTO;
import com.example.myapp.dto.response.ResponseObject;
import com.example.myapp.entites.Feedback;
import com.example.myapp.entites.Product;
import com.example.myapp.entites.User;
import com.example.myapp.exceptions.ResourceNotFoundException;
import com.example.myapp.mapper.FeedbackMapper;
import com.example.myapp.models.ItemTotalPage;
import com.example.myapp.repositories.FeedbackRepository;
import com.example.myapp.repositories.ProductRepository;
import com.example.myapp.repositories.UserRepository;
import com.example.myapp.services.FeedbackService;
import javax.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired private FeedbackRepository feedbackRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private ProductRepository productRepository;

    private final FeedbackMapper mapper = Mappers.getMapper(FeedbackMapper.class);

    @Override
    public ResponseEntity<?> getAllFeedbackOnTrading(Pageable pageable) {
        Page<Feedback> getFeedbackList = feedbackRepository.findAll(pageable);
        List<Feedback> feedbackList = getFeedbackList.getContent();
        List<FeedbackResponseDTO> feedbackResponseDTOList = new ArrayList<>();

        for (Feedback f : feedbackList) {
            FeedbackResponseDTO feedbackResponseDTO = mapper.feedbackToFeedbackResponseDTO(f);
            feedbackResponseDTOList.add(feedbackResponseDTO);
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ItemTotalPage(feedbackResponseDTOList, getFeedbackList.getTotalPages()));
    }

    @Override
    public ResponseEntity<?> getAllFeedbackByProduct(Pageable pageable, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Could not find product with ID = " + productId));
        Page<Feedback> getFeedbackList = feedbackRepository.findFeedbackByProduct(pageable,product);
        List<Feedback> feedbackList = getFeedbackList.getContent();
        List<FeedbackResponseDTO> feedbackResponseDTOList = new ArrayList<>();

        for (Feedback f : feedbackList) {
            FeedbackResponseDTO feedbackResponseDTO = mapper.feedbackToFeedbackResponseDTO(f);
            feedbackResponseDTOList.add(feedbackResponseDTO);
        }

        return ResponseEntity.status(HttpStatus.OK).body(new ItemTotalPage(feedbackResponseDTOList,getFeedbackList.getTotalPages()));
    }

    @Override
    public ResponseEntity<ResponseObject> createFeedback(FeedbackRequestDTO feedbackRequestDTO) {
        Feedback feedback = mapper.feedbackRequestDTOToFeedback(feedbackRequestDTO);

        User user = userRepository.findById(feedbackRequestDTO.getUser()).orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy User với ID = " + feedbackRequestDTO.getUser()));
        Product product = productRepository.findById(feedbackRequestDTO.getProduct()).orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy Product với ID = " + feedbackRequestDTO.getProduct()));

        feedback.setUser(user);
        feedback.setProduct(product);

        Feedback feedbackSaved = feedbackRepository.save(feedback);
        FeedbackResponseDTO feedbackResponseDTO = mapper.feedbackToFeedbackResponseDTO(feedbackSaved);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "Thêm mới Feedback thành công!", feedbackResponseDTO));
    }

    @Override
    public ResponseEntity<ResponseObject> updateFeedback(FeedbackRequestDTO feedbackRequestDTO) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseObject> deleteFeedback(Long feedbackId) {
        Feedback getFeedback = feedbackRepository.findById(feedbackId).orElseThrow(() -> new ResourceNotFoundException("Could not find feedback with ID = " + feedbackId));

        feedbackRepository.delete(getFeedback);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(HttpStatus.OK, "Xóa Feedback thành công!")
        );
    }

    @Override
    public FeedbackResponseDTO getFeedbackById(Long feedbackId) {
        Feedback feedback = feedbackRepository.findById(feedbackId).orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy Feedback với ID = " + feedbackId));
        FeedbackResponseDTO feedbackResponseDTO = mapper.feedbackToFeedbackResponseDTO(feedback);

        return feedbackResponseDTO;
    }
}
