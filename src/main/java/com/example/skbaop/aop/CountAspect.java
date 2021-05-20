package com.example.skbaop.aop;

import com.example.skbaop.api.ApiController;
import lombok.Data;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Aspect
@Component
@Data
@ConfigurationProperties(prefix = "request-map")
public class CountAspect {

    private static final Logger log = LoggerFactory.getLogger(ApiController.class);
    private Map<String, Integer> methods;

    @Around("@annotation(com.example.skbaop.aop.LogApi)")
    public synchronized void preventAccess(ProceedingJoinPoint jp) throws Throwable {
        var methodName = jp.getSignature().getName();
        if (methods.containsKey(methodName)) {
            var currentNumber = methods.get(methodName);
            --currentNumber;
            methods.put(methodName, currentNumber);
            if (currentNumber >= 0) {
                jp.proceed();
            } else {
                log.info("Access to " + methodName +" restricted");
            }
        } else{
            var currentDefaultNumber = methods.get("default");
            --currentDefaultNumber;
            if (currentDefaultNumber >= 0){
                jp.proceed();
            } else{
                log.info("Access to " + methodName +" restricted");
            }
        }
    }
}
