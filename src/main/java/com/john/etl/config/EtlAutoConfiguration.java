package com.john.etl.config;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @Description 清洗器自动配置
 * @Author: Yb.Z
 * @Date: 2018/11/26.22:42
 * @Version：1.0
 */
@ConfigurationProperties(prefix = "etl")
@Data
public class EtlAutoConfiguration {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private EtlMethod method = EtlMethod.single;

    private boolean loadUnfinished;
    private List<String> loadUnfinishedTableName;
}

/**
 * @Description 清洗器执行方法（多线程、单线程）
 * @Author: Yb.Z
 * @Date: 2018/11/26.22:42
 * @Version：1.0
 */
enum EtlMethod{

    multipart(0), //多线程
    single(1);// 单线程

    private final int method;

    EtlMethod(int m){
        this.method = m;
    }
}
