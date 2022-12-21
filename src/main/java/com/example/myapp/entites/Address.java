package com.example.myapp.entites;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Address apartment number is required")
    private String apartmentNumber;

    @NotNull(message = "Address ward is required")
    private String ward;

    @NotNull(message = "Address district is required")
    private String district;

    @NotNull(message = "Address province is required")
    private String province;
}
