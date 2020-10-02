package com.ing.barber.shop.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/hello")
@Slf4j
public class HelloController {

    @GetMapping
    public String hello() {
        return "Welcome to Edwin's Barber Shop";
    }

}
