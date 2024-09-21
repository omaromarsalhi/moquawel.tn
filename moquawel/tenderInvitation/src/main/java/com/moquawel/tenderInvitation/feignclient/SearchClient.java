package com.moquawel.tenderInvitation.feignclient;


import com.moquawel.tenderInvitation.response.OfferResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "searchClient", url = "${search-service.url}")
public interface SearchClient {
    @PostMapping
    ResponseEntity<OfferResponse> searchOffers(@RequestBody Map<String, Object> payload);
}
