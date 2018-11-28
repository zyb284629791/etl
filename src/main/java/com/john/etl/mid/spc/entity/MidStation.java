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
 * 站点表
 * </p>
 *
 * @author Mac OS X
 * @since 2018-11-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_spc_station")
public class MidStation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 中间库ID
     */
    @TableId(value = "mid_id", type = IdType.ID_WORKER_STR)
    private String midId;

    /**
     * 站点地址
     */
    private String address;

    /**
     * node表id
     */
    private Long nodeId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 删除标识
     */
    @TableLogic
    private Boolean deleteFlag;


}
