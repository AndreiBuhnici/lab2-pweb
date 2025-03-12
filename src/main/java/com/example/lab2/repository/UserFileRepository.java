package com.example.lab2.repository;

import com.example.lab2.model.UserFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFileRepository extends JpaRepository<UserFile, String> {}