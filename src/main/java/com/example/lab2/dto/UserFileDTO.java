package com.example.lab2.dto;

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
public class UserFileDTO {
    private UUID id;
    private String path;
    private String name;
    private String description;
    private UUID user_id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
