package com.qk.dam.metadata.catacollect.repo;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.db.Entity;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.qk.dam.metadata.catacollect.pojo.AtlasBaseSearchVO;
import com.qk.dam.metadata.catacollect.util.SourcesUtil;
import com.qk.dam.metedata.property.AtlasBaseProperty;
import com.qk.dam.metedata.property.AtlasSearchProperty;
import com.qk.dam.metedata.util.AtlasSearchUtil;
import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.discovery.AtlasSearchResult;
import org.apache.atlas.model.discovery.SearchParameters;
import org.apache.atlas.model.instance.AtlasEntity;
import org.apache.atlas.model.instance.AtlasEntityHeader;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zys
 * @date 2022/4/25 14:17
 * @since 1.0.0
 */
@Component
public class AtlasAgg {
  private static final Log LOG = LogFactory.get("atals处理器");
  /**
   * 处理元数据采集策略问题
   * @param tableList （导入的表）
   * @param db （导入的数据库）
   * @param atlasClientV2 （Atals操作对象）
   * @param atalsTableName (Atals中表名的key)
   * @param strategy （策略编码 1：仅更新、2：仅添加、3：既更新又添加、4：忽略更新添加）
   * @param atalsEnum 传入的数据类
   * @return
   */
  public List<Entity> checkTable(List<Entity> tableList, String db,
      AtlasClientV2 atlasClientV2,String strategy, String atalsEnum,String atalsTableName)
      throws AtlasServiceException {
    List<Entity> resultTableList = new ArrayList<>();
    Map<String,String> dbMap = getDbList(atalsEnum, atlasClientV2);
    if (MapUtils.isNotEmpty(dbMap) && StringUtils.isNotEmpty(dbMap.get(db))){
      resultTableList=getTableList(dbMap.get(db),atlasClientV2,tableList,strategy,atalsTableName);
    }else {
      resultTableList.addAll(tableList);
    }
    return  resultTableList;
  }

  /**
   * 策略模式返回需要同步的表信息
   * @param dbGuid atals中库的guid
   * @param atlasClientV2 atals操作对象
   * @param tableList 传入需要新增或修改的表名称
   * @param strategy （策略编码 1：仅更新、2：仅添加、3：既更新又添加、4：忽略更新添加）
   * @param atalsTableName (Atals中表名的key)
   * @return
   */
  private  List<Entity> getTableList(String dbGuid, AtlasClientV2 atlasClientV2,
      List<Entity> tableList, String strategy, String atalsTableName)
      throws AtlasServiceException {
    List<Entity> returnTableList = new ArrayList<>();
    switch (strategy) {
      case SourcesUtil.UPDATE_ONLY:
        returnTableList = getUpdateOnly(dbGuid,atlasClientV2,tableList,atalsTableName);
        break;
      case SourcesUtil.ADD_ONLY:
        returnTableList = getAddOnly(dbGuid,atlasClientV2,tableList,atalsTableName);
        break;
      case SourcesUtil.UPDATE_ADD:
        returnTableList = getUpdateAdd(dbGuid,atlasClientV2,tableList,atalsTableName);
        break;
      default:
        break;
    }
    return returnTableList;
  }

  /**
   * 既更新又添加
   * @param dbGuid atals中数据库guid
   * @param atlasClientV2 atals操作对象
   * @param tableList 传入的待添加或更新的表
   * @param atalsTableName (Atals中表名的key)
   * @return
   */
  private  List<Entity> getUpdateAdd(String dbGuid, AtlasClientV2 atlasClientV2,
      List<Entity> tableList, String atalsTableName)
      throws AtlasServiceException {
    List<String> tableGuidList = new ArrayList<>();
    Map<String,String> atalsTableMap = getAtalsTableName(dbGuid,atlasClientV2);
    UpdateAddCrossScreen(tableGuidList,tableList,atalsTableMap,atalsTableName);
    removeAtalsTables(tableGuidList,atlasClientV2);
    return tableList;
  }


  /**
   * 仅添加
   * @param dbGuid atals中数据库guid
   * @param atlasClientV2 atals操作对象
   * @param tableList 传入的待添加或更新的表
   * @param atalsTableName (Atals中表名的key)
   * @return
   */
  private  List<Entity> getAddOnly(String dbGuid, AtlasClientV2 atlasClientV2,
      List<Entity> tableList, String atalsTableName)
      throws AtlasServiceException {
    Map<String,String> atalsTableMap = getAtalsTableName(dbGuid,atlasClientV2);
    onlyAddCrossScreen(tableList,atalsTableMap,atalsTableName);
    return tableList;
  }

  /**
   * 仅更新
   * @param dbGuid atals中数据库guid
   * @param atlasClientV2 atals操作对象
   * @param tableList 传入的待添加或更新的表
   * @param atalsTableName (Atals中表名的key)
   * @return
   */
  private  List<Entity> getUpdateOnly(String dbGuid, AtlasClientV2 atlasClientV2, List<Entity> tableList,
      String atalsTableName)
      throws AtlasServiceException {
    List<String> tableGuidList = new ArrayList<>();
    Map<String,String> atalsTableMap = getAtalsTableName(dbGuid,atlasClientV2);
    onlyUpdateCrossScreen(tableGuidList,tableList,atalsTableMap,atalsTableName);
    removeAtalsTables(tableGuidList,atlasClientV2);
    return tableList;
  }

  /**
   * 删除atals中需要更新的表
   * @param tableGuidList
   * @param atlasClientV2
   */
  private  void removeAtalsTables(List<String> tableGuidList, AtlasClientV2 atlasClientV2)
      throws AtlasServiceException {
    if (CollectionUtil.isNotEmpty(tableGuidList)){
      atlasClientV2.deleteEntitiesByGuids(tableGuidList);
    }else {
      LOG.info("无需要删除的Atals表");
    }
  }

  /**
   *  既更新又添加交叉筛选
   * @param tableGuidList
   * @param tableList
   * @param atalsTableMap
   * @param atalsTableName
   */
  private void UpdateAddCrossScreen(List<String> tableGuidList, List<Entity> tableList, Map<String,String> atalsTableMap, String atalsTableName) {
    if (CollectionUtil.isNotEmpty(tableList)){
      Iterator<Entity> iterator = tableList.iterator();
      while (iterator.hasNext()){
        Entity entity = iterator.next();
        String tableGuid = atalsTableMap.get(entity.get(atalsTableName));
        if (StringUtils.isNotEmpty(tableGuid)){
          tableGuidList.add(tableGuid);
        }
      }
    }else{
      LOG.info("当前传入的表为空");
    }
  }

  /**
   * 仅仅添加交叉筛选
   * @param tableList
   * @param atalsTableMap
   * @param atalsTableName
   */
  private void onlyAddCrossScreen(List<Entity> tableList, Map<String,String> atalsTableMap, String atalsTableName) {
    if (CollectionUtil.isNotEmpty(tableList)){
      Iterator<Entity> iterator = tableList.iterator();
      while (iterator.hasNext()){
        Entity entity = iterator.next();
        String tableGuid = atalsTableMap.get(entity.get(atalsTableName));
        if (StringUtils.isNotEmpty(tableGuid)){
          iterator.remove();
        }
      }
    }else{
      LOG.info("当前传入的表为空");
    }
  }

  /**
   * 仅更新交叉筛选
   * @param tableGuidList
   * @param tableList
   * @param atalsTableMap
   * @param atalsTableName
   */
  private  void onlyUpdateCrossScreen(List<String> tableGuidList, List<Entity> tableList,
      Map<String,String> atalsTableMap, String atalsTableName) {
    if (CollectionUtil.isNotEmpty(tableList)){
      Iterator<Entity> iterator = tableList.iterator();
      while (iterator.hasNext()){
        Entity entity = iterator.next();
        String tableGuid = atalsTableMap.get(entity.get(atalsTableName));
        if (StringUtils.isNotEmpty(tableGuid)){
          tableGuidList.add(tableGuid);
        }else {
          iterator.remove();
        }
      }
    }else{
     LOG.info("当前传入的表为空");
    }
  }

  /**
   * 根据数据库guid查询表名称
   * @param dbGuid
   * @param atlasClientV2
   * @return
   */
  private  Map<String,String> getAtalsTableName(String dbGuid, AtlasClientV2 atlasClientV2)
      throws AtlasServiceException {
    AtlasEntity.AtlasEntityWithExtInfo detail = atlasClientV2.getEntityByGuid(dbGuid,true,true);
    if (Objects.nonNull(detail) && MapUtils.isNotEmpty(detail.getEntity().getAttributes())){
      Map<String, Object> attributes = detail.getEntity().getAttributes();
      String typeName = transTypeName(detail.getEntity().getTypeName());
      String displayText = attributes.get(AtlasBaseProperty.NAME).toString();
      String serverInfo = attributes.get(AtlasSearchProperty.AttributeName.SERVER_INFO).toString();
      List<AtlasEntityHeader> tableList = AtlasSearchUtil
          .getTableList(transTypeName(typeName),displayText, serverInfo, 1000, 0);
      if (CollectionUtil.isNotEmpty(tableList)){
        return tableList.stream().collect(Collectors
            .toMap(e -> e.getAttribute(AtlasSearchProperty.AttributeName.NAME).toString(),e -> e.getGuid(), (k1, k2) -> k2));
      }
    }
    return new HashMap<>();
  }

  private  String transTypeName(String typeName) {
    return String.join(AtlasBaseProperty.UNDER_LINE,typeName.split(AtlasBaseProperty.UNDER_LINE)[0], AtlasBaseProperty.TABLE);
  }

  /**
   * 查询atlas中对应类型的数据库
   * @param atalsEnum
   * @param atlasClientV2
   * @return
   */
  private  Map<String,String> getDbList(String atalsEnum, AtlasClientV2 atlasClientV2)
      throws AtlasServiceException {
    Map<String,String> map  = new HashMap<>();
    AtlasBaseSearchVO atlasBaseSearchVO = new AtlasBaseSearchVO();
    String[] type = new String[]{atalsEnum};
    atlasBaseSearchVO.setTypeNameValue(type);
    AtlasSearchResult atlasSearchResult = atlasClientV2.basicSearch(atlasBaseSearchVO.getTypeName(), getFilterCriteria(atlasBaseSearchVO), atlasBaseSearchVO.getClassification(), atlasBaseSearchVO.getQuery(),
        true, atlasBaseSearchVO.getLimit(), atlasBaseSearchVO.getOffset());
    if (Objects.nonNull(atlasSearchResult.getEntities())){
      map = atlasSearchResult.getEntities().stream().collect(Collectors.toMap(e -> e.getAttribute(AtlasSearchProperty.AttributeName.NAME).toString(),e -> e.getGuid(), (k1, k2) -> k2));
    }
    return map;
  }

  private  SearchParameters.FilterCriteria getFilterCriteria(
      AtlasBaseSearchVO mtdAtlasParamsVO) {
    SearchParameters.FilterCriteria entityFilters = new SearchParameters.FilterCriteria();
    entityFilters.setCondition(SearchParameters.FilterCriteria.Condition.AND);
    List<SearchParameters.FilterCriteria> filterCriteriaList = new ArrayList<>();
    if (mtdAtlasParamsVO.getTypeNameValue() != null
        && mtdAtlasParamsVO.getTypeNameValue().length > 0) {
      filterCriteriaList.add(
          getFilterCriterias(
              mtdAtlasParamsVO.getTypeNameValue(),
              AtlasSearchProperty.AttributeName.TYPENAME,
              AtlasSearchProperty.Operator.EQ,
              SearchParameters.FilterCriteria.Condition.OR));
    }
    if (mtdAtlasParamsVO.getNameValue() != null && mtdAtlasParamsVO.getNameValue().length > 0) {
      filterCriteriaList.add(
          getFilterCriterias(
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
              getFilterCriterias(
                  mtdAtlasParamsVO.getLabelsValue(),
                  AtlasSearchProperty.AttributeName.LABELS,
                  AtlasSearchProperty.Operator.CONTAINS,
                  SearchParameters.FilterCriteria.Condition.OR)));
      filterCriteriaList.add(entity);
    }
    entityFilters.setCriterion(filterCriteriaList);
    return entityFilters;
  }

  private  SearchParameters.FilterCriteria getFilterCriterias(
      String[] typeNameValue, String typename, String eq,
      SearchParameters.FilterCriteria.Condition or) {
    SearchParameters.FilterCriteria entity = new SearchParameters.FilterCriteria();
    entity.setCriterion(getFilterCriteriaList(typeNameValue, typename, eq));
    entity.setCondition(or);
    return entity;
  }

  private  List<SearchParameters.FilterCriteria> getFilterCriteriaList(
      String[] typeNameValue, String typename, String eq) {
    List<SearchParameters.FilterCriteria> entityList = new ArrayList<>();
    Arrays.stream(typeNameValue)
        .forEach(
            i -> {
              SearchParameters.FilterCriteria criteria = new SearchParameters.FilterCriteria();
              criteria.setAttributeValue(i);
              criteria.setAttributeName(typename);
              criteria.setOperator(SearchParameters.Operator.fromString(eq));
              entityList.add(criteria);
            });
    return entityList;
  }
}