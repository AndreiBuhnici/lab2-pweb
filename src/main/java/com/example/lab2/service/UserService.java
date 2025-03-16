package com.example.lab2.service;

import com.example.lab2.dto.UserAddDTO;
import com.example.lab2.dto.UserDTO;
import com.example.lab2.dto.UserRegisterDTO;
import com.example.lab2.exception.UserAlreadyExistsException;
import com.example.lab2.model.UserEntity;
import com.example.lab2.model.UserRoleEnum;
import com.example.lab2.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public Page<UserDTO> getAllUsers(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<UserEntity> users = userRepository.findAll(pageable);
        return users.map(user -> new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getRole(),
                user.getCreatedAt(), user.getUpdatedAt()));
    }

    public void register(UserRegisterDTO userRegisterDTO) throws UserAlreadyExistsException {
        UserAddDTO userAddDTO = new UserAddDTO( userRegisterDTO.getName(), userRegisterDTO.getEmail(),
                userRegisterDTO.getPassword(), UserRoleEnum.CLIENT);
        addUser(userAddDTO);
    }

    public void addUser(UserAddDTO userAddDTO) throws UserAlreadyExistsException {
        Optional<UserEntity> existingUser = userRepository.findByEmail(userAddDTO.getEmail());
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException("User already exists!");
        }

        UserEntity user = new UserEntity();
        user.setEmail(userAddDTO.getEmail());
        user.setName(userAddDTO.getName());
        user.setPassword(encoder.encode(userAddDTO.getPassword()));
        user.setRole(userAddDTO.getRole());
        user.setUserFiles(new HashSet<>());

        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByEmail(username);
        if (user.isEmpty())
            throw new UsernameNotFoundException("User doesn't exist");

        return user.get();
    }
}