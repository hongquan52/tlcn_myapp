package com.example.myapp.mapper;

import com.example.myapp.dto.response.CartDetailResponseDTO;
import com.example.myapp.entites.CartDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartDetailMapper {
    @Mapping(target = "productId", source = "c.product.id")
    @Mapping(target = "cartId", source = "c.cart.id")
    @Mapping(target = "amount", source = "c.amount")
    @Mapping(target = "price", source = "c.product.price")
    @Mapping(target = "productName", source = "c.product.name")
    @Mapping(target = "productImage", source = "c.product.image")
    @Mapping(target = "promotion", source = "c.product.promotion")



    CartDetailResponseDTO cartDetailToCartDetailResponseDTO(CartDetail c);
}
