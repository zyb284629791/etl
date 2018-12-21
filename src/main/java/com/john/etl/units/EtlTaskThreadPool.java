package com.john.etl.units;

import com.john.etl.enums.EtlMethod;
import com.john.etl.kafka.KafkaProducer;
import com.john.etl.listener.EtlUnitMapping;
import com.john.etl.mid.mission.entity.EtlMission;
import com.john.etl.mid.mission.service.IEtlMissionService;
import com.john.etl.properties.EtlConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;


/**
 * @Author: 张彦斌
 * @Date: 2018-12-18 11:29
 */
@Component
public class EtlTaskThreadPool implements InitializingBean, ApplicationContextAware {

    @Autowired
    private EtlConfigProperties etlConfigProperties;

    @Autowired
    private EtlUnitMapping etlUnitMapping;

    /**
     * 此处不知道在多线程清洗时会不会有问题
     */
    @Autowired
    private IEtlMissionService etlMissionService;

    @Autowired
    private KafkaProducer kafkaProducer;

    // 线程池
    private ExecutorService executorService;

    private ApplicationContext applicationContext;

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 信号量，用来控制并发数
    private Semaphore semaphore;


    @Override
    public void afterPropertiesSet(){
        EtlMethod method = etlConfigProperties.getMethod();
        switch (method) {
            case single:
                executorService = Executors.newSingleThreadExecutor();
                semaphore = new Semaphore(1);
                break;
            case multipart:
                executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
                semaphore = new Semaphore(Runtime.getRuntime().availableProcessors());
                break;
            default:
                logger.warn("未设置etl运行方式,默认以多线程运行...");
                executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
                semaphore = new Semaphore(Runtime.getRuntime().availableProcessors());
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
        long abandonTimes = etlConfigProperties.getAbandonTimes();
        Class<EntityEtlUnit> etlUnitByTableName = etlUnitMapping.getEtlUnitByTableName(etlMission.getTableName());
        if (!ObjectUtils.isEmpty(etlUnitByTableName)) {
            EntityEtlUnit entityEtlUnit = applicationContext.getBean(etlUnitByTableName);
            EtlTask etlTask = new EtlTask();
            etlTask.setAbandonTimes(abandonTimes);
            etlTask.setDefaultTopic(defaultTopic);
            etlTask.setEntityEtlUnit(entityEtlUnit);
            etlTask.setEtlMission(etlMission);
            etlTask.setEtlMissionService(etlMissionService);
            etlTask.setKafkaProducer(kafkaProducer);
            etlTask.setSemaphore(semaphore);
            executorService.submit(etlTask);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
