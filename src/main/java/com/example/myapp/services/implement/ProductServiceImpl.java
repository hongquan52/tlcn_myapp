package com.example.myapp.services.implement;

import com.example.myapp.dto.request.ProductRequestDTO;
import com.example.myapp.dto.response.ProductGalleryDTO;
import com.example.myapp.dto.response.ResponseObject;
import com.example.myapp.entites.Product;
import com.example.myapp.exceptions.ResourceAlreadyExistsException;
import com.example.myapp.mapper.ProductMapper;
import com.example.myapp.repositories.ProductRepository;
import com.example.myapp.services.ProductService;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired private ProductRepository productRepository;

    private final ProductMapper mapper = Mappers.getMapper(ProductMapper.class);

    @Override
    public ResponseEntity<?> getAllProductOnTrading(Pageable pageable) {
        Page<Product> getProductList = productRepository.findAll(pageable);
        List<Product> productList = getProductList.getContent();
        List<ProductGalleryDTO> productGalleryDTOList = new ArrayList<>();
        for(Product product : productList) {
            ProductGalleryDTO productGalleryDTO = toProductGalleryDTO(product);
            productGalleryDTOList.add(productGalleryDTO);
        }

        return ResponseEntity.status(HttpStatus.OK).body(productGalleryDTOList);
    }

    @Override
    public ResponseEntity<ResponseObject> createProduct(ProductRequestDTO productRequestDTO) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseObject> updateProduct(ProductRequestDTO productRequestDTO, Long id) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseObject> deleteProduct(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<?> getProductById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<?> search(String search, int page, int size) {
        return null;
    }
    private ProductGalleryDTO toProductGalleryDTO(Product product){
        ProductGalleryDTO productGalleryDTO = new ProductGalleryDTO();
        productGalleryDTO.setId(product.getId());
        productGalleryDTO.setName(product.getName());
        productGalleryDTO.setPrice(product.getPrice());
        productGalleryDTO.setImage(product.getImage());
        productGalleryDTO.setPromotion(product.getPromotion());

        return productGalleryDTO;
    }
    private Product checkExists(Product product){
        //Check product name exists
        Optional<Product> getProduct = productRepository.findProductByName(product.getName());
        if (getProduct.isPresent()){
            /*
            if (product.getId() == null){
                throw new ResourceAlreadyExistsException("Product name is exists");
            } else {
                if (product.getId() != getProduct.get().getId()){
                    throw new ResourceAlreadyExistsException("Product name is exists");
                }
            }
            */
            if(product.getId() != getProduct.get().getId()) {
                throw new ResourceAlreadyExistsException("Product name is exists");
            }
        }

        return product;
    }
}
