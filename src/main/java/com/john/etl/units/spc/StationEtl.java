package com.john.etl.units.spc;

import com.john.etl.mid.mission.entity.EtlMission;
import com.john.etl.units.EntityEtlUnit;
import com.john.etl.units.EtlUnit;

/**
 * @Description 站点清洗器
 * @Author: Yb.Z
 * @Date: 2018/11/26.22:34
 * @Version：1.0
 */
@EtlUnit("t_spc_station")
public class StationEtl extends EntityEtlUnit {


    @Override
    protected boolean hasFullDataInMid(EtlMission mission) throws Exception {
        return false;
    }

    @Override
    protected boolean hasOfficialData(EtlMission mission) throws Exception {
        return false;
    }

    @Override
    protected boolean insertToOfficial(EtlMission mission) throws Exception {
        return false;
    }

    @Override
    protected boolean updateToOfficial(EtlMission mission) throws Exception {
        return false;
    }

    @Override
    protected boolean deleteFromOfficial(EtlMission mission) throws Exception {
        return false;
    }

    @Override
    protected boolean canDeleteFromOfficial(EtlMission mission) throws Exception {
        return false;
    }
}
