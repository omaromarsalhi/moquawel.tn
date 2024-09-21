package com.moquawel.tenderInvitation.controller;


import com.moquawel.tenderInvitation.response.OfferResponse;
import com.moquawel.tenderInvitation.service.TenderInvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tenderInvitations")
@RequiredArgsConstructor
public class TenderInvitationController {

    private final TenderInvitationService tenderInvitationService;

    @PostMapping("/hello")
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("hello");
    }

    @GetMapping("/getOffers")
    public ResponseEntity<OfferResponse> getOffers(){
        return ResponseEntity.ok(tenderInvitationService.getOffers());
    }



}
