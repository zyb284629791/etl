package com.john.etl.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlHelper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.john.etl.mybatisplus.mapper.BaseMapperExtend;
import com.john.etl.mybatisplus.method.SqlMethodExtend;
import com.john.etl.mybatisplus.service.IServiceExtend;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * @Author: 张彦斌
 * @Date: 2018-12-26 14:19
 */
public class ServiceExtendImpl<M extends BaseMapperExtend<T>,T> extends ServiceImpl<M,T> implements IServiceExtend<T> {

    @Autowired
    protected M baseMapperExtend;

    @Override
    public boolean saveWithId(T entity) {
        return ServiceImpl.retBool(baseMapperExtend.insertWithId(entity));
    }

    @Override
    public boolean saveBatchWithId(Collection<T> entityList) {
        return this.saveBatchWithId(entityList, 30);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveBatchWithId(Collection<T> entityList, int batchSize) {
        if (CollectionUtils.isEmpty(entityList)) {
            throw new IllegalArgumentException("Error: entityList must not be empty");
        }
        try (SqlSession batchSqlSession = sqlSessionBatch()) {
            int i = 0;
            String sqlStatement = sqlStatement(SqlMethodExtend.INSERT_ONE_WITH_ID);
            for (T anEntityList : entityList) {
                batchSqlSession.insert(sqlStatement, anEntityList);
                if (i >= 1 && i % batchSize == 0) {
                    batchSqlSession.flushStatements();
                }
                i++;
            }
            batchSqlSession.flushStatements();
        } catch (Throwable e) {
            throw ExceptionUtils.mpe("Error: Cannot execute saveBatch Method. Cause", e);
        }
        return true;
    }

    /**
     * 覆盖原生的方法，获取自定义的sqlMethod对应的sql
     * @param sqlMethodExtend
     * @return
     */
    protected String sqlStatement(SqlMethodExtend sqlMethodExtend) {

        return SqlHelper.table(currentModelClass()).getSqlStatement(sqlMethodExtend.getMethod());
    }
}
