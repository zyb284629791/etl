package com.john.etl.config;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.john.etl.mybatisplus.injector.InsertWithIdInjector;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @Author: 张彦斌
 * @Date: 2018-11-26 10:59
 */
@Configuration
@EnableTransactionManagement
public class MybatisPlusConfiguration {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    public static SqlSessionFactory createSqlSessionFactory(DataSource dataSource,
                                                            PaginationInterceptor paginationInterceptor,
                                                            String mapper_location)
            throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setTypeEnumsPackage("com.john.etl.enums");
        sqlSessionFactory.setTypeAliasesPackage("com.john.etl.*.*.entity");
        sqlSessionFactory.setDataSource(dataSource);
        MybatisConfiguration configuration = new MybatisConfiguration();
        GlobalConfig globalConfig = GlobalConfigUtils.getGlobalConfig(configuration.toString());
        globalConfig.setRefresh(true);
        InsertWithIdInjector insertWithIdInjector = new InsertWithIdInjector();
        globalConfig.setSqlInjector(insertWithIdInjector);
        GlobalConfigUtils.setGlobalConfig(configuration,globalConfig);
        // 注意，此处必须要通过这种方法将global赋给sqlSessionFactory，否则自定义的配置无法生效!
        sqlSessionFactory.setGlobalConfig(globalConfig);
        configuration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        sqlSessionFactory.setConfiguration(configuration);
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(mapper_location));
        sqlSessionFactory.setPlugins(new Interceptor[]{
                paginationInterceptor
        });
        return sqlSessionFactory.getObject();
    }
}
