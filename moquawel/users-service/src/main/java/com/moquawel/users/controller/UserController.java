package com.moquawel.users.controller;


import com.moquawel.users.dto.UserDto;
import com.moquawel.users.exception.UserNameAlreadyExists;
import com.moquawel.users.request.RegisterRequest;
import com.moquawel.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/{username}")
    public ResponseEntity<UserDto> getUser(
            @PathVariable("username") String username
    ) {
        return ResponseEntity.ok(userService.getUser(username));
    }


    @PostMapping("/save")
    public ResponseEntity<?> save(
            @RequestBody RegisterRequest request
    ) {
        try {
            return ResponseEntity.ok(userService.save(request));
        } catch (UserNameAlreadyExists e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}

