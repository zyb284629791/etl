package com.john.etl.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * dao层方法名前缀，用来判断方法类型，添加缓存支持
 * @Author: 张彦斌
 * @Date: 2019-01-03 15:39
 */
@Getter
public enum MethodNameType {

    /**
     * 查询方法
     */
    Select("select","Select","查询"),
    Find("find","Select","查询"),
    List("list","Select","查询"),
    Query("query","Select","查询"),
    Load("load","Select","查询"),
    Get("get","Select","查询"),

    /**
     * 插入方法
     */
    Insert("insert","Insert","插入"),
    Save("save","Insert","插入"),

    /**
     * 修改方法
     */
    Update("update","Update","修改"),
    Merge("merge","Update","修改"),

    /**
     * 删除方法
     */
    Delete("delete","Delete","删除"),
    Remove("remove","Delete","删除");

    private String methodName;
    private String methodType;
    private String desc;

    MethodNameType(String methodName, String methodType, String desc) {
        this.methodName = methodName;
        this.methodType = methodType;
        this.desc = desc;
    }

    /**
     * 根据方法类型获取所有方法的枚举
     * @param methodType
     * @return
     */
    public static List<MethodNameType> getMethodNameTypesByMethodType(String methodType){
        MethodNameType[] values = MethodNameType.values();
        List<MethodNameType> result = Arrays.stream(values).filter((methodNameType) -> {
            if (methodNameType.methodType.equalsIgnoreCase(methodType)) {
                return true;
            }
            return false;
        }).collect(Collectors.toList());
        return result;
    }


}
