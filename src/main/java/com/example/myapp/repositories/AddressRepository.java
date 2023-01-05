package com.example.myapp.repositories;

import com.example.myapp.entites.Address;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findAddressByProvinceAndDistrictAndWardAndApartmentNumber(
            String province,
            String district,
            String ward,
            String apartmentNumber
    );
}
