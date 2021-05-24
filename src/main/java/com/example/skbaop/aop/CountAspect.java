package com.example.skbaop.aop;

import com.example.skbaop.api.ApiController;
import com.example.skbaop.config.AspectConfig;
import com.example.skbaop.data.ApiRestrictions;
import lombok.Data;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Aspect
@Component
@Data
public class CountAspect {

    private static final Logger log = LoggerFactory.getLogger(ApiController.class);
    private ApiRestrictions methods;

    public CountAspect(ApiRestrictions methods){
        this.methods = methods;
    }

    @Around("@annotation(com.example.skbaop.aop.LogApi)")
    public synchronized void preventAccess(ProceedingJoinPoint jp) throws Throwable {
        var methodName = jp.getSignature().getName();
        if (methods.getRestrictions().containsKey(methodName)) {
            var currentNumber = methods.getRestrictions().get(methodName);
            --currentNumber;
            methods.getRestrictions().put(methodName, currentNumber);
            if (currentNumber >= 0) {
                jp.proceed();
            } else {
                log.info("Access to " + methodName +" restricted");
            }
        } else{
            var currentDefaultNumber = methods.getRestrictions().get("default");
            --currentDefaultNumber;
            if (currentDefaultNumber >= 0){
                jp.proceed();
            } else{
                log.info("Access to " + methodName +" restricted");
            }
        }
    }
}
