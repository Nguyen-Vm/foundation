package org.linker.foundation.web;

import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * @author RWM
 * @date 2018/9/19
 */
@Aspect
public class ApiOperationInterceptor {

    @Around("@annotation(operation)")
    public Object around(ProceedingJoinPoint joinPoint, ApiOperation operation) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Object response = joinPoint.proceed();
        for (Object o : args) {
            System.out.println(o);
        }
        System.out.println(operation.value());
        System.out.println(response);
        return response;
    }

}
