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

import java.util.*;

/**
 * @Description 监听容器启动
 * @Author: Yb.Z
 * @Date: 2018/11/26.22:34
 * @Version：1.0
 */
@Component
public class StartUpListener implements ApplicationListener<ContextRefreshedEvent> {

    private Logger logger = LoggerFactory.getLogger(StartUpListener.class);
    private String defaultTopic;

    @Autowired
    private KafkaProducer kafkaProducer;
    @Autowired
    private IEtlMissionService etlMissionService;
    @Autowired
    private EtlConfigProperties etlConfigProperties;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
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
            logger.info("从数据库加载未清洗的mission");
//            kafkaProducer = applicationContext.getBean("kafkaProducer", KafkaProducer.class);
//            etlMissionService = applicationContext.getBean("etlMissionService", IEtlMissionService.class);
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
                Collection<EtlMission> etlMissions = new ArrayList<>(0);
                if (!CollectionUtils.isEmpty(loadById)) {
                    etlMissions = loadById(loadById);
                }
                if (!CollectionUtils.isEmpty(loadByPosition) && CollectionUtils.isEmpty(etlMissions)) {
                    etlMissions = loadByPosition(loadByPosition);
                }
                if (!CollectionUtils.isEmpty(loadByTableName) && CollectionUtils.isEmpty(etlMissions)) {
                    etlMissions = loadByTableName(loadByTableName);
                }
                if (!CollectionUtils.isEmpty(loadExcludePosition) && CollectionUtils.isEmpty(etlMissions)) {
                    etlMissions = loadExcludePosition(loadExcludePosition);
                }
                if (!CollectionUtils.isEmpty(loadExcludeTableName) && CollectionUtils.isEmpty(etlMissions)) {
                    etlMissions = loadExcludeTableName(loadExcludeTableName);
                }

                if (!CollectionUtils.isEmpty(etlMissions)) {
                    kafkaProducer.batchProduce(defaultTopic, etlMissions);
                }
            }
        }
    }

    /**
     * 根据missionId加载mission
     * @param ids
     */
    private Collection<EtlMission> loadById(List<String> ids) {
        logger.info("根据missionId批量加载mission");
        return etlMissionService.listByIds(ids);
    }

    /**
     * 根据position加载mission
     * @param positions
     */
    private Collection<EtlMission> loadByPosition(List<String> positions) {
        logger.info("根据position批量加载mission");
        return etlMissionService.loadByList(positions,"position",false);
    }

    /**
     * 根据表名加载mission
     * @param tableNames
     */
    private Collection<EtlMission> loadByTableName(List<String> tableNames) {
        logger.info("根据tableName批量加载mission");
        return etlMissionService.loadByList(tableNames,"tableName",false);
    }

    /**
     * 根据position过滤mission
     * @param excludePositions
     */
    private Collection<EtlMission> loadExcludePosition(List<String> excludePositions) {
        logger.info("根据position批量过滤mission");
        return etlMissionService.loadByList(excludePositions,"position",true);
    }

    /**
     * 根据表名过滤mission
     * @param excludeTableNames
     */
    private Collection<EtlMission> loadExcludeTableName(List<String> excludeTableNames) {
        logger.info("根据tableName批量过滤mission");
        return etlMissionService.loadByList(excludeTableNames,"tableName",true);
    }


}
