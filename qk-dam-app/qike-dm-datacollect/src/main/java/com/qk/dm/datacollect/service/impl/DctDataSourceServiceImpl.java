package com.qk.dm.datacollect.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.qk.dam.datasource.utils.SourcesUtil;
import com.qk.dam.metedata.AtlasClient;
import com.qk.dm.client.DataBaseInfoDefaultApi;
import com.qk.dm.datacollect.service.DctDataSourceService;
import com.qk.dm.datacollect.vo.DctTableDataVO;
import org.apache.atlas.AtlasClientV2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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


  public DctDataSourceServiceImpl(AtlasClient atlasClient,
      DataBaseInfoDefaultApi dataBaseInfoDefaultApi) {
    this.atlasClient = atlasClient;
    this.atlasClientV2= this.atlasClient.instance();
    this.dataBaseInfoDefaultApi = dataBaseInfoDefaultApi;
  }

  @Override
  public List<String> getResultDb(String dataSourceId) {
    return dataBaseInfoDefaultApi.getUnifiedDctResultDb(dataSourceId);
  }

  @Override
  public List<DctTableDataVO> getResultTable(String dataSourceId, String databaseName) {
    List<String> list = dataBaseInfoDefaultApi.getUnifiedDctResultTable(dataSourceId,databaseName);
    return getDctTableDataVO(list);
  }

  private List<DctTableDataVO> getDctTableDataVO(List<String> list) {
    List<DctTableDataVO> tableLists = new ArrayList<>();
    if (CollectionUtils.isNotEmpty(list)){
      DctTableDataVO dctTableDataVOS= DctTableDataVO.builder().dirId(
          SourcesUtil.TABLE_NUMS).title(SourcesUtil.TABLE_NAME_ALL)
          .value(SourcesUtil.TABLE_NAME_ALL).build();
      List<DctTableDataVO> tableList = list.stream().map(e -> {
        DctTableDataVO dctTableDataVO = DctTableDataVO.builder().dirId(e).title(e)
            .value(e).children(new ArrayList<>()).build();
        return dctTableDataVO;
      }).collect(Collectors.toList());
      dctTableDataVOS.setChildren(tableList);
      tableLists.add(dctTableDataVOS);
    }
    return tableLists;
  }
}