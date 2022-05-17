package com.qk.dm.dataquery.controller;

import com.qk.dam.cache.CacheManagerEnum;
import com.qk.dam.cache.QueryCache;
import com.qk.dam.model.HttpDataParamModel;
import com.qk.dm.dataquery.mybatis.DataServiceSqlSessionFactory;
import com.qk.dm.dataquery.mybatis.MybatisMapperContainer;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.RowBounds;
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
@RequiredArgsConstructor
public class QueryDataRestController {
    final DataServiceSqlSessionFactory dataServiceSqlSessionFactory;
    final MybatisMapperContainer mybatisMapperContainer;

    @PostMapping("/app/query")
    @QueryCache(value = CacheManagerEnum.CACHE_NAME_DAS_QUERY_LIST, unless = "#httpDataParamModel.cacheLevel != 2")
    public List<Object> query(@RequestBody HttpDataParamModel httpDataParamModel) {
        String apiId = httpDataParamModel.getApiId();
        String dsName = mybatisMapperContainer.getDsName(apiId);

        SqlSession sqlSession = getSqlSession(dsName);

        List<Object> objects;
        // 是否需要分页
        if (httpDataParamModel.isPageFlag()) {
            objects = sqlSession.selectList(apiId, getMybatisDataParam(httpDataParamModel),
                    new RowBounds(
                            httpDataParamModel.getPagination().getPage() - 1, httpDataParamModel.getPagination().getSize()));
        } else {
            objects = sqlSession.selectList(apiId, getMybatisDataParam(httpDataParamModel));
        }
        return objects;
    }

    @PostMapping("/app/query/one")
    @QueryCache(value = CacheManagerEnum.CACHE_NAME_DAS_QUERY_ONE)
    public Object queryOne(@RequestBody HttpDataParamModel httpDataParamModel) {
        String apiId = httpDataParamModel.getApiId();
        String dsName = mybatisMapperContainer.getDsName(apiId);

        SqlSession sqlSession = getSqlSession(dsName);

        Object objects = sqlSession.selectOne(apiId, getMybatisDataParam(httpDataParamModel));

        return objects;
    }

    private SqlSession getSqlSession(String dsName) {
        SqlSessionFactory sqlSessionFactory = dataServiceSqlSessionFactory.getSqlSessionFactory(dsName);
        Objects.requireNonNull(sqlSessionFactory, "未能获取数据源连接,请检查是否配置数据源！");
        return sqlSessionFactory.openSession();
    }

    private Map<String, Object> getMybatisDataParam(HttpDataParamModel httpDataParamModel) {
        Map<String, Object> mybatisDataParam = new HashMap<>();
        Object body = httpDataParamModel.getBody();
        if (Objects.nonNull(httpDataParamModel.getParams())) {
            mybatisDataParam.putAll(httpDataParamModel.getParams());
        }
        if (Objects.nonNull(httpDataParamModel.getUriPathParam())) {
            mybatisDataParam.putAll(httpDataParamModel.getUriPathParam());
        }

        if (Objects.nonNull(body)) {
            if (body instanceof Map) {
                mybatisDataParam.putAll((Map) body);
            }
            if (body instanceof Collection || body.getClass().isArray()) {
                mybatisDataParam.put("body", body);
            }
        }
        return mybatisDataParam;
    }
}
