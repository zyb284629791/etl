package com.john.etl.kafka;

import com.john.etl.mid.mission.entity.EtlMission;
import com.john.etl.mid.mission.service.IEtlMissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * @Description Kafka生产者
 * @Author: Yb.Z
 * @Date: 2018/11/29.21:01
 * @Version：1.0
 */
@Component
public class KafkaProducer {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private KafkaTemplate<String, EtlMission> kafkaTemplate;

    @Autowired
    private IEtlMissionService etlMissionService;

    /**
     * 根据id生产1条mission
     * @param topic
     * @param missionId
     */
    public void produce(String topic, Integer missionId) {
        logger.info("当前mission的ID是%s",missionId);
        EtlMission etlMission = etlMissionService.getById(missionId);
        if (!ObjectUtils.isEmpty(etlMission) && !StringUtils.isEmpty(topic)) {
            kafkaTemplate.send(topic, etlMission);
        }
    }

    /**
     * 生产一条mission
     * @param topic
     * @param mission
     */
    public void produce(String topic, EtlMission mission) {
        if (!ObjectUtils.isEmpty(mission) && !StringUtils.isEmpty(topic)) {
            kafkaTemplate.send(topic, mission);
        }
    }

    /**
     * 批量生产mission
     * @param topic
     * @param etlMissions
     */
    public void batchProduce(String topic, Collection<EtlMission> etlMissions) {
        if (!CollectionUtils.isEmpty(etlMissions) && !StringUtils.isEmpty(topic)) {
            logger.info("批量生产mission，当前共生产%d条mission", etlMissions.size());
            etlMissions.stream().forEach((etlMission ->
                    kafkaTemplate.send(topic, etlMission)));
            logger.info("批量生产mission结束");
        }
    }
}
