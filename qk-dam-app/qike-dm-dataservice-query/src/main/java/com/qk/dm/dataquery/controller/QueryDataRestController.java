package com.qk.dm.dataquery.controller;

import com.qk.dam.model.HttpDataParamModel;
import com.qk.dm.dataquery.mybatis.DataServiceSqlSessionFactory;
import com.qk.dm.dataquery.mybatis.MybatisDatasourceManager;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

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

        Map<String, Object> mybatisDataParam = getMybatisDataParam(httpDataParamModel);

        List<Object> objects = sqlSession.selectList(apiId,mybatisDataParam);

        sqlSession.close();
        return objects;
    }

    private Map<String, Object> getMybatisDataParam(HttpDataParamModel httpDataParamModel) {
        Map<String,Object> mybatisDataParam = new HashMap<>();
        Object body = httpDataParamModel.getBody();
        if (Objects.nonNull(httpDataParamModel.getParams())){
            mybatisDataParam.putAll(httpDataParamModel.getParams());
        }
        if (Objects.nonNull(httpDataParamModel.getUriPathParam())){
            mybatisDataParam.putAll(httpDataParamModel.getUriPathParam());
        }

        if (Objects.nonNull(body)){
            if (body instanceof Map){
                mybatisDataParam.putAll((Map)body);
            }
            if (body instanceof Collection||
                    body.getClass().isArray()){
                mybatisDataParam.put("body",body);
            }
        }
        return mybatisDataParam;
    }

}
