package ru.myblog.usersservice.controller;


import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.myblog.commonlib.user.UserEntityDto;
import ru.myblog.commonlib.user.UserResponseDto;
import ru.myblog.usersservice.repository.UserEntity;
import ru.myblog.usersservice.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers(){
        log.info("Called get All Users");
        return ResponseEntity.ok(userService.findByAll());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDto> getByEmail(
            @PathVariable String email){
        log.info("Called get By Email: " + email);
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.findByEmail(email));
    }
    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponseDto> getByUsername(
            @PathVariable("username") String username){
        log.info("Called by username: " + username);
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.findByUsername(username));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserResponseDto> getById(
            @PathVariable("id") Long id
    ){
        log.info("Called by id:" + id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.findById(id));
    }

    @PostMapping
    public ResponseEntity<UserEntityDto> createUser(
            @RequestBody @Valid UserEntityDto userToCreate
            ){
        log.info("Called user create");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.createUser(userToCreate));
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteUsers() {
        userService.deleteCurrentUser();
        return ResponseEntity.ok().build();
    }


}
