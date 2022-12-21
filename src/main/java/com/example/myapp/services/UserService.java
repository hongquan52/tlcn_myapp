package com.example.myapp.services;

import com.example.myapp.dto.request.UserRequestDTO;
import com.example.myapp.dto.response.ResponseObject;
import com.example.myapp.dto.response.UserResponseDTO;
import jakarta.mail.MessagingException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;

public interface UserService {
    ResponseEntity<ResponseObject> saveUser(UserRequestDTO userRequestDTO/*, String siteUrl*/)
            throws MessagingException, UnsupportedEncodingException;
    ResponseEntity<ResponseObject> updateUser(Long id, UserRequestDTO userRequestDTO);
    ResponseEntity<?> getAllUser(Pageable pageable);
    ResponseEntity<ResponseObject> deleteUser(Long id);
    ResponseEntity<UserResponseDTO> getUserById(Long id);
}
