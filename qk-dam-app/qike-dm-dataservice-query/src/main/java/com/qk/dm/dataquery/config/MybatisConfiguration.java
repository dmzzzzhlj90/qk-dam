package com.qk.dm.dataquery.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.datasource.entity.MysqlInfo;
import com.qk.dam.datasource.entity.ResultDatasourceInfo;
import com.qk.dam.datasource.enums.ConnTypeEnum;
import com.qk.dm.DataBaseService;
import com.qk.dm.dataquery.domain.Mapper;
import com.qk.dm.dataquery.domain.MapperSelect;
import com.qk.dm.dataquery.domain.ResultMap;
import com.qk.dm.dataquery.mybatis.DataServiceSqlSessionFactory;
import com.qk.dm.dataquery.mybatis.MybatisDatasourceManager;
import com.qk.dm.dataquery.mybatis.MybatisEnvironmentManager;
import com.qk.dm.dataquery.mybatis.MybatisMapperContainer;
import com.qk.dm.dataservice.vo.DataQueryInfoVO;
import com.qk.dm.feign.DataQueryInfoFeign;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;


/**
 * mybatis 数据查询服务配置
 *
 * @author zhudaoming
 * @since 1.5
 * @date 20220411
 */
@Configuration
@Slf4j
public class MybatisConfiguration {

    /**
     * 获取数据连接服务的数据源信息
     *
     * @param dataBaseService 数据连接服务
     * @return 多数据源管理器
     */
    @Bean
    MybatisDatasourceManager mybatisDatasourceContext(final DataBaseService dataBaseService) {
        // 对接数据源管理，且定时扫描新数据源，时间为2分钟
        MybatisDatasourceManager mybatisDatasourceManager = new MybatisDatasourceManager();
        List<ResultDatasourceInfo> resultDataSource = dataBaseService.getResultDataSourceByType(ConnTypeEnum.MYSQL.getName());

        ObjectMapper objectMapper = new ObjectMapper();

        resultDataSource.forEach(resultDatasourceInfo -> {
            try {
                MysqlInfo mysqlInfo = objectMapper.readValue(resultDatasourceInfo.getConnectBasicInfoJson(), MysqlInfo.class);
                //todo 这里还需要加查询具体对应数据库的接口
                mybatisDatasourceManager.regDatasource(ConnTypeEnum.MYSQL, resultDatasourceInfo.getDataSourceName(), mysqlInfo);
                log.info("注册mysql数据源连接:【{}】！！", resultDatasourceInfo.getDataSourceName());

            } catch (JsonProcessingException e) {
                log.error("jackson 处理异常:【{}】！！", e.getLocalizedMessage());
                e.printStackTrace();
                throw new BizException(e);
            }

        });

        return mybatisDatasourceManager;
    }

    /**
     * 多数据源管理器获取连接信息生成mybatis的环境配置
     *
     * @param mybatisDatasourceManager 数据源管理器
     * @return MybatisEnvironmentManager 环境管理器
     */
    @Bean
    MybatisEnvironmentManager mybatisEnvironment(final MybatisDatasourceManager mybatisDatasourceManager) {
        MybatisEnvironmentManager mybatisEnvironmentManager = new MybatisEnvironmentManager();

        mybatisDatasourceManager.bindDatasource((connectName, dataSource) -> {
            JdbcTransactionFactory jdbcTransactionFactory = new JdbcTransactionFactory();
            Environment environment = new Environment(connectName, jdbcTransactionFactory, dataSource);
            mybatisEnvironmentManager.registerJdbcTransactionFactory(connectName, jdbcTransactionFactory);
            mybatisEnvironmentManager.registerEnvironment(connectName, environment);
        });

        return mybatisEnvironmentManager;
    }

    /**
     * mybatis数据查询工厂
     *
     * @param mybatisEnvironmentManager 环境管理器
     * @return DataServiceSqlSessionFactory
     */
    @Bean
    DataServiceSqlSessionFactory dataServiceSqlSessionFactory(final MybatisEnvironmentManager mybatisEnvironmentManager) {
        DataServiceSqlSessionFactory dataServiceSqlSessionFactory = new DataServiceSqlSessionFactory();
        mybatisEnvironmentManager.bindEnvironment((connectName, environment) -> {
            org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
            configuration.setEnvironment(environment);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);

            dataServiceSqlSessionFactory.registerEnvironment(connectName, sqlSessionFactory);

        });

        return dataServiceSqlSessionFactory;
    }

    /**
     * mybatis mapper管理容器 绑定DataServiceSqlSessionFactory 的mybatis config
     * @param dataServiceSqlSessionFactory mybatis数据查询工厂
     * @param dataQueryInfoFeign 查询数据服务管理的配置信息
     * @return MybatisMapperContainer mapper管理容器
     */
    @Bean
    MybatisMapperContainer mybatisMapper(final DataServiceSqlSessionFactory dataServiceSqlSessionFactory, final DataQueryInfoFeign dataQueryInfoFeign) {
        MybatisMapperContainer mybatisMapperContainer = new MybatisMapperContainer();

        log.info("开始查询注册的sql数据查询！！");
        DefaultCommonResult<List<DataQueryInfoVO>> listDefaultCommonResult = dataQueryInfoFeign.dataQueryInfo();
        List<DataQueryInfoVO> dataQueryInfoVOS = listDefaultCommonResult.getData();
        log.info("查询注册的sql数据查询成功！！一共发现【{}】个sql查询", dataQueryInfoVOS.size());

        final Mapper.MapperBuilder mapperBuilder = Mapper.builder();

        dataQueryInfoVOS.stream().collect(
                        // 按照数据连接名称分组
                        Collectors.groupingBy(it -> getStrNonNull(it.getDasApiCreateSqlScript().getDataSourceName())
                        ))
                //生成mapper
                .forEach(generatedMappers(mybatisMapperContainer, mapperBuilder));

        // 扫描注册生成的mapper
        dataServiceSqlSessionFactory.scanMappers(mybatisMapperContainer);

        return mybatisMapperContainer;
    }

    /**
     * 生成mapper
     *
     * @param mybatisMapperContainer mapper容器
     * @param mapperBuilder          生成mapper的构造器
     * @return BiConsumer 回调函数
     */
    private BiConsumer<String, List<DataQueryInfoVO>> generatedMappers(MybatisMapperContainer mybatisMapperContainer, Mapper.MapperBuilder mapperBuilder) {
        return (namespace, dataQueryInfoVOList) -> {
            // dsName 数据源连接名称获取mybatis的session factory
            Mapper.MapperBuilder mapperBuilderTemp = mapperBuilder.namespace(namespace);

            // 相同数据源生产mapper select 查询
            List<MapperSelect> mapperSelects = dataQueryInfoVOList.stream().map(dataQueryInfoVO ->
                    MapperSelect.builder()
                            .id(dataQueryInfoVO.getDasApiBasicInfo().getApiId())
                            .resultType("java.util.HashMap")
                            .sqlContent(dataQueryInfoVO.getDasApiCreateSqlScript().getSqlPara()).build())
                    .collect(Collectors.toList());
            dataQueryInfoVOList.forEach(dataQueryInfoVO -> {
                mybatisMapperContainer.addApiIdDbNameMap(
                        dataQueryInfoVO.getDasApiCreateSqlScript().getApiId(),
                        dataQueryInfoVO.getDasApiCreateSqlScript().getDataBaseName());
            });
            // 相同数据连接配置mapper
            mapperBuilderTemp.resultMap(List.of(
                    ResultMap.builder()
                            .autoMapping(true)
                            .id(namespace+":"+"rt")
                            .type("HashMap")
                            .result(List.of(
                                    //todo 此处为页面配置持久化关系映射
                                    new ResultMap.Result("id", "id", "Integer")
                            )).build()
            ));
            mapperBuilderTemp.select(mapperSelects);
            Mapper mapper = mapperBuilderTemp.build();

            mybatisMapperContainer.addMapper(namespace, mapper);

        };
    }

    String getStrNonNull(String n){
       return Optional.ofNullable(n).orElse("");
    }

}