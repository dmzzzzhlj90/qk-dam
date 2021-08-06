package com.qk.dm.datastandards.vo;

import java.util.List;

public class DataSourceYamlVO {
  private List<DataSourceJobVO> dataSourceJobs;

  public List<DataSourceJobVO> getDataSourceJobs() {
    return dataSourceJobs;
  }

  public void setDataSourceJobs(List<DataSourceJobVO> dataSourceJobs) {
    this.dataSourceJobs = dataSourceJobs;
  }
}
