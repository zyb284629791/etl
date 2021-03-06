package com.john.etl.units;

import com.john.etl.enums.EtlOperStatus;
import com.john.etl.mid.mission.entity.EtlMission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据清洗器抽象类
 * Created by wangping on 2016/10/10.
 */
public abstract class EntityEtlUnit {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 执行清洗
     *
     * @param mission
     * @return 清洗是否成功
     */
    public boolean doEtl(EtlMission mission) throws Exception {
        if (mission.getOperType() == 3){//删除操作时
            if (canDeleteFromOfficial(mission)){//能否从正式库删除
                //将status设置成为实际的操作类型，方便后续排查bug时定位
                boolean deleteFromOfficial = deleteFromOfficial(mission);
                if (!deleteFromOfficial) {
                    mission.setOperStatus(EtlOperStatus.DeleteFail);
                }
                return deleteFromOfficial;//从正式库删除
            }else{
                mission.setOperStatus(EtlOperStatus.DeleteValidationFail);
                logger.info("--------清洗任务不成功，canDeleteFromOfficial方法校验未通过。\n" + mission);
            }
        }else if (mission.getOperType() == 1 || mission.getOperType() == 2){//新增或修改操作
            if (hasFullDataInMid(mission)){//在中间库具有完整数据时
                if (hasOfficialData(mission)){
                    boolean result = updateToOfficial(mission);
                    if (!result){
                        logger.info("--------清洗任务不成功，updateToOfficial方法执行失败。\n" + mission);
                        //将status设置成为实际的操作类型，方便后续排查bug时定位
                        mission.setOperStatus(EtlOperStatus.UpdateFail);
                    }
                    return result;
                }else{
                    boolean result = insertToOfficial(mission);
                    if (!result){
                        logger.info("--------清洗任务不成功，insertToOfficial方法执行失败。\n" + mission);
                        //将status设置成为实际的操作类型，方便后续排查bug时定位
                        mission.setOperStatus(EtlOperStatus.InsertFail);
                    }
                    return result;
                }
            }else{
                logger.info("--------清洗任务不成功，hasFullDataInMid方法校验未通过。\n" + mission);
                mission.setOperStatus(EtlOperStatus.MidValidationFail);
            }
        }else{
            throw new RuntimeException("数据清洗任务出现了无法识别的操作类型。mission id = " );
        }


        return false;
    }

    /**
     * 在中间库是否存在完整的数据
     * @param mission
     * @return
     */
    protected abstract boolean hasFullDataInMid(EtlMission mission) throws Exception;

    /**
     * 在正式库是否存在数据
     * @param mission
     * @return
     */
    protected abstract boolean hasOfficialData(EtlMission mission) throws Exception;;

    /**
     * 插入数据到正式库
     * @param mission
     * @return
     */
    protected abstract boolean insertToOfficial(EtlMission mission) throws Exception;;

    /**
     * 修改正式库数据
     * @param mission
     * @return
     */
    protected abstract boolean updateToOfficial(EtlMission mission)throws Exception;;

    /**
     * 删除正式库数据
     * @param mission
     * @return
     */
    protected abstract boolean deleteFromOfficial(EtlMission mission)throws Exception;;

    /**
     * 能否从正式库删除数据
     * @param mission
     * @return
     */
    protected abstract boolean canDeleteFromOfficial(EtlMission mission)throws Exception;

    protected boolean ignoreMission(EtlMission mission) throws Exception{
        return false;
    }

}
