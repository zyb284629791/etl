package com.john.etl.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @Author: 张彦斌
 * @Date: 2018-12-28 16:00
 */
@Aspect
@Component
public class CacheAspect {

    @Pointcut("execution(public * com.john.etl.official.*.mapper.*.*(..))")
    public void cachePointcut(){}

    @After("cachePointcut()")
    public void after(){

    }
}
