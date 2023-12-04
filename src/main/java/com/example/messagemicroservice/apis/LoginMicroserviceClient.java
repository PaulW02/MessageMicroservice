package com.example.messagemicroservice.apis;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "login-microservice")
public interface LoginMicroserviceClient {

    @GetMapping("/user/{userId}")
    boolean doesUserExist(@PathVariable Long userId);
}