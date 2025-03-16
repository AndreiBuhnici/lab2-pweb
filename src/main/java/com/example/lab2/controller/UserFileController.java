package com.example.lab2.controller;

import com.example.lab2.dto.FileAddDTO;
import com.example.lab2.dto.UserFileDTO;
import com.example.lab2.exception.FileAlreadyExistsException;
import com.example.lab2.exception.UserNotFoundException;
import com.example.lab2.service.UserFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/files")
public class UserFileController {
    @Autowired
    private UserFileService userFileService;

    @PostMapping("/addFile")
    @PreAuthorize("hasAuthority('CLIENT')")
    public ResponseEntity<String> addFile(@RequestBody FileAddDTO fileAddDTO) {
        try {
            userFileService.addFile(fileAddDTO);
        } catch (FileAlreadyExistsException | UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("File added");
    }

    @GetMapping("/getFiles")
    @PreAuthorize("hasAuthority('CLIENT')")
    public ResponseEntity<Page<UserFileDTO>> getFiles(@RequestParam(defaultValue = "0") int pageNo,
                                                      @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(userFileService.getFiles(pageNo, pageSize));
    }
}
