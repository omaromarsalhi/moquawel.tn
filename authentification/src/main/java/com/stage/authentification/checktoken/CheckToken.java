package com.stage.authentification.checktoken;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class CheckToken {


    @PostMapping("/checkToken/{username}")
    public String test(
            @PathVariable String username
    ) {
        return "Hi "+username+" !";
    }

}
