package com.john.etl.mybatisplus.method;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import com.john.etl.mybatisplus.metadata.TableInfoExtend;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.springframework.beans.BeanUtils;


/**
 * @Author: 张彦斌
 * @Date: 2018-12-26 11:04
 */
public class InsertWithId extends AbstractMethod {


    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        KeyGenerator keyGenerator = new NoKeyGenerator();
        SqlMethodExtend sqlMethodExtend = SqlMethodExtend.INSERT_ONE_WITH_ID;
        TableInfoExtend tableInfoExtend = new TableInfoExtend();
        BeanUtils.copyProperties(tableInfo,tableInfoExtend);
        String columnScript = SqlScriptUtils.convertTrim(tableInfoExtend.getAllInsertSqlColumn(),
                StringPool.LEFT_BRACKET, StringPool.RIGHT_BRACKET, null, StringPool.COMMA);
        String valuesScript = SqlScriptUtils.convertTrim(tableInfoExtend.getAllInsertSqlProperty(),
                StringPool.LEFT_BRACKET, StringPool.RIGHT_BRACKET, null, StringPool.COMMA);
        String keyProperty = null;
        String keyColumn = null;
        String sql = String.format(sqlMethodExtend.getSql(), tableInfo.getTableName(), columnScript, valuesScript);
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        return this.addInsertMappedStatement(mapperClass, modelClass, sqlMethodExtend.getMethod(), sqlSource, keyGenerator, keyProperty, keyColumn);
    }
}
