package com.qk.dm.metadata.service.impl;

import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.metedata.config.AtlasConfig;
import com.qk.dam.metedata.entity.MtdApi;
import com.qk.dam.metedata.entity.MtdApiDb;
import com.qk.dam.metedata.entity.MtdAtlasEntityType;
import com.qk.dam.metedata.entity.MtdAttributes;
import com.qk.dm.metadata.service.MtdApiService;
import com.qk.dm.metadata.vo.*;
import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.SearchFilter;
import org.apache.atlas.model.discovery.AtlasSearchResult;
import org.apache.atlas.model.instance.AtlasEntity;
import org.apache.atlas.model.instance.AtlasEntityHeader;
import org.apache.atlas.model.typedef.AtlasTypeDefHeader;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wangzp
 * @date 2021/8/03 17:20
 * @since 1.0.0
 */
@Service
public class MtdApiServiceImpl implements MtdApiService {

  private static final AtlasClientV2 atlasClientV2 = AtlasConfig.getAtlasClientV2();

  @Override
  public List<MtdAtlasEntityType> getAllEntityType() {
    List<MtdAtlasEntityType> mtdAtlasEntityTypeVOList = new ArrayList<>();
    try {
      SearchFilter searchFilter = new SearchFilter();
      searchFilter.setParam("type", "entity");
      List<AtlasTypeDefHeader> allTypeDefHeaders = atlasClientV2.getAllTypeDefHeaders(searchFilter);
      mtdAtlasEntityTypeVOList =
          GsonUtil.fromJsonString(
              GsonUtil.toJsonString(allTypeDefHeaders),
              new TypeToken<List<MtdAtlasEntityType>>() {}.getType());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return mtdAtlasEntityTypeVOList;
  }

  @Override
  public MtdApi mtdDetail(String typeName, String dbName, String tableName, String server) {
    if (StringUtils.isBlank(dbName)
        && StringUtils.isBlank(tableName)
        && StringUtils.isBlank(server)) {
      return getDbs(typeName);
    } else {
      List<Map<String, String>> uniqAttributesList = new ArrayList<>();
      Map<String, String> map = new HashMap<>();
      String qualifiedName = "";
      if (StringUtils.isNotBlank(dbName)) {
        qualifiedName = qualifiedName + dbName;
      }
      if (StringUtils.isNotBlank(tableName)) {
        qualifiedName = qualifiedName + "." + tableName;
      }
      if (StringUtils.isNotBlank(server)) {
        qualifiedName = qualifiedName + "@" + server;
      }
      map.put("qualifiedName", qualifiedName);
      uniqAttributesList.add(map);
      return getDetail(typeName, uniqAttributesList, tableName);
    }
  }

  private MtdApi getDbs(String typeName) {
    MtdApi mtdApi = new MtdApi();
    try {
      AtlasSearchResult atlasSearchResult =
          atlasClientV2.basicSearch(typeName, null, null, true, 1000, 0);
      List<AtlasEntityHeader> atlasEntityHeaderList = atlasSearchResult.getEntities();
      mtdApi.setEntities(buildMataDataList(atlasEntityHeaderList));
    } catch (AtlasServiceException e) {
      e.printStackTrace();
    }
    return mtdApi;
  }

  private List<MtdApiDb> buildMataDataList(List<AtlasEntityHeader> entities) {
    List<MtdApiDb> mtdApiDbVOList = new ArrayList<>();
    entities.forEach(
        e -> {
          mtdApiDbVOList.add(
              MtdApiDb.builder()
                  .guid(e.getGuid())
                  .typeName(e.getTypeName())
                  .displayText(e.getDisplayText())
                  .build());
        });
    return mtdApiDbVOList;
  }

  private MtdApi getDetail(
      String typeName, List<Map<String, String>> uniqAttributesList, String tableName) {
    MtdApi mtdApi = null;
    try {
      AtlasEntity.AtlasEntitiesWithExtInfo result =
          atlasClientV2.getEntitiesByAttribute(typeName, uniqAttributesList);
      Map<String, Object> tables = result.getEntities().get(0).getRelationshipAttributes();
      mtdApi = GsonUtil.fromMap(tables, MtdApi.class);
      if (StringUtils.isNotBlank(tableName)) {
        List<AtlasEntity> atlasEntityList = new ArrayList<>(result.getReferredEntities().values());
        List<MtdAttributes> tableAttrs =
            atlasEntityList.stream()
                .map(
                    e -> {
                      Map<String, Object> att = e.getAttributes();
                      return GsonUtil.fromMap(att, MtdAttributes.class);
                    })
                .collect(Collectors.toList());
        mtdApi.setColumns(tableAttrs);
      }

    } catch (AtlasServiceException e) {
      e.printStackTrace();
    }
    return mtdApi;
  }
}