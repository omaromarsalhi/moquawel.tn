package com.moquawel.marketplace.mkservice;

import java.util.List;

public record Field(
        String name,
        String type,
        List<String> values
) {}