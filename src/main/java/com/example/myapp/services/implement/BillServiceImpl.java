package com.example.myapp.services.implement;

import com.example.myapp.dto.request.BillRequestDTO;
import com.example.myapp.dto.response.BillResponseDTO;
import com.example.myapp.dto.response.ResponseObject;
import com.example.myapp.entites.Bill;
import com.example.myapp.entites.User;
import com.example.myapp.exceptions.ResourceNotFoundException;
import com.example.myapp.mapper.BillMapper;
import com.example.myapp.repositories.BillRepository;
import com.example.myapp.repositories.UserRepository;
import com.example.myapp.services.BillService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class BillServiceImpl implements BillService {

    @Autowired private BillRepository billRepository;
    @Autowired private UserRepository userRepository;
    private final BillMapper billMapper = Mappers.getMapper(BillMapper.class);

    @Override
    public ResponseEntity<?> getAllBill() {
        List<BillResponseDTO> billResponseDTOList = new ArrayList<>();
        List<Bill> billList = billRepository.findAll();

        for (Bill bill : billList) {
            BillResponseDTO billResponseDTO = billMapper.billToBillResponseDTO(bill);
            billResponseDTOList.add(billResponseDTO);
        }

        return ResponseEntity.status(HttpStatus.OK).body(billResponseDTOList);
    }

    @Override
    public ResponseEntity<?> getBillByUser(Long userId) {
        List<BillResponseDTO> billResponseDTOList = new ArrayList<>();
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Could not find user with ID = " + userId));
        List<Bill> billList = billRepository.findBillByUser(user);

        for (Bill bill : billList) {
            BillResponseDTO billResponseDTO = billMapper.billToBillResponseDTO(bill);
            billResponseDTOList.add(billResponseDTO);
        }

        return ResponseEntity.status(HttpStatus.OK).body(billResponseDTOList);
    }

    @Override
    public ResponseEntity<?> getBillById(Long id) {
        Bill getBill = billRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy Bill với id = " + id));
        BillResponseDTO billResponseDTO = billMapper.billToBillResponseDTO(getBill);

        return ResponseEntity.status(HttpStatus.OK).body(billResponseDTO);

    }

    @Override
    public ResponseEntity<ResponseObject> createBill(Long userId, BillRequestDTO billRequestDTO) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Could not find user with ID = " + userId));
        Bill bill = billMapper.billRequestDTOToBill(billRequestDTO);
        bill.setUser(user);
        // set totalPrice khi mới tạo = 0 VNĐ
        bill.setTotalPrice(BigDecimal.valueOf(0));

        BillResponseDTO billResponseDTO = billMapper.billToBillResponseDTO(billRepository.save(bill));

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK, "Tạo bill mới thành công !!!", billResponseDTO));
    }

    @Override
    public ResponseEntity<ResponseObject> updateBill(Long billId, BillRequestDTO billRequestDTO) {
        Bill getBill = billRepository.findById(billId).orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy bill với ID = " + billId));
        Bill bill = billMapper.billRequestDTOToBill(billRequestDTO);

        bill.setId(getBill.getId());
        bill.setTotalPrice(getBill.getTotalPrice());
        bill.setUser(getBill.getUser());

        BillResponseDTO billResponseDTO = billMapper.billToBillResponseDTO(billRepository.save(bill));
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK,"Cập nhật thông tin BILL thành công !!!", billResponseDTO));
    }

    @Override
    public ResponseEntity<ResponseObject> deleteBill(Long billId) {
        Bill getBill = billRepository.findById(billId).orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy Bill với id = " + billId));

        // delete bill detail
        // code after

        billRepository.delete(getBill);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK, "Delete bill success!"));
    }
}
