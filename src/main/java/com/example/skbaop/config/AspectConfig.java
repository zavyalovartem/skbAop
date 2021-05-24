package com.example.skbaop.config;

import com.example.skbaop.data.ApiRestrictions;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "request-map")
@Data
public class AspectConfig {
    private ConcurrentHashMap<String, Integer> methods;

    @Bean
    public ApiRestrictions restrictions(){
        return new ApiRestrictions(methods);
    }
}
