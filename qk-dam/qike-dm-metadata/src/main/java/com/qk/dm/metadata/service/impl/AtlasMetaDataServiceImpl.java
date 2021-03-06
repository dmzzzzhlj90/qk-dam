package com.qk.dm.metadata.service.impl;

import com.google.common.collect.Sets;
import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.metedata.AtlasClient;
import com.qk.dam.metedata.config.AtlasConfig;
import com.qk.dam.metedata.entity.MtdAtlasEntityType;
import com.qk.dam.metedata.property.AtlasBaseProperty;
import com.qk.dam.metedata.property.AtlasSearchProperty;
import com.qk.dam.metedata.util.AtlasSearchUtil;
import com.qk.dm.metadata.service.AtlasMetaDataService;
import com.qk.dm.metadata.service.MtdClassifyAtlasService;
import com.qk.dm.metadata.service.MtdLabelsAtlasService;
import com.qk.dm.metadata.vo.*;
import java.util.*;
import java.util.stream.Collectors;
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

/**
 * @author wangzp
 * @date 2021/8/03 10:06
 * @since 1.0.0
 */
@Data
@Service
public class AtlasMetaDataServiceImpl implements AtlasMetaDataService {

  private AtlasClientV2 atlasClientV2;
  private final AtlasClient atlasClient;
  private final MtdLabelsAtlasService mtdLabelsAtlasService;

  private final MtdClassifyAtlasService mtdClassifyAtlasService;

  public AtlasMetaDataServiceImpl(
          AtlasClient atlasClient, MtdLabelsAtlasService mtdLabelsAtlasService,
          MtdClassifyAtlasService mtdClassifyAtlasService) {
    this.atlasClient = atlasClient;
    this.mtdLabelsAtlasService = mtdLabelsAtlasService;
    this.mtdClassifyAtlasService = mtdClassifyAtlasService;
    this.atlasClientV2=atlasClient.instance();
  }

  @Override
  public List<MtdAtlasBaseVO> searchList(MtdAtlasParamsVO mtdAtlasParamsVO) {
    try {
      AtlasSearchResult atlasSearchResult = atlasClientV2.basicSearch(mtdAtlasParamsVO.getTypeName(),
              mtdAtlasParamsVO.getClassification(), mtdAtlasParamsVO.getQuery(), true,
              mtdAtlasParamsVO.getLimit(), mtdAtlasParamsVO.getOffse());
      return buildMataDataList(atlasSearchResult.getEntities());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Collections.emptyList();
  }

  @Override
  public List<MtdAtlasBaseVO> searchList(MtdAtlasBaseSearchVO mtdAtlasParamsVO, Boolean excludeDeletedEntities) {
    try {
      AtlasSearchResult atlasSearchResult = atlasClientV2.basicSearch(mtdAtlasParamsVO.getTypeName(),
              getFilterCriteria(mtdAtlasParamsVO), mtdAtlasParamsVO.getClassification(),
              mtdAtlasParamsVO.getQuery(), true,
              mtdAtlasParamsVO.getLimit(), mtdAtlasParamsVO.getOffset());
       return buildMataDataList(atlasSearchResult.getEntities());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Collections.emptyList();
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


  /**
   * ?????????????????????
   *
   * @param entities
   * @return
   */
  private List<MtdAtlasBaseVO> buildMataDataList(List<AtlasEntityHeader> entities) {
    if(CollectionUtils.isEmpty(entities)) return null;
    List<MtdAtlasBaseVO> atlasBaseMainDataVOList = new ArrayList<>();
    entities.forEach(e -> {
          atlasBaseMainDataVOList.add(MtdAtlasBaseVO.builder().guid(e.getGuid()).typeName(e.getTypeName())
                  .displayName(e.getDisplayText()).qualifiedName(e.getAttributes().get(AtlasBaseProperty.QUALIFIEDNAME).toString().replace(".", "/"))
                  .createTime(Objects.isNull(e.getAttributes().get(AtlasBaseProperty.CREATE_TIME)) ? null
                          : new Date((Long) e.getAttributes().get(AtlasBaseProperty.CREATE_TIME)))
                  .build());
        });
    return atlasBaseMainDataVOList;
  }

  @Override
  public MtdColumnVO getColumnDetailByGuid(String guid) {
    try {
      AtlasEntity.AtlasEntityWithExtInfo detail = atlasClientV2.getEntityByGuid(guid, true, false);
      Map<String, Object> attributes = detail.getEntity().getAttributes();
      MtdColumnVO  mtdColumnVO = GsonUtil.fromMap(attributes, MtdColumnVO.class);
      mtdColumnVO.setTypeName(detail.getEntity().getTypeName());
      mtdColumnVO.setCreateTime(detail.getEntity().getCreateTime());
      mtdColumnVO.setDataType(getColumnDataType(attributes.get(AtlasBaseProperty.DATA_TYPE),attributes.get(AtlasBaseProperty.TYPE)));
      mtdColumnVO.setDefaultValue(transformation(attributes.get(AtlasBaseProperty.DEFAULT_VALUE)));
      MtdTableInfoVO mtdTableInfoVO = GsonUtil.fromJsonString(GsonUtil.toJsonString(detail.getEntity()
                      .getRelationshipAttributes().get(AtlasBaseProperty.TABLE)), MtdTableInfoVO.class);
      mtdColumnVO.setTable(mtdTableInfoVO);
      mtdColumnVO.setLabels(getLabs(guid));
      mtdColumnVO.setClassification(getClassify(guid));
      return mtdColumnVO;

    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public MtdDbDetailVO getDbDetailByGuid(String guid) {
    try {
      AtlasEntity.AtlasEntityWithExtInfo detail = atlasClientV2.getEntityByGuid(guid,true,true);
      Map<String, Object> attributes = detail.getEntity().getAttributes();
      MtdDbDetailVO mtdAtlasDbDetailVO = GsonUtil.fromMap(attributes, MtdDbDetailVO.class);

      mtdAtlasDbDetailVO.setTypeName(detail.getEntity().getTypeName());
      mtdAtlasDbDetailVO.setCreateTime(detail.getEntity().getCreateTime());
      mtdAtlasDbDetailVO.setServerInfo(Objects.requireNonNullElse(mtdAtlasDbDetailVO.getServerInfo(),
              attributes.getOrDefault(AtlasBaseProperty.CLUSTER_NAME,AtlasBaseProperty.EMPTY).toString()));
      mtdAtlasDbDetailVO.setDisplayText(Objects.requireNonNullElse(detail.getEntity().getAttribute(AtlasBaseProperty.NAME),
              transformation(detail.getEntity().getAttribute(AtlasBaseProperty.DISPLAY_NAME))).toString());
      mtdAtlasDbDetailVO.setTables(buildReferredEntities(detail,mtdAtlasDbDetailVO));
      mtdAtlasDbDetailVO.setLabels(getLabs(guid));
      mtdAtlasDbDetailVO.setClassification(getClassify(guid));
      return mtdAtlasDbDetailVO;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public MtdTableDetailVO getTableDetailByGuid(String guid) {
    try {
      AtlasEntity.AtlasEntityWithExtInfo detail = atlasClientV2.getEntityByGuid(guid, true, false);
      Map<String, Object> attributes = detail.getEntity().getAttributes();
      attributes.remove(AtlasBaseProperty.CREATE_TIME);
      MtdTableDetailVO mtdTableDetailVO = GsonUtil.fromMap(attributes, MtdTableDetailVO.class);
      MtdDbInfoVO mtdDbInfoVO = GsonUtil.fromJsonString(GsonUtil.toJsonString(
              detail.getEntity().getRelationshipAttributes().get(AtlasBaseProperty.DB)), MtdDbInfoVO.class);
      mtdTableDetailVO.setDb(mtdDbInfoVO);
      mtdTableDetailVO.setTypeName(detail.getEntity().getTypeName());
      mtdTableDetailVO.setCreateTime(detail.getEntity().getCreateTime());
      mtdTableDetailVO.setColumns(buildReferredEntities(detail));
      mtdTableDetailVO.setLabels(getLabs(guid));
      mtdTableDetailVO.setClassification(getClassify(guid));
      return mtdTableDetailVO;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }


  @Override
  public List<MtdTableVO> getTableList(MtdTableParaVO mtdTableParaVO) {
    List<AtlasEntityHeader> atlasEntityHeaderList = AtlasSearchUtil.getTableList(transTypeName(mtdTableParaVO.getTypeName()),
            mtdTableParaVO.getDbName(), mtdTableParaVO.getServer(),
            mtdTableParaVO.getLimit(), mtdTableParaVO.getOffset());
    return builderMtdTables(atlasEntityHeaderList);
  }

  /**
   * ????????????????????????
   * @param atlasEntityHeaderList
   * @return
   */
  private List<MtdTableVO> builderMtdTables(List<AtlasEntityHeader> atlasEntityHeaderList) {
    if (CollectionUtils.isEmpty(atlasEntityHeaderList)) { return null; }
    return atlasEntityHeaderList.stream().map(e -> MtdTableVO.builder()
            .name(e.getDisplayText())
            .guid(e.getGuid())
            .typeName(e.getTypeName())
            .owner(e.getAttributes().getOrDefault(AtlasBaseProperty.OWNER,AtlasBaseProperty.EMPTY).toString())
            .createTime(formatDate(e.getAttributes().get(AtlasBaseProperty.CREATE_TIME)))
            .qualifiedName(transformation(e.getAttributes().get(AtlasBaseProperty.QUALIFIEDNAME)))
            .comment(transformation(e.getAttribute(AtlasSearchProperty.AttributeName.DESCRIPTION)))
            .description(transformation(e.getAttribute(AtlasSearchProperty.AttributeName.DESCRIPTION)))
            .classification(String.join(AtlasBaseProperty.EMPTY,e.getClassificationNames()))
            .labels(String.join(AtlasBaseProperty.EMPTY,e.getLabels()))
            .build()).collect(Collectors.toList());
  }

  /**
   * ??????????????????
   *
   * @param detail
   * @return
   */
  private List<Map<String, Object>> buildReferredEntities(AtlasEntity.AtlasEntityWithExtInfo detail, MtdDbDetailVO mtdAtlasDbDetailVO) {
    List<AtlasEntity> atlasEntityList = new ArrayList<>(detail.getReferredEntities().values());
    Map<String, List<MtdLabelsAtlasVO>> labsMap = getlabs(atlasEntityList);
    Map<String, List<MtdClassifyAtlasVO>> classMap = getClassification(atlasEntityList);
      List<AtlasEntityHeader> tableList = AtlasSearchUtil.getTableList(transTypeName(mtdAtlasDbDetailVO.getTypeName())
              ,mtdAtlasDbDetailVO.getDisplayText(), mtdAtlasDbDetailVO.getServerInfo(), 1000, 0);
      if(CollectionUtils.isEmpty(tableList)) return Collections.emptyList();
      return tableList.stream().map(e -> buildDetail(e.getAttributes(),e.getGuid(),e.getTypeName(),labsMap,classMap))
              .collect(Collectors.toList());
  }
  /**
   * ??????????????????
   *
   * @param detail
   * @return
   */
  private List<Map<String, Object>> buildReferredEntities(AtlasEntity.AtlasEntityWithExtInfo detail) {
    List<AtlasEntity> atlasEntityList = new ArrayList<>(detail.getReferredEntities().values());
    Map<String, List<MtdLabelsAtlasVO>> labsMap = getlabs(atlasEntityList);
    Map<String, List<MtdClassifyAtlasVO>> classMap = getClassification(atlasEntityList);
    return atlasEntityList.stream().map(e -> buildColumnDetail(e.getAttributes(),e.getCreateTime(),e.getGuid(),e.getTypeName(),labsMap,classMap))
            .collect(Collectors.toList());
  }

  /**
   * ??????????????????
   * @param attr
   * @param createTime
   * @param guid
   * @param typeName
   * @param labsMap
   * @param classMap
   * @return
   */
  private Map<String,Object> buildColumnDetail(Map<String, Object> attr,Date createTime,String guid,String typeName,
                                         Map<String, List<MtdLabelsAtlasVO>> labsMap, Map<String, List<MtdClassifyAtlasVO>> classMap){
    attr.put(AtlasBaseProperty.CREATE_TIME,DateFormatUtils.format(createTime,"yyyy-MM-dd HH:mm"));
    attr.put(AtlasBaseProperty.GUID, guid);
    attr.put(AtlasBaseProperty.TYPENAME, typeName);
    attr.put(AtlasBaseProperty.DATATYPE,getColumnDataType(attr.get(AtlasBaseProperty.DATA_TYPE),attr.get(AtlasBaseProperty.TYPE)));
    List<MtdLabelsAtlasVO> labList = labsMap.get(guid);
    attr.put(AtlasBaseProperty.LABELS,CollectionUtils.isEmpty(labList)?AtlasBaseProperty.EMPTY:labsMap.get(guid).get(0).getLabels());
    attr.put(AtlasBaseProperty.CLASSIFICATION,CollectionUtils.isEmpty(classMap.get(guid))?AtlasBaseProperty.EMPTY: classMap.get(guid).get(0).getClassify());
    return attr;
  }

  private Map<String,Object> buildDetail(Map<String, Object> attr,String guid,String typeName,
                                         Map<String, List<MtdLabelsAtlasVO>> labsMap, Map<String, List<MtdClassifyAtlasVO>> classMap){
    attr.put(AtlasBaseProperty.CREATE_TIME,formatDate(attr.get(AtlasBaseProperty.CREATE_TIME)));
    attr.put(AtlasBaseProperty.GUID, guid);
    attr.put(AtlasBaseProperty.TYPENAME, typeName);
    attr.put(AtlasBaseProperty.LABELS,CollectionUtils.isEmpty(labsMap.get(guid))?AtlasBaseProperty.EMPTY:labsMap.get(guid).get(0).getLabels());
    attr.put(AtlasBaseProperty.CLASSIFICATION,CollectionUtils.isEmpty(classMap.get(guid))?AtlasBaseProperty.EMPTY: classMap.get(guid).get(0).getClassify());
    return attr;
  }

  /**
   * ??????????????????
   *
   * @param atlasEntityList
   * @return
   */
  private Map<String, List<MtdLabelsAtlasVO>> getlabs(List<AtlasEntity> atlasEntityList) {
    List<String> guidList = atlasEntityList.stream().map(AtlasEntity::getGuid).collect(Collectors.toList());
    List<MtdLabelsAtlasVO> labelsAtlasVOList = mtdLabelsAtlasService.getByBulk(guidList);
    if (!CollectionUtils.isEmpty(labelsAtlasVOList)) {
      return labelsAtlasVOList.stream().collect(Collectors.groupingBy(MtdLabelsAtlasVO::getGuid));
    }
    return Collections.emptyMap();
  }

  /**
   * ????????????
   *
   * @param atlasEntityList
   */
  private Map<String, List<MtdClassifyAtlasVO>> getClassification(
      List<AtlasEntity> atlasEntityList) {
    List<String> guidList = atlasEntityList.stream().map(AtlasEntity::getGuid).collect(Collectors.toList());
    List<MtdClassifyAtlasVO> classList = mtdClassifyAtlasService.getByBulk(guidList);
    if (!CollectionUtils.isEmpty(classList)) {
      return classList.stream().collect(Collectors.groupingBy(MtdClassifyAtlasVO::getGuid));
    }
    return Collections.emptyMap();
  }

  @Override
  public Map<String, List<MtdAtlasEntityType>> getAllEntityType() {
    try {
      SearchFilter searchFilter = new SearchFilter();
      searchFilter.setParam(AtlasBaseProperty.TYPE, AtlasBaseProperty.ENTITY);
      List<AtlasTypeDefHeader> allTypeDefHeaders = atlasClientV2.getAllTypeDefHeaders(searchFilter);
      List<MtdAtlasEntityType> mtdAtlasEntityTypeVOList = GsonUtil.fromJsonString(GsonUtil.toJsonString(allTypeDefHeaders),
              new TypeToken<List<MtdAtlasEntityType>>() {}.getType());
      return mtdAtlasEntityTypeVOList.stream().collect(Collectors.groupingBy(mtd ->Objects.requireNonNullElse(mtd.getServiceType(),AtlasBaseProperty.EMPTY)));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Collections.emptyMap();
  }

  @Override
  public void deleteEntitiesByGuids(String guids) {
    try {
      atlasClientV2.deleteEntitiesByGuids(Arrays.asList(guids.split(",")));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * ????????????
   * @param guid
   * @return
   */
  private String getLabs(String guid){
    MtdLabelsAtlasVO mtdLabelsAtlasVO = mtdLabelsAtlasService.getByGuid(guid);
    return Objects.isNull(mtdLabelsAtlasVO)?null:mtdLabelsAtlasVO.getLabels();
  }

  /**
   * '????????????
   * @param guid
   * @return
   */
  private String getClassify(String guid){
    MtdClassifyAtlasVO mtdClass = mtdClassifyAtlasService.getByGuid(guid);
    return Objects.isNull(mtdClass)?null:mtdClass.getClassify();
  }

  private String transformation(Object obj){
    return String.valueOf(Objects.requireNonNullElse(obj,AtlasBaseProperty.EMPTY));
  }

  private String formatDate(Object obj){
    return Objects.isNull(obj)?AtlasBaseProperty.EMPTY:DateFormatUtils.format((Long)obj, "yyyy-MM-dd HH:mm");
  }

  private String transTypeName(String typeName){
    return String.join(AtlasBaseProperty.UNDER_LINE,typeName.split(AtlasBaseProperty.UNDER_LINE)[0], AtlasBaseProperty.TABLE);
  }
  public String getColumnDataType(Object sourceValue,Object defaultValue){
    return transformation(Objects.isNull(sourceValue)? defaultValue:sourceValue);
  }
  @Override
  public AtlasEntity getDetailByQName(String qualifiedName, String typename) {
    Map<String, String> uniqAttributes = new HashMap<>();
    uniqAttributes.put("qualifiedName", qualifiedName);
    try {
      AtlasEntity.AtlasEntityWithExtInfo entityHeaderByAttribute =
              atlasClientV2.getEntityByAttribute(typename, uniqAttributes, true, true);
      // todo ??????????????????DB  String qfd = qualifiedName.split("\\.")[0]+qualifiedName.split("@")[1];
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
}
