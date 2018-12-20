package com.john.etl;

import com.john.etl.mid.mission.entity.EtlMission;
import com.john.etl.mid.mission.service.IEtlMissionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collection;

/**
 * @Author: 张彦斌
 * @Date: 2018-12-19 9:27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisPlusTest {

    @Autowired
    IEtlMissionService missionService;

    @Test
    public void test(){
        EtlMission byId = missionService.getById(1);
        missionService.etlSuccess(byId);
        System.out.println(byId);
    }

    @Test
    public void testdynamic(){
        Collection<EtlMission> missions = missionService.loadByList(Arrays.asList("t_spc_station"),"tableName",true);
        missions.stream().forEach(System.out::println);
    }
}
