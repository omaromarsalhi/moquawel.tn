package com.moquawel.authentication.user;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

//    @PostMapping("/students/{idGrade}")
//    public ResponseEntity<List<UserDto>> getUsers(
//            @PathVariable("idGrade") int idGrade
//    ) {
//        return ResponseEntity.ok(userService.getStudents(idGrade));
//    }

}
