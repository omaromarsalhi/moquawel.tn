package com.moquawel.subscription.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/subscription")
public class SubscriptionController {

    @PostMapping("/hello")
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("hello omar to subscription");
    }

    // This endpoint requires the user to be authenticated
    @PostMapping("/all")
    public String getAllProducts() {
        return "Here are all the products";
    }

    // This endpoint requires the user to have the 'ADMIN' role
    @PostMapping("/admin")
    public String adminOnly() {
        return "Admin-only access to manage products";
    }

    // Public endpoint, no JWT needed
    @PostMapping("/public")
    public String publicEndpoint() {
        return "This is a public endpoint";
    }
}
