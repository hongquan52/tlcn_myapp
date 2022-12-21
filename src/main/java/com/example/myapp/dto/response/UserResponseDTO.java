package com.example.myapp.dto.response;

import com.example.myapp.entites.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private boolean enable;
    private String image;
    private Role role;

}
