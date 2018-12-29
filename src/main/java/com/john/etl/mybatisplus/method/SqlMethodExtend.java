package com.john.etl.mybatisplus.method;

/**
 * @Author: 张彦斌
 * @Date: 2018-12-26 16:26
 */
public enum  SqlMethodExtend{

    /**
     * 插入一条指定主键的数据
     */
    INSERT_ONE_WITH_ID("insertWithId", "插入一条数据（选择字段插入）", "<script>\nINSERT INTO %s %s VALUES %s\n</script>");

    private final String method;
    private final String desc;
    private final String sql;

    SqlMethodExtend(String method, String desc, String sql) {
        this.method = method;
        this.desc = desc;
        this.sql = sql;
    }

    public String getMethod() {
        return method;
    }

    public String getDesc() {
        return desc;
    }

    public String getSql() {
        return sql;
    }

}
