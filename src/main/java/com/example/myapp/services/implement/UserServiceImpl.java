package com.example.myapp.services.implement;

import com.example.myapp.dto.request.UserRequestDTO;
import com.example.myapp.dto.response.ResponseObject;
import com.example.myapp.dto.response.UserResponseDTO;
import com.example.myapp.entites.User;
import com.example.myapp.exceptions.ResourceAlreadyExistsException;
import com.example.myapp.exceptions.ResourceNotFoundException;
import com.example.myapp.mapper.UserMapper;
import com.example.myapp.repositories.UserRepository;
import com.example.myapp.services.UserService;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Autowired
    private UserRepository userRepository;


    @Override
    public ResponseEntity<ResponseObject> saveUser(UserRequestDTO userRequestDTO) throws MessagingException, UnsupportedEncodingException {
        User user = mapper.userRequestDTOtoUser(userRequestDTO);

        //Check phone user existed
        Optional<User> userCheckPhone = userRepository.findUserByPhone(userRequestDTO.getPhone());
        if (userCheckPhone.isPresent()){
            throw new ResourceAlreadyExistsException("Phone user existed");
        }

        //Check email user existed
        Optional<User> userCheckEmail = userRepository.findUserByEmail(userRequestDTO.getEmail());
        if (userCheckEmail.isPresent()){
            throw new ResourceAlreadyExistsException("Email user existed");
        }
        // check role already exists
        // code after
        UserResponseDTO userResponseDTO = mapper.userToUserResponseDTO(userRepository.save(user));

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "Create user successfully!", userResponseDTO));
    }

    @Override
    public ResponseEntity<ResponseObject> updateUser(Long id, UserRequestDTO userRequestDTO) {
        User user = mapper.userRequestDTOtoUser(userRequestDTO);
        user.setId(id);
        User userExists = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Could not find user with ID = " + id));

        //Check email user existed
        if (!user.getEmail().equals(userExists.getEmail())) {
            Optional<User> userCheckEmail = userRepository.findUserByEmail(userRequestDTO.getEmail());
            if (userCheckEmail.isPresent()){
                throw new ResourceAlreadyExistsException("Email user existed");
            }
        }
        //Check phone user existed
        if (!user.getPhone().equals(userExists.getPhone())) {
            Optional<User> userCheckPhone = userRepository.findUserByPhone(userRequestDTO.getPhone());
            if (userCheckPhone.isPresent()){
                throw new ResourceAlreadyExistsException("Phone user existed");
            }
        }
        // check role already exists
        // code after

        UserResponseDTO userResponseDTO = mapper.userToUserResponseDTO(userRepository.save(user));

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK, "Update User successfully!", userResponseDTO));
    }

    @Override
    public ResponseEntity<?> getAllUser(Pageable pageable) {
        List<UserResponseDTO> userResponseDTOList = new ArrayList<>();
        Page<User> getUserList = userRepository.findAll(pageable);
        List<User> userList = getUserList.getContent();
        for (User user : userList) {
            UserResponseDTO userResponseDTO = mapper.userToUserResponseDTO(user);
            userResponseDTOList.add(userResponseDTO);
        }
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDTOList);
    }

    @Override
    public ResponseEntity<ResponseObject> deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Could not find user with ID = " + id));
        // delete cart by user
        // code after

        // delete feedback by user
        // code after

        // delete address
        // code after

        userRepository.delete(user);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK, "Delete user success!!!", null));
    }

    @Override
    public ResponseEntity<UserResponseDTO> getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Could not find user with ID = " + id));
        UserResponseDTO userResponseDTO = mapper.userToUserResponseDTO(user);

        return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
    }
}
