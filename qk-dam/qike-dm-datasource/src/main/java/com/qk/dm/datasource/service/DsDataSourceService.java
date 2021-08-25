package com.qk.dm.datasource.service;

import com.qk.dm.datasource.vo.DsDataSourceParamsVO;
import com.qk.dm.datasource.vo.DsDatasourceVO;
import com.qk.dm.datasource.vo.PageResultVO;
import java.util.List;
import org.springframework.stereotype.Service;

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

  List<String> getDataType();

  List<String> dsDataSourceService();

  List<DsDatasourceVO> getDataSourceByType(String linkType);

  List<DsDatasourceVO> getDataSourceByDsname(String dataSourceName);
}
