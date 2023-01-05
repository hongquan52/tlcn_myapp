package com.example.myapp.mapper;

import com.example.myapp.dto.request.CategoryRequestDTO;
import com.example.myapp.dto.response.CategoryResponseDTO;
import com.example.myapp.entites.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "name", source = "c.name")
    Category categoryRequestDTOToCategory(CategoryRequestDTO c);

    @Mapping(target = "id", source = "c.id")
    @Mapping(target = "name", source = "c.name")
    CategoryResponseDTO categoryToCategoryResponseDTO(Category c);
}
