package com.john.etl.properties;

import com.john.etl.enums.EtlMethod;
import lombok.Data;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;


import javax.sql.DataSource;

/**
 * @Description 清洗器自动配置
 * @Author: Yb.Z
 * @Date: 2018/11/26.22:42
 * @Version：1.0
 */
@Component
@Data
@ToString
@ConfigurationProperties(prefix = "etl")
public class EtlConfigProperties {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 清洗器执行方式：multipart 多线程 single 单线程
     */
    private EtlMethod method = EtlMethod.single;

    /**
     * 中间库数据源
     */
    private DataSource mid;

    /**
     * 正式库数据源
     */
    private DataSource official;

    /**
     * 是否在启动时加载未清洗完成数据
     */
    private boolean isLoadUnfinished;

    /**
     * 清洗失败达到抛弃mission的次数
     */
    private long abandonTimes;

    /**
     * 启动时根据表名加载mission
     */
    private List<String> loadUnfinishedByTableName;

    /**
     * 启动时根据地区标识加载mission
     */
    private List<String> loadUnfinishedByPosition;

    /**
     * 启动是根据执行ID加载mission
     */
    private List<String> loadUnfinishedById;

    /**
     * 启动时根据表名过滤mission
     */
    private List<String> loadUnfinishedExcludeTableName;

    /**
     * 启动时根据地区标识过滤mission
     */
    private List<String> loadUnfinishedExcludePosition;

    /**
     * Kafka相关配置
     */
    private KafkaProperties kafkaProperties;

}
