package com.john.etl.kafka;

import com.john.etl.mid.mission.entity.EtlMission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @Description Kafka消费者
 * @Author: Yb.Z
 * @Date: 2018/11/29.21:01
 * @Version：1.0
 */
@Component
public class Consumer<T extends EtlMission> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @KafkaListener(topics = "test")
    public void consume(T message){
        logger.info("消费到消息：%s",message);
    }
}
