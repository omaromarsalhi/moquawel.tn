package com.moquawel.subscription.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/subscription")
@RequiredArgsConstructor
public class SubscriptionController {
    @PostMapping("/hello")
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("hello omar to subscription");
    }
}
