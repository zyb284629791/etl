package com.john.etl.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @Description 正式库数据源配置
 * @Author: Yb.Z
 * @Date: 2018/11/27.20:49
 * @Version：1.0
 */
@Configuration
@MapperScan(basePackages = "com.john.etl.official.**.mapper",sqlSessionFactoryRef = "officialSqlSessionFactory")
public class OfficialDataSourceConfiguration {

    private String mapper_location = "classpath:/mapper/official/*/*.xml";

    @Bean
    @ConfigurationProperties("spring.datasource.druid.official")
    public DataSource officialDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    public DataSourceTransactionManager officialTransactionManager() {
        return new DataSourceTransactionManager(officialDataSource());
    }

    @Bean
    public SqlSessionFactory officialSqlSessionFactory(@Qualifier("officialDataSource") DataSource officialDataSource,
                                                       @Qualifier("paginationInterceptor") PaginationInterceptor paginationInterceptor)
            throws Exception {
        return MybatisPlusConfiguration.createSqlSessionFactory(officialDataSource, paginationInterceptor, mapper_location);
    }
}
