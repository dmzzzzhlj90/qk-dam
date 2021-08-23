package com.qk.dm.metadata.service.impl;

import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.metedata.config.AtlasConfig;
import com.qk.dm.metadata.service.AtlasMetaDataService;
import com.qk.dm.metadata.vo.MtdAtlasBaseDetailVO;
import com.qk.dm.metadata.vo.MtdAtlasBaseVO;
import com.qk.dm.metadata.vo.MtdAtlasEntityTypeVO;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.SearchFilter;
import org.apache.atlas.model.discovery.AtlasSearchResult;
import org.apache.atlas.model.instance.AtlasEntity;
import org.apache.atlas.model.instance.AtlasEntityHeader;
import org.apache.atlas.model.typedef.AtlasTypeDefHeader;
import org.springframework.stereotype.Service;

/**
 * @author wangzp
 * @date 2021/8/03 10:06
 * @since 1.0.0
 */
@Service
public class AtlasMetaDataServiceImpl implements AtlasMetaDataService {
  private static final AtlasClientV2 atlasClientV2 = AtlasConfig.getAtlasClientV2();

  @Override
  public List<MtdAtlasBaseVO> searchList(
      String query, String typeName, String classification, int limit, int offse) {
    List<MtdAtlasBaseVO> atlasBaseMainDataVOList = null;
    try {
      AtlasSearchResult atlasSearchResult =
          atlasClientV2.basicSearch(typeName, classification, query, true, limit, offse);

      List<AtlasEntityHeader> entities = atlasSearchResult.getEntities();
      atlasBaseMainDataVOList = buildMataDataList(entities);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return atlasBaseMainDataVOList;
  }

  /**
   * 组装元数据列表
   *
   * @param entities
   * @return
   */
  private List<MtdAtlasBaseVO> buildMataDataList(List<AtlasEntityHeader> entities) {
    List<MtdAtlasBaseVO> atlasBaseMainDataVOList = new ArrayList<>();
    entities.forEach(
        e -> {
          atlasBaseMainDataVOList.add(
              MtdAtlasBaseVO.builder()
                  .guid(e.getGuid())
                  .typeName(e.getTypeName())
                  .displayName(e.getDisplayText())
                  .qualifiedName(e.getAttributes().get("qualifiedName").toString())
                  .createTime(
                      Objects.isNull(e.getAttributes().get("createTime"))
                          ? null
                          : new Date((Long) e.getAttributes().get("createTime")))
                  .build());
        });
    return atlasBaseMainDataVOList;
  }

  @Override
  public MtdAtlasBaseDetailVO getEntityByGuid(String guid) {
    MtdAtlasBaseDetailVO atlasBaseMainDataDetailVO = null;
    try {
      AtlasEntity.AtlasEntityWithExtInfo detail = atlasClientV2.getEntityByGuid(guid, true, false);
      Map<String, Object> attributes = detail.getEntity().getAttributes();
      atlasBaseMainDataDetailVO = GsonUtil.fromMap(attributes, MtdAtlasBaseDetailVO.class);
      atlasBaseMainDataDetailVO.setTypeName(detail.getEntity().getTypeName());
      atlasBaseMainDataDetailVO.setReferredEntities(buildReferredEntities(detail));
      atlasBaseMainDataDetailVO.setRelationshipAttributes(
          detail.getEntity().getRelationshipAttributes());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return atlasBaseMainDataDetailVO;
  }

  /**
   * 组装参考实体
   *
   * @param detail
   * @return
   */
  private List<Map<String, Object>> buildReferredEntities(
      AtlasEntity.AtlasEntityWithExtInfo detail) {
    List<AtlasEntity> atlasEntityList = new ArrayList<>(detail.getReferredEntities().values());
    return atlasEntityList.stream()
        .map(
            e -> {
              Map<String, Object> attr = e.getAttributes();
              attr.put("guid", e.getGuid());
              return attr;
            })
        .collect(Collectors.toList());
  }

  @Override
  public Map<String, List<MtdAtlasEntityTypeVO>>getAllEntityType() {
    Map<String, List<MtdAtlasEntityTypeVO>> mtdAtlasEntityMap = new HashMap<>();
    try {
      SearchFilter searchFilter = new SearchFilter();
      searchFilter.setParam("type", "entity");
      List<AtlasTypeDefHeader> allTypeDefHeaders = atlasClientV2.getAllTypeDefHeaders(searchFilter);
      List<MtdAtlasEntityTypeVO>  mtdAtlasEntityTypeVOList =
          GsonUtil.fromJsonString(
              GsonUtil.toJsonString(allTypeDefHeaders),
              new TypeToken<List<MtdAtlasEntityTypeVO>>() {}.getType());
       mtdAtlasEntityMap = mtdAtlasEntityTypeVOList.stream().collect(Collectors.groupingBy(MtdAtlasEntityTypeVO::getServiceType));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return mtdAtlasEntityMap;
  }

  @Override
  public void deleteEntitiesByGuids(String guids) {
    try{
      atlasClientV2.deleteEntitiesByGuids(Arrays.asList(guids.split(",")));
    }catch (Exception e){
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws AtlasServiceException {
    AtlasSearchResult result = atlasClientV2.basicSearch("mysql_db", null, null, true, 20, 0);
    System.out.println(GsonUtil.toJsonString(result));
  }
}
