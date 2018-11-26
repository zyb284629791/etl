package com.john.etl.mission.service.impl;

import com.john.etl.mission.entity.EtlMission;
import com.john.etl.mission.mapper.EtlMissionMapper;
import com.john.etl.mission.service.IEtlMissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 清洗任务表 服务实现类
 * </p>
 *
 * @author Mac OS X
 * @since 2018-11-26
 */
@Service
public class EtlMissionServiceImpl extends ServiceImpl<EtlMissionMapper, EtlMission> implements IEtlMissionService {

}
