package com.john.etl.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.john.etl.mid.mission.entity.EtlMission;
import com.john.etl.properties.EtlConfigProperties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

/**
 * @Description Kafka手动配置
 * @Author: Yb.Z
 * @Date: 2018/11/30.21:13
 * @Version：1.0
 */
@Configuration
@EnableKafka
public class KafkaConsumerConfiguration {

    @Autowired
    private EtlConfigProperties etlConfigProperties;

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());
        // new module, NOT JSR310Module
        // or, alternatively, you can also auto-discover these modules with:
        // ObjectMapper mapper = new ObjectMapper();
        // mapper.findAndRegisterModules();
        return mapper;
    }

    @Bean
    @Primary
    public JsonDeserializer<EtlMission> customDeserializer(@Qualifier("objectMapper") ObjectMapper objectMapper){
        return new JsonDeserializer<>(EtlMission.class,objectMapper);
    }

    @Bean
    @Primary
    public KafkaListenerContainerFactory<?> kafkaListenerContainerFactory(
            @Qualifier("kafkaConsumerFactory") ConsumerFactory kafkaConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(kafkaConsumerFactory);
        return factory;
    }

    @Bean
    @Primary
    public ConsumerFactory<String, EtlMission> kafkaConsumerFactory() {
        KafkaProperties properties = etlConfigProperties.getKafkaProperties();
        Map<String, Object> consumerConfig = properties.buildConsumerProperties();
        JsonDeserializer<EtlMission> customDeserializer = customDeserializer(objectMapper());
        return new DefaultKafkaConsumerFactory<>(consumerConfig,new StringDeserializer(),customDeserializer);
    }


}
