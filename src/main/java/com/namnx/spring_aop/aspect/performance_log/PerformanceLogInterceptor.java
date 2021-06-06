package com.namnx.spring_aop.aspect.performance_log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

@Service
@Aspect
@Slf4j
public class PerformanceLogInterceptor {

    @Around(value = "com.namnx.spring_aop.aspect.performance_log.PointcutManager.performanceLog()"
            + "&& target(bean) "
            + "&& @annotation(com.namnx.spring_aop.aspect.performance_log.PerformanceLoggable)", argNames = "joinPoint,bean")
    public Object performanceLog(ProceedingJoinPoint joinPoint, Object bean) throws Throwable {
        StopWatch stopWatch = new StopWatch();

        stopWatch.start();
        Object result = joinPoint.proceed();
        stopWatch.stop();

        StringBuilder logMessage = new StringBuilder();
        logMessage.append(joinPoint.getTarget().getClass().getName());
        logMessage.append(".");
        logMessage.append(joinPoint.getSignature().getName());
        logMessage.append("(");

        String[] parameterNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
        Object[] args = joinPoint.getArgs();
        if (parameterNames.length != args.length) {
            return result;
        }
        for (int i = 0, argsLength = args.length; i < argsLength; i++) {
            String parameterName = parameterNames[i];
            Object arg = args[i];
            logMessage.append(parameterName).append(": ").append(arg).append(", ");
        }
        if (args.length > 0) {
            logMessage.replace(logMessage.length() - 2, logMessage.length(), "");
        }

        logMessage.append(")");
        logMessage.append(" execution takes: ");
        logMessage.append(stopWatch.getTotalTimeMillis());
        logMessage.append(" ms");

        log.info(logMessage.toString());
        return result;
    }
}
