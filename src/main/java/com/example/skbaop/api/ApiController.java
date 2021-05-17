package com.example.skbaop.api;

import com.example.skbaop.aop.LogApi;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    private static final Logger log = LoggerFactory.getLogger(ApiController.class);

    @GetMapping("/first")
    @LogApi
    public String firstApi() {
        log.info("First Api");
        return "first";
    }

    @PostMapping("/second")
    @LogApi
    public String secondApi() {
        log.info("Second Api");
        return "second";
    }
}