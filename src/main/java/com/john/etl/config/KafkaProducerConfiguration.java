package com.john.etl.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.john.etl.mid.mission.entity.EtlMission;
import com.john.etl.properties.EtlConfigProperties;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;

/**
 * @Description Kafka生产者相关配置
 * @Author: Yb.Z
 * @Date: 2018/11/30.21:53
 * @Version：1.0
 */
@Configuration
public class KafkaProducerConfiguration {

    @Autowired
    private EtlConfigProperties etlConfigProperties;

    @Bean
    @Primary
    public JsonSerializer<EtlMission> customSerializer(@Qualifier("objectMapper") ObjectMapper objectMapper) {
        return new JsonSerializer<>(objectMapper);
    }

    @Bean
    @Primary
    public ProducerFactory<String, EtlMission> kafkaProducerFactory(
            @Qualifier("customSerializer") JsonSerializer customSerializer) {
        KafkaProperties properties = etlConfigProperties.getKafkaProperties();
        Map<String, Object> producerConfig = properties.buildConsumerProperties();
        return new DefaultKafkaProducerFactory<>(producerConfig,new StringSerializer(),customSerializer);
    }

    @Bean
    public KafkaTemplate<String,EtlMission> kafkaTemplate(
            @Qualifier("kafkaProducerFactory") ProducerFactory producerFactory){
        return new KafkaTemplate<String,EtlMission>(producerFactory);
    }
}
