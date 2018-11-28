package com.john.etl.mid.instance.entity;

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
 * 实体Node表
 * </p>
 *
 * @author Mac OS X
 * @since 2018-11-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_int_nodes")
public class MidNodes implements Serializable {

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
     * 实体名称
     */
    private String name;

    /**
     * 实体编码
     */
    private String code;

    /**
     * 正副本标识
     */
    private Boolean resowner;

    /**
     * 唯一标识
     */
    private String mark;

    /**
     * 位置标识
     */
    private String position;

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
