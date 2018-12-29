package com.john.etl.mybatisplus.metadata;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;

import java.lang.reflect.Field;
import java.util.List;

import static java.util.stream.Collectors.joining;

/**
 * @Author: 张彦斌
 * @Date: 2018-12-26 11:19
 */
public class TableInfoExtend extends TableInfo {

    /**
     * 获取 inset 时候主键 sql 脚本片段
     * insert into table (字段) values (值)
     * 位于 "值" 部位
     *
     * @return sql 脚本片段
     */
    public String getKeyInsertSqlProperty() {
        if (StringUtils.isNotEmpty(getKeyProperty())) {
            return SqlScriptUtils.safeParam(getKeyProperty()) + StringPool.COMMA + StringPool.NEWLINE;
        }
        return StringPool.EMPTY;
    }

    /**
     * 获取 inset 时候主键 sql 脚本片段
     * insert into table (字段) values (值)
     * 位于 "字段" 部位
     *
     * @return sql 脚本片段
     */
    public String getKeyInsertSqlColumn() {
        if (StringUtils.isNotEmpty(getKeyColumn())) {
            return getKeyColumn() + StringPool.COMMA + StringPool.NEWLINE;
        }
        return StringPool.EMPTY;
    }


    /**
     * 获取所有 inset 时候插入值 sql 脚本片段
     * insert into table (字段) values (值)
     * 位于 "值" 部位
     *
     * @return sql 脚本片段
     */
    public String getAllInsertSqlProperty() {
        return getKeyInsertSqlProperty() + getFieldList().stream().map(TableFieldInfo::getInsertSqlProperty)
                .collect(joining(StringPool.NEWLINE));
    }

    /**
     * 获取 inset 时候字段 sql 脚本片段
     * insert into table (字段) values (值)
     * 位于 "字段" 部位
     *
     * @return sql 脚本片段
     */
    public String getAllInsertSqlColumn() {
        return getKeyInsertSqlColumn() + getFieldList().stream().map(TableFieldInfo::getInsertSqlColumn)
                .collect(joining(StringPool.NEWLINE));
    }
}
