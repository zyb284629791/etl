package com.john.etl.units;

import com.john.etl.enums.EtlOperStatus;
import com.john.etl.kafka.KafkaProducer;
import com.john.etl.mid.mission.entity.EtlMission;
import com.john.etl.mid.mission.service.IEtlMissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @Author: 张彦斌
 * @Date: 2018-12-18 14:14
 */
public class EtlTask implements Runnable {

    private EtlMission etlMission;

    private EntityEtlUnit entityEtlUnit;

    private KafkaProducer kafkaProducer;

    private String defaultTopic;

    private IEtlMissionService etlMissionService;

    private long abandonTimes;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public EtlTask(EtlMission etlMission, EntityEtlUnit entityEtlUnit, KafkaProducer kafkaProducer,
                   String defaultTopic, IEtlMissionService etlMissionService,long abandonTimes) {
        this.etlMission = etlMission;
        this.entityEtlUnit = entityEtlUnit;
        this.kafkaProducer = kafkaProducer;
        this.defaultTopic = defaultTopic;
        this.etlMissionService = etlMissionService;
        this.abandonTimes = abandonTimes;
    }

    @Override
    public void run() {
        // 执行etlunit的doetl方法
        // 根据返回结果进行相应处理
        // 返回为true，则清空mission的note字段，删除mission
        // 返回false，判断是否被忽略，若被忽略则直接删除mission
        // 返回false且未被忽略的mission，重新生产，加入Kafka中
        LocalDateTime start = LocalDateTime.now();
        if (ObjectUtils.isEmpty(etlMission) || ObjectUtils.isEmpty(entityEtlUnit) || ObjectUtils.isEmpty(kafkaProducer)
                || ObjectUtils.isEmpty(defaultTopic)) {
            throw new EtlException("mission,etlUnit,kafkaProducer,defaultTopic must be not null");
        } else {
            try {
                boolean etlResult = entityEtlUnit.doEtl(etlMission);
                // 每次执行结束后执行次数自增
                etlMission.setOperTimes(etlMission.getOperTimes() == null ? 1 : etlMission.getOperTimes() + 1);
                if (etlResult) {
                    etlSuccess(etlMission);
                } else {
                    if (EtlOperStatus.Ignore == etlMission.getOperStatus()) {
                        etlIgnore(etlMission);
                    } else {
                        if (etlMission.getOperTimes() > abandonTimes) {
                            etlMission.setNote("mission失败超过预设次数，忽略此mission" + etlMission.getNote());
                            etlIgnore(etlMission);
                        } else {
                            etlFail(etlMission);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("清洗失败，错误信息是：" + e.toString());
            } finally {
                LocalDateTime end = LocalDateTime.now();
                Duration between = Duration.between(start, end);
                // 这里用毫秒判断，因为如果用秒的话，1.X秒得到的结果都是1,此时1.9秒的结果也是1不符合要求。
                long difference = between.toMillis();
                if (difference > 1000) {
                    logger.warn("当前mission %s 运行时长为：d%", etlMission.toString(), difference);
                }
            }
        }
    }

    /**
     * 清洗失败
     *
     * @param etlMission
     */
    private void etlFail(EtlMission etlMission) {
        logger.error("mission -> %s失败", etlMission.toString());
        kafkaProducer.produce(defaultTopic, etlMission);
    }

    /**
     * 清洗任务被忽略
     *
     * @param etlMission
     */
    private void etlIgnore(EtlMission etlMission) {
        logger.info("mission -> %s被忽略", etlMission.toString());
        etlMissionService.etlIgnore(etlMission);
    }

    /**
     * 清洗成功
     *
     * @param etlMission
     */
    private void etlSuccess(EtlMission etlMission) {
        logger.info("mission -> %s成功", etlMission.toString());
        etlMissionService.etlSuccess(etlMission);
    }
}
