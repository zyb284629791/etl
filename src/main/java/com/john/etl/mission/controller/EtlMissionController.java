package com.john.etl.mission.controller;


import com.john.etl.mission.entity.EtlMission;
import com.john.etl.mission.service.IEtlMissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 清洗任务表 前端控制器
 * </p>
 *
 * @author Windows 10
 * @since 2018-11-26
 */
@RestController
@RequestMapping("/mission/etl-mission")
public class EtlMissionController {

    @Autowired
    private IEtlMissionService etlMissionService;

    @RequestMapping("/listAll")
    public List<EtlMission> listAll(){
        return etlMissionService.list(null);
    }
}
