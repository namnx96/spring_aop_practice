package com.namnx.spring_aop.aspect.datasources;

import com.namnx.spring_aop.aspect.datasources.routing.RoutingDataSource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Aspect
@Component
@Order(20)
public class ReadOnlyDatasourceInterceptor {

    //use: @Transactional(readOnly = true)
    @Around("@annotation(transactional)")
    public Object proceed(ProceedingJoinPoint proceedingJoinPoint, Transactional transactional) throws Throwable {
        try {
            if (transactional.readOnly()) {
                RoutingDataSource.setReplicaRoute();
            }
            return proceedingJoinPoint.proceed();
        } finally {
            RoutingDataSource.clearRoute();
        }
    }
}
