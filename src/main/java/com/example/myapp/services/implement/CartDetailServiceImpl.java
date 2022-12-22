package com.example.myapp.services.implement;

import com.example.myapp.dto.response.CartDetailResponseDTO;
import com.example.myapp.dto.response.ResponseObject;
import com.example.myapp.entites.Cart;
import com.example.myapp.entites.CartDetail;
import com.example.myapp.entites.Product;
import com.example.myapp.exceptions.InvalidValueException;
import com.example.myapp.exceptions.ResourceNotFoundException;
import com.example.myapp.mapper.CartDetailMapper;
import com.example.myapp.repositories.CartDetailRepository;
import com.example.myapp.repositories.CartRepository;
import com.example.myapp.repositories.ProductRepository;
import com.example.myapp.services.CartDetailService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartDetailServiceImpl implements CartDetailService {

    @Autowired private CartDetailRepository cartDetailRepository;
    @Autowired private CartRepository cartRepository;
    @Autowired private ProductRepository productRepository;

    private final CartDetailMapper cartDetailMapper = Mappers.getMapper(CartDetailMapper.class);

    @Override
    public ResponseEntity<ResponseObject> addProductToCart(Long cartId, Long productId, int amount) {
        CartDetail cartDetail = new CartDetail();
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Could not find cart with ID = " + cartId));
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Could not find product with ID = " + productId));

        // check số lượng mua > số lượng còn trong kho
        if(amount > product.getQuantity()) {
            throw new InvalidValueException("Số lượng sản phẩm mua phải bé hơn số lượng sản phẩm còn trong cửa hàng !!!");
        }
        Optional<CartDetail> getCartDetail = cartDetailRepository.findCartDetailByCartAndProduct(cart, product);
        if (getCartDetail.isPresent()) {
            /*
            if (amount < 0) {
                throw new InvalidValueException("Số lượng sản phẩm mua phải lớn hơn 1");
            }
            */
            cartDetail = getCartDetail.get();
            int newAmount = getCartDetail.get().getAmount() + amount;
            if (newAmount < 0) {
                throw new InvalidValueException("Số lượng sản phẩm mua không thể có giá trị âm !!!");
            }
            cartDetail.setAmount(newAmount);
        }
        else {
            cartDetail.setCart(cart);
            cartDetail.setProduct(product);
            cartDetail.setAmount(amount);
        }

        CartDetail cartDetailSaved = cartDetailRepository.save(cartDetail);
        CartDetailResponseDTO cartDetailResponseDTO = cartDetailMapper.cartDetailToCartDetailResponseDTO(cartDetailSaved);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK, "Thêm sản phẩm thành công vào giỏ hàng!", cartDetailResponseDTO));
    }

    @Override
    public ResponseEntity<ResponseObject> deleteProductToCart(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Could not find cart with ID = " + cartId));
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Could not find product with ID = " + productId));

        CartDetail cartDetail = cartDetailRepository.findCartDetailByCartAndProduct(cart, product).orElseThrow(() -> new ResourceNotFoundException("Could not find product with ID = " + productId + " and cart ID = " + cartId));
        cartDetailRepository.delete(cartDetail);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK, "Xóa sản phẩm trong giỏ hàng thành công !!!"));
    }

    @Override
    public ResponseEntity<?> getProductToCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Could not find cart with ID = " + cartId));
        List<CartDetail> cartDetailList = cartDetailRepository.findCartDetailByCart(cart);

        List<CartDetailResponseDTO> cartDetailResponseDTOList = new ArrayList<>();
        for(CartDetail cartDetail : cartDetailList) {
            CartDetailResponseDTO cartDetailResponseDTO = cartDetailMapper.cartDetailToCartDetailResponseDTO(cartDetail);
            cartDetailResponseDTOList.add(cartDetailResponseDTO);
        }

        return ResponseEntity.status(HttpStatus.OK).body(cartDetailResponseDTOList);
    }
}
