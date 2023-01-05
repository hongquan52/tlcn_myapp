package com.example.myapp.services.implement;

import com.example.myapp.dto.response.ProductStatisticResponseDTO;
import com.example.myapp.dto.response.StatisticResponseDTO;
import com.example.myapp.entites.BillDetail;
import com.example.myapp.entites.Product;
import com.example.myapp.exceptions.ResourceNotFoundException;
import com.example.myapp.mapper.ProductMapper;
import com.example.myapp.models.IProductQuantity;
import com.example.myapp.models.IStatisticDay;
import com.example.myapp.repositories.BillDetailRepository;
import com.example.myapp.repositories.BillRepository;
import com.example.myapp.repositories.UserRepository;
import com.example.myapp.services.StatisticService;
import com.example.myapp.utils.Utils;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
@Transactional
public class StatisticServiceImpl implements StatisticService {

    @Autowired private BillRepository billRepository;
    @Autowired private BillDetailRepository billDetailRepository;
    @Autowired private UserRepository userRepository;
    private final ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @Override
    public ResponseEntity<?> getAllUserByDay() {
        List<IStatisticDay> userList = userRepository.findAllUserByDay();
        return statisticByDay(7, userList);
    }

    @Override
    public ResponseEntity<?> getRevenueByDay() {
        List<IStatisticDay> revenueList = billRepository.findRevenueByDay();
        return statisticByDay(5, revenueList);
    }

    @Override
    public ResponseEntity<Integer> getNumberProductOfAllBill() {
        int numberProduct = billDetailRepository.numberProductOfAllBill().orElseThrow(() -> new ResourceNotFoundException("Not found product on bill"));

        return ResponseEntity.status(HttpStatus.OK).body(numberProduct);
    }

    @Override
    public ResponseEntity<?> getNumberProductOfBill() {
        Sort sort = Sort.by("quantity").descending();
        List<IProductQuantity> numberProduct = billDetailRepository.numberProductOfBill(sort);
        List<ProductStatisticResponseDTO> productResponseDTOList = new ArrayList<>();

        for (IProductQuantity iProductQuantity : numberProduct) {
            Product product = iProductQuantity.getProduct();
            ProductStatisticResponseDTO productResponseDTO = productMapper.productToProductStatisticResponseDTO(iProductQuantity);
            BigDecimal totalPrice = (BigDecimal.valueOf(0.00));
            List<BillDetail> billDetailList = billDetailRepository.findBillDetailByProduct(product);

            for (BillDetail b : billDetailList) {
                // total price of product all bill
                totalPrice = Utils.getTotalPrice(product, totalPrice, b.getQuantity());
            }
            productResponseDTO.setTotalPrice(totalPrice);
            productResponseDTOList.add(productResponseDTO);
        }

        return ResponseEntity.status(HttpStatus.OK).body(productResponseDTOList);
    }

    @Override
    public ResponseEntity<?> getNumberProductOfBillInThreeWeek(Long productId) {
        return null;
    }

    // statistic by day
    public ResponseEntity<?> statisticByDay(int amountDay, List<IStatisticDay> userList) {
        String[] daysOfWeek = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        List<StatisticResponseDTO> statisticResponseDTOList = new ArrayList<>();



        List<String> dayList = new ArrayList<>();
        for (int i=0; i < amountDay; i++){
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_WEEK, - i);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == 1){
                dayList.add("Sun");
            } else {
                dayList.add(daysOfWeek[dayOfWeek - 2]);
            }
        }

        for (String day : dayList){
            StatisticResponseDTO statisticResponseDTO = new StatisticResponseDTO();
            statisticResponseDTO.setValue(0);
            statisticResponseDTO.setTimes(day);
            for (IStatisticDay user : userList) {
                if (daysOfWeek[user.getWeekDay()].equals(day)){
                    statisticResponseDTO.setValue(user.getTotalValue());
                }
            }
            statisticResponseDTOList.add(statisticResponseDTO);
        }
        return ResponseEntity.status(HttpStatus.OK).body(statisticResponseDTOList);
    }
}
