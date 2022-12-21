package com.example.myapp.entites;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_product")
public class Product {
    @Id
    @GeneratedValue(strategy=  GenerationType.IDENTITY)
    private long id;

    @Column(length = 45, unique = true)
    @NotNull (message = "Product name is required")
    private String name;

    @NotNull(message = "Price is required")
    private BigDecimal price;

    private int promotion;

    private int quantity;

    private String description;

    private String image;

    // tự tạo ngày giờ hiện tại khi create / update xuống database
    @CreationTimestamp
    private Date createDate;

    @UpdateTimestamp
    private Date updateDate;

    // thiết lập khóa ngoại---------------------------------------------
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Category brand;
}
