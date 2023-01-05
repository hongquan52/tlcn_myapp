package com.example.myapp.mapper;

import com.example.myapp.dto.request.BillRequestDTO;
import com.example.myapp.dto.response.BillResponseDTO;
import com.example.myapp.entites.Bill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BillMapper {
    @Mapping(target = "userName",source = "b.user.name")
    @Mapping(target = "billId", source = "b.id")
    @Mapping(target = "status", source = "b.status")
    @Mapping(target = "paymentMethod", source = "b.paymentMethod")
    @Mapping(target = "totalPrice", source = "b.totalPrice")
    @Mapping(target = "payDate", source = "b.payDate")

    BillResponseDTO billToBillResponseDTO(Bill b);

    @Mapping(target = "status", source ="b.status")
    @Mapping(target = "payDate", source ="b.payDate")
    @Mapping(target = "paymentMethod", source ="b.paymentMethod")
    @Mapping(target = "totalPrice", source = "b.totalPrice")

    Bill billRequestDTOToBill(BillRequestDTO b);
}
