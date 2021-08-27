package com.qk.dm.datasource.service;

import com.qk.dam.datasource.entity.ConnectBasicInfo;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datasource.entity.DsDatasource;
import com.qk.dm.datasource.vo.DsDataSourceParamsVO;
import com.qk.dm.datasource.vo.DsDatasourceVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zys
 * @date 20210729
 * @since 1.0.0 数据源连接接口
 */
@Service
public interface DsDataSourceService {
  PageResultVO<DsDatasourceVO> getDsDataSource(DsDataSourceParamsVO dsDataSourceParamsVO);

  void addDsDataSource(DsDatasourceVO dsDatasourceVO);

  void deleteDsDataSource(Integer id);

  void updateDsDataSource(DsDatasourceVO dsDatasourceVO);

  Boolean dataSourceConnect(DsDatasourceVO dsDatasourceVO);

  List<DsDatasourceVO> getDataSourceByType(String type);

  List<String> getDataType();

  List<DsDatasourceVO> getDataSourceByDsname(String dataSourceName);

  ConnectBasicInfo getConnectInfo(String type, DsDatasource dsDatasource);

}
