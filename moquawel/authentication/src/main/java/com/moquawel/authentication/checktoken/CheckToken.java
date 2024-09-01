package com.moquawel.authentication.checktoken;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class CheckToken {


    @PostMapping("/checkToken/{username}")
    public String test(
            @PathVariable String username
    ) {
        return "Hi "+username+" !";
    }

}
