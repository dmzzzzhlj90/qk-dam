package com.qk.dm.metadata.service.impl;

import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.util.GsonUtil;
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
import org.apache.atlas.model.SearchFilter;
import org.apache.atlas.model.discovery.AtlasSearchResult;
import org.apache.atlas.model.discovery.SearchParameters;
import org.apache.atlas.model.instance.AtlasEntity;
import org.apache.atlas.model.instance.AtlasEntityHeader;
import org.apache.atlas.model.typedef.AtlasTypeDefHeader;
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
   * 组装元数据列表
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
      mtdColumnVO.setDataType(String.valueOf(Objects.requireNonNullElse(attributes.get(AtlasBaseProperty.DATA_TYPE),AtlasBaseProperty.EMPTY)));
      mtdColumnVO.setDefaultValue(String.valueOf(Objects.requireNonNullElse(attributes.get(AtlasBaseProperty.DEFAULT_VALUE),AtlasBaseProperty.EMPTY)));
      MtdTableInfoVO mtdTableInfoVO = GsonUtil.fromJsonString(GsonUtil.toJsonString(detail.getEntity().getRelationshipAttributes().get(AtlasBaseProperty.TABLE)),
              MtdTableInfoVO.class);
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
      mtdAtlasDbDetailVO.setServerInfo(Objects.requireNonNullElse(mtdAtlasDbDetailVO.getServerInfo(),attributes.getOrDefault(AtlasBaseProperty.CLUSTER_NAME,AtlasBaseProperty.EMPTY).toString()));
      mtdAtlasDbDetailVO.setDisplayText(Objects.requireNonNullElse(detail.getEntity().getAttribute(AtlasBaseProperty.NAME),
              Objects.isNull(detail.getEntity().getAttribute(AtlasBaseProperty.DISPLAY_NAME))?AtlasBaseProperty.EMPTY:detail.getEntity().getAttribute(AtlasBaseProperty.DISPLAY_NAME)).toString());
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
    List<AtlasEntityHeader> atlasEntityHeaderList = AtlasSearchUtil.getTableList(String.join(AtlasBaseProperty.UNDER_LINE,mtdTableParaVO.getTypeName().split(AtlasBaseProperty.UNDER_LINE)[0], AtlasBaseProperty.TABLE),
            mtdTableParaVO.getDbName(), mtdTableParaVO.getServer(),
            mtdTableParaVO.getLimit(), mtdTableParaVO.getOffset());
    return builderMtdTables(atlasEntityHeaderList);
  }

  /**
   * 组装表元数据信息
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
            .createTime(e.getAttributes().get(AtlasBaseProperty.CREATE_TIME)==null?AtlasBaseProperty.EMPTY:DateFormatUtils.format((Long)e.getAttributes().get(AtlasBaseProperty.CREATE_TIME), "yyyy-MM-dd HH:mm"))
            .qualifiedName(e.getAttributes().getOrDefault(AtlasBaseProperty.QUALIFIEDNAME,AtlasBaseProperty.EMPTY).toString())
            .comment(Objects.requireNonNullElse(e.getAttribute(AtlasSearchProperty.AttributeName.DESCRIPTION),AtlasBaseProperty.EMPTY).toString())
            .description(Objects.requireNonNullElse(e.getAttribute(AtlasSearchProperty.AttributeName.DESCRIPTION),AtlasBaseProperty.EMPTY).toString())
            .classification(String.join(AtlasBaseProperty.EMPTY,e.getClassificationNames()))
            .labels(String.join(AtlasBaseProperty.EMPTY,e.getLabels()))
            .build()).collect(Collectors.toList());
  }

  /**
   * 组装参考实体
   *
   * @param detail
   * @return
   */
  private List<Map<String, Object>> buildReferredEntities(AtlasEntity.AtlasEntityWithExtInfo detail, MtdDbDetailVO mtdAtlasDbDetailVO) {
    List<AtlasEntity> atlasEntityList = new ArrayList<>(detail.getReferredEntities().values());
    Map<String, List<MtdLabelsAtlasVO>> labsMap = getlabs(atlasEntityList);
    Map<String, List<MtdClassifyAtlasVO>> classMap = getClassification(atlasEntityList);
      List<AtlasEntityHeader> tableList = AtlasSearchUtil.getTableList(String.join(AtlasBaseProperty.UNDER_LINE,mtdAtlasDbDetailVO.getTypeName().split(AtlasBaseProperty.UNDER_LINE)[0], AtlasBaseProperty.TABLE)
              ,mtdAtlasDbDetailVO.getDisplayText(), mtdAtlasDbDetailVO.getServerInfo(), 1000, 0);
      if(CollectionUtils.isEmpty(tableList)) return new ArrayList<>();
      return tableList.stream().map(e -> buildDetail(e.getAttributes(),e.getGuid(),e.getTypeName(),labsMap,classMap))
              .collect(Collectors.toList());
  }
  /**
   * 组装参考实体
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
   * 组装字段详情
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
    attr.put(AtlasBaseProperty.CREATE_TIME,Objects.nonNull(createTime)?DateFormatUtils.format(createTime, "yyyy-MM-dd HH:mm"):AtlasBaseProperty.EMPTY);
    attr.put(AtlasBaseProperty.GUID, guid);
    attr.put(AtlasBaseProperty.TYPENAME, typeName);
    List<MtdLabelsAtlasVO> labList = labsMap.get(guid);
    attr.put(AtlasBaseProperty.LABELS,CollectionUtils.isEmpty(labList)?null:labsMap.get(guid).get(0).getLabels());
    attr.put(AtlasBaseProperty.CLASSIFICATION,CollectionUtils.isEmpty(classMap.get(guid))?null: classMap.get(guid).get(0).getClassify());
    return attr;
  }

  private Map<String,Object> buildDetail(Map<String, Object> attr,String guid,String typeName,
                                         Map<String, List<MtdLabelsAtlasVO>> labsMap, Map<String, List<MtdClassifyAtlasVO>> classMap){
    attr.put(AtlasBaseProperty.CREATE_TIME,Objects.nonNull(attr.get(AtlasBaseProperty.CREATE_TIME))?DateFormatUtils.format((Long)attr.get(AtlasBaseProperty.CREATE_TIME), "yyyy-MM-dd HH:mm"):AtlasBaseProperty.EMPTY);
    attr.put(AtlasBaseProperty.GUID, guid);
    attr.put(AtlasBaseProperty.TYPENAME, typeName);
    attr.put(AtlasBaseProperty.LABELS,CollectionUtils.isEmpty(labsMap.get(guid))?null:labsMap.get(guid).get(0).getLabels());
    attr.put(AtlasBaseProperty.CLASSIFICATION,CollectionUtils.isEmpty(classMap.get(guid))?null: classMap.get(guid).get(0).getClassify());
    return attr;
  }

  /**
   * 批量获取标签
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
   * 获取分类
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
   * 获取标签
   * @param guid
   * @return
   */
  private String getLabs(String guid){
    MtdLabelsAtlasVO mtdLabelsAtlasVO = mtdLabelsAtlasService.getByGuid(guid);
    return Objects.isNull(mtdLabelsAtlasVO)?null:mtdLabelsAtlasVO.getLabels();
  }

  /**
   * '获取分类
   * @param guid
   * @return
   */
  private String getClassify(String guid){
    MtdClassifyAtlasVO mtdClass = mtdClassifyAtlasService.getByGuid(guid);
    return Objects.isNull(mtdClass)?null:mtdClass.getClassify();
  }
}
