package com.example.myapp.controller;

import com.example.myapp.dto.response.ResponseObject;
import com.example.myapp.services.CartDetailService;
import com.example.myapp.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/cart")
public class CartController {

    @Autowired private CartService cartService;
    @Autowired private CartDetailService cartDetailService;

    @GetMapping(value = "/user")
    public ResponseEntity<?> getCartByUser(@RequestParam(name = "userId") Long userId) {
        return cartService.getCartByUser(userId);
    }

    @PostMapping(value = "/product")
    public ResponseEntity<ResponseObject> addProductToCart(@RequestParam(name = "cartId") Long cartId,
                                                           @RequestParam(name = "productId") Long productId,
                                                           @RequestParam(name = "amount") int amount) {
        return cartDetailService.addProductToCart(cartId, productId, amount);
    }

    @GetMapping(value = "/product")
    public ResponseEntity<?> getProductInCart(@RequestParam(name = "cartId") Long cartId){
        return cartDetailService.getProductToCart(cartId);
    }

    @DeleteMapping(value = "/product")
    public ResponseEntity<ResponseObject> deleteProductToCart(@RequestParam(name = "cartId") Long cartId,
                                                              @RequestParam(name = "productId") Long productId) {
        return cartDetailService.deleteProductToCart(cartId, productId);
    }
}
