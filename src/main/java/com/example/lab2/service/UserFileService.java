package com.example.lab2.service;

import com.example.lab2.dto.FileAddDTO;
import com.example.lab2.dto.UserFileDTO;
import com.example.lab2.exception.FileAlreadyExistsException;
import com.example.lab2.exception.UserNotFoundException;
import com.example.lab2.model.UserEntity;
import com.example.lab2.model.UserFile;
import com.example.lab2.repository.UserFileRepository;
import com.example.lab2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserFileService {
    @Autowired
    private UserFileRepository userFileRepository;

    @Autowired
    private UserRepository userRepository;

    public void addFile(FileAddDTO fileAddDTO) throws FileAlreadyExistsException, UserNotFoundException {
        Optional<UserFile> file = userFileRepository.findByPathAndName(fileAddDTO.getPath(), fileAddDTO.getName());
        if (file.isPresent()) {
            throw new FileAlreadyExistsException("File with this name already exists at specified path.");
        }
        UserEntity authUser = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<UserEntity> user = userRepository.findById(authUser.getId());
        if (user.isEmpty())
            throw new UserNotFoundException("User not found.");

        UserFile userFile = new UserFile(fileAddDTO.getPath(), fileAddDTO.getName(), fileAddDTO.getDescription(), user.get());

        userFileRepository.save(userFile);
    }

    public Page<UserFileDTO> getFiles(int pageNo, int pageSize) {
        UserEntity authUser = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<UserFile> userFiles = userFileRepository.findByUserEntityId(authUser.getId(), pageable);
        return userFiles.map(userFile -> new UserFileDTO(userFile.getId(), userFile.getPath(), userFile.getName(),
                userFile.getDescription(), userFile.getUserEntity().getId(),
                userFile.getCreatedAt(), userFile.getCreatedAt()));
    }
}
