package com.example.myapp.mapper;

import com.example.myapp.dto.request.BrandRequestDTO;
import com.example.myapp.dto.response.BrandResponseDTO;
import com.example.myapp.entites.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "description", source = "dto.description")
    Brand brandRequestDTOToBrand(BrandRequestDTO dto);

    @Mapping(target = "id",source ="brand.id" )
    @Mapping(target = "name", source ="brand.name" )
    @Mapping(target = "description", source = "brand.description")
    BrandResponseDTO brandToBrandResponseDTO(Brand brand);

}
