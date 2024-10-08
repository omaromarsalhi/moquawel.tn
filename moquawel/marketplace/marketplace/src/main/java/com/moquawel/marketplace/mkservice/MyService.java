package com.moquawel.marketplace.mkservice;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(collection = "service")
public class MyService {

    @Id
    private String serviceId;

    private String serviceName;

    private Map<String, String> fields;

}
