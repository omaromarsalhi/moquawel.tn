package com.moquawel.marketplace.controller;


import com.moquawel.marketplace.service.MyServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/marketplace/admin")
@RequiredArgsConstructor
public class AdminMarketPlaceController {

    private final MyServiceService myServiceService;

    @PostMapping("/service/save")
    public ResponseEntity<Void> saveService(@RequestBody Map<String, String> fields) {
        myServiceService.save(fields);
        return ResponseEntity.ok().build();
    }


}
