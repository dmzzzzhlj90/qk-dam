package com.qk.dm.client;

import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.datasource.entity.ConnectBasicInfo;
import com.qk.dam.datasource.entity.ResultDatasourceInfo;
import com.qk.dam.datasource.utils.ConnectInfoConvertUtils;
import com.qk.dam.metedata.entity.*;

import com.qk.dm.feign.DataSourceFeign;
import com.qk.dm.feign.MetaDataFeign;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 数据库信息统一Client API
 *
 * @author wjq
 * @date 2021/11/26
 * @since 1.0.0
 */
@Component
public class DataBaseInfoDefaultApi {

    private final DataSourceFeign dataSourceFeign;
    private final MetaDataFeign metaDataFeign;

    public DataBaseInfoDefaultApi(DataSourceFeign dataSourceFeign, MetaDataFeign metaDataFeign) {
        this.dataSourceFeign = dataSourceFeign;
        this.metaDataFeign = metaDataFeign;
    }

    // ========================数据源服务_API调用=====================================

    /**
     * 查询所有数据源连接类型
     *
     * @return DefaultCommonResult
     */
    public List<String> getAllConnType() {
        return dataSourceFeign.getAllConnType().getData();
    }

    /**
     * 根据数据库类型获取数据源连接信息
     *
     * @return DefaultCommonResult
     */
    public List<ResultDatasourceInfo> getResultDataSourceByType(String type) {
        return dataSourceFeign.getResultDataSourceByType(type).getData();
    }

    /**
     * 根据数据源名称获取数据源连接信息
     *
     * @return DefaultCommonResult
     */
    public ResultDatasourceInfo getResultDataSourceByConnectName(String connectName) {
        ResultDatasourceInfo resultDatasourceInfo =
                dataSourceFeign.getResultDataSourceByConnectName(connectName).getData();
        //todo feign 加判断null
        if (resultDatasourceInfo != null) {
          ConnectBasicInfo connectInfo =
              ConnectInfoConvertUtils.getConnectInfo(
                  resultDatasourceInfo.getDbType(), resultDatasourceInfo.getConnectBasicInfoJson());
        }
      return resultDatasourceInfo;
    }

    // ========================元数据服务_API调用=====================================

    /**
     * 获取所有的元数据类型
     *
     * @return DefaultCommonResult<List < MtdAtlasEntityType>>
     */
    public List<MtdAtlasEntityType> getAllEntityType() {
        return metaDataFeign.getAllEntityType().getData();
    }

    /**
     * 获取元数据详情信息
     *
     * @param mtdApiParams
     * @return DefaultCommonResult<MtdApi>
     */
    MtdApi mtdDetail(MtdApiParams mtdApiParams) {
        return metaDataFeign.mtdDetail(mtdApiParams).getData();
    }

    /**
     * 新建API__获取db库信息下拉列表
     *
     * @param dbType
     * @return DefaultCommonResult<List < String>>
     */
    public List<String> getAllDataBase(String dbType) {
        String type = dbType.split("-")[1];
        DefaultCommonResult<MtdApi> mtdApiDefaultCommonResult =
                metaDataFeign.mtdDetail(MtdApiParams.builder().typeName(type + "_db").build());
        List<MtdApiDb> mtdApiDbs = mtdApiDefaultCommonResult.getData().getEntities();
        return mtdApiDbs.stream().map(MtdApiDb::getDisplayText).collect(Collectors.toList());
    }

    /**
     * 新建API__获取db库信息下拉列表
     * @param dbType
     * @param server
     * @return
     */
    public List<String> getAllDataBase(String dbType,String server) {
        String type = dbType.split("-")[1];
        DefaultCommonResult<MtdApi> mtdApiDefaultCommonResult =
                metaDataFeign.getDbs(type + "_db",server);
        List<MtdApiDb> mtdApiDbs = mtdApiDefaultCommonResult.getData().getEntities();
        return mtdApiDbs.stream().map(MtdApiDb::getDisplayText).collect(Collectors.toList());
    }

    /**
     * 新建API__获取table表信息下拉列表
     *
     * @param dbType,server,dbName
     * @return DefaultCommonResult
     */
    public List<String> getAllTable(String dbType, String server, String dbName) {
        String type = dbType.split("-")[1];
        DefaultCommonResult<MtdApi> mtdApiDefaultCommonResult =
                metaDataFeign.mtdDetail(
                        MtdApiParams.builder().typeName(type + "_db").server(server).dbName(dbName).build());
        List<MtdTables> mtdTablesList = mtdApiDefaultCommonResult.getData().getTables();
        return mtdTablesList.stream().map(MtdTables::getDisplayText).collect(Collectors.toList());
    }

    /**
     * 新建API__获取column字段信息下拉列表
     *
     * @param dbType,server,dbName
     * @return DefaultCommonResult
     */
    public List getAllColumn(String dbType, String server, String dbName, String tableName) {
        String type = dbType.split("-")[1];
        DefaultCommonResult<MtdApi> mtdApiDefaultCommonResult =
                metaDataFeign.mtdDetail(
                        MtdApiParams.builder()
                                .typeName(type + "_table")
                                .server(server)
                                .dbName(dbName)
                                .tableName(tableName)
                                .build());
        return mtdApiDefaultCommonResult.getData().getColumns();
    }

    /**
     * 通过元数据获取表是否存在和表中是否存在数据
     * @param dbType
     * @param server
     * @param dbName
     * @param tableName
     * @return
     */
    public Boolean getExistData(String dbType, String server, String dbName, String tableName){
        String type = dbType.split("-")[1];
        DefaultCommonResult<Boolean> existData = metaDataFeign.getExistData(
                MtdApiParams.builder()
                        .typeName(type + "_table")
                        .server(server)
                        .dbName(dbName)
                        .tableName(tableName)
                        .build());
        return existData.getData();
    }
}
