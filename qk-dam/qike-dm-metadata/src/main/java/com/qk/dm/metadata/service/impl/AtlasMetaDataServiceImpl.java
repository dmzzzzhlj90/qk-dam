package com.qk.dm.metadata.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.metedata.config.AtlasConfig;
import com.qk.dam.metedata.entity.MtdAtlasEntityType;
import com.qk.dam.metedata.property.AtlasSearchProperty;
import com.qk.dm.metadata.mapstruct.mapper.MtdCommonDetailMapper;
import com.qk.dm.metadata.service.AtlasMetaDataService;
import com.qk.dm.metadata.service.MtdClassifyAtlasService;
import com.qk.dm.metadata.service.MtdLabelsAtlasService;
import com.qk.dm.metadata.vo.*;
import lombok.Data;
import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.SearchFilter;
import org.apache.atlas.model.audit.EntityAuditEventV2;
import org.apache.atlas.model.discovery.AtlasSearchResult;
import org.apache.atlas.model.discovery.SearchParameters;
import org.apache.atlas.model.instance.AtlasEntity;
import org.apache.atlas.model.instance.AtlasEntityHeader;
import org.apache.atlas.model.typedef.AtlasTypeDefHeader;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wangzp
 * @date 2021/8/03 10:06
 * @since 1.0.0
 */
@Data
@Service
public class AtlasMetaDataServiceImpl implements AtlasMetaDataService {
  private static final String DB = "db";
  private static final String TABLE = "table";
  private static final String COLUMN = "column";

  private static final AtlasClientV2 atlasClientV2 = AtlasConfig.getAtlasClientV2();

  private final MtdLabelsAtlasService mtdLabelsAtlasService;

  private final MtdClassifyAtlasService mtdClassifyAtlasService;

  @Autowired
  public AtlasMetaDataServiceImpl(
      MtdLabelsAtlasService mtdLabelsAtlasService,
      MtdClassifyAtlasService mtdClassifyAtlasService) {
    this.mtdLabelsAtlasService = mtdLabelsAtlasService;
    this.mtdClassifyAtlasService = mtdClassifyAtlasService;
  }

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
              AtlasSearchProperty.AttributeName.TYPENAME,
              AtlasSearchProperty.Operator.EQ,
              SearchParameters.FilterCriteria.Condition.OR));
    }
    if (mtdAtlasParamsVO.getNameValue() != null && mtdAtlasParamsVO.getNameValue().length > 0) {
      filterCriteriaList.add(
          getFilterCriteria(
              mtdAtlasParamsVO.getNameValue(),
              AtlasSearchProperty.AttributeName.NAME,
              AtlasSearchProperty.Operator.CONTAINS,
              SearchParameters.FilterCriteria.Condition.AND));
    }
    if (mtdAtlasParamsVO.getLabelsValue() != null && mtdAtlasParamsVO.getLabelsValue().length > 0) {
      SearchParameters.FilterCriteria entity = new SearchParameters.FilterCriteria();
      entity.setCondition(SearchParameters.FilterCriteria.Condition.AND);
      entity.setCriterion(
          Arrays.asList(
              getFilterCriteria(
                  mtdAtlasParamsVO.getLabelsValue(),
                  AtlasSearchProperty.AttributeName.LABELS,
                  AtlasSearchProperty.Operator.CONTAINS,
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
  public List<EntityAuditEventV2> getAuditByGuid(String guid, String startKey) {
    try {
      List<EntityAuditEventV2> tmp = Lists.newArrayList();
      List<EntityAuditEventV2> auditEvents1 =
          atlasClientV2.getAuditEvents(
              guid, startKey, EntityAuditEventV2.EntityAuditActionV2.ENTITY_CREATE, (short) 1);
      List<EntityAuditEventV2> auditEvents2 =
          atlasClientV2.getAuditEvents(
              guid, startKey, EntityAuditEventV2.EntityAuditActionV2.ENTITY_UPDATE, (short) 10);
      List<EntityAuditEventV2> auditEvents3 =
          atlasClientV2.getAuditEvents(
              guid, startKey, EntityAuditEventV2.EntityAuditActionV2.ENTITY_DELETE, (short) 10);
      tmp.addAll(auditEvents1);
      tmp.addAll(auditEvents2);
      tmp.addAll(auditEvents3);
      return tmp;
    } catch (AtlasServiceException e) {
      e.printStackTrace();
    }
    return List.of();
  }

  @Override
  public MtdCommonDetailVO getDetailByGuid(String guid, String typeName) {
    MtdCommonDetailVO mtdCommonDetailVO = null;
    Map<String, Object> info = new HashMap<>();
    if (typeName.contains(DB)) {
      MtdDbDetailVO dbDetail = getDbDetailByGuid(guid);
      mtdCommonDetailVO = MtdCommonDetailMapper.INSTANCE.userMtdCommonDetail(dbDetail);
      info.put("tables", dbDetail.getTables());
      mtdCommonDetailVO.setRelationshipAttributes(info);
    } else if (typeName.contains(TABLE)) {
      MtdTableDetailVO tableDetail = getTableDetailByGuid(guid);
      mtdCommonDetailVO = MtdCommonDetailMapper.INSTANCE.userMtdCommonDetail(tableDetail);
      info.put("columns", tableDetail.getColumns());
      mtdCommonDetailVO.setRelationshipAttributes(info);
    } else if (typeName.contains(COLUMN)) {
      MtdColumnVO columnDetail = getColumnDetailByGuid(guid);
      mtdCommonDetailVO = MtdCommonDetailMapper.INSTANCE.userMtdCommonDetail(columnDetail);
      info.put("table", columnDetail.getTable());
      mtdCommonDetailVO.setRelationshipAttributes(info);
    }
    return mtdCommonDetailVO;
  }

  @Override
  public AtlasEntity getDetailByQName(String qualifiedName, String typename) {
    Map<String, String> uniqAttributes = new HashMap<>();
    uniqAttributes.put("qualifiedName", qualifiedName);
    try {
      AtlasEntity.AtlasEntityWithExtInfo entityHeaderByAttribute =
          atlasClientV2.getEntityByAttribute(typename, uniqAttributes, true, true);
      // todo 查询最上级的DB  String qfd = qualifiedName.split("\\.")[0]+qualifiedName.split("@")[1];
      MtdLabelsAtlasVO mtdLabelsAtlasVO =
          mtdLabelsAtlasService.getByGuid(entityHeaderByAttribute.getEntity().getGuid());
      AtlasEntity entity = entityHeaderByAttribute.getEntity();
      if (mtdLabelsAtlasVO != null) {
        entity.setLabels(Sets.newHashSet(mtdLabelsAtlasVO.getLabels().split(",")));
      }
      return entity;
    } catch (AtlasServiceException e) {
      e.printStackTrace();
    }

    return null;
  }

  @Override
  public MtdColumnVO getColumnDetailByGuid(String guid) {
    MtdColumnVO mtdColumnVO = null;
    try {
      AtlasEntity.AtlasEntityWithExtInfo detail = atlasClientV2.getEntityByGuid(guid, true, false);
      Map<String, Object> attributes = detail.getEntity().getAttributes();
      mtdColumnVO = GsonUtil.fromMap(attributes, MtdColumnVO.class);
      mtdColumnVO.setTypeName(detail.getEntity().getTypeName());
      mtdColumnVO.setCreateTime(detail.getEntity().getCreateTime());
      mtdColumnVO.setDataType(
          Objects.nonNull(attributes.get("data_type"))
              ? attributes.get("data_type").toString()
              : null);
      mtdColumnVO.setDefaultValue(
          Objects.nonNull(attributes.get("default_value"))
              ? attributes.get("default_value").toString()
              : null);
      MtdTableInfoVO mtdTableInfoVO =
          GsonUtil.fromJsonString(
              GsonUtil.toJsonString(detail.getEntity().getRelationshipAttributes().get("table")),
              MtdTableInfoVO.class);
      mtdColumnVO.setTable(mtdTableInfoVO);
      MtdLabelsAtlasVO mtdLabelsAtlasVO = mtdLabelsAtlasService.getByGuid(guid);
      if (Objects.nonNull(mtdLabelsAtlasVO)) {
        mtdColumnVO.setLabels(mtdLabelsAtlasVO.getLabels());
      }
      MtdClassifyAtlasVO mtdClass = mtdClassifyAtlasService.getByGuid(guid);
      if (Objects.nonNull(mtdClass)) {
        mtdColumnVO.setClassification(mtdClass.getClassify());
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return mtdColumnVO;
  }

  public MtdCommonDetailVO getDetailByGuid(String guid) {
    Map<String, Object> info = new HashMap<>();
    MtdCommonDetailVO mtdCommonDetailVO = null;
    try {
      AtlasEntity.AtlasEntityWithExtInfo detail = atlasClientV2.getEntityByGuid(guid, true, false);
      Map<String, Object> attributes = detail.getEntity().getAttributes();
      attributes.remove("createTime");
      mtdCommonDetailVO = GsonUtil.fromMap(attributes, MtdCommonDetailVO.class);
      mtdCommonDetailVO.setTypeName(detail.getEntity().getTypeName());
      mtdCommonDetailVO.setCreateTime(detail.getEntity().getCreateTime());
      MtdLabelsAtlasVO mtdLabelsAtlasVO = mtdLabelsAtlasService.getByGuid(guid);
      if (Objects.nonNull(mtdLabelsAtlasVO)) {
        mtdCommonDetailVO.setLabels(mtdLabelsAtlasVO.getLabels());
      }
      MtdClassifyAtlasVO mtdClass = mtdClassifyAtlasService.getByGuid(guid);
      if (Objects.nonNull(mtdClass)) {
        mtdCommonDetailVO.setClassification(mtdClass.getClassify());
      }
      if (mtdCommonDetailVO.getTypeName().contains(DB)) {
        info.put("tables", buildReferredEntities(detail));
        mtdCommonDetailVO.setRelationshipAttributes(info);
      } else if (mtdCommonDetailVO.getTypeName().contains(TABLE)) {
        MtdDbInfoVO mtdDbInfoVO =
            GsonUtil.fromJsonString(
                GsonUtil.toJsonString(detail.getEntity().getRelationshipAttributes().get("db")),
                MtdDbInfoVO.class);
        mtdCommonDetailVO.setDb(mtdDbInfoVO);
        info.put("columns", buildReferredEntities(detail));
        mtdCommonDetailVO.setRelationshipAttributes(info);
      } else if (mtdCommonDetailVO.getTypeName().contains(COLUMN)) {
        mtdCommonDetailVO.setDataType(
            Objects.nonNull(attributes.get("data_type"))
                ? attributes.get("data_type").toString()
                : null);
        mtdCommonDetailVO.setDefaultValue(
            Objects.nonNull(attributes.get("default_value"))
                ? attributes.get("default_value").toString()
                : null);
        MtdTableInfoVO mtdTableInfoVO =
            GsonUtil.fromJsonString(
                GsonUtil.toJsonString(detail.getEntity().getRelationshipAttributes().get("table")),
                MtdTableInfoVO.class);
        info.put("table", mtdTableInfoVO);
        mtdCommonDetailVO.setRelationshipAttributes(info);
        MtdDbInfoVO mtdDbInfoVO =
            GsonUtil.fromJsonString(
                GsonUtil.toJsonString(detail.getEntity().getRelationshipAttributes().get("db")),
                MtdDbInfoVO.class);
        mtdCommonDetailVO.setDb(mtdDbInfoVO);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return mtdCommonDetailVO;
  }

  @Override
  public MtdDbDetailVO getDbDetailByGuid(String guid) {
    MtdDbDetailVO mtdAtlasDbDetailVO = null;
    try {
      AtlasEntity.AtlasEntityWithExtInfo detail = atlasClientV2.getEntityByGuid(guid, true, false);
      Map<String, Object> attributes = detail.getEntity().getAttributes();
      mtdAtlasDbDetailVO = GsonUtil.fromMap(attributes, MtdDbDetailVO.class);
      mtdAtlasDbDetailVO.setTypeName(detail.getEntity().getTypeName());
      mtdAtlasDbDetailVO.setCreateTime(detail.getEntity().getCreateTime());
      mtdAtlasDbDetailVO.setTables(buildReferredEntities(detail));
      MtdLabelsAtlasVO mtdLabelsAtlasVO = mtdLabelsAtlasService.getByGuid(guid);
      if (Objects.nonNull(mtdLabelsAtlasVO)) {
        mtdAtlasDbDetailVO.setLabels(mtdLabelsAtlasVO.getLabels());
      }
      MtdClassifyAtlasVO mtdClass = mtdClassifyAtlasService.getByGuid(guid);
      if (Objects.nonNull(mtdClass)) {
        mtdAtlasDbDetailVO.setClassification(mtdClass.getClassify());
      }
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
      attributes.remove("createTime");
      mtdTableDetailVO = GsonUtil.fromMap(attributes, MtdTableDetailVO.class);
      MtdDbInfoVO mtdDbInfoVO =
          GsonUtil.fromJsonString(
              GsonUtil.toJsonString(detail.getEntity().getRelationshipAttributes().get("db")),
              MtdDbInfoVO.class);
      mtdTableDetailVO.setDb(mtdDbInfoVO);
      mtdTableDetailVO.setTypeName(detail.getEntity().getTypeName());
      mtdTableDetailVO.setCreateTime(detail.getEntity().getCreateTime());
      mtdTableDetailVO.setColumns(buildReferredEntities(detail));
      MtdLabelsAtlasVO mtdLabelsAtlasVO = mtdLabelsAtlasService.getByGuid(guid);
      if (Objects.nonNull(mtdLabelsAtlasVO)) {
        mtdTableDetailVO.setLabels(mtdLabelsAtlasVO.getLabels());
      }
      MtdClassifyAtlasVO mtdClass = mtdClassifyAtlasService.getByGuid(guid);
      if (Objects.nonNull(mtdClass)) {
        mtdTableDetailVO.setClassification(mtdClass.getClassify());
      }
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
          //todo 此方法需优化
      AtlasEntity.AtlasEntityWithExtInfo detail) {
    List<AtlasEntity> atlasEntityList = new ArrayList<>(detail.getReferredEntities().values());
   if(atlasEntityList.size()==0){
     List<Map<String,Object>> list= (List<Map<String,Object>>)detail.getEntity().getRelationshipAttributes().get("tables");
     ObjectMapper mapper = new ObjectMapper();
     list =  mapper.convertValue(list, new TypeReference<>() {
     });
     list.forEach(e->{
       e.put("name",e.get("displayText"));
       e.remove("relationshipAttributes");
       e.remove("relationshipStatus");
       e.remove("relationshipGuid");
     });
    return list;
   }
    Map<String, List<MtdLabelsAtlasVO>> labsMap = getlabs(atlasEntityList);
    Map<String, List<MtdClassifyAtlasVO>> classMap = getClassification(atlasEntityList);
    return atlasEntityList.stream()
        .map(
            e -> {
              Map<String, Object> attr = e.getAttributes();
              attr.put("guid", e.getGuid());
              attr.put("typeName", e.getTypeName());
              attr.put(
                  "createTime", DateFormatUtils.format(e.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
              attr.put("qualifiedName",Objects.isNull(attr.get("qualifiedName"))?null:attr.get("qualifiedName").toString().split("@")[0]);
             if(Objects.isNull(attr.get("data_type"))&&Objects.nonNull(attr.get("type"))){
                  attr.put("data_type",attr.get("type"));
             }
              List<MtdLabelsAtlasVO> labList = labsMap.get(e.getGuid());
              if (!CollectionUtils.isEmpty(labList)) {
                attr.put("labels", labsMap.get(e.getGuid()).get(0).getLabels());
              } else {
                attr.put("labels", null);
              }
              List<MtdClassifyAtlasVO> classList = classMap.get(e.getGuid());
              if (!CollectionUtils.isEmpty(classList)) {
                attr.put("classification", classMap.get(e.getGuid()).get(0).getClassify());
              } else {
                attr.put("classification", null);
              }
              return attr;
            })
        .collect(Collectors.toList());
  }

  /**
   * 批量获取标签
   *
   * @param atlasEntityList
   * @return
   */
  private Map<String, List<MtdLabelsAtlasVO>> getlabs(List<AtlasEntity> atlasEntityList) {
    Map<String, List<MtdLabelsAtlasVO>> labMap = new HashMap<>();
    List<String> guidList =
        atlasEntityList.stream().map(AtlasEntity::getGuid).collect(Collectors.toList());
    List<MtdLabelsAtlasVO> labelsAtlasVOList = mtdLabelsAtlasService.getByBulk(guidList);
    if (!CollectionUtils.isEmpty(labelsAtlasVOList)) {
      labMap = labelsAtlasVOList.stream().collect(Collectors.groupingBy(MtdLabelsAtlasVO::getGuid));
    }
    return labMap;
  }

  /**
   * 获取分类
   *
   * @param atlasEntityList
   */
  private Map<String, List<MtdClassifyAtlasVO>> getClassification(
      List<AtlasEntity> atlasEntityList) {
    Map<String, List<MtdClassifyAtlasVO>> classMap = new HashMap<>();
    List<String> guidList =
        atlasEntityList.stream().map(AtlasEntity::getGuid).collect(Collectors.toList());
    List<MtdClassifyAtlasVO> classList = mtdClassifyAtlasService.getByBulk(guidList);
    if (!CollectionUtils.isEmpty(classList)) {
      classMap = classList.stream().collect(Collectors.groupingBy(MtdClassifyAtlasVO::getGuid));
    }
    return classMap;
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
}
