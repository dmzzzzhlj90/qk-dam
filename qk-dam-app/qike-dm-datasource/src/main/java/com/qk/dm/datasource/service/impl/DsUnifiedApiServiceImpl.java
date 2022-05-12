package com.qk.dm.datasource.service.impl;

import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.datasource.entity.ResultDatasourceInfo;
import com.qk.dam.datasource.pojo.ConnectInfoVo;
import com.qk.dam.datasource.service.MetadataApiService;
import com.qk.dam.metedata.entity.MtdApiDb;
import com.qk.dam.metedata.entity.MtdAttributes;
import com.qk.dam.metedata.entity.MtdTables;
import com.qk.dam.metedata.property.AtlasBaseProperty;
import com.qk.dam.metedata.property.AtlasSearchProperty;
import com.qk.dam.metedata.util.AtlasSearchUtil;
import com.qk.dam.metedata.vo.AtlasPagination;
import com.qk.dm.datasource.constant.DsConstant;
import com.qk.dm.datasource.service.DataSourceApiService;
import com.qk.dm.datasource.service.DsUnifiedApiService;
import org.apache.atlas.model.instance.AtlasEntityHeader;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 通用数据源连接service
 * @author zys
 * @date 2022/4/29 15:22
 * @since 1.0.0
 */
@Service
public class DsUnifiedApiServiceImpl implements DsUnifiedApiService {
  private final DataSourceApiService dataSourceApiService;
  private final MetadataApiService metadataApiService;

  public DsUnifiedApiServiceImpl(DataSourceApiService dataSourceApiService,
      MetadataApiService metadataApiService) {
    this.dataSourceApiService = dataSourceApiService;
    this.metadataApiService = metadataApiService;
  }

  @Override
  public List<String> getAllConnType() {
    return dataSourceApiService.getAllConnType();
  }

  @Override
  public Map<String,String> getAllDataSourcesByType(String engineType) {
    List<ResultDatasourceInfo> resultDataSourceByType = dataSourceApiService.getResultDataSourceByType(engineType);
    return resultDataSourceByType.stream().collect(
        Collectors.toMap(ResultDatasourceInfo::getDataSourceCode,ResultDatasourceInfo::getDataSourceName));
  }

  @Override
  public List<MtdApiDb> getAllDataBase(String engineType,
      String dataSourceCode) {
    ResultDatasourceInfo dataSourceByConnId = dataSourceApiService.getDataSourceByCode(dataSourceCode);
    if (Objects.nonNull(dataSourceByConnId)){
      List<AtlasEntityHeader> atlasEntityHeaderList = AtlasSearchUtil.getDataBaseList(getDbTypeName(engineType),getService(dataSourceByConnId), AtlasPagination.DEF_LIMIT, AtlasPagination.DEF_OFFSET);
      return buildMataDataList(atlasEntityHeaderList);
    }
    throw  new BizException("dataSourceCode为"+dataSourceCode+"的数据源为空");
  }

  @Override
  public List<MtdTables> getAllTable(String engineType, String dataSourceCode,String dataBaseName) {
    ResultDatasourceInfo dataSourceByConnId = dataSourceApiService.getDataSourceByCode(dataSourceCode);
    if (Objects.nonNull(dataSourceByConnId)){
      List<AtlasEntityHeader> atlasEntityHeaderList = AtlasSearchUtil.getTableList(getTableTypeName(engineType), dataBaseName, getService(dataSourceByConnId),
          AtlasPagination.DEF_LIMIT, AtlasPagination.DEF_OFFSET);
      return builderMtdTables(atlasEntityHeaderList);
    }
    throw  new BizException("dataSourceCode为"+dataSourceCode+"的数据源为空");
  }

  @Override
  public List<MtdAttributes> getAllColumn(String engineType, String dataSourceCode,
      String dataBaseName, String tableName) {
    ResultDatasourceInfo dataSourceByConnId = dataSourceApiService.getDataSourceByCode(dataSourceCode);
    if (Objects.nonNull(dataSourceByConnId)){
      List<AtlasEntityHeader> atlasEntityHeaderList = AtlasSearchUtil.getColumnList(getTypeName(engineType), dataBaseName,
          tableName,getService(dataSourceByConnId),
          AtlasPagination.DEF_LIMIT, AtlasPagination.DEF_OFFSET);
      return builderMtdAttributes(atlasEntityHeaderList);
    }
    throw  new BizException("dataSourceCode为"+dataSourceCode+"的数据源为空");
  }

  @Override
  public List<String> getDctResultDb(String dataSourceCode) {
    ResultDatasourceInfo dataSourceByConnId = dataSourceApiService.getDataSourceByCode(dataSourceCode);
    if (Objects.nonNull(dataSourceByConnId)){
      ConnectInfoVo connectInfoVo = new ConnectInfoVo();
      connectInfoVo = GsonUtil.fromJsonString(dataSourceByConnId.getConnectBasicInfoJson(), new TypeToken<ConnectInfoVo>() {}.getType());
      return metadataApiService.queryDB(connectInfoVo);
    }else {
      throw  new BizException("dataSourceCode为"+dataSourceCode+"的数据源为空");
    }
  }

  @Override
  public List<String> getDctResultTable(String dataSourceCode,
      String databaseName) {
    ResultDatasourceInfo dataSourceByConnId = dataSourceApiService.getDataSourceByCode(dataSourceCode);
    if (Objects.nonNull(dataSourceByConnId)){
      ConnectInfoVo connectInfoVo = new ConnectInfoVo();
      connectInfoVo = GsonUtil.fromJsonString(dataSourceByConnId.getConnectBasicInfoJson(), new TypeToken<ConnectInfoVo>() {}.getType());
      connectInfoVo.setDatabaseName(databaseName);
      return metadataApiService.queryTable(connectInfoVo);
    }else {
      throw  new BizException("根据连接名称获取连接信息失败");
    }
  }

  private List<MtdAttributes> builderMtdAttributes(List<AtlasEntityHeader> atlasEntityHeaderList) {
    if (CollectionUtils.isEmpty(atlasEntityHeaderList)) { return null; }
    return atlasEntityHeaderList.stream().map(e -> MtdAttributes.builder()
        .type(e.getTypeName())
        .owner(Objects.requireNonNullElse(e.getAttribute(AtlasSearchProperty.AttributeName.OWNER),AtlasBaseProperty.EMPTY).toString())
        .comment(getNullElse(e.getAttribute(AtlasSearchProperty.AttributeName.DESCRIPTION),
            e.getAttribute(AtlasSearchProperty.AttributeName.COMMENT))
        )
        .name(e.getDisplayText())
        .dataType(getNullElse(e.getAttribute(AtlasBaseProperty.DATA_TYPE),e.getAttribute(AtlasBaseProperty.TYPE)))
        .build()).collect(Collectors.toList());
  }

  public String getNullElse(Object sourceValue,Object defaultValue){
    return transformation(Objects.isNull(sourceValue)? defaultValue:sourceValue);
  }

  private String transformation(Object obj){
    return String.valueOf(Objects.requireNonNullElse(obj,AtlasBaseProperty.EMPTY));
  }

  private List<MtdTables> builderMtdTables(List<AtlasEntityHeader> atlasEntityHeaderList) {
    if (CollectionUtils.isEmpty(atlasEntityHeaderList)) { return new ArrayList<>(); }
    List<AtlasEntityHeader> checkList = atlasEntityHeaderList.stream().filter(e -> e.getStatus().toString().equals(DsConstant.STATUS)).collect(Collectors.toList());
    return checkList.stream().map(e ->
        MtdTables.builder()
        .displayText(e.getDisplayText())
        .guid(e.getGuid())
        .typeName(e.getTypeName())
        .comment(Objects.requireNonNullElse(e.getAttribute(AtlasSearchProperty.AttributeName.DESCRIPTION),AtlasBaseProperty.EMPTY).toString())
        .entityStatus(Objects.requireNonNullElse(e.getAttribute(AtlasSearchProperty.AttributeName.STATUS),AtlasBaseProperty.EMPTY).toString())
        .build()).collect(Collectors.toList());
  }

  private List<MtdApiDb> buildMataDataList(List<AtlasEntityHeader> entities) {
    {
      if (CollectionUtils.isEmpty(entities)) {
        return new ArrayList<>();
      }
      return entities.stream().map(e -> MtdApiDb.builder()
          .guid(e.getGuid())
          .typeName(e.getTypeName())
          .displayText(e.getDisplayText())
          .description(Objects.requireNonNullElse(e.getAttribute(
              AtlasSearchProperty.AttributeName.DESCRIPTION), AtlasBaseProperty.EMPTY).toString())
          .build()).collect(Collectors.toList());
    }
  }

  private String getService(ResultDatasourceInfo dataSourceCode) {
    Map<String, String> map = GsonUtil.fromJsonString(dataSourceCode.getConnectBasicInfoJson(), new TypeToken<Map<String, String>>() {
    }.getType());
    return map.get(DsConstant.SERVER);
  }

  private String getDbTypeName(String engineType) {
    return engineType + DsConstant.DB_NAME;
  }
  private String getTableTypeName(String engineType) {
    return engineType + DsConstant.TABLE_NAME;
  }
  private String getTypeName(String engineType) {
    return engineType + DsConstant.COLUMN_NAME;
  }
}