package com.john.etl.enums;

/**
 * @Description 清洗器执行方法（多线程、单线程）
 * @Author: Yb.Z
 * @Date: 2018/11/26.22:42
 * @Version：1.0
 */
public enum EtlMethod {

    multipart(0), //多线程
    single(1);// 单线程

    private final int method;

    EtlMethod(int m){
        this.method = m;
    }
}
