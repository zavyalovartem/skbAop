package com.example.skbaop.aop;

import com.example.skbaop.api.ApiController;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Aspect
@Component
@EnableConfigurationProperties
@ConfigurationProperties("app")
public class CountAspect {

    @Value("${app.get}")
    private int numberOfRequestsToGetApi;
    @Value("${app.post}")
    private int numberOfRequestsToPostApi;
    private static final Logger log = LoggerFactory.getLogger(ApiController.class);

    @Around("@annotation(com.example.skbaop.aop.LogApi)")
    public void preventAccess(ProceedingJoinPoint jp) throws Throwable {
        if (jp.getSignature().getName().equals("firstApi")) {
            --numberOfRequestsToGetApi;

            if (numberOfRequestsToGetApi >= 0) {
                jp.proceed();
            } else {
                log.info("Access to first restricted");
            }
        } else{
            --numberOfRequestsToPostApi;
            if (numberOfRequestsToPostApi >= 0){
                jp.proceed();
            } else{
                log.info("Access to second restricted");
            }
        }
    }
}
