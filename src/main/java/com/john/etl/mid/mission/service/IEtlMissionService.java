package com.john.etl.mid.mission.service;

import com.john.etl.mid.mission.entity.EtlMission;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 清洗任务表 服务类
 * </p>
 *
 * @author Mac OS X
 * @since 2018-11-28
 */
public interface IEtlMissionService extends IService<EtlMission> {

    void etlSuccess(@Param("etlMission") EtlMission etlMission);

    void etlIgnore(@Param("etlMission") EtlMission etlMission);

    void etlFail(@Param("etlMission") EtlMission etlMission);
}
