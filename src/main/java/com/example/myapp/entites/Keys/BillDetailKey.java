package com.example.myapp.entites.Keys;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BillDetailKey implements Serializable {
    private Long product;
    private Long bill;

    @Override
    public int hashCode() {
        return Objects.hash(getBill(), getProduct());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof BillDetailKey)) return false;
        BillDetailKey that = (BillDetailKey) obj;
        return getBill().equals(that.bill) && getProduct().equals(that.product);
    }
}
