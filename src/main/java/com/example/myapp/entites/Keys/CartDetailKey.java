package com.example.myapp.entites.Keys;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDetailKey implements Serializable {
    private Long product;
    private Long cart;

    @Override
    public int hashCode() {
        return Objects.hash(getCart(), getProduct());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof CartDetailKey)) return false;
        CartDetailKey that = (CartDetailKey) obj;
        return getCart().equals(that.cart) && getProduct().equals(that.product);
    }
}
