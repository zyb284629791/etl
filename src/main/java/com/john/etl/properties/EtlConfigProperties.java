package com.john.etl.properties;

import lombok.Data;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

/**
 * @Description 清洗器自动配置
 * @Author: Yb.Z
 * @Date: 2018/11/26.22:42
 * @Version：1.0
 */
@ConfigurationProperties(prefix = "etl")
@Component
@Data
@ToString
public class EtlConfigProperties {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private EtlMethod method = EtlMethod.single;

    /**
     * 是否在启动时加载未清洗完成数据
     */
    private boolean loadUnfinished;

    /**
     * 启动时根据表名加载mission
     */
    private List<String> loadUnfinishedTableName;

    /**
     * 启动时根据地区标识加载mission
     */
    private List<String> loadUnfinishedPosition;

    /**
     * 启动时根据表名过滤mission
     */
    private List<String> loadUnfinishedExcludeTableName;

    /**
     * 启动时根据地区标识过滤mission
     */
    private List<String> loadUnfinishedExcludePosition;

    /**
     * 中间库数据源
     */
    private DataSource mid;

    /**
     * 正式库数据源
     */
    private DataSource official;
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
