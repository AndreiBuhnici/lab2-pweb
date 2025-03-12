package com.example.lab2.dto;

import com.example.lab2.model.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAddDTO {
    private String name;
    private String email;
    private String password;
    private UserRoleEnum role;
}
