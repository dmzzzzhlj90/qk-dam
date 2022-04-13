package com.qk.dm.dataservice.service;

import com.qk.dm.dataservice.entity.DasApiBasicInfo;
import com.qk.dm.dataservice.entity.DasApiCreateConfig;
import com.qk.dm.dataservice.vo.DasApiBasicInfoVO;
import com.qk.dm.dataservice.vo.DasApiCreateConfigVO;
import com.qk.dm.dataservice.vo.DebugApiResultVO;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 数据服务_新建API_配置方式
 *
 * @author wjq
 * @date 20210823
 * @since 1.0.0
 */
@Service
public interface DasApiCreateConfigService {

    DasApiCreateConfigVO detail(DasApiBasicInfo dasApiBasicInfo, DasApiCreateConfig dasApiCreateConfig);

    void insert(DasApiCreateConfigVO dasApiCreateConfigVO);

    void update(DasApiCreateConfigVO dasApiCreateConfigVO);

    // ========================参数配置表头信息=====================================
    LinkedList<Map<String, Object>> getRequestParamHeaderInfo();

    LinkedList<Map<String, Object>> getResponseParamHeaderInfo();

    LinkedList<Map<String, Object>> getOrderParamHeaderInfo();

    List<String> getParamCompareSymbol();

    Map<String, String> getParamSortStyle();

    LinkedList<Map<String, Object>> getParamHeaderInfo();

    Object debugModel(DasApiCreateConfigVO dasApiCreateConfigVO);

    DasApiBasicInfoVO setDasApiBasicInfoDelInputParam(DasApiBasicInfo dasApiBasicInfo);

//  // ========================数据源服务API调用=====================================
//  List<String> getAllConnType();
//
//  List<ResultDatasourceInfo> getResultDataSourceByType(String type);
//
//  ResultDatasourceInfo getResultDataSourceByConnectName(String connectName);
//
//  // ========================元数据服务API调用=====================================
//  List<MtdAtlasEntityType> getAllEntityType();
//
//  MtdApi mtdDetail(MtdApiParams mtdApiParams);
//
//  List<String> getAllDataBase(String dbType);
//
//  List<String> getAllTable(String dbType, String server, String dbName);
//
//  List getAllColumn(String dbType, String server, String dbName, String tableName);
}
