package com.example.lab2.service;

import com.example.lab2.dto.UserAddDTO;
import com.example.lab2.exception.UserAlreadyExistsException;
import com.example.lab2.model.UserEntity;
import com.example.lab2.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public void addUser(UserAddDTO userAddDTO) throws UserAlreadyExistsException {
        UserEntity existingUser = userRepository.findByEmail(userAddDTO.getEmail());
        if (existingUser != null) {
            throw new UserAlreadyExistsException("User already exists!");
        }

        UserEntity user = new UserEntity();
        user.setEmail(userAddDTO.getEmail());
        user.setName(userAddDTO.getName());
        user.setPassword(userAddDTO.getPassword());
        user.setRole(userAddDTO.getRole());

        userRepository.save(user);
    }
}