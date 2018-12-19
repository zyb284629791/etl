package com.john.etl.listener;


import com.john.etl.kafka.KafkaProducer;
import com.john.etl.mid.mission.entity.EtlMission;
import com.john.etl.mid.mission.service.IEtlMissionService;
import com.john.etl.properties.EtlConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 监听容器启动
 * @Author: Yb.Z
 * @Date: 2018/11/26.22:34
 * @Version：1.0
 */
@Component
public class StartUpListener implements ApplicationListener<ContextRefreshedEvent> {

    private Logger logger = LoggerFactory.getLogger(StartUpListener.class);
    private ApplicationContext applicationContext = null;
    private KafkaProducer kafkaProducer;
    private IEtlMissionService etlMissionService;
    private String defaultTopic;

    @Autowired
    private EtlConfigProperties etlConfigProperties;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        applicationContext = event.getApplicationContext();
        /**
         * 是否需要加载未清洗的mission继续清洗
         * 需要时可
         *    1、根据表名加载mission
         *    2、根据地区标识加载mission
         *    3、根据执行ID加载mission
         *    4、根据表名过滤mission
         *    5、根据地区标识过滤mission
         * 同时配置多个无效，将按照上述顺序进行加载
         * 一个都没配置时默认全部加载
         */
        if (etlConfigProperties.isLoadUnfinished()) {
            kafkaProducer = applicationContext.getBean("kafkaProducer", KafkaProducer.class);
            etlMissionService = applicationContext.getBean("etlMissionService", IEtlMissionService.class);
            defaultTopic = etlConfigProperties.getKafkaProperties().getTemplate().getDefaultTopic();
            List<String> loadById = etlConfigProperties.getLoadUnfinishedById();
            List<String> loadByPosition = etlConfigProperties.getLoadUnfinishedByPosition();
            List<String> loadByTableName = etlConfigProperties.getLoadUnfinishedByTableName();
            List<String> loadExcludePosition = etlConfigProperties.getLoadUnfinishedExcludePosition();
            List<String> loadExcludeTableName = etlConfigProperties.getLoadUnfinishedExcludeTableName();
            //
            if (CollectionUtils.isEmpty(loadById) && CollectionUtils.isEmpty(loadByPosition)
                    && CollectionUtils.isEmpty(loadByTableName) && CollectionUtils.isEmpty(loadExcludePosition)
                    && CollectionUtils.isEmpty(loadExcludeTableName)) {
                HashMap<String, Object> params = new HashMap<>();
                Collection<EtlMission> etlMissions = etlMissionService.listByMap(params);
                kafkaProducer.batchProduce(defaultTopic,etlMissions);
            } else {
                if (!CollectionUtils.isEmpty(loadById)) {
                    loadById(loadById);
                    return;
                }
                if (!CollectionUtils.isEmpty(loadByPosition)) {
                    loaddByPosition(loadByPosition);
                    return;
                }
                if (!CollectionUtils.isEmpty(loadByTableName)) {
                    loadByTableName(loadByTableName);
                    return;
                }
                if (!CollectionUtils.isEmpty(loadExcludePosition)) {
                    loadExcludePosition(loadExcludePosition);
                    return;
                }
                if (!CollectionUtils.isEmpty(loadExcludeTableName)) {
                    loadExcludeTableName(loadExcludeTableName);
                    return;
                }
            }
        }
    }

    /**
     * 根据missionId加载mission
     * @param loadById
     */
    private void loadById(List<String> loadById) {
        Collection<EtlMission> etlMissions = etlMissionService.listByIds(loadById);
        if (!CollectionUtils.isEmpty(etlMissions)) {
            kafkaProducer.batchProduce(defaultTopic, etlMissions);
        }
    }

    /**
     * 根据position加载mission
     * @param loadByPosition
     */
    private void loaddByPosition(List<String> loadByPosition) {

    }

    /**
     * 根据表名加载mission
     * @param loadByTableName
     */
    private void loadByTableName(List<String> loadByTableName) {

    }

    /**
     * 根据position过滤mission
     * @param loadExcludePosition
     */
    private void loadExcludePosition(List<String> loadExcludePosition) {

    }

    /**
     * 根据表名过滤mission
     * @param loadExcludeTableName
     */
    private void loadExcludeTableName(List<String> loadExcludeTableName) {

    }


}
