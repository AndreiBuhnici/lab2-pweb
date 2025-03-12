package com.example.lab2.controller;

import com.example.lab2.dto.UserAddDTO;
import com.example.lab2.exception.UserAlreadyExistsException;
import com.example.lab2.model.UserEntity;
import com.example.lab2.model.UserRoleEnum;
import com.example.lab2.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }


    @Operation(
            summary = "Adauga un nou utilizator",
            description = "Adauga un nou utilizator in baza de date daca nu exista deja",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Utilizator adaugat"),
                    @ApiResponse(responseCode = "400", description = "Utilizatorul exista deja")
            }
    )
    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody UserAddDTO userAddDTO) {
        try {
            userService.addUser(userAddDTO);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().body("User already exists");
        }
        return ResponseEntity.ok("User added.");
    }
}
