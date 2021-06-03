package com.qk.mybatisplus.conf;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class MapperScanner {

    @Bean
    @ConditionalOnMissingBean(MapperScannerConfigurer.class)
    public MapperScannerConfigurer mapperScannerConfigurer(){
        var mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.qk.**.mapper");
        return mapperScannerConfigurer;
    }
    @Bean
    @ConditionalOnMissingBean(MybatisSqlSessionFactoryBean.class)
    public MybatisSqlSessionFactoryBean sqlSessionFactory(DataSource mysqlDataSource){
        var mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        mybatisSqlSessionFactoryBean.setDataSource(mysqlDataSource);
        mybatisSqlSessionFactoryBean.setDatabaseIdProvider(new VendorDatabaseIdProvider());
        return mybatisSqlSessionFactoryBean;
    }
}
