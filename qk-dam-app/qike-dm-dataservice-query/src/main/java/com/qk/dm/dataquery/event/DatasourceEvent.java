package com.qk.dm.dataquery.event;

import com.qk.dam.datasource.entity.ResultDatasourceInfo;
import com.qk.dm.dataservice.vo.DataQueryInfoVO;
import org.apache.ibatis.mapping.Environment;
import org.springframework.context.ApplicationEvent;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * @author zhudaoming
 */
public class DatasourceEvent {
  private DatasourceEvent() {
    throw new IllegalStateException("Utility class");
  }

  public static class DatasourceInsertEvent extends ApplicationEvent {

    private final List<ResultDatasourceInfo> resultDatasourceInfos;

    public DatasourceInsertEvent(List<DataQueryInfoVO> queryInfoList, List<ResultDatasourceInfo> resultDatasourceInfos) {
      super(queryInfoList);
      this.resultDatasourceInfos = resultDatasourceInfos;
    }

    public List<DataQueryInfoVO> getQueryInfoList() {
      return (List<DataQueryInfoVO>) source;
    }

    public List<ResultDatasourceInfo> getResultDatasourceInfos(){
      return resultDatasourceInfos;
    }
  }

  public static class EnvironmentInsertEvent extends ApplicationEvent {
    public EnvironmentInsertEvent(Map<String, DataSource> dataSourceMap) {
      super(dataSourceMap);
    }

    public Map<String, DataSource> getDataSourceMap() {
      return (Map<String, DataSource>) source;
    }
  }

  public static class SqlSessionFactoryInsertEvent extends ApplicationEvent {
    public SqlSessionFactoryInsertEvent(Map<String, Environment> environmentMap) {
      super(environmentMap);
    }

    public Map<String, Environment> getEnvironmentMap() {
      return (Map<String, Environment>) source;
    }
  }

  public static class MapperInsertEvent extends ApplicationEvent {
    public MapperInsertEvent(List<DataQueryInfoVO> dataQueryInfoVOList) {
      super(dataQueryInfoVOList);
    }

    public List<DataQueryInfoVO> getDataQueryInfos() {
      return (List<DataQueryInfoVO>) source;
    }
  }
}
