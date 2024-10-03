package com.moquawel.marketplace.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/marketplace/users")
@RequiredArgsConstructor
public class UserMarketPlaceController {

    @PostMapping("/hello")
    public String hello(){
        System.out.println("hello");
        return "hello";
    }
}
