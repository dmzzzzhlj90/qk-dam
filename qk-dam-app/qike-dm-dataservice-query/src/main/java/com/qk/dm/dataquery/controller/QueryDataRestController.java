package com.qk.dm.dataquery.controller;

import com.qk.dm.dataquery.domain.Mapper;
import com.qk.dm.dataquery.mybatis.DataServiceSqlSessionFactory;
import com.qk.dm.dataquery.mybatis.MybatisDatasourceManager;
import com.qk.dm.dataquery.mybatis.MybatisMapperContainer;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.List;

@RestController
public class QueryDataRestController {
    final DataServiceSqlSessionFactory dataServiceSqlSessionFactory;
    final MybatisDatasourceManager mybatisDatasourceManager;
    final MybatisMapperContainer mybatisMapperContainer;

    public QueryDataRestController(DataServiceSqlSessionFactory dataServiceSqlSessionFactory, MybatisDatasourceManager mybatisDatasourceManager, MybatisMapperContainer mybatisMapperContainer) {
        this.dataServiceSqlSessionFactory = dataServiceSqlSessionFactory;
        this.mybatisDatasourceManager = mybatisDatasourceManager;
        this.mybatisMapperContainer = mybatisMapperContainer;
    }

    @GetMapping("/app/query/{dsName}/{id}")
    public List<Object> query(
                        @PathVariable String dsName,
                        @PathVariable String id){
        SqlSessionFactory sqlSessionFactory = dataServiceSqlSessionFactory.getSqlSessionFactory(dsName);

        String dbname = mybatisMapperContainer.getDbName(id);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        List<Object> objects = sqlSession.selectList(id);
        sqlSession.close();
        return objects;
    }

}
