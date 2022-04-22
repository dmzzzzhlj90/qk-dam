package com.qk.dm.dataquery.config;

import com.google.common.collect.Lists;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.datasource.entity.ResultDatasourceInfo;
import com.qk.dam.datasource.enums.ConnTypeEnum;
import com.qk.dm.DataBaseService;
import com.qk.dm.dataquery.mybatis.DataServiceSqlSessionFactory;
import com.qk.dm.dataquery.mybatis.MybatisDatasourceManager;
import com.qk.dm.dataquery.mybatis.MybatisEnvironmentManager;
import com.qk.dm.dataquery.mybatis.MybatisMapperContainer;
import com.qk.dm.dataservice.vo.DataQueryInfoVO;
import com.qk.dm.feign.DataQueryInfoFeign;
import com.zaxxer.hikari.HikariConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

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
  private final List<DataQueryInfoVO> dataQueryInfos;

  public MybatisConfiguration(List<DataQueryInfoVO> dataQueryInfos) {
    this.dataQueryInfos = dataQueryInfos;
  }

  /**
   * 获取数据连接服务的数据源信息
   *
   * @return 多数据源管理器
   */
  @Bean
  MybatisDatasourceManager mybatisDatasourceManager(
      final DataQueryInfoFeign dataQueryInfoFeign, final DataBaseService dataBaseService) {
    log.info("开始查询注册的sql数据查询！！");
    DefaultCommonResult<List<DataQueryInfoVO>> listDefaultCommonResult =
        dataQueryInfoFeign.dataQueryInfo();
    dataQueryInfos.addAll(
        Optional.ofNullable(listDefaultCommonResult.getData()).orElse(Lists.newArrayList()));
    log.info("查询注册的sql数据查询成功！！一共发现【{}】个sql查询", dataQueryInfos.size());

    log.info("查询数据源服务获取数据源列表-mysql数据源");
    List<ResultDatasourceInfo> resultDataSource =
        dataBaseService.getResultDataSourceByType(ConnTypeEnum.MYSQL.getName());

    HikariConfig hikariConfigDefault = hikariConfigDefault();

    // 对接数据源管理，且定时扫描新数据源，时间为2分钟
    MybatisDatasourceManager mybatisDatasourceManager =
        new MybatisDatasourceManager(
            ConnTypeEnum.MYSQL, hikariConfigDefault, resultDataSource);

    mybatisDatasourceManager.initDataSource(dataQueryInfos);

    return mybatisDatasourceManager;
  }

  /**
   * 多数据源管理器获取连接信息生成mybatis的环境配置
   *
   * @param mybatisDatasourceManager 数据源管理器
   * @return MybatisEnvironmentManager 环境管理器
   */
  @Bean
  MybatisEnvironmentManager mybatisEnvironmentManager(
      final MybatisDatasourceManager mybatisDatasourceManager) {
    MybatisEnvironmentManager mybatisEnvironmentManager = new MybatisEnvironmentManager();

    mybatisDatasourceManager.bindDatasource(mybatisEnvironmentManager::registerEnvironment);

    return mybatisEnvironmentManager;
  }

  /**
   * mybatis数据查询工厂
   *
   * @param mybatisEnvironmentManager 环境管理器
   * @return DataServiceSqlSessionFactory
   */
  @Bean
  DataServiceSqlSessionFactory dataServiceSqlSessionFactory(
      final MybatisEnvironmentManager mybatisEnvironmentManager) {
    DataServiceSqlSessionFactory dataServiceSqlSessionFactory = new DataServiceSqlSessionFactory();
    mybatisEnvironmentManager.bindEnvironment(dataServiceSqlSessionFactory::registerEnvironment);

    return dataServiceSqlSessionFactory;
  }

  /**
   * mybatis mapper管理容器 绑定DataServiceSqlSessionFactory 的mybatis config
   *
   * @param dataServiceSqlSessionFactory mybatis数据查询工厂
   * @param mybatisDatasourceManager 查询数据服务管理的配置信息
   * @return MybatisMapperContainer mapper管理容器
   */
  @Bean
  MybatisMapperContainer mybatisMapperContainer(
      final DataServiceSqlSessionFactory dataServiceSqlSessionFactory,
      final MybatisDatasourceManager mybatisDatasourceManager) {

    return new MybatisMapperContainer(
        dataServiceSqlSessionFactory, dataQueryInfos);
  }

  @Bean
  @ConfigurationProperties(value = "hikari", ignoreInvalidFields = true)
  HikariConfig hikariConfigDefault() {
    return new HikariConfig();
  }
}
