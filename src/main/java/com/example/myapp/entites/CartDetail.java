package com.example.myapp.entites;

import com.example.myapp.entites.Keys.CartDetailKey;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_cart_detail")
@IdClass(CartDetailKey.class)
public class CartDetail {
    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    @NotNull(message = "Cart detail amount is required !!!")
    private int amount;
}
