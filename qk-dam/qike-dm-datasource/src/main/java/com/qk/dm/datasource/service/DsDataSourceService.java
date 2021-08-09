package com.qk.dm.datasource.service;

import com.qk.dm.datasource.vo.DsDatasourceVO;
import com.qk.dm.datasource.vo.PageResultVO;
import com.qk.dm.datasource.vo.params.DsDataSourceParamsVO;
import org.springframework.stereotype.Service;

import java.util.List;

/** @author zys
 * @date 20210729
 *  @since 1.0.0 数据源连接接口
 */
@Service
public interface DsDataSourceService {
  PageResultVO<DsDatasourceVO> getDsDataSource(DsDataSourceParamsVO dsDataSourceParamsVO);

  void addDsDataSource(DsDatasourceVO dsDatasourceVO);

  void deleteDsDataSource(Integer id);

  void updateDsDataSourece(DsDatasourceVO dsDatasourceVO);

  List<String> getDataType();

  Boolean dsDataSoureceConnect(DsDatasourceVO dsDatasourceVO);
}
