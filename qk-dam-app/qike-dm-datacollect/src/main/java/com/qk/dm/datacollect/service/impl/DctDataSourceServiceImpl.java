package com.qk.dm.datacollect.service.impl;

import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.datasource.entity.ResultDatasourceInfo;
import com.qk.dam.metadata.catacollect.pojo.ConnectInfoVo;
import com.qk.dam.metadata.catacollect.pojo.MetadataConnectInfoVo;
import com.qk.dam.metadata.catacollect.service.MetadataApiService;
import com.qk.dam.metadata.catacollect.util.SourcesUtil;
import com.qk.dam.metedata.AtlasClient;
import com.qk.dm.client.DataBaseInfoDefaultApi;
import com.qk.dm.datacollect.mapstruct.DctDataBaseMapper;
import com.qk.dm.datacollect.service.DctDataSourceService;
import com.qk.dm.datacollect.vo.DctSchedulerRulesVO;
import com.qk.dm.datacollect.vo.DctTableDataVO;
import org.apache.atlas.AtlasClientV2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zys
 * @date 2022/4/18 16:06
 * @since 1.0.0
 */
@Service
public class DctDataSourceServiceImpl implements DctDataSourceService {
  private AtlasClientV2 atlasClientV2;
  private final AtlasClient atlasClient;
  private final DataBaseInfoDefaultApi dataBaseInfoDefaultApi;
  private final MetadataApiService metadataApiService;

  public DctDataSourceServiceImpl(AtlasClient atlasClient,
      DataBaseInfoDefaultApi dataBaseInfoDefaultApi,
      MetadataApiService metadataApiService) {
    this.atlasClient = atlasClient;
    this.atlasClientV2= this.atlasClient.instance();
    this.dataBaseInfoDefaultApi = dataBaseInfoDefaultApi;
    this.metadataApiService = metadataApiService;
  }

  @Override
  public List<String> getResultDb(String dataSourceId) {
    ResultDatasourceInfo dsDatasourceVO = dataBaseInfoDefaultApi.getResultDataSourceById(dataSourceId);
    if (Objects.nonNull(dsDatasourceVO)){
      ConnectInfoVo connectInfoVo = new ConnectInfoVo();
      connectInfoVo = GsonUtil.fromJsonString(dsDatasourceVO.getConnectBasicInfoJson(), new TypeToken<ConnectInfoVo>() {}.getType());
     return metadataApiService.queryDB(connectInfoVo);
    }else {
      throw  new BizException("根据连接名称获取连接信息失败");
    }
  }

  @Override
  public DctTableDataVO getResultTable(String dataSourceId, String databaseName) {
    ResultDatasourceInfo dsDatasourceVO = dataBaseInfoDefaultApi.getResultDataSourceById(dataSourceId);
    if (Objects.nonNull(dsDatasourceVO)){
      ConnectInfoVo connectInfoVo = new ConnectInfoVo();
      connectInfoVo = GsonUtil.fromJsonString(dsDatasourceVO.getConnectBasicInfoJson(), new TypeToken<ConnectInfoVo>() {}.getType());
      connectInfoVo.setDatabaseName(databaseName);
      List<String> list = metadataApiService.queryTable(connectInfoVo);
      return getDctTableDataVO(list);
    }else {
      throw  new BizException("根据连接名称获取连接信息失败");
    }
  }

  private DctTableDataVO getDctTableDataVO(List<String> list) {
    DctTableDataVO dctTableDataVOS= DctTableDataVO.builder().dirId(
        SourcesUtil.TABLE_NUMS).title(SourcesUtil.TABLE_NAME_ALL)
        .value(SourcesUtil.TABLE_NAME_ALL).build();
    List<DctTableDataVO> tableList = list.stream().map(e -> {
      DctTableDataVO dctTableDataVO = DctTableDataVO.builder().dirId(e).title(e)
          .value(e).children(new ArrayList<>()).build();
      return dctTableDataVO;
    }).collect(Collectors.toList());
    dctTableDataVOS.setChildren(tableList);
    return dctTableDataVOS;
  }

  @Override
  public void dolphinCallback(String schedulerRules) throws Exception {
    DctSchedulerRulesVO dctSchedulerRulesVO = GsonUtil.fromJsonString(schedulerRules, new TypeToken<DctSchedulerRulesVO>() {}.getType());
    ResultDatasourceInfo dsDatasourceVO = dataBaseInfoDefaultApi.getResultDataSourceById(dctSchedulerRulesVO.getDataSourceId());
    if (Objects.nonNull(dsDatasourceVO)){
      MetadataConnectInfoVo metadataConnectInfoVo =new MetadataConnectInfoVo();
      metadataConnectInfoVo = GsonUtil.fromJsonString(dsDatasourceVO.getConnectBasicInfoJson(), new TypeToken<MetadataConnectInfoVo>() {}.getType());
      DctDataBaseMapper.INSTANCE.from(dctSchedulerRulesVO,metadataConnectInfoVo);
      metadataApiService.extractorAtlasEntitiesWith(metadataConnectInfoVo,atlasClientV2);
    }else {
      throw new BizException("根据连接名称获取连接信息失败");
    }
  }
}