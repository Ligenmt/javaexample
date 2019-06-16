package com.ligen.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Created by ligen on 2018/5/9.
 */
@Configuration
@MapperScan(basePackages = {"com.ligen.mapper.prod"}, sqlSessionFactoryRef = "sqlSessionFactoryProd")
public class MybatisDbProdConfig {

    @Autowired
    @Qualifier("prodDb")
    private DataSource ds1;


    @Bean
    public SqlSessionFactory sqlSessionFactoryProd() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(ds1); // 使用prod数据源, 连接prod库
        return factoryBean.getObject();

    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplateProd() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactoryProd()); // 使用上面配置的Factory
        return template;
    }
}
