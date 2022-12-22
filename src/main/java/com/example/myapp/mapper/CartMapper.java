package com.example.myapp.mapper;

import com.example.myapp.dto.response.CartResponseDTO;
import com.example.myapp.entites.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "userId", source = "c.user.id")
    @Mapping(target = "cartId", source = "c.id")

    CartResponseDTO cartToCartResponseDTO(Cart c);
}
