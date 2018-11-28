package com.john.etl.official.instance.service.impl;

import com.john.etl.official.instance.entity.OfficialNodes;
import com.john.etl.official.instance.mapper.OfficialNodesMapper;
import com.john.etl.official.instance.service.OfficialNodesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 实体Node表 服务实现类
 * </p>
 *
 * @author Mac OS X
 * @since 2018-11-27
 */
@Service
public class OfficialNodesServiceImpl extends ServiceImpl<OfficialNodesMapper, OfficialNodes> implements OfficialNodesService {

    @Resource
    private OfficialNodesMapper officialNodesMapper;

}
