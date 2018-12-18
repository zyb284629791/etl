package com.john.etl.units;

import com.john.etl.listener.EtlUnitMapping;
import com.john.etl.mid.mission.entity.EtlMission;
import com.john.etl.mid.mission.service.IEtlMissionService;
import com.john.etl.properties.EtlConfigProperties;
import com.john.etl.constant.EtlMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: 张彦斌
 * @Date: 2018-12-18 11:29
 */
@Component
public class EtlTaskThreadPool implements InitializingBean {

    @Autowired
    private EtlConfigProperties etlConfigProperties;

    @Autowired
    private EtlUnitMapping etlUnitMapping;

    /**
     * 此处不知道多线程下会不会有问题
     */
    @Autowired
    private IEtlMissionService etlMissionService;

    private ExecutorService executorService;

    private Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public void afterPropertiesSet(){
        EtlMethod method = etlConfigProperties.getMethod();
        switch (method) {
            case single:
                executorService = Executors.newSingleThreadExecutor();
                break;
            case multipart:
                executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
                break;
            default:
                logger.warn("未设置etl运行方式,默认以多线程运行...");
                executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
                break;
        }
    }

    /**
     * 消费Kafka中的消息
     * 1、从etlunitmapping中获取etiunit的class
     * 2、通过application context获取unit
     * 3、使用创建线程，加入线程池
     *    线程中执行unit的doetl方法，分别处理成功、失败及忽略
     * @param etlMission
     */
    public void startEtl(EtlMission etlMission){
        String defaultTopic = etlConfigProperties.getKafkaProperties().getTemplate().getDefaultTopic();

    }
}
