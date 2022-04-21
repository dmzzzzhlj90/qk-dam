package com.qk.dm.dataquery.controller;

import com.qk.dm.dataquery.mybatis.DataServiceSqlSessionFactory;
import com.qk.dm.dataquery.mybatis.MybatisDatasourceManager;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 数据查询服务
 *
 * @author zhudaoming
 * @since 1.5.0
 * @date 20220419
 */
@RestController
public class QueryDataRestController {
    final DataServiceSqlSessionFactory dataServiceSqlSessionFactory;
    final MybatisDatasourceManager mybatisDatasourceManager;

    public QueryDataRestController(DataServiceSqlSessionFactory dataServiceSqlSessionFactory, MybatisDatasourceManager mybatisDatasourceManager) {
        this.dataServiceSqlSessionFactory = dataServiceSqlSessionFactory;
        this.mybatisDatasourceManager = mybatisDatasourceManager;
    }

    @GetMapping("/app/query/{apiId}")
    public List<Object> query(
                        @PathVariable String apiId){
        String dsName = mybatisDatasourceManager.getDsName(apiId);
        SqlSessionFactory sqlSessionFactory = dataServiceSqlSessionFactory.getSqlSessionFactory(dsName);

        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Object> objects = sqlSession.selectList(apiId);

        sqlSession.close();
        return objects;
    }

}
