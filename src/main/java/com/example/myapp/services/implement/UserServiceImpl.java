package com.example.myapp.services.implement;

import com.example.myapp.dto.request.UserRequestDTO;
import com.example.myapp.dto.response.ResponseObject;
import com.example.myapp.dto.response.UserResponseDTO;
import com.example.myapp.dto.response.UserShipperResponseDTO;
import com.example.myapp.entites.*;
import com.example.myapp.exceptions.ResourceAlreadyExistsException;
import com.example.myapp.exceptions.ResourceNotFoundException;
import com.example.myapp.mapper.UserMapper;
import com.example.myapp.models.IListShipper;
import com.example.myapp.repositories.*;
import com.example.myapp.services.UserService;
import javax.mail.MessagingException;
import javax.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import net.bytebuddy.utility.RandomString;

import javax.mail.MessagingException;
import javax.swing.text.html.Option;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;


@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);
    @Autowired private RoleRepository roleRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private UserRepository userRepository;
    @Autowired private CartRepository cartRepository;
    @Autowired private AddressDetailRepository addressDetailRepository;
    @Autowired private FeedbackRepository feedbackRepository;
    @Autowired private CartDetailRepository cartDetailRepository;

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
        encodePassword(user);
        //Check role already exists
        Role role = roleRepository.findRoleById(user.getRole().getId()).orElseThrow(() -> new ResourceNotFoundException("Could not find role with ID = " + user.getRole().getId()));
        user.setRole(role);
        user.setEnable(true);

        String randomCodeVerify = RandomString.make(64);
        user.setVerificationCode(randomCodeVerify);
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
        encodePassword(user);
        //Check role already exists
        Role role = roleRepository.findRoleById(user.getRole().getId()).orElseThrow(() -> new ResourceNotFoundException("Could not find role with ID = " + user.getRole().getId()));
        user.setRole(role);
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
        Optional<Cart> getCart = cartRepository.findCartByUser(user);
        if (getCart.isPresent()) {
            List<CartDetail> cartDetailList = cartDetailRepository.findCartDetailByCart(getCart.get());
            // xoa chi tiet Gio hang
            cartDetailRepository.deleteAll(cartDetailList);
            // xoa gio hang
            cartRepository.delete(getCart.get());
        }

        // delete feedback by user
        // code after
        List<Feedback> feedbackList = feedbackRepository.findFeedbackByUser(user);
        for (Feedback f : feedbackList) {
            feedbackRepository.delete(f);
        }

        // delete address
        List<AddressDetail> addressDetailList = addressDetailRepository.findAddressDetailsByUser(user);
        addressDetailRepository.deleteAll(addressDetailList);

        /*
        * imageStorageService.deleteFile(user.getImages(), "user");
        * */

        userRepository.delete(user);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK, "Delete user success!!!", null));
    }

    @Override
    public ResponseEntity<UserResponseDTO> getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Could not find user with ID = " + id));
        UserResponseDTO userResponseDTO = mapper.userToUserResponseDTO(user);

        return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
    }

    @Override
    public ResponseEntity<?> getALlShipper() {
        List<IListShipper> shipperList = userRepository.getAllShipper();
        List<UserShipperResponseDTO> userShipperResponseDTOList = new ArrayList<>();

        for (IListShipper i: shipperList) {
            UserShipperResponseDTO userShipperResponseDTO = mapper.iListShipperToShipperResponseDTO(i);
            userShipperResponseDTOList.add(userShipperResponseDTO);
        }
        return ResponseEntity.status(HttpStatus.OK).body(userShipperResponseDTOList);
    }


    @Override
    public ResponseEntity<Integer> getNumberOfCustomer() {
        int numberOfCustomer = userRepository.getNumberOfCustomer().orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return ResponseEntity.status(HttpStatus.OK).body(numberOfCustomer);
    }
    @Override
    public ResponseEntity<ResponseObject> verifyUser(String verifyCode) {
        User getUser = userRepository.findUserByVerificationCode(verifyCode)
                .orElseThrow(() -> new ResourceNotFoundException("Verify code is incorrect"));
        getUser.setEnable(true);
        User user =  userRepository.save(getUser);
        //Create cart by user
        Cart cart = new Cart();
        cart.setUser(user);
        this.cartRepository.save(cart);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK, "Verify account success!!!"));
    }

    private void encodePassword(User user){
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }
}
