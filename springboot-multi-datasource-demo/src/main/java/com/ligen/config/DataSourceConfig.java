package com.ligen.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * Created by ligen on 2018/5/9.
 */
@Component
public class DataSourceConfig {

    @Bean(name = "prodDb")
    @ConfigurationProperties(prefix = "spring.datasource.prod") // application.properteis中对应属性的前缀
    public DataSource dataSourceProd() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "testDb")
    @ConfigurationProperties(prefix = "spring.datasource.test") // application.properteis中对应属性的前缀
    public DataSource dataSourceTest() {
        return DataSourceBuilder.create().build();
    }

}
