package com.john.etl.official.spc.controller;


import com.john.etl.official.spc.entity.OfficialStation;
import com.john.etl.official.spc.service.OfficialStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 站点 前端控制器
 * </p>
 *
 * @author Windows 10
 * @since 2018-12-21
 */
@RestController
@RequestMapping("/spc/officialStation")
public class OfficialStationController {

    @Autowired
    OfficialStationService officialStationService;

    @GetMapping("/listAll")
    public List<OfficialStation> listAll(){
        return officialStationService.list(null);
    }
}
