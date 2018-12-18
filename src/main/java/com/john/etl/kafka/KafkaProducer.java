package com.john.etl.kafka;

import com.john.etl.mid.mission.entity.EtlMission;
import com.john.etl.mid.mission.service.IEtlMissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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

    public void produce(String topic,Integer missionId){
        EtlMission etlMission = etlMissionService.getById(missionId);
        logger.info("############");
        logger.info("当前mission的ID是%s",etlMission.getId());
        kafkaTemplate.send(topic, etlMission);
    }

    public void produce(String topic, EtlMission mission){
        kafkaTemplate.send(topic, mission);
    }


}
