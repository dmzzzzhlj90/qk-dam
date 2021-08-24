package com.qk.dm.metadata.service.impl;

import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.metedata.config.AtlasConfig;
import com.qk.dm.metadata.service.MtdApiService;
import com.qk.dm.metadata.vo.MtdApiVO;
import com.qk.dm.metadata.vo.MtdAtlasEntityTypeVO;
import com.qk.dm.metadata.vo.MtdAttributesVO;
import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.SearchFilter;
import org.apache.atlas.model.instance.AtlasEntity;
import org.apache.atlas.model.typedef.AtlasTypeDefHeader;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
  public MtdApiVO mtdDetail(String typeName, String dbName, String tableName) {
    List<Map<String, String>> uniqAttributesList = new ArrayList<>();
    Map<String, String> map = new HashMap<>();
    MtdApiVO mtdApiVO = new MtdApiVO();
    if (typeName.endsWith("_table")) {
      String attr = dbName + "." + tableName + "@primary";
      map.put("qualifiedName", attr);
      uniqAttributesList.add(map);
      mtdApiVO = getColumns(typeName, uniqAttributesList);
    } else if (typeName.endsWith("_db")) {
      String attr = dbName + "@primary";
      map.put("qualifiedName", attr);
      uniqAttributesList.add(map);
      mtdApiVO = getTables(typeName, uniqAttributesList);
    }
    return mtdApiVO;
  }

  /**
   * 获取数据库中的表信息
   *
   * @param typeName
   * @param uniqAttributesList
   * @return
   */
  private MtdApiVO getTables(String typeName, List<Map<String, String>> uniqAttributesList) {
    Map<String, Object> tables = new HashMap<>();
    try {
      AtlasEntity.AtlasEntitiesWithExtInfo attr =
          atlasClientV2.getEntitiesByAttribute(typeName, uniqAttributesList);
      tables = attr.getEntities().get(0).getRelationshipAttributes();
    } catch (AtlasServiceException e) {
      e.printStackTrace();
    }
    return GsonUtil.fromMap(tables, MtdApiVO.class);
  }

  /**
   * 获取表中的列信息
   *
   * @param typeName
   * @param uniqAttributesList
   * @return
   */
  private MtdApiVO getColumns(String typeName, List<Map<String, String>> uniqAttributesList) {
    List<MtdAttributesVO> tableAttrs = new ArrayList<>();
    try {
      AtlasEntity.AtlasEntitiesWithExtInfo result =
          atlasClientV2.getEntitiesByAttribute(typeName, uniqAttributesList);
      List<AtlasEntity> atlasEntityList = new ArrayList<>(result.getReferredEntities().values());
      tableAttrs =
          atlasEntityList.stream()
              .map(
                  e -> {
                    Map<String, Object> att = e.getAttributes();
                    return GsonUtil.fromMap(att, MtdAttributesVO.class);
                  })
              .collect(Collectors.toList());

    } catch (Exception e) {
      e.printStackTrace();
    }
    MtdApiVO mtdApiVO = new MtdApiVO();
    mtdApiVO.setColumns(tableAttrs);
    return mtdApiVO;
  }
}
