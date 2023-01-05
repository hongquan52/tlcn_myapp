package com.example.myapp.services.implement;

import com.example.myapp.dto.request.CategoryRequestDTO;
import com.example.myapp.dto.response.CategoryResponseDTO;
import com.example.myapp.dto.response.ResponseObject;
import com.example.myapp.entites.Category;
import com.example.myapp.exceptions.ResourceNotFoundException;
import com.example.myapp.mapper.CategoryMapper;
import com.example.myapp.repositories.CategoryRepository;
import com.example.myapp.services.CategoryService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper mapper = Mappers.getMapper(CategoryMapper.class);

    @Autowired private CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<?> getAllCategoryOnTrading(Pageable pageable) {
        return null;
    }

    @Override
    public ResponseEntity<?> getAllCategory() {
        List<Category> getCategoryList = categoryRepository.findAll();
        List<CategoryResponseDTO> categoryResponseDTOList = new ArrayList<>();

        for (Category c : getCategoryList) {
            CategoryResponseDTO categoryResponseDTO = mapper.categoryToCategoryResponseDTO(c);
            categoryResponseDTOList.add(categoryResponseDTO);
        }

        return ResponseEntity.status(HttpStatus.OK).body(categoryResponseDTOList);
    }

    @Override
    public ResponseEntity<ResponseObject> createCategory(CategoryRequestDTO categoryRequestDTO) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseObject> updateCategory(CategoryRequestDTO categoryRequestDTO, Long id) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseObject> deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find category with ID = " + id));
        categoryRepository.delete(category);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject(HttpStatus.OK, "Delete category successfully!"));
    }

    @Override
    public CategoryResponseDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find category with ID = " + id));
        CategoryResponseDTO categoryResponseDTO = mapper.categoryToCategoryResponseDTO(category);

        return categoryResponseDTO;
    }
}
