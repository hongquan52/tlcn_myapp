package com.example.myapp.mapper;

import com.example.myapp.dto.response.BillDetailResponseDTO;
import com.example.myapp.entites.BillDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BillDetailMapper {

    @Mapping(target = "productId", source = "b.product.id")
    @Mapping(target = "productName", source = "b.product.name")
    @Mapping(target = "productImage", source = "b.product.image")
    @Mapping(target = "billId", source = "b.bill.id")
    @Mapping(target = "amount", source = "b.quantity")
    @Mapping(target = "payDate", source = "b.bill.payDate")
    @Mapping(target = "payMethod", source = "b.bill.paymentMethod")
    @Mapping(target = "status", source = "b.bill.status")
    @Mapping(target = "price",  source = "b.product.price")

    BillDetailResponseDTO billDetailToBillDetailResponseDTO(BillDetail b);
}
