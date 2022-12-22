package com.example.myapp.services.implement;

import com.example.myapp.dto.response.CartResponseDTO;
import com.example.myapp.entites.Cart;
import com.example.myapp.entites.User;
import com.example.myapp.exceptions.ResourceNotFoundException;
import com.example.myapp.repositories.CartDetailRepository;
import com.example.myapp.repositories.CartRepository;
import com.example.myapp.repositories.UserRepository;
import com.example.myapp.services.CartService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.myapp.mapper.CartMapper;

@Service
public class CartServiceImpl implements CartService {

    @Autowired private CartRepository cartRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private CartDetailRepository cartDetailRepository;

    private final CartMapper cartMapper = Mappers.getMapper(CartMapper.class);

    @Override
    public ResponseEntity<CartResponseDTO> getCartByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Could not find user with ID = " + userId));
        Cart cart = cartRepository.findCartByUser(user).orElseThrow(() -> new ResourceNotFoundException("Could not find cart with user ID = " + userId));

        CartResponseDTO cartResponseDTO = cartMapper.cartToCartResponseDTO(cart);

        // tính tống số sản phẩm trong giỏ hàng
        int totalProduct = cartDetailRepository.findCartDetailByCart(cart).size();
        cartResponseDTO.setAmount(totalProduct);
        return ResponseEntity.status(HttpStatus.OK).body(cartResponseDTO);
    }
}
