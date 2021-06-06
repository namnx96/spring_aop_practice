package com.namnx.spring_aop.aspect.performance_log;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.LOCAL_VARIABLE})
public @interface PerformanceLoggable {}
