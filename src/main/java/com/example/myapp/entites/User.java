package com.example.myapp.entites;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true)
    @Pattern(regexp = ("^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9\\+_-]+)*@"
            + "[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$"), message = "Invalid email")
    @Size(max = 30, min = 10, message = "Invalid mail size")
    private String email;

    @NotNull(message = "Enable is required")
    private boolean enable;

    // custom trường data trong entity
    @Column(name = "name", length = 45)
    @NotNull(message = "Name is required")
    private String name;

    @Column(unique = true)
    @Size(max = 12, min = 9, message = "Invalid phone size")
    @NotNull(message = "Phone is required")
    private String phone;

    @NotNull(message = "Password is required")
    private String password;

    @Column(name = "images", length = 255)
    private String images;

    @Column(length = 64)
    private String verificationCode;

    @ManyToOne(optional = true)
    @JoinColumn(name = "role_id")
    private Role role;

    @CreationTimestamp
    private Date createDate;

    @UpdateTimestamp
    private Date updateDate;
}
