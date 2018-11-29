package com.john.etl.listener;


import com.john.etl.kafka.Producer;
import com.john.etl.properties.EtlConfigProperties;
import com.john.etl.units.EtlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Description 监听容器启动，启动Kafka消费线程
 * @Author: Yb.Z
 * @Date: 2018/11/26.22:34
 * @Version：1.0
 */
@Component
public class StartUpListener implements ApplicationListener<ContextRefreshedEvent> {

    private Logger logger = LoggerFactory.getLogger(StartUpListener.class);
    private ApplicationContext applicationContext = null;

    @Autowired
    private EtlConfigProperties etlConfigProperties;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        applicationContext = event.getApplicationContext();
        // init units map

        // start kafka listener, consume message
        Producer producer = applicationContext.getBean("producer", Producer.class);
        List<String> kafkaTopics = etlConfigProperties.getKafkaTopics();
        if (CollectionUtils.isEmpty(kafkaTopics)) {
            throw new EtlException("Kafka topic must be not null.");
        }
        // 目前暂时设置获取第一个topic
        producer.produce(kafkaTopics.get(0),1);
    }


}
