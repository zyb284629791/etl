package com.john.etl.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author: 张彦斌
 * @Date: 2018-11-26 10:59
 */
@Configuration
@MapperScan("com.john.etl.*.mapper")
@EnableTransactionManagement
public class MybatisPlusConfiguration {


    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
