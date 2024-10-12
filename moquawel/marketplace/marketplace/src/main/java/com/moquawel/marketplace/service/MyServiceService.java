package com.moquawel.marketplace.service;


import com.moquawel.marketplace.category.Category;
import com.moquawel.marketplace.mkservice.MyService;
import com.moquawel.marketplace.mkservice.MyServiceRepository;
import com.moquawel.marketplace.request.ServiceRequest;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class MyServiceService {

    private final MyServiceRepository myServiceRepository;

    public void saveService(ServiceRequest serviceRequest) {
        try {
            myServiceRepository.save(MyService
                    .builder()
                    .serviceName(serviceRequest.serviceName())
                    .fields(serviceRequest.fields())
                    .categoryId(serviceRequest.categoryId())
                    .build()
            );
        } catch (Exception e) {
            log.error("this err occurred while saving the new service: {}", e.getMessage());
        }
    }

    public List<MyService> getServicesByCategory(String categoryId) {
        try {
            return myServiceRepository.findAllByCategoryId(categoryId);
        } catch (Exception e) {
            log.error("this err occurred while retrieving services by category: {}", e.getMessage());
            return new ArrayList<>();
        }
    }


}
