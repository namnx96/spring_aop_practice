package com.namnx.spring_aop.aspect.performance_log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;

@Service
@Aspect
@Slf4j
public class PointcutManager {

    @Pointcut("execution(* com.namnx.spring_aop.service.*.*(..))")
    public void performanceLog(){

    }
}
