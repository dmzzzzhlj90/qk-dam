package com.qk.dm.dataservice.service;

import com.qk.dam.datasource.entity.ResultDatasourceInfo;
import com.qk.dam.metedata.entity.MtdApi;
import com.qk.dam.metedata.entity.MtdApiParams;
import com.qk.dam.metedata.entity.MtdAtlasEntityType;
import com.qk.dm.dataservice.vo.DasApiCreateConfigVO;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * 数据服务_新建API_配置方式
 *
 * @author wjq
 * @date 20210823
 * @since 1.0.0
 */
@Service
public interface DasApiCreateConfigService {

  DasApiCreateConfigVO detail(String apiId);

  void insert(DasApiCreateConfigVO dasApiCreateConfigVO);

  void update(DasApiCreateConfigVO dasApiCreateConfigVO);

  // ========================参数配置表头信息=====================================
  Map<String, String> getDasApiCreateRequestParaHeaderInfo();

  Map<String, String> getDasApiCreateResponseParaHeaderInfo();

  Map<String, String> getDasApiCreateOrderParaHeaderInfo();

  List<String> getDasApiCreateParasCompareSymbol();

  Map<String, String> getDasApiCreateParasSortStyle();

  // ========================数据源服务API调用=====================================
  List<String> getAllConnType();

  List<ResultDatasourceInfo> getResultDataSourceByType(String type);

  ResultDatasourceInfo getResultDataSourceByConnectName(String connectName);

  // ========================元数据服务API调用=====================================
  List<MtdAtlasEntityType> getAllEntityType();

  MtdApi mtdDetail(MtdApiParams mtdApiParams);

  List<String> getAllDataBase(String dbType);

  List<String> getAllTable(String dbType, String server, String dbName);

  List getAllColumn(String dbType, String server, String dbName, String tableName);
}
