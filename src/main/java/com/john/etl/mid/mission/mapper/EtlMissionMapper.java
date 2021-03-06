package com.john.etl.mid.mission.mapper;

import com.john.etl.mid.mission.entity.EtlMission;
import com.john.etl.mybatisplus.mapper.BaseMapperExtend;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 清洗任务表 Mapper 接口
 * </p>
 *
 * @author Mac OS X
 * @since 2018-11-28
 */
@Mapper
public interface EtlMissionMapper extends BaseMapperExtend<EtlMission> {

    void etlSuccess(@Param("etlMission") EtlMission etlMission);

    void etlIgnore(@Param("etlMission") EtlMission etlMission);

    void etlFail(@Param("etlMission") EtlMission etlMission);

    <T> List<EtlMission> loadByList(@Param("candidates") List<T> candidates,@Param("field") String field, @Param("isExclude") boolean isExclude);
}
