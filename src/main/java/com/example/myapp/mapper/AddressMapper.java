package com.example.myapp.mapper;

import com.example.myapp.dto.request.AddressRequestDTO;
import com.example.myapp.entites.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "province", source = "dto.province")
    @Mapping(target = "district", source = "dto.district")
    @Mapping(target = "ward", source = "dto.ward")
    @Mapping(target = "apartmentNumber", source = "dto.apartmentNumber")
    Address addressRequestDTOtoAddress(AddressRequestDTO dto);
}
