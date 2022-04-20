package com.qk.dm.dataquery.config;

import com.qk.dm.DataBaseService;
import com.qk.dm.dataquery.mybatis.DataServiceSqlSessionFactory;
import com.qk.dm.dataquery.mybatis.MybatisDatasource;
import com.qk.dm.dataquery.mybatis.MybatisEnvironment;
import com.qk.dm.dataquery.mybatis.MybatisMapper;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


/**
 * @author zhudaoming
 */
@Configuration
public class MybatisConfiguration {

    @Bean
    MybatisDatasource mybatisDatasourceContext(final DataBaseService dataBaseService){
        // 对接数据源管理，且定时扫描新数据源，时间为2分钟
        List<String> allConnType = dataBaseService.getAllConnType();

        List<String> allDataSource = dataBaseService.getAllDataSource("");

        return new MybatisDatasource();
    }

    @Bean
    MybatisEnvironment mybatisEnvironment(final MybatisDatasource mybatisDatasource){
        MybatisEnvironment mybatisEnvironment = new MybatisEnvironment();

        mybatisDatasource.bindDatasource((connectName, dataSource)-> {
            JdbcTransactionFactory jdbcTransactionFactory = new JdbcTransactionFactory();
            mybatisEnvironment.registerJdbcTransactionFactory(connectName,jdbcTransactionFactory);
            mybatisEnvironment.registerEnvironment(connectName,new Environment(
                    connectName,
                    jdbcTransactionFactory,
                    dataSource
            ));
        });

        return mybatisEnvironment;
    }

    @Bean
    DataServiceSqlSessionFactory dataServiceSqlSessionFactory(final MybatisEnvironment mybatisEnvironment){
        DataServiceSqlSessionFactory dataServiceSqlSessionFactory = new DataServiceSqlSessionFactory();
        mybatisEnvironment.bindEnvironment((connectName, environment)-> {
            org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
            configuration.setEnvironment(environment);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);

            dataServiceSqlSessionFactory.registerEnvironment(connectName, sqlSessionFactory);

        });

        return dataServiceSqlSessionFactory;
    }

    @Bean
    MybatisMapper mybatisMapper(){
        return new MybatisMapper();
    }




}
