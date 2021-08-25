package com.qk.dm.metadata.service.impl;

import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.metedata.config.AtlasConfig;
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
  public List<MtdAtlasEntityTypeVO> getAllEntityType() {
    List<MtdAtlasEntityTypeVO> mtdAtlasEntityTypeVOList = new ArrayList<>();
    try {
      SearchFilter searchFilter = new SearchFilter();
      searchFilter.setParam("type", "entity");
      List<AtlasTypeDefHeader> allTypeDefHeaders = atlasClientV2.getAllTypeDefHeaders(searchFilter);
      mtdAtlasEntityTypeVOList =
          GsonUtil.fromJsonString(
              GsonUtil.toJsonString(allTypeDefHeaders),
              new TypeToken<List<MtdAtlasEntityTypeVO>>() {}.getType());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return mtdAtlasEntityTypeVOList;
  }

  @Override
  public MtdApiVO mtdDetail(String typeName, String dbName, String tableName, String server) {
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
        qualifiedName = qualifiedName + "." +tableName;
      }
      if (StringUtils.isNotBlank(server)) {
        qualifiedName = qualifiedName+ "@" + server;
      }
      map.put("qualifiedName", qualifiedName);
      uniqAttributesList.add(map);
      return getDetail(typeName, uniqAttributesList);
    }
  }

  private MtdApiVO getDbs(String typeName) {
    MtdApiVO mtdApiVO = new MtdApiVO();
    try {
      AtlasSearchResult atlasSearchResult =
          atlasClientV2.basicSearch(typeName, null, null, true, 1000, 0);
      List<AtlasEntityHeader> atlasEntityHeaderList = atlasSearchResult.getEntities();
      mtdApiVO.setEntities(buildMataDataList(atlasEntityHeaderList));
    } catch (AtlasServiceException e) {
      e.printStackTrace();
    }
    return mtdApiVO;
  }

  private List<MtdApiDbVO> buildMataDataList(List<AtlasEntityHeader> entities) {
    List<MtdApiDbVO> mtdApiDbVOList = new ArrayList<>();
    entities.forEach(
        e -> {
          mtdApiDbVOList.add(
              MtdApiDbVO.builder()
                  .guid(e.getGuid())
                  .typeName(e.getTypeName())
                  .displayText(e.getDisplayText())
                  .build());
        });
    return mtdApiDbVOList;
  }

  private MtdApiVO getDetail(String typeName, List<Map<String, String>> uniqAttributesList) {
    MtdApiVO mtdApiVO = null;
    try {
      AtlasEntity.AtlasEntitiesWithExtInfo result =
          atlasClientV2.getEntitiesByAttribute(typeName, uniqAttributesList);
        Map<String, Object> tables = result.getEntities().get(0).getRelationshipAttributes();
        List<AtlasEntity> atlasEntityList = new ArrayList<>(result.getReferredEntities().values());
        List<MtdAttributesVO> tableAttrs =
            atlasEntityList.stream()
                .map(
                    e -> {
                      Map<String, Object> att = e.getAttributes();
                      return GsonUtil.fromMap(att, MtdAttributesVO.class);
                    })
                .collect(Collectors.toList());
        mtdApiVO = GsonUtil.fromMap(tables, MtdApiVO.class);
        mtdApiVO.setColumns(tableAttrs);

    } catch (AtlasServiceException e) {
      e.printStackTrace();
    }
    return mtdApiVO;
  }

}
