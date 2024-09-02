package com.moquawel.gateway.config;

import com.moquawel.gateway.filter.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class GatewayConfig {
    private final JwtAuthenticationFilter filter;

}