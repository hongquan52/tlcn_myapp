package com.example.myapp.entites;

import com.example.myapp.entites.Keys.BillDetailKey;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_bill_detail")
@IdClass(BillDetailKey.class)
public class BillDetail {
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "bill_id")
    private Bill bill;

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;
}
