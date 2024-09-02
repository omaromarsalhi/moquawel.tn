package com.moquawel.authentication.config;


import com.moquawel.authentication.jwt.JitAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private static final String[] WHITE_LIST_URL = {"/api/v1/auth/**"};
    private final JitAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
//    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http)
            throws Exception {
        http
//                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL)
                                .permitAll()
//                                .requestMatchers("/api/v1/management/**").hasAnyRole(ADMIN.name(), MANAGER.name())
//                                .requestMatchers(HttpMethod.GET, "/api/v1/management/**").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
//                                .requestMatchers(HttpMethod.POST, "/api/v1/management/**").hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
//                                .requestMatchers(HttpMethod.PUT, "/api/v1/management/**").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
//                                .requestMatchers(HttpMethod.DELETE, "/api/v1/management/**").hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
//                .logout(logout ->
//                        logout.logoutUrl("/api/v1/auth/logout")
//                                .addLogoutHandler(logoutHandler)
//                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
//                )
        ;

        return http.build();
    }

}
