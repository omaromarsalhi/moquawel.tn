package com.moquawel.tenderInvitation.feignclient;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "agrementInfo", url = "${search-service.agrement-info.url}")
public interface AgrementInfo {
    @GetMapping("/{epBidMasterId}")
    ResponseEntity<?> getAgrementInfoClient(@PathVariable("epBidMasterId") String epBidMasterId);

}