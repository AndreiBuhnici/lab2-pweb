package com.example.lab2.controller;

import com.example.lab2.dto.UserAddDTO;
import com.example.lab2.dto.UserDTO;
import com.example.lab2.exception.UserAlreadyExistsException;
import com.example.lab2.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(
            summary = "Obține toti utilizatorii",
            description = "Returnează detaliile tuturor utilizatoriilor.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Toti utilizatorii",
                            content = @Content(schema = @Schema(implementation = List.class))),
            }
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAllUsers")
    public ResponseEntity<Page<UserDTO>> getAllUsers(@RequestParam(defaultValue = "0") int pageNo,
                                                     @RequestParam(defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(userService.getAllUsers(pageNo, pageSize));
    }


    @Operation(
            summary = "Adauga un nou utilizator",
            description = "Adauga un nou utilizator in baza de date daca nu exista deja",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Utilizator adaugat"),
                    @ApiResponse(responseCode = "400", description = "Utilizatorul exista deja")
            }
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody UserAddDTO userAddDTO) {
        try {
            userService.addUser(userAddDTO);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("User added.");
    }
}
