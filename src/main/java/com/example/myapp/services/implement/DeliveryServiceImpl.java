package com.example.myapp.services.implement;

import com.example.myapp.dto.request.DeliveryRequestDTO;
import com.example.myapp.dto.response.DeliveryResponseDTO;
import com.example.myapp.dto.response.ResponseObject;
import com.example.myapp.entites.Address;
import com.example.myapp.entites.Bill;
import com.example.myapp.entites.Delivery;
import com.example.myapp.entites.User;
import com.example.myapp.exceptions.ResourceNotFoundException;
import com.example.myapp.mapper.DeliveryMapper;
import com.example.myapp.models.ItemTotalPage;
import com.example.myapp.repositories.*;
import com.example.myapp.services.DeliveryService;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DeliveryServiceImpl implements DeliveryService {
    @Autowired private DeliveryRepository deliveryRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private BillRepository billRepository;
    @Autowired private AddressRepository addressRepository;
    @Autowired private AddressDetailRepository addressDetailRepository;

    private final DeliveryMapper mapper = Mappers.getMapper(DeliveryMapper.class);

    @Override
    public ResponseEntity<?> getAllDeliveryOnTrading(Pageable pageable) {
        Page<Delivery> getDeliveryList = deliveryRepository.findAll(pageable);
        List<Delivery> deliveryList = getDeliveryList.getContent();
        List<DeliveryResponseDTO> deliveryResponseDTOList = new ArrayList<>();

        for (Delivery d : deliveryList) {
            DeliveryResponseDTO deliveryResponseDTO = mapper.deliveryToDeliveryResponseDTO(d);
            deliveryResponseDTOList.add(deliveryResponseDTO);
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ItemTotalPage(deliveryResponseDTOList, getDeliveryList.getTotalPages()));
    }

    @Override
    public ResponseEntity<ResponseObject> createDelivery(DeliveryRequestDTO deliveryRequestDTO) {
        Delivery delivery = mapper.deliveryRequestDTOToDelivery(deliveryRequestDTO);

        User user = userRepository.findById(deliveryRequestDTO.getShipperId()).orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy User với ID = " + deliveryRequestDTO.getShipperId()));
        Bill bill = billRepository.findById(deliveryRequestDTO.getBillId()).orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy Bill với ID = " + deliveryRequestDTO.getBillId()));
        /*Address address = addressRepository.findById(deliveryRequestDTO.getAddressId()).orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy Address với ID = " + deliveryRequestDTO.getAddressId()));
*/
        delivery.setShipper(user);
        /*delivery.setAddress(address);*/
        delivery.setBill(bill);

        Delivery deliverySaved = deliveryRepository.save(delivery);
        DeliveryResponseDTO deliveryResponseDTO = mapper.deliveryToDeliveryResponseDTO(deliverySaved);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK, "Create delivery successfully!", deliveryResponseDTO));
    }

    @Override
    public ResponseEntity<ResponseObject> updateDelivery(DeliveryRequestDTO deliveryRequestDTO, Long id) {
        Delivery delivery = mapper.deliveryRequestDTOToDelivery(deliveryRequestDTO);
        Delivery getDelivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find delivery with ID = " + id));
        delivery.setId(id);

        Bill bill = billRepository.findById(deliveryRequestDTO.getBillId())
                .orElseThrow(() -> new ResourceNotFoundException("Could not find bill with ID = " + deliveryRequestDTO.getBillId()));
        delivery.setBill(bill);

        delivery.setAddress(getDelivery.getAddress());
        delivery.setShipper(getDelivery.getShipper());

        // change shipper
        if (deliveryRequestDTO.getShipperId() != null) {
            Optional<User> newShipper = userRepository.findById(deliveryRequestDTO.getShipperId());
            if (newShipper.isPresent()) {
                delivery.setShipper(newShipper.get());
            }
        }

        //Update bill payed
        if (deliveryRequestDTO.getBillStatus() != null){
            delivery.getBill().setStatus(deliveryRequestDTO.getBillStatus());
            delivery.getBill().setPayDate(deliveryRequestDTO.getPayDate());
            billRepository.save(delivery.getBill());
        }

        Delivery deliverySaved = deliveryRepository.save(delivery);
        DeliveryResponseDTO deliveryResponseDTO = mapper.deliveryToDeliveryResponseDTO(deliverySaved);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK, "Update delivery successfully!", deliveryResponseDTO));
    }

    @Override
    public ResponseEntity<ResponseObject> deleteDelivery(Long id) {
        Delivery delivery = deliveryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Could not find delivery with ID = " + id));
        deliveryRepository.delete(delivery);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK, "Delete delivery successfully!"));
    }

    @Override
    public DeliveryResponseDTO getDeliveryById(Long id) {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find delivery with ID = " + id));
        DeliveryResponseDTO deliveryResponseDTO = mapper.deliveryToDeliveryResponseDTO(delivery);

        return deliveryResponseDTO;
    }

    @Override
    public ResponseEntity<?> getDeliveryByStatus(String status) {
        List<Delivery> deliveryList = deliveryRepository.findDeliveriesByStatus(status);
        List<DeliveryResponseDTO> deliveryResponseDTOList = new ArrayList<>();

        for (Delivery d : deliveryList) {
            DeliveryResponseDTO deliveryResponseDTO = mapper.deliveryToDeliveryResponseDTO(d);
            deliveryResponseDTOList.add(deliveryResponseDTO);
        }

        return ResponseEntity.status(HttpStatus.OK).body(deliveryResponseDTOList);
    }

    @Override
    public ResponseEntity<?> getDeliveryByShipper(Long shipperId) {
        User shipper = userRepository.findById(shipperId).orElseThrow(() -> new ResourceNotFoundException("Could not find shipper with ID = " + shipperId));
        List<Delivery> deliveryList = deliveryRepository.findDeliveriesByShipper(shipper);
        List<DeliveryResponseDTO> deliveryResponseDTOList = new ArrayList<>();

        for (Delivery d : deliveryList) {
            DeliveryResponseDTO deliveryResponseDTO = mapper.deliveryToDeliveryResponseDTO(d);
            deliveryResponseDTOList.add(deliveryResponseDTO);
        }

        return ResponseEntity.status(HttpStatus.OK).body(deliveryResponseDTOList);
    }

    @Override
    public ResponseEntity<?> getDeliveryByStatusAndShipper(String status, Long shipperId) {
        User shipper = userRepository.findById(shipperId).orElseThrow(() -> new ResourceNotFoundException("Could not find shipper with ID = " + shipperId));
        List<Delivery> deliveryList = deliveryRepository.findDeliveriesByStatusAndShipper(status, shipper);
        List<DeliveryResponseDTO> deliveryResponseDTOList = new ArrayList<>();

        for (Delivery d : deliveryList) {
            DeliveryResponseDTO deliveryResponseDTO = mapper.deliveryToDeliveryResponseDTO(d);
            deliveryResponseDTOList.add(deliveryResponseDTO);
        }

        return ResponseEntity.status(HttpStatus.OK).body(deliveryResponseDTOList);
    }


    @Override
    public DeliveryResponseDTO getDeliveryByBill(Long billId) {
        return null;
    }
}
