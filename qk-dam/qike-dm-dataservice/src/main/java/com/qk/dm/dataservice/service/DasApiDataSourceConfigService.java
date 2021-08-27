package com.qk.dm.dataservice.service;

import com.qk.dam.datasource.entity.ResultDatasourceInfo;
import com.qk.dam.metedata.entity.MtdApi;
import com.qk.dam.metedata.entity.MtdApiParams;
import com.qk.dam.metedata.entity.MtdAtlasEntityType;
import com.qk.dm.dataservice.vo.DasApiDataSourceConfigVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author wjq
 * @date 20210823
 * @since 1.0.0
 */
@Service
public interface DasApiDataSourceConfigService {

    DasApiDataSourceConfigVO getDasDataSourceConfigInfoByApiId(String apiId);

    void addDasDataSourceConfig(DasApiDataSourceConfigVO dasDataSourceConfigVO);

    void updateDasDataSourceConfig(DasApiDataSourceConfigVO dasDataSourceConfigVO);

    Map<String, String> getDSConfigRequestParaHeaderInfo();

    Map<String, String> getDSConfigResponseParaHeaderInfo();

    Map<String, String> getDSConfigOrderParaHeaderInfo();

    List<String> getDSConfigParasCompareSymbol();

    Map<String, String> getDSConfigParasSortStyle();

    List<String> getAllDataBase(String dbType);

    List<String> getAllTable(String dbType, String server, String dbName);

    List getAllColumn(String dbType, String server, String dbName, String tableName);

    // ========================数据源服务API调用=====================================

    List<String> getAllConnType();

    List<ResultDatasourceInfo> getResultDataSourceByType(String type);

    ResultDatasourceInfo getResultDataSourceByConnectName(String connectName);

    // ========================元数据服务API调用=====================================

    List<MtdAtlasEntityType> getAllEntityType();

    MtdApi mtdDetail(MtdApiParams mtdApiParams);



}
