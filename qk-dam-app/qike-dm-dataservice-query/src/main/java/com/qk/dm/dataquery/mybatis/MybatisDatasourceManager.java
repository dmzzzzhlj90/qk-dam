package com.qk.dm.dataquery.mybatis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.datasource.entity.ConnectBasicInfo;
import com.qk.dam.datasource.entity.MysqlInfo;
import com.qk.dam.datasource.entity.ResultDatasourceInfo;
import com.qk.dam.datasource.enums.ConnTypeEnum;
import com.qk.dm.dataquery.datasouce.HikariDataSourceFactory;
import com.qk.dm.dataquery.event.DatasourceEvent;
import com.qk.dm.dataservice.vo.DataQueryInfoVO;
import com.zaxxer.hikari.HikariConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.context.event.EventListener;

import javax.sql.DataSource;
import java.beans.PropertyDescriptor;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * @author zhudaoming
 */
@Slf4j
@RequiredArgsConstructor
public class MybatisDatasourceManager {
  private final Map<String, DataSource> datasourceMap = new ConcurrentHashMap<>(256);
  private final Map<String, HikariDataSourceFactory> hikariDataSourceFactoryMap =
      new ConcurrentHashMap<>(256);
  private final ConnTypeEnum connType;
  private final HikariConfig hikariConfigDefault;
  private final List<ResultDatasourceInfo> resultDataSource;

  @EventListener
  public void onDatasourceInsert(DatasourceEvent.DatasourceInsertEvent datasourceEvent) {
    List<DataQueryInfoVO> queryInfoList = datasourceEvent.getQueryInfoList();
    log.info(
        "数据API SQL配置发生变化,时间：{}", new DateTime(datasourceEvent.getTimestamp()).toLocalDateTime());

    refreshDatasource(queryInfoList, datasourceEvent.getResultDatasourceInfos());
    log.info("数据数据API SQL配置完成，共新增：【{}】个配置", queryInfoList.size());
  }

  public void initDataSource(List<DataQueryInfoVO> dataQueryInfos) {
    refreshDatasource(dataQueryInfos, resultDataSource);
  }

  private void refreshDatasource(
      List<DataQueryInfoVO> dataQueryInfo, List<ResultDatasourceInfo> resultDataSource) {
    Map<String, ResultDatasourceInfo> datasourceInfoMap =
        resultDataSource.stream()
            .collect(Collectors.toMap(ResultDatasourceInfo::getDataSourceName, (v -> v)));
    log.info("查询数据源服务成功！共获取【{}】个mysql数据源", resultDataSource.size());
    ObjectMapper objectMapper = new ObjectMapper();

    dataQueryInfo.forEach(
        (dataQueryInfoVO -> {
          String dataSourceName = dataQueryInfoVO.getApiCreateDefinitionVO().getDataSourceName();
          ResultDatasourceInfo resultDatasourceInfo = datasourceInfoMap.get(dataSourceName);

          String connectBasicInfoJson = resultDatasourceInfo.getConnectBasicInfoJson();
          try {
            MysqlInfo mysqlInfo = objectMapper.readValue(connectBasicInfoJson, MysqlInfo.class);
            this.regDatasource(dataSourceName, mysqlInfo);
            log.info("注册mysql数据源连接:【{}】！！", resultDatasourceInfo.getDataSourceName());
          } catch (Exception e) {
            throw new BizException("错误的json处理！");
          }
        }));
  }

  public void regDatasource(String dataSourceName, ConnectBasicInfo connectBasicInfo) {
    HikariConfig hc = new HikariConfig();
    hc.setJdbcUrl(
        "jdbc:"
            + connType.getName()
            + "://"
            + connectBasicInfo.getServer()
            + ":"
            + connectBasicInfo.getPort());
    hc.setDriverClassName(connectBasicInfo.getDriverInfo());
    hc.setUsername(connectBasicInfo.getUserName());
    hc.setPassword(connectBasicInfo.getPassword());

    BeanUtils.copyProperties(hikariConfigDefault,hc,getNullPropertyNames(hikariConfigDefault));


    HikariDataSourceFactory hikariDataSourceFactory =
        HikariDataSourceFactory.builder()
            .config(hc)
            // hikariConfig 默认参数配置
            .dataSourceProperties(new Properties())
            .build();

    hikariDataSourceFactoryMap.put(dataSourceName, hikariDataSourceFactory);
    datasourceMap.put(dataSourceName, hikariDataSourceFactory.dataSourceInstance());
  }
  /**
   * 获取需要忽略的属性
   *
   * @param source
   * @return
   */
  public static String[] getNullPropertyNames (Object source) {
    final BeanWrapper src = new BeanWrapperImpl(source);
    PropertyDescriptor[] pds = src.getPropertyDescriptors();

    Set<String> emptyNames = new HashSet<>();
    for(PropertyDescriptor pd : pds) {
      Object srcValue = src.getPropertyValue(pd.getName());
      // 此处判断可根据需求修改
      if (srcValue == null) {
        emptyNames.add(pd.getName());
      }
    }
    String[] result = new String[emptyNames.size()];
    return emptyNames.toArray(result);
  }
  public Boolean containsDataSource(String connectName) {
    return Objects.isNull(datasourceMap.get(connectName));
  }

  public DataSource getDataSource(String connectName) {
    return datasourceMap.get(connectName);
  }


  public void bindDatasource(BiConsumer<String, DataSource> biConsumer) {
    datasourceMap.forEach(biConsumer);
  }
}
