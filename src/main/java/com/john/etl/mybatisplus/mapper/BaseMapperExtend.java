package com.john.etl.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Author: 张彦斌
 * @Date: 2018-12-26 14:11
 */
public interface BaseMapperExtend<T> extends BaseMapper<T> {

    /**
     * <p>
     * 指定id插入一条记录
     * </p>
     *
     * @param entity 实体对象
     */
    int insertWithId(T entity);
}
