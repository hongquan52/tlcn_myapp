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
        Bill getBill = billRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Kh??ng t??m th???y Bill v???i id = " + id));
        BillResponseDTO billResponseDTO = billMapper.billToBillResponseDTO(getBill);

        return ResponseEntity.status(HttpStatus.OK).body(billResponseDTO);

    }

    @Override
    public ResponseEntity<ResponseObject> createBill(Long userId, BillRequestDTO billRequestDTO) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Could not find user with ID = " + userId));
        Bill bill = billMapper.billRequestDTOToBill(billRequestDTO);
        bill.setUser(user);

        /*// set totalPrice khi m???i t???o = 0 VN??
        bill.setTotalPrice(BigDecimal.valueOf(0));*/

        BillResponseDTO billResponseDTO = billMapper.billToBillResponseDTO(billRepository.save(bill));

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK, "T???o bill m???i th??nh c??ng !!!", billResponseDTO));
    }

    @Override
    public ResponseEntity<ResponseObject> updateBill(Long billId, BillRequestDTO billRequestDTO) {
        Bill getBill = billRepository.findById(billId).orElseThrow(() -> new ResourceNotFoundException("Kh??ng t??m th???y bill v???i ID = " + billId));
        Bill bill = billMapper.billRequestDTOToBill(billRequestDTO);

        bill.setId(getBill.getId());
        bill.setTotalPrice(getBill.getTotalPrice());
        bill.setUser(getBill.getUser());

        BillResponseDTO billResponseDTO = billMapper.billToBillResponseDTO(billRepository.save(bill));
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK,"C???p nh???t th??ng tin BILL th??nh c??ng !!!", billResponseDTO));
    }

    @Override
    public ResponseEntity<ResponseObject> deleteBill(Long billId) {
        Bill getBill = billRepository.findById(billId).orElseThrow(() -> new ResourceNotFoundException("Kh??ng t??m th???y Bill v???i id = " + billId));

        // delete bill detail
        // code after

        billRepository.delete(getBill);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK, "Delete bill success!"));
    }

    @Override
    public ResponseEntity<Integer> getNumberOfBill() {
        int numberOfBill = billRepository.getNumberOfBill().orElseThrow(() -> new ResourceNotFoundException("Bill not found"));
        return ResponseEntity.status(HttpStatus.OK).body(numberOfBill);
    }

    @Override
    public ResponseEntity<Double> getSales() {
        double totalSale = billRepository.getSales().orElseThrow(() -> new ResourceNotFoundException("Bill not found"));
        return ResponseEntity.status(HttpStatus.OK).body(totalSale);
    }

    @Override
    public ResponseEntity<Integer> getNumberOfBillWaitingConfirm() {
        int numberOfBill = billRepository.getNumberOfBillWaitingConfirm().orElseThrow(() -> new ResourceNotFoundException("Bill not found"));
        return ResponseEntity.status(HttpStatus.OK).body(numberOfBill);
    }

    @Override
    public ResponseEntity<Integer> getNumberOfBillConfimed() {
        int numberOfBill = billRepository.getNumberOfBillConfirmed().orElseThrow(() -> new ResourceNotFoundException("Bill not found"));
        return ResponseEntity.status(HttpStatus.OK).body(numberOfBill);
    }

    @Override
    public ResponseEntity<Integer> getNumberOfBillReadyToDelivery() {
        int numberOfBill = billRepository.getNumberOfBillReadyToDelivery().orElseThrow(() -> new ResourceNotFoundException("Bill not found"));
        return ResponseEntity.status(HttpStatus.OK).body(numberOfBill);
    }

    @Override
    public ResponseEntity<Integer> getNumberOfBillDelivering() {
        int numberOfBill = billRepository.getNumberOfBillDelivering().orElseThrow(() -> new ResourceNotFoundException("Bill not found"));
        return ResponseEntity.status(HttpStatus.OK).body(numberOfBill);
    }

    @Override
    public ResponseEntity<Integer> getNumberOfBillPaid() {
        int numberOfBill = billRepository.getNumberOfBillPaid().orElseThrow(() -> new ResourceNotFoundException("Bill not found"));
        return ResponseEntity.status(HttpStatus.OK).body(numberOfBill);
    }

    @Override
    public ResponseEntity<Integer> getNumberOfBillCancel() {
        int numberOfBill = billRepository.getNumberOfBillCancel().orElseThrow(() -> new ResourceNotFoundException("Bill not found"));
        return ResponseEntity.status(HttpStatus.OK).body(numberOfBill);
    }
}
