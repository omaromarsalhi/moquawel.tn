package com.moquawel.tenderInvitation.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/subscriptions")
@RequiredArgsConstructor
public class TenderInvitationController {

//    private final SubscriptionService subscriptionService;
//
//    @PostMapping("/activate")
//    public ResponseEntity<ActivationResponse> activate(
//            @RequestBody @Valid ActivationRequest request
//    ){
//        return ResponseEntity.ok(subscriptionService.activate(request));
//    }
//
//    @PostMapping("/deactivate")
//    public ResponseEntity<ActivationResponse> deactivate(
//            @RequestBody @Valid ActivationRequest request
//    ){
//        return ResponseEntity.ok(subscriptionService.deactivate(request));
//    }
//
//    @PostMapping("/renew")
//    public ResponseEntity<ActivationResponse> renew(
//            @RequestBody @Valid ActivationRequest request
//    ){
//        return ResponseEntity.ok(subscriptionService.renew(request));
//    }


}
