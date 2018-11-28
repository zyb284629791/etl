package com.john.etl.mid.instance.controller;


import com.john.etl.mid.instance.entity.MidNodes;
import com.john.etl.mid.instance.service.MidNodesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 实体Node表 前端控制器
 * </p>
 *
 * @author Mac OS X
 * @since 2018-11-27
 */
@RestController
@RequestMapping("/instance/midNodes")
public class MidNodesController {

    @Autowired
    private MidNodesService midNodesService;

    @RequestMapping("/findById/{midId}")
    public MidNodes findById(@PathVariable String midId){
        return midNodesService.getById(midId);
    }
}
