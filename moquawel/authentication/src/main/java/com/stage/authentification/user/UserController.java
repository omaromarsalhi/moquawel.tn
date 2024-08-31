package com.stage.authentification.user;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/students/{idGrade}")
    public ResponseEntity<List<UserDto>> getUsers(
            @PathVariable("idGrade") int idGrade
    ) {
        return ResponseEntity.ok(userService.getStudents(idGrade));
    }

}
