package com.john.etl.mybatisplus.service;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;

/**
 * @Author: 张彦斌
 * @Date: 2018-12-26 14:17
 */
public interface IServiceExtend<T> extends IService<T> {

    /**
     * <p>
     * 插入一条记录（选择字段，策略插入）
     * </p>
     *
     * @param entity 实体对象
     */
    boolean saveWithId(T entity);

    /**
     * <p>
     * 插入（批量），该方法不适合 Oracle
     * </p>
     *
     * @param entityList 实体对象集合
     */
    boolean saveBatchWithId(Collection<T> entityList);

    /**
     * <p>
     * 插入（批量）
     * </p>
     *
     * @param entityList 实体对象集合
     * @param batchSize  插入批次数量
     */
    boolean saveBatchWithId(Collection<T> entityList, int batchSize);
}
