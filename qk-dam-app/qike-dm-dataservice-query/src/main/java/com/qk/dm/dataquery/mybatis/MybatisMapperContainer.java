package com.qk.dm.dataquery.mybatis;

import com.qk.dm.dataquery.cache.MybatisRedisCacheManager;
import com.qk.dm.dataquery.model.Mapper;
import com.qk.dm.dataquery.model.MapperSelect;
import com.qk.dm.dataquery.model.ResultMap;
import com.qk.dm.dataquery.event.DatasourceEvent;
import com.qk.dm.dataservice.vo.DataQueryInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * @author zhudaoming
 */
@Slf4j
public class MybatisMapperContainer {
  private static final String SEPARATOR = ":";
  private final DataServiceSqlSessionFactory dataServiceSqlSessionFactory;
  private final List<DataQueryInfoVO> dataQueryInfos;
  private final Map<String, Mapper> mapperMap = new ConcurrentHashMap<>(256);
  private final Map<String, String> apiIdDbNameMap = new ConcurrentHashMap<>(256);

  @Autowired MybatisRedisCacheManager mybatisRedisCacheManager;

  public MybatisMapperContainer(
      DataServiceSqlSessionFactory dataServiceSqlSessionFactory,
      List<DataQueryInfoVO> dataQueryInfos) {
    this.dataServiceSqlSessionFactory = dataServiceSqlSessionFactory;
    this.dataQueryInfos = dataQueryInfos;
    this.initMappers(dataQueryInfos);
    // 扫描注册生成的mapper
    dataServiceSqlSessionFactory.scanMappers(this);
  }

  @EventListener
  public void onMapperInsert(DatasourceEvent.MapperInsertEvent mapperInsertEvent) {
    log.info("开始添加新增mappers");
    initMappers(mapperInsertEvent.getDataQueryInfos());
    mapperInsertEvent
        .getDataQueryInfos()
        .forEach(
            (dataQueryInfoVO) -> {
              dataServiceSqlSessionFactory.scanMapper(
                  this, dataQueryInfoVO.getDasApiCreateSqlScript().getDataSourceName());
            });

    log.info("添加新增mappers完成");
  }

  public void initMappers(List<DataQueryInfoVO> dataQueryInfos) {
    final Mapper.MapperBuilder mapperBuilder = Mapper.builder();

    dataQueryInfos.stream()
        .collect(
            // 按照数据连接名称分组
            Collectors.groupingBy(
                it -> getStrNonNull(it.getDasApiCreateSqlScript().getDataSourceName())))
        // 生成mapper
        .forEach(generatedMappers(mapperBuilder));
  }

  /**
   * 生成mapper
   *
   * @param mapperBuilder 生成mapper的构造器
   * @return BiConsumer 回调函数
   */
  private BiConsumer<String, List<DataQueryInfoVO>> generatedMappers(
      Mapper.MapperBuilder mapperBuilder) {
    return (namespace, dataQueryInfoVOList) -> {
      // dsName 数据源连接名称获取mybatis的session factory
      Mapper.MapperBuilder mapperBuilderTemp = mapperBuilder.namespace(namespace);

      // 相同数据源生产mapper select 查询
      List<MapperSelect> mapperSelects =
          dataQueryInfoVOList.stream()
              .map(
                  dataQueryInfoVO ->
                      MapperSelect.builder()
                          .id(dataQueryInfoVO.getDasApiBasicInfo().getApiId())
                          .resultType("java.util.HashMap")
                          .sqlContent(dataQueryInfoVO.getDasApiCreateSqlScript().getSqlPara())
                          .build())
              .collect(Collectors.toList());
      dataQueryInfoVOList.forEach(
          dataQueryInfoVO ->
              this.addApiIdDbNameMap(
                  dataQueryInfoVO.getDasApiCreateSqlScript().getApiId(),
                  dataQueryInfoVO.getDasApiCreateSqlScript().getDataBaseName()));
      // 相同数据连接配置mapper
      mapperBuilderTemp.resultMap(
          List.of(
              ResultMap.builder()
                  .autoMapping(true)
                  .id("rt")
                  .type("HashMap")
                  .result(List.of(new ResultMap.Result("id", "id")))
                  .build()));
      mapperBuilderTemp.select(mapperSelects);
      Mapper mapper = mapperBuilderTemp.build();

      log.info("mybatis mapper=====>【{}】", mapper);
      this.addMapper(namespace, mapper);
    };
  }

  public void addMapper(String namespace, Mapper mapper) {
    mapperMap.put(namespace, mapper);
  }

  public void addApiIdDbNameMap(String apiId, String dbname) {
    apiIdDbNameMap.put(apiId, dbname);
  }

  public Mapper getMapper(String namespace) {
    return mapperMap.get(namespace);
  }

  String getStrNonNull(String n) {
    return Optional.ofNullable(n).orElse("");
  }

  public List<DataQueryInfoVO> getDataQueryInfos() {
    return dataQueryInfos;
  }

  public void addDataQueryInfos(List<DataQueryInfoVO> dataQueryInfoVOList) {
    dataQueryInfos.addAll(dataQueryInfoVOList);
  }

  public String getDsName(String apiId) {
    return dataQueryInfos.stream()
        .filter(
            dataQueryInfoVO -> apiId.equals(dataQueryInfoVO.getDasApiCreateSqlScript().getApiId()))
        .map(dataQueryInfoVO -> dataQueryInfoVO.getDasApiCreateSqlScript().getDataSourceName())
        .findFirst()
        .orElse("");
  }

  public Long getDsLastDasCreateApi() {
    return dataQueryInfos.stream()
        .map(
            dataQueryInfoVO ->
                dataQueryInfoVO.getDasApiCreateSqlScript().getGmtModified().getTime())
        .max(Comparator.comparing(Long::longValue))
        .orElse(0L);
  }

  public String getDbName(String apiId) {
    return dataQueryInfos.stream()
        .filter(
            dataQueryInfoVO -> apiId.equals(dataQueryInfoVO.getDasApiCreateSqlScript().getApiId()))
        .map(dataQueryInfoVO -> dataQueryInfoVO.getDasApiCreateSqlScript().getDataBaseName())
        .findFirst()
        .orElse("");
  }
}
