package com.john.etl.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @Description 中间库数据源配置
 * @Author: Yb.Z
 * @Date: 2018/11/27.20:20
 * @Version：1.0
 */
@Configuration
@MapperScan(basePackages = "com.john.etl.mid.*.mapper",sqlSessionFactoryRef = "midSqlSessionFactory")
public class MidDataSourceConfiguration {

    private String mapper_location = "classpath:/mapper/mid/**/*.xml";

    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource.druid.mid")
    public DataSource midDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    public DataSourceTransactionManager midTransactionManager() {
        return new DataSourceTransactionManager(midDataSource());
    }

    @Bean
    @Primary
    public SqlSessionFactory midSqlSessionFactory(@Qualifier("midDataSource") DataSource midDataSource,
                                                  @Qualifier("paginationInterceptor") PaginationInterceptor paginationInterceptor)
            throws Exception {
        return MybatisPlusConfiguration.createSqlSessionFactory(midDataSource, paginationInterceptor, mapper_location);
    }
}
