package com.example.lab2.dto;

import com.example.lab2.model.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private UUID id;
    private String name;
    private String email;
    private String password;
    private UserRoleEnum role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
