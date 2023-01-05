package com.example.myapp.services.implement;

import com.example.myapp.dto.request.ProductRequestDTO;
import com.example.myapp.dto.response.ProductGalleryDTO;
import com.example.myapp.dto.response.ProductResponseDTO;
import com.example.myapp.dto.response.ResponseObject;
import com.example.myapp.entites.Brand;
import com.example.myapp.entites.Product;
import com.example.myapp.exceptions.ResourceAlreadyExistsException;
import com.example.myapp.exceptions.ResourceNotFoundException;
import com.example.myapp.mapper.ProductMapper;
import com.example.myapp.repositories.BrandRepository;
import com.example.myapp.repositories.ProductRepository;
import com.example.myapp.services.ProductService;
import javax.transaction.Transactional;
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
    @Autowired private BrandRepository brandRepository;


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
        Product product = mapper.productRequestDTOtoProduct(productRequestDTO);
        product = checkExists(product);
        // set image:
        // code after

        Product productSaved = productRepository.save(product);
        ProductResponseDTO productResponseDTO = mapper.productToProductResponseDTO(productSaved);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK, "Create product successfully!", productResponseDTO));
    }

    @Override
    public ResponseEntity<ResponseObject> updateProduct(ProductRequestDTO productRequestDTO, Long id) {
        Product product = mapper.productRequestDTOtoProduct(productRequestDTO);

        // được xài khi set thumbnail
        Product getProduct = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Could not find product with ID = " + id));
        product.setId(id);

        product = checkExists(product);
        Product productSaved = productRepository.save(product);
        ProductResponseDTO productResponseDTO = mapper.productToProductResponseDTO(productSaved);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK, "Update product successfully!", productResponseDTO));
    }

    @Override
    public ResponseEntity<ResponseObject> deleteProduct(Long id) {
        Product getProduct = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Could not find product with ID = " + id));

        // delete image of product
        // code after

        // delete attribute of product
        // code after

        productRepository.delete(getProduct);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK, "Delete product successfully!"));
    }

    @Override
    public ResponseEntity<?> getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Could not find product with ID = " + id));
        ProductResponseDTO productResponseDTO = mapper.productToProductResponseDTO(product);

        return ResponseEntity.status(HttpStatus.OK).body(productResponseDTO);
    }

    @Override
    public ResponseEntity<?> search(String search, int page, int size) {


        return null;
    }

    @Override
    public ResponseEntity<Integer> getNumberOfProduct() {
        int numberOfProduct = productRepository.getNumberOfProduct().orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        return ResponseEntity.status(HttpStatus.OK).body(numberOfProduct);
    }

    @Override
    public ResponseEntity<?> getProductByBrand(Long brandId) {
        Brand brand = brandRepository.findById(brandId).orElseThrow(() -> new ResourceNotFoundException("Could not find product with brand ID = " + brandId));
        List<Product> productList = productRepository.findProductByBrand(brand);

        List<ProductResponseDTO> productResponseDTOList = new ArrayList<>();

        for (Product p : productList) {
            ProductResponseDTO productResponseDTO = mapper.productToProductResponseDTO(p);
            productResponseDTOList.add(productResponseDTO);
        }

        return ResponseEntity.status(HttpStatus.OK).body(productResponseDTOList);
    }

    @Override
    public ResponseEntity<?> getProductByCategory(Long categoryId) {
        return null;
    }

    private ProductGalleryDTO toProductGalleryDTO(Product product){
        ProductGalleryDTO productGalleryDTO = new ProductGalleryDTO();
        productGalleryDTO.setId(product.getId());
        productGalleryDTO.setName(product.getName());
        productGalleryDTO.setPrice(product.getPrice());
        productGalleryDTO.setBrand(product.getBrand().getName());
        productGalleryDTO.setCategory(product.getCategory().getName());
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
