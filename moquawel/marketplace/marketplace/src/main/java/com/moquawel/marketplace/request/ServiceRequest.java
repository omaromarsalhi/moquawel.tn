package com.moquawel.marketplace.request;

import com.moquawel.marketplace.mkservice.Field;
import lombok.Builder;
import java.util.List;

@Builder
public record ServiceRequest(
        String serviceName,
        List<Field> fields,
        String categoryId
) {}


