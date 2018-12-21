package com.john.etl.mid.mission.controller;


import com.john.etl.kafka.KafkaProducer;
import com.john.etl.mid.mission.entity.EtlMission;
import com.john.etl.mid.mission.service.IEtlMissionService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 清洗任务表 前端控制器
 * </p>
 *
 * @author Mac OS X
 * @since 2018-11-28
 */
@RestController
@RequestMapping("/mission/etlMission")
public class EtlMissionController {

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private IEtlMissionService etlMissionService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @ApiOperation(value="新增一条mission到MQ中", notes="根据mission的id来获取mission并加入MQ")
    @ApiImplicitParam(name = "missionId", value = "missionId", required = true, dataType = "Long")
    @GetMapping("/createMission")
    public void createMission(long missionId){
        EtlMission mission = etlMissionService.getById(missionId);
        logger.info(mission.toString());
    }

    @ApiOperation(value="新增一条mission到MQ中", notes="根据mission的id来获取mission并加入MQ")
    @ApiImplicitParam(name = "resIds", value = "resIds", required = true, dataType = "Long")
    @GetMapping("/createMissionByResId")
    public void createMissionByResId(List<Long> resIds){
        Collection<EtlMission> mission = etlMissionService.loadByList(resIds,"resId",false);
        logger.info(mission.toString());
    }

    @ApiOperation(value="根据position查询mission并加入到MQ中", notes="根据position查询mission并加入到MQ中")
    @ApiImplicitParam(name = "missionId", value = "missionId", required = true, dataType = "Long")
    @GetMapping("/createMissionByPositions")
    public void createMissionByPositions(List<String> positions){
        Collection<EtlMission> mission = etlMissionService.loadByList(positions,"position",false);
        logger.info(mission.toString());
    }

    @ApiOperation(value="根据tableName查询mission并加入到MQ中", notes="根据tableName查询mission并加入到MQ中")
    @ApiImplicitParam(name = "missionId", value = "missionId", required = true, dataType = "Long")
    @GetMapping("/createMissionByTableName")
    public void createMissionByTableName(List<String> tableNames){
        Collection<EtlMission> mission = etlMissionService.loadByList(tableNames,"tableName",false);
        logger.info(mission.toString());
    }

    @ApiOperation(value="根据position过滤mission并加入到MQ中", notes="根据position过滤mission并加入到MQ中")
    @ApiImplicitParam(name = "missionId", value = "missionId", required = true, dataType = "Long")
    @GetMapping("/createMissionExcludePositions")
    public void createMissionExcludePositions(List<String> positions){
        Collection<EtlMission> mission = etlMissionService.loadByList(positions,"position",true);
        logger.info(mission.toString());
    }

    @ApiOperation(value="根据tableName过滤mission并加入到MQ中", notes="根据tableName过滤mission并加入到MQ中")
    @ApiImplicitParam(name = "missionId", value = "missionId", required = true, dataType = "Long")
    @GetMapping("/createMissionExcludeTableName")
    public void createMissionExcludeTableName(List<String> tableNames){
        Collection<EtlMission> mission = etlMissionService.loadByList(tableNames,"tableName",true);
        logger.info(mission.toString());
    }
}
