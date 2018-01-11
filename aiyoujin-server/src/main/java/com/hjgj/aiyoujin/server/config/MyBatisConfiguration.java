package com.hjgj.aiyoujin.server.config;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@MapperScan({"com.hjgj.aiyoujin.core.dao"})
public class MyBatisConfiguration {
	
	@Autowired
	private DataSource dataSource;
	
	@Bean
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(this.dataSource);
    }
	
	@Bean
    public SqlSessionTemplate sqlSessionTemplateBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(this.dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:com/hjgj/aiyoujin/core/dao/*Mapper.xml"));
        sqlSessionFactoryBean.setConfigLocation(resolver.getResource("classpath:com/mybatis-config.xml"));
        return new SqlSessionTemplate(sqlSessionFactoryBean.getObject());
    }
	
}
