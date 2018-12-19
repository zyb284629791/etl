package com.john.etl.mid.mission.service.impl;

import com.john.etl.enums.EtlOperStatus;
import com.john.etl.mid.mission.entity.EtlMission;
import com.john.etl.mid.mission.mapper.EtlMissionMapper;
import com.john.etl.mid.mission.service.IEtlMissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 清洗任务表 服务实现类
 * </p>
 *
 * @author Mac OS X
 * @since 2018-11-28
 */
@Service
public class EtlMissionServiceImpl extends ServiceImpl<EtlMissionMapper, EtlMission> implements IEtlMissionService {

    @Autowired
    EtlMissionMapper etlMissionMapper;

    @Override
    public void etlSuccess(EtlMission etlMission) {
        etlMission.setOperStatus(EtlOperStatus.Success);
        etlMissionMapper.etlSuccess(etlMission);
    }

    @Override
    public void etlIgnore(EtlMission etlMission) {
        etlMission.setOperStatus(EtlOperStatus.Ignore);
        etlMissionMapper.etlIgnore(etlMission);
    }

    @Override
    public void etlFail(EtlMission etlMission) {
        etlMissionMapper.etlFail(etlMission);
    }
}
