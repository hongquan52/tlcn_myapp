package com.example.myapp.mapper;

import com.example.myapp.dto.request.DeliveryRequestDTO;
import com.example.myapp.dto.response.DeliveryResponseDTO;
import com.example.myapp.entites.Delivery;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DeliveryMapper {

    @Mapping(target = "status", source = "dto.status")
    @Mapping(target = "address", expression = "java(null)")
    @Mapping(target = "bill", expression = "java(null)")
    @Mapping(target = "shipper", expression = "java(null)")

    Delivery deliveryRequestDTOToDelivery(DeliveryRequestDTO dto);

    @Mapping(target = "id", source = "d.id")
    @Mapping(target = "status", source = "d.status")
    @Mapping(target = "billId", source = "d.bill.id")
    @Mapping(target = "shipperId", source = "d.shipper.id")
    @Mapping(target = "shipperName", source = "d.shipper.name")
    @Mapping(target = "shipperPhone", source = "d.shipper.phone")

    DeliveryResponseDTO deliveryToDeliveryResponseDTO(Delivery d);
}
