package com.moquawel.tenderInvitation.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.moquawel.tenderInvitation.response.PayloadResponse;
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
    public ResponseEntity<PayloadResponse> getOffers(){
        return ResponseEntity.ok(tenderInvitationService.getOffers());
    }

    @GetMapping("/getAoAndGeneralInfo/{epBidMasterId}")
    public ResponseEntity<Object> getAoAndGeneralInfo(@PathVariable String epBidMasterId){
        return ResponseEntity.ok(tenderInvitationService.getAoAndGeneralInfo(epBidMasterId));
    }

    @GetMapping("/getLotAndArticleInfo/{epBidMasterId}")
    public ResponseEntity<Object> getLotAndArticleInfo(@PathVariable String bidNo){
        return ResponseEntity.ok(tenderInvitationService.getLotAndArticleInfo(bidNo));
    }

    @GetMapping("/getProdInfo/{epBidMasterId}")
    public ResponseEntity<Object> getProdInfo(@PathVariable String epBidMasterId){
        return ResponseEntity.ok(tenderInvitationService.getProdInfo(epBidMasterId));
    }

    @GetMapping("/getAgrementInfo/{epBidMasterId}")
    public ResponseEntity<Object> getAgrementInfo(@PathVariable String epBidMasterId){
        return ResponseEntity.ok(tenderInvitationService.getAgrementInfo(epBidMasterId));
    }

    @GetMapping("/getTermsOfReferenceInfo/{epBidMasterId}")
    public ResponseEntity<Object> getTermsOfReferenceInfo(@PathVariable String epBidMasterId){
        return ResponseEntity.ok(tenderInvitationService.getTermsOfReferenceInfo(epBidMasterId));
    }



}
