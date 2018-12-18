package com.john.etl.kafka;

import com.john.etl.mid.mission.entity.EtlMission;
import com.john.etl.units.EtlTaskThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Description Kafka消费者
 * @Author: Yb.Z
 * @Date: 2018/11/29.21:01
 * @Version：1.0
 */
@Component
public class KafkaConsumer {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private EtlTaskThreadPool etlTaskThreadPool;

    @KafkaListener(topics = "${etl.kafkaProperties.template.defaultTopic}")
    public void consume(EtlMission etlMission){
        logger.info("消费到消息：" + etlMission.toString());
        etlTaskThreadPool.startEtl(etlMission);
    }

    @KafkaListener(topics = "test")
    public void consume(String message){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("消费到：" + message);
    }

}
