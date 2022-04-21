package com.qk.dm.dataquery.controller;

import com.qk.dam.model.HttpDataParamModel;
import com.qk.dm.dataquery.mybatis.DataServiceSqlSessionFactory;
import com.qk.dm.dataquery.mybatis.MybatisDatasourceManager;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 数据查询服务
 *
 * @author zhudaoming
 * @date 20220419
 * @since 1.5.0
 */
@RestController
public class QueryDataRestController {
    final DataServiceSqlSessionFactory dataServiceSqlSessionFactory;
    final MybatisDatasourceManager mybatisDatasourceManager;

    public QueryDataRestController(DataServiceSqlSessionFactory dataServiceSqlSessionFactory, MybatisDatasourceManager mybatisDatasourceManager) {
        this.dataServiceSqlSessionFactory = dataServiceSqlSessionFactory;
        this.mybatisDatasourceManager = mybatisDatasourceManager;
    }

    @PostMapping("/app/query")
    public List<Object> query( @RequestBody HttpDataParamModel httpDataParamModel) {
        String apiId = httpDataParamModel.getApiId();
        String dsName = mybatisDatasourceManager.getDsName(apiId);
        SqlSessionFactory sqlSessionFactory = dataServiceSqlSessionFactory.getSqlSessionFactory(dsName);

        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Object> objects = sqlSession.selectList(apiId,httpDataParamModel);

        sqlSession.close();
        return objects;
    }

}
