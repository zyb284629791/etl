package com.john.etl.mission.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 清洗任务表
 * </p>
 *
 * @author Mac OS X
 * @since 2018-11-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_etl_mission")
public class EtlMission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 资源ID
     */
    private Long resId;

    /**
     * 资源表名称
     */
    private String tableName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 删除标识， 0: 未删除 1: 已删除
     */
    @TableLogic
    private Integer deleteFlag;

    /**
     * 清洗类型，1: insert 2: update 3: delete
     */
    private Integer operType;

    /**
     * 清洗状态，0:成功 1:忽略 2: 中间库完整性校验失败 3: 正式库校验失败 4: 插入异常 5: 更新异常 6: 删除异常
     */
    private Integer operStatus;

    /**
     * 清洗次数
     */
    private Long operTimes;

    /**
     * 异常说明
     */
    private String note;

    /**
     * 资源所属地区标识
     */
    private String position;


}
