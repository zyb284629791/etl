package com.john.etl.official.instance.controller;


import com.john.etl.official.instance.entity.OfficialNodes;
import com.john.etl.official.instance.service.OfficialNodesService;
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
@RequestMapping("/instance/officialNodes")
public class OfficialNodesController {

    @Autowired
    private OfficialNodesService officialNodesService;

    @RequestMapping("/findById/{id}")
    public OfficialNodes findById(@PathVariable Integer id){
        return officialNodesService.getById(id);
    }
}
