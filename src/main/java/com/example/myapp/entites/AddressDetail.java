package com.example.myapp.entites;

import com.example.myapp.entites.Keys.AddressDetailKey;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_address_detail")
@IdClass(AddressDetailKey.class)
public class AddressDetail {
    @Id
    @ManyToOne(fetch = FetchType.LAZY) // => không load lập tức
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private Address address;

    private boolean defaultAddress;
}
