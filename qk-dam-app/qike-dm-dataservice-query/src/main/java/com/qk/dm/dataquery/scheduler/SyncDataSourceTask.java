package com.qk.dm.dataquery.scheduler;

import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.datasource.entity.ResultDatasourceInfo;
import com.qk.dam.datasource.enums.ConnTypeEnum;
import com.qk.dm.DataBaseService;
import com.qk.dm.dataquery.event.DatasourceEvent;
import com.qk.dm.dataquery.mybatis.MybatisDatasourceManager;
import com.qk.dm.dataquery.mybatis.MybatisEnvironmentManager;
import com.qk.dm.dataquery.mybatis.MybatisMapperContainer;
import com.qk.dm.dataservice.vo.DataQueryInfoVO;
import com.qk.dm.feign.DataQueryInfoFeign;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.mapping.Environment;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhudaoming
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SyncDataSourceTask {
  private final MybatisDatasourceManager mybatisDatasourceManager;
  private final MybatisEnvironmentManager mybatisEnvironmentManager;
  private final MybatisMapperContainer mybatisMapperContainer;
  private final DataQueryInfoFeign dataQueryInfoFeign;
  private final DataBaseService dataBaseService;
  private final ApplicationEventPublisher publisher;

  @Scheduled(cron = "0/50 * * * * ? ")
  public void syncLastUpdate() {
    DefaultCommonResult<List<DataQueryInfoVO>> listDefaultCommonResult =
        dataQueryInfoFeign.dataQueryInfoLast(mybatisMapperContainer.getDsLastDasCreateApi());
    List<DataQueryInfoVO> queryInfoList = listDefaultCommonResult.getData();
    if (CollectionUtils.isNotEmpty(queryInfoList)) {
      mybatisMapperContainer.addDataQueryInfos(queryInfoList);
      // 过滤已经配置过的数据源
      List<DataQueryInfoVO> dataQueryInfoVOList =
          queryInfoList.stream()
              .filter(
                  dataQueryInfoVO ->
                      mybatisDatasourceManager.containsDataSource(
                          dataQueryInfoVO.getDasApiCreateMybatisSqlScript().getConnId()))
              .collect(Collectors.toList());
      if (CollectionUtils.isNotEmpty(dataQueryInfoVOList)) {
        // 1. 触发新增数据源事件
        List<ResultDatasourceInfo> resultDatasourceInfos =
                dataBaseService.getResultDataSourceByType(ConnTypeEnum.MYSQL.getName());
        publisher.publishEvent(new DatasourceEvent.DatasourceInsertEvent(dataQueryInfoVOList,resultDatasourceInfos));
        // 2. 触发新增mybatis环境事件
        Map<String, DataSource> dataSourceMap = getDataSourceMap(dataQueryInfoVOList);
        publisher.publishEvent(new DatasourceEvent.EnvironmentInsertEvent(dataSourceMap));
        // 3. 触发新增mybatis SqlSession 环境事件
        Map<String, Environment> environmentMap = getEnvironmentMap(dataQueryInfoVOList);
        publisher.publishEvent(new DatasourceEvent.SqlSessionFactoryInsertEvent(environmentMap));
      }
      // 4. 触发新增mapper绑定事件
      publisher.publishEvent(new DatasourceEvent.MapperInsertEvent(queryInfoList));
    }
  }

  private Map<String, DataSource> getDataSourceMap(List<DataQueryInfoVO> queryInfoList) {
    return queryInfoList.stream()
        .collect(
            Collectors.toMap(
                k -> k.getDasApiCreateMybatisSqlScript().getConnId(),
                v ->
                    mybatisDatasourceManager.getDataSource(
                        v.getDasApiCreateMybatisSqlScript().getConnId())));
  }

  private Map<String, Environment> getEnvironmentMap(List<DataQueryInfoVO> queryInfoList) {
    return queryInfoList.stream()
        .collect(
            Collectors.toMap(
                k -> k.getDasApiCreateMybatisSqlScript().getConnId(),
                v ->
                    mybatisEnvironmentManager.getEnvironment(
                        v.getDasApiCreateMybatisSqlScript().getConnId())));
  }
}
