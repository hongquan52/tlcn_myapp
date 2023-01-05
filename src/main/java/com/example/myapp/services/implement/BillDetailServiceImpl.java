package com.example.myapp.services.implement;

import com.example.myapp.dto.response.BillDetailResponseDTO;
import com.example.myapp.dto.response.ResponseObject;
import com.example.myapp.entites.Bill;
import com.example.myapp.entites.BillDetail;
import com.example.myapp.entites.Product;
import com.example.myapp.exceptions.InvalidValueException;
import com.example.myapp.exceptions.ResourceNotFoundException;
import com.example.myapp.mapper.BillDetailMapper;
import com.example.myapp.mapper.UserMapper;
import com.example.myapp.repositories.BillDetailRepository;
import com.example.myapp.repositories.BillRepository;
import com.example.myapp.repositories.ProductRepository;
import com.example.myapp.services.BillDetailService;
import com.example.myapp.utils.Utils;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BillDetailServiceImpl implements BillDetailService {
    @Autowired private BillRepository billRepository;
    @Autowired private BillDetailRepository billDetailRepository;
    @Autowired private ProductRepository productRepository;

    private final BillDetailMapper billDetailMapper = Mappers.getMapper(BillDetailMapper.class);
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Override
    public ResponseEntity<ResponseObject> addProductToBill(Long billId, Long productId, int amount) {
        BillDetail billDetail = new BillDetail();
        Bill bill = billRepository.findById(billId).orElseThrow(() -> new ResourceNotFoundException("Could not find bill with ID = " + billId));
        BigDecimal totalPrice = bill.getTotalPrice();
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Could not find product with ID = " + productId));

        Optional<BillDetail> getBillDetail = billDetailRepository.findBillDetailByBillAndProduct(bill, product);
        if (amount > product.getQuantity()){
            throw new InvalidValueException("Amount product " + product.getName() + " must be less than amount product exists");
        } else if(amount < 0) {
            throw new InvalidValueException("Amount product must greater than 1");
        } else {
            //Update amount product
            int newAmountProduct = product.getQuantity() - amount;
            product.setQuantity(newAmountProduct);
            productRepository.save(product);
            if (getBillDetail.isPresent()){
                int newAmount = getBillDetail.get().getQuantity() + amount;
                billDetail = getBillDetail.get();
                billDetail.setQuantity(newAmount);
            } else {
                billDetail.setBill(bill);
                billDetail.setProduct(product);
                billDetail.setQuantity(amount);

            }

            //Total price of bill
            totalPrice = totalPrice.add(totalPrice(product, amount));
            bill.setTotalPrice(totalPrice);
            billRepository.save(bill);
        }
        BillDetailResponseDTO billDetailResponseDTO = billDetailMapper.billDetailToBillDetailResponseDTO(billDetailRepository.save(billDetail));
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK, "Add product to bill success!", billDetailResponseDTO));
    }
    private BigDecimal totalPrice(Product product, int amount){
        BigDecimal totalPrice;

        totalPrice = product.getPrice().multiply(BigDecimal.valueOf(amount)).setScale(2, RoundingMode.UP);

        return totalPrice;
    }

    @Override
    public ResponseEntity<ResponseObject> deleteProductToBill(Long billId, Long productId, int amount) {
        return null;
    }

    @Override
    public ResponseEntity<?> getProductByBill(Long billId) {
        Bill bill = billRepository.findById(billId).orElseThrow(() -> new ResourceNotFoundException("Could not find bill with ID = " + billId));
        List<BillDetail> billDetailList = billDetailRepository.findBillDetailByBill(bill);
        List<BillDetailResponseDTO> billDetailResponseDTOList = new ArrayList<>();

        for (BillDetail b : billDetailList) {
            BillDetailResponseDTO billDetailResponseDTO = billDetailMapper.billDetailToBillDetailResponseDTO(b);
            billDetailResponseDTOList.add(billDetailResponseDTO);
        }

        return ResponseEntity.status(HttpStatus.OK).body(billDetailResponseDTOList);
    }

    @Override
    public ResponseEntity<?> getAllProductByBillPayed() {

        // sap xep giam dan theo truong "createDate"
        Sort sort = Sort.by("createDate").descending();
        List<Bill> billList = billRepository.findAll(sort);
        List<BillDetailResponseDTO> billDetailResponseDTOList = new ArrayList<>();

        for (Bill bill : billList) {
            List<BillDetail> billDetailList = billDetailRepository.findBillDetailByBill(bill);
            for (BillDetail billDetail : billDetailList) {
                BillDetailResponseDTO billDetailResponseDTO = billDetailMapper.billDetailToBillDetailResponseDTO(billDetail);

                billDetailResponseDTO.setPrice(Utils.getTotalPrice(billDetail.getProduct(), BigDecimal.valueOf(0.00), billDetail.getQuantity()));
                billDetailResponseDTO.setUserResponseDTO(userMapper.userToUserResponseDTO(bill.getUser()));
                billDetailResponseDTOList.add(billDetailResponseDTO);
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(billDetailResponseDTOList);
    }
}
