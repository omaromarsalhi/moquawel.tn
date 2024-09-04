package com.moquawel.authentication.client;

import com.moquawel.authentication.dto.UserDto;
import com.moquawel.authentication.request.RegisterRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.Optional;

@FeignClient(
        name = "users-service",
        url = "/api/v1/users"
)
public interface UserClient {

    @GetMapping("/{username}")
    Optional<UserDto> getUser(@PathVariable("username") String username);

    @PostMapping("/save")
    UserDto save(@RequestBody RegisterRequest request);

}