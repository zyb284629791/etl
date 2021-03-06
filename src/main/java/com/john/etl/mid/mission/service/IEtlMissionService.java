package com.john.etl.mid.mission.service;

import com.john.etl.mid.mission.entity.EtlMission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.john.etl.mybatisplus.service.IServiceExtend;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 清洗任务表 服务类
 * </p>
 *
 * @author Mac OS X
 * @since 2018-11-28
 */
public interface IEtlMissionService extends IServiceExtend<EtlMission> {

    void etlSuccess(EtlMission etlMission);

    void etlIgnore(EtlMission etlMission);

    void etlFail(EtlMission etlMission);

    <T> Collection<EtlMission> loadByList(List<T> list,String field, boolean isExclude);

}
