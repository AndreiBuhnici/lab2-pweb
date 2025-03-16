package com.example.lab2.repository;

import com.example.lab2.model.UserEntity;
import com.example.lab2.model.UserFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserFileRepository extends JpaRepository<UserFile, UUID> {
    Optional<UserFile> findByPathAndName(String path, String name);
    Page<UserFile> findByUserEntityId(UUID id, Pageable pageable);
}