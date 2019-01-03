package com.john.etl.aop;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.john.etl.enums.MethodNameType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

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
    public void after(JoinPoint joinPoint){
        Signature signature = joinPoint.getSignature();
        String methodName = signature.getName();
        Class declaringType = signature.getDeclaringType();
        if (!declaringType.equals(BaseMapper.class)) {
            return;
        }
        if (isSelect(methodName)) {
            // addCache
        } else if (isInsert(methodName)){
            // addCache
        } else if (isUpdate(methodName)){
            // updateCache
        } else if (isDelete(methodName)) {
            // deleteCache
        }
    }

    private boolean isSelect(String methodName) {
        if (!StringUtils.isEmpty(methodName)) {
            return isMatch(MethodNameType.Select.getMethodType(), methodName);
        }
        return false;
    }

    private boolean isInsert(String methodName) {
        if (!StringUtils.isEmpty(methodName)) {
            return isMatch(MethodNameType.Insert.getMethodType(), methodName);
        }
        return false;
    }

    private boolean isUpdate(String methodName) {
        if (!StringUtils.isEmpty(methodName)) {
            return isMatch(MethodNameType.Update.getMethodType(), methodName);
        }
        return false;
    }

    private boolean isDelete(String methodName) {
        if (!StringUtils.isEmpty(methodName)) {
            return isMatch(MethodNameType.Delete.getMethodType(), methodName);
        }
        return false;
    }

    private boolean isMatch(String methodType,String methodName){
        List<MethodNameType> methodNameTypes = MethodNameType.getMethodNameTypesByMethodType(methodType);
        boolean result = methodNameTypes.parallelStream().anyMatch((methodNameType) -> {
            if (methodName.startsWith(methodNameType.getMethodName())) {
                return true;
            }
            return false;
        });
        return result;
    }
}
