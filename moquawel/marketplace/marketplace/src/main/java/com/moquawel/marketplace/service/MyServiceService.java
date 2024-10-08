package com.moquawel.marketplace.service;


import com.moquawel.marketplace.mkservice.MyService;
import com.moquawel.marketplace.mkservice.MyServiceRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyServiceService {

    private final MyServiceRepository myServiceRepository;

    public void save(Map<String, String> fields) {
        try {
            myServiceRepository.save(MyService
                    .builder()
                    .serviceName("architecture")
                    .fields(fields)
                    .build()
            );
        }catch (Exception e) {
            log.error("this err occurred while saving the new service: {}", e.getMessage());
        }
    }
}
