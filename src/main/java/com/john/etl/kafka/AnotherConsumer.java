package com.john.etl.kafka;

import com.john.etl.mid.mission.entity.EtlMission;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @Description 另一个消费者
 * @Author: Yb.Z
 * @Date: 2018/12/2.16:37
 * @Version：1.0
 */
@Component
public class AnotherConsumer {


    @KafkaListener(topics = "${etl.kafkaProperties.template.defaultTopic}")
    public void consume(EtlMission etlMission){
        System.err.format("另一个消费者，%s", etlMission);
    }
}
