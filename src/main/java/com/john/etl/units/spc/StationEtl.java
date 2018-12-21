package com.john.etl.units.spc;

import com.john.etl.mid.mission.entity.EtlMission;
import com.john.etl.mid.spc.entity.MidStation;
import com.john.etl.mid.spc.service.MidStationService;
import com.john.etl.official.spc.entity.OfficialStation;
import com.john.etl.official.spc.service.OfficialStationService;
import com.john.etl.units.EntityEtlUnit;
import com.john.etl.units.EtlUnit;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

/**
 * @Description 站点清洗器
 * @Author: Yb.Z
 * @Date: 2018/11/26.22:34
 * @Version：1.0
 */
@EtlUnit("t_spc_station")
public class StationEtl extends EntityEtlUnit {

    @Autowired
    private MidStationService midStationService;

    @Autowired
    private OfficialStationService officialStationService;

    private MidStation midStation;

    @Override
    protected boolean hasFullDataInMid(EtlMission mission) throws Exception {
        String midId = mission.getPosition() + "-" + mission.getResId();
        midStation = midStationService.getById(midId);

        if (ObjectUtils.isEmpty(midStation)) {
            mission.setNote("midStation must not be null!");
            return false;
        }
        return true;
    }

    @Override
    protected boolean hasOfficialData(EtlMission mission) throws Exception {
        OfficialStation officialStation = officialStationService.getById(mission.getResId());
        return officialStation != null;
    }

    @Override
    protected boolean insertToOfficial(EtlMission mission) throws Exception {
        OfficialStation officialStation = new OfficialStation();
        BeanUtils.copyProperties(midStation,officialStation);
        officialStationService.saveOrUpdate(officialStation);
        return true;
    }

    @Override
    protected boolean updateToOfficial(EtlMission mission) throws Exception {
        OfficialStation officialStation = officialStationService.getById(mission.getResId());
        BeanUtils.copyProperties(midStation,officialStation);
        officialStationService.saveOrUpdate(officialStation);
        return true;
    }

    @Override
    protected boolean deleteFromOfficial(EtlMission mission) throws Exception {
        officialStationService.removeById(mission.getResId());
        return true;
    }

    @Override
    protected boolean canDeleteFromOfficial(EtlMission mission) throws Exception {
        return true;
    }
}
