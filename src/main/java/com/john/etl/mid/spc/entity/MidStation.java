package com.john.etl.mid.spc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 站点
 * </p>
 *
 * @author Windows 10
 * @since 2018-12-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_spc_station")
public class MidStation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * cs库Id
     */
    private Long id;

    /**
     * 中间库Id
     */
    @TableId(value = "mid_id", type = IdType.ID_WORKER_STR)
    private String midId;

    /**
     * 站点地址
     */
    private String address;

    /**
     * NodeId
     */
    private Long nodeId;

    /**
     * 地区标识
     */
    private String position;

    private LocalDateTime createTime;

    @TableLogic
    private Integer deleteFlag;


}
