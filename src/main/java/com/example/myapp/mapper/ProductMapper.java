package com.example.myapp.mapper;


import com.example.myapp.dto.response.ProductResponseDTO;
import com.example.myapp.dto.request.ProductRequestDTO;
import com.example.myapp.dto.response.ProductStatisticResponseDTO;
import com.example.myapp.entites.Product;
import com.example.myapp.models.IProductQuantity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    // id không được set null trong Mapper
//    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "quantity", source = "dto.quantity")
    @Mapping(target = "price", source = "dto.price")
    @Mapping(target = "promotion", source = "dto.promotion")
    @Mapping(target = "image", expression = "java(null)")
    @Mapping(target = "category.id", source = "dto.category")
    @Mapping(target = "brand.id", source = "dto.brand")
    Product productRequestDTOtoProduct(ProductRequestDTO dto);

    @Mapping(target = "id", source = "p.id")
    @Mapping(target = "name", source = "p.name")
    @Mapping(target = "quantity", source = "p.quantity")
    @Mapping(target = "promotion", source = "p.promotion")
    @Mapping(target = "price", source = "p.price")
    @Mapping(target = "description", source = "p.description")
    @Mapping(target = "image", source = "p.image")

    @Mapping(target = "category", source = "p.category.name")
    @Mapping(target = "brand", source = "p.brand.name")
    ProductResponseDTO productToProductResponseDTO(Product p);


    @Mapping(target = "id", source = "ip.product.id")
    @Mapping(target = "name", source = "ip.product.name")
    @Mapping(target = "quantity", source = "ip.product.quantity")
    @Mapping(target = "price", source = "ip.product.price")
    @Mapping(target = "quantitySales", source = "ip.quantity")
    @Mapping(target = "totalPrice", expression = "java(null)")
    ProductStatisticResponseDTO productToProductStatisticResponseDTO(IProductQuantity ip);
}

