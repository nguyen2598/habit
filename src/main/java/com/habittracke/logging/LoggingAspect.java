package com.habittracke.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {


    @Before("execution(* com.habittracke.service.*.*(..))")
    public void logBeforeMethod(JoinPoint joinPoint) {
        log.info("\uD83D\uDD14 Method service called: {}", joinPoint.getSignature().getName());
    }
}
