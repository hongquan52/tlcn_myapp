package com.example.myapp.controller;

import com.example.myapp.dto.request.UserRequestDTO;
import com.example.myapp.dto.response.ResponseObject;
import com.example.myapp.dto.response.UserResponseDTO;
import com.example.myapp.services.ProductService;
import com.example.myapp.services.UserService;
import com.example.myapp.utils.Utils;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping(value = "/api/v1/user")
public class UserController {
    @Autowired private UserService userService;

    @GetMapping(value = "")
    public ResponseEntity<?> getAllUser(@RequestParam(name = "size", required = false, defaultValue = Utils.DEFAULT_PAGE_SIZE) int size,
                                        @RequestParam(name = "page", required = false, defaultValue = Utils.DEFAULT_PAGE_NUMBER) int page){
        Pageable pageable = PageRequest.of(page - 1, size);
        return userService.getAllUser(pageable);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ResponseObject> deleteUser(@PathVariable(name = "id") Long id){
        return userService.deleteUser(id);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable(name = "id") Long id){
        return userService.getUserById(id);
    }

    @PostMapping(value = "")
    public ResponseEntity<ResponseObject> createUser(@ModelAttribute @Valid UserRequestDTO userRequestDTO)
            throws MessagingException, UnsupportedEncodingException {

        return userService.saveUser(userRequestDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ResponseObject> updateUser(@PathVariable(name = "id") Long id, @ModelAttribute @Valid UserRequestDTO userRequestDTO){
        return userService.updateUser(id, userRequestDTO);
    }
}
