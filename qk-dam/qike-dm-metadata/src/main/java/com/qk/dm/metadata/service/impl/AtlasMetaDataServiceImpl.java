package com.qk.dm.metadata.service.impl;

import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.metedata.config.AtlasConfig;
import com.qk.dam.metedata.entity.MtdAtlasEntityType;
import com.qk.dm.metadata.properties.AtlasSearchParameters;
import com.qk.dm.metadata.service.AtlasMetaDataService;
import com.qk.dm.metadata.vo.*;
import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.SearchFilter;
import org.apache.atlas.model.discovery.AtlasSearchResult;
import org.apache.atlas.model.discovery.SearchParameters;
import org.apache.atlas.model.instance.AtlasEntity;
import org.apache.atlas.model.instance.AtlasEntityHeader;
import org.apache.atlas.model.typedef.AtlasTypeDefHeader;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wangzp
 * @date 2021/8/03 10:06
 * @since 1.0.0
 */
@Service
public class AtlasMetaDataServiceImpl implements AtlasMetaDataService {
  private static final AtlasClientV2 atlasClientV2 = AtlasConfig.getAtlasClientV2();

  @Override
  public List<MtdAtlasBaseVO> searchList(MtdAtlasParamsVO mtdAtlasParamsVO) {
    List<MtdAtlasBaseVO> atlasBaseMainDataVOList = null;
    try {
      AtlasSearchResult atlasSearchResult =
          atlasClientV2.basicSearch(
              mtdAtlasParamsVO.getTypeName(),
              mtdAtlasParamsVO.getClassification(),
              mtdAtlasParamsVO.getQuery(),
              true,
              mtdAtlasParamsVO.getLimit(),
              mtdAtlasParamsVO.getOffse());
      List<AtlasEntityHeader> entities = atlasSearchResult.getEntities();
      atlasBaseMainDataVOList = buildMataDataList(entities);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return atlasBaseMainDataVOList;
  }

  @Override
  public List<MtdAtlasBaseVO> searchList(
      MtdAtlasBaseSearchVO mtdAtlasParamsVO, Boolean excludeDeletedEntities) {
    List<MtdAtlasBaseVO> atlasBaseMainDataVOList = null;
    try {
      AtlasSearchResult atlasSearchResult =
          atlasClientV2.basicSearch(
              mtdAtlasParamsVO.getTypeName(),
              getFilterCriteria(mtdAtlasParamsVO),
              mtdAtlasParamsVO.getClassification(),
              mtdAtlasParamsVO.getQuery(),
              true,
              mtdAtlasParamsVO.getLimit(),
              mtdAtlasParamsVO.getOffset());
      List<AtlasEntityHeader> entities = atlasSearchResult.getEntities();
      if (entities != null) {
        atlasBaseMainDataVOList = buildMataDataList(entities);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return atlasBaseMainDataVOList;
  }

  public SearchParameters.FilterCriteria getFilterCriteria(MtdAtlasBaseSearchVO mtdAtlasParamsVO) {
    SearchParameters.FilterCriteria entityFilters = new SearchParameters.FilterCriteria();
    entityFilters.setCondition(SearchParameters.FilterCriteria.Condition.AND);
    List<SearchParameters.FilterCriteria> filterCriteriaList = new ArrayList<>();
    if (mtdAtlasParamsVO.getTypeNameValue() != null
        && mtdAtlasParamsVO.getTypeNameValue().length > 0) {
      filterCriteriaList.add(
          getFilterCriteria(
              mtdAtlasParamsVO.getTypeNameValue(),
              AtlasSearchParameters.AttributeName.TYPENAME,
              AtlasSearchParameters.Operator.EQ,
              SearchParameters.FilterCriteria.Condition.OR));
    }
    if (mtdAtlasParamsVO.getNameValue() != null && mtdAtlasParamsVO.getNameValue().length > 0) {
      filterCriteriaList.add(
          getFilterCriteria(
              mtdAtlasParamsVO.getNameValue(),
              AtlasSearchParameters.AttributeName.NAME,
              AtlasSearchParameters.Operator.CONTAINS,
              SearchParameters.FilterCriteria.Condition.AND));
    }
    if (mtdAtlasParamsVO.getLabelsValue() != null && mtdAtlasParamsVO.getLabelsValue().length > 0) {
      SearchParameters.FilterCriteria entity = new SearchParameters.FilterCriteria();
      entity.setCondition(SearchParameters.FilterCriteria.Condition.AND);
      entity.setCriterion(Arrays.asList(getFilterCriteria(
              mtdAtlasParamsVO.getLabelsValue(),
              AtlasSearchParameters.AttributeName.LABELS,
              AtlasSearchParameters.Operator.CONTAINS,
              SearchParameters.FilterCriteria.Condition.OR)));
      filterCriteriaList.add(entity);
    }
    entityFilters.setCriterion(filterCriteriaList);
    return entityFilters;
  }

  public SearchParameters.FilterCriteria getFilterCriteria(
      String[] typeNameValue,
      String attributeName,
      String operator,
      SearchParameters.FilterCriteria.Condition condition) {
    SearchParameters.FilterCriteria entity = new SearchParameters.FilterCriteria();
    entity.setCriterion(getFilterCriteriaList(typeNameValue, attributeName, operator));
    entity.setCondition(condition);
    //    entity.setCondition(
    //        typeNameValue.length > 1
    //            ? SearchParameters.FilterCriteria.Condition.OR
    //            : SearchParameters.FilterCriteria.Condition.AND);
    return entity;
  }

  public List<SearchParameters.FilterCriteria> getFilterCriteriaList(
      String[] attributeValue, String attributeName, String operator) {
    List<SearchParameters.FilterCriteria> entityList = new ArrayList<>();
    Arrays.stream(attributeValue)
        .forEach(
            i -> {
              SearchParameters.FilterCriteria criteria = new SearchParameters.FilterCriteria();
              criteria.setAttributeValue(i);
              criteria.setAttributeName(attributeName);
              criteria.setOperator(SearchParameters.Operator.fromString(operator));
              entityList.add(criteria);
            });
    return entityList;
  }

  public SearchParameters.FilterCriteria getFilterCriteria(List<MtdAtlasSearchVO> list) {
    SearchParameters.FilterCriteria entityFilters = new SearchParameters.FilterCriteria();
    entityFilters.setCondition(SearchParameters.FilterCriteria.Condition.AND);
    entityFilters.setCriterion(
        list.stream()
            .map(
                mtdAtlasSearchVO -> {
                  SearchParameters.FilterCriteria entity = new SearchParameters.FilterCriteria();
                  String[] attributeValue = mtdAtlasSearchVO.getAttributeValue();
                  entity.setCondition(
                      attributeValue.length > 1
                          ? SearchParameters.FilterCriteria.Condition.OR
                          : SearchParameters.FilterCriteria.Condition.AND);
                  entity.setCriterion(
                      getFilterCriteriaList(
                          attributeValue,
                          mtdAtlasSearchVO.getAttributeName(),
                          mtdAtlasSearchVO.getOperator()));
                  return entity;
                })
            .collect(Collectors.toList()));
    return entityFilters;
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
                  .qualifiedName(
                      e.getAttributes().get("qualifiedName").toString().replace(".", "/"))
                  .createTime(
                      Objects.isNull(e.getAttributes().get("createTime"))
                          ? null
                          : new Date((Long) e.getAttributes().get("createTime")))
                  .build());
        });
    return atlasBaseMainDataVOList;
  }

  @Override
  public MtdColumnVO getColumnDetailByGuid(String guid) {
    MtdColumnVO mtdColumnVO = null;
    try {
      AtlasEntity.AtlasEntityWithExtInfo detail = atlasClientV2.getEntityByGuid(guid, true, false);
      Map<String, Object> attributes = detail.getEntity().getAttributes();
      mtdColumnVO = GsonUtil.fromMap(attributes, MtdColumnVO.class);
      mtdColumnVO.setTypeName(detail.getEntity().getTypeName());
      mtdColumnVO.setDataType(Objects.nonNull(attributes.get("data_type"))?attributes.get("data_type").toString():null);
      mtdColumnVO.setDefaultValue(Objects.nonNull(attributes.get("default_value"))?attributes.get("default_value").toString():null);
      MtdTableInfoVO mtdTableInfoVO = GsonUtil.fromJsonString(GsonUtil.toJsonString(detail.getEntity().getRelationshipAttributes().get("table")), MtdTableInfoVO.class);
      mtdColumnVO.setTable(mtdTableInfoVO);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return mtdColumnVO;
  }

  @Override
  public MtdDbDetailVO getDbDetailByGuid(String guid) {
    MtdDbDetailVO mtdAtlasDbDetailVO = null;
    try {
      AtlasEntity.AtlasEntityWithExtInfo detail = atlasClientV2.getEntityByGuid(guid, true, false);
      Map<String, Object> attributes = detail.getEntity().getAttributes();
      mtdAtlasDbDetailVO = GsonUtil.fromMap(attributes, MtdDbDetailVO.class);
      mtdAtlasDbDetailVO.setTypeName(detail.getEntity().getTypeName());
      mtdAtlasDbDetailVO.setTables(buildReferredEntities(detail));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return mtdAtlasDbDetailVO;
  }

  @Override
  public MtdTableDetailVO getTableDetailByGuid(String guid) {
    MtdTableDetailVO mtdTableDetailVO = null;
    try {
      AtlasEntity.AtlasEntityWithExtInfo detail = atlasClientV2.getEntityByGuid(guid, true, false);
      Map<String, Object> attributes = detail.getEntity().getAttributes();
      mtdTableDetailVO = GsonUtil.fromMap(attributes, MtdTableDetailVO.class);
      MtdDbInfoVO mtdDbInfoVO = GsonUtil.fromJsonString(GsonUtil.toJsonString(detail.getEntity().getRelationshipAttributes().get("db")), MtdDbInfoVO.class);
      mtdTableDetailVO.setDb(mtdDbInfoVO);
      mtdTableDetailVO.setTypeName(detail.getEntity().getTypeName());
      mtdTableDetailVO.setColumns(buildReferredEntities(detail));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return mtdTableDetailVO;
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
              attr.put("typeName",e.getTypeName());
              return attr;
            })
        .collect(Collectors.toList());
  }

  @Override
  public Map<String, List<MtdAtlasEntityType>> getAllEntityType() {
    Map<String, List<MtdAtlasEntityType>> mtdAtlasEntityMap = new HashMap<>();
    try {
      SearchFilter searchFilter = new SearchFilter();
      searchFilter.setParam("type", "entity");
      List<AtlasTypeDefHeader> allTypeDefHeaders = atlasClientV2.getAllTypeDefHeaders(searchFilter);
      List<MtdAtlasEntityType> mtdAtlasEntityTypeVOList =
          GsonUtil.fromJsonString(
              GsonUtil.toJsonString(allTypeDefHeaders),
              new TypeToken<List<MtdAtlasEntityType>>() {}.getType());
      mtdAtlasEntityMap =
          mtdAtlasEntityTypeVOList.stream()
              .collect(Collectors.groupingBy(MtdAtlasEntityType::getServiceType));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return mtdAtlasEntityMap;
  }

  @Override
  public void deleteEntitiesByGuids(String guids) {
    try {
      atlasClientV2.deleteEntitiesByGuids(Arrays.asList(guids.split(",")));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws AtlasServiceException {
    AtlasEntity.AtlasEntityWithExtInfo detail = atlasClientV2.getEntityByGuid("85b14f4d-d09b-4c98-9004-069b7c21a289", true, false);
    System.out.println(GsonUtil.toJsonString(detail));
  }
}
