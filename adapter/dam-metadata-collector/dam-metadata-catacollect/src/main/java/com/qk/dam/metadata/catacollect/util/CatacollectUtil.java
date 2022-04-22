package com.qk.dam.metadata.catacollect.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.db.Entity;
import com.qk.dam.metadata.catacollect.pojo.AtlasBaseSearchVO;
import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.discovery.AtlasSearchResult;
import org.apache.atlas.model.discovery.SearchParameters;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zys
 * @date 2022/4/14 20:04
 * @since 1.0.0
 */
public class CatacollectUtil {
  /**
   * MYSQL数据库
   */
  public static final String MYSQL_SCHEMA = "table_schema";
  public static final String MYSQL_NAME = "table_name";
  /**
   * HIVE数据库
   */
  public static final String HIVE_SCHEMA = "database_name";
  public static final String HIVE_NAME = "tab_name";

  public static String convertListToString(List<String> lists) {
    StringBuffer sb = new StringBuffer();
    if(lists !=null){
      for (int i=0;i<lists.size();i++) {
        if(i==0){
          sb.append("'").append(lists.get(i)).append("'");
        }else{
          sb.append(",").append("'").append(lists.get(i)).append("'");
        }
      }
    }
    return sb.toString();
  }

  public static List<String> getDbResult(List<Entity> list, String type) {
    List<String> dbList = new ArrayList<>();
    if (CollectionUtil.isNotEmpty(list)){
      switch (type){
        case SourcesUtil.MYSQL:
          dbList = list.stream().map(entity -> (String)entity.get(MYSQL_SCHEMA)).collect(
              Collectors.toList());
          break;
        case SourcesUtil.HIVE:
          dbList = list.stream().map(entity -> (String)entity.get(HIVE_SCHEMA)).collect(
              Collectors.toList());
          break;
        default:
          break;
      }
    }
    return dbList;
  }

  public static List<String> getTableResult(List<Entity> list, String type) {
    List<String> tableList = new ArrayList<>();
    if (CollectionUtil.isNotEmpty(list)) {
      switch (type) {
        case SourcesUtil.MYSQL:
          tableList = list.stream().map(entity -> (String) entity.get(MYSQL_NAME))
              .collect(Collectors.toList());
          break;
        case SourcesUtil.HIVE:
          tableList = list.stream().map(entity -> (String) entity.get(HIVE_NAME))
              .collect(Collectors.toList());
          break;
        default:
          break;
      }
    }
    return tableList;
  }

  /**
   * 处理元数据采集策略问题
   * @param tableList （导入的表）
   * @param db （导入的数据库）
   * @param atlasClientV2 （Atals操作对象）
   * @param strategy （策略编码 1：仅更新、2：仅添加、3：既更新又添加、4：忽略更新添加）
   * @param atalsEnum 传入的数据类
   * @return
   */
  public static List<Entity> checkTable(List<Entity> tableList, String db,
      AtlasClientV2 atlasClientV2, String strategy, String atalsEnum)
      throws AtlasServiceException {
    List<Entity> resultTableList = new ArrayList<>();
    //1判断当前新采集的库是否已经存在atals中，如果存在就执行策略，如果不存在就直接添加
    Map<String,String> dbMap = getDbList(atalsEnum, atlasClientV2);
    if (MapUtils.isNotEmpty(dbMap) && StringUtils.isNotEmpty(dbMap.get(db))){
      //2当采集的库已经存在atals中，执行策略，对添加的表进行赛选，并且删除atals中符合条件的表
      resultTableList=getTableList(dbMap.get(db),atlasClientV2,tableList,strategy);
    System.out.println("当前数据库已经存在，需要走策略模式");
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
   * @return
   */
  private static List<Entity> getTableList(String dbGuid, AtlasClientV2 atlasClientV2, List<Entity> tableList, String strategy) {
    List<Entity> returnTableList = new ArrayList<>();
    switch (strategy) {
      case SourcesUtil.UPDATE_ONLY:
        returnTableList = getUpdateOnly(dbGuid,atlasClientV2,tableList);
        break;
      case SourcesUtil.ADD_ONLY:
        returnTableList = getAddOnly(dbGuid,atlasClientV2,tableList);
        break;
      case SourcesUtil.UPDATE_ADD:
        returnTableList = getUpdateAdd(dbGuid,atlasClientV2,tableList);
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
   * @return
   */
  private static List<Entity> getUpdateAdd(String dbGuid, AtlasClientV2 atlasClientV2, List<Entity> tableList) {
    return null;
  }

  /**
   * 仅添加
   * @param dbGuid atals中数据库guid
   * @param atlasClientV2 atals操作对象
   * @param tableList 传入的待添加或更新的表
   * @return
   */
  private static List<Entity> getAddOnly(String dbGuid, AtlasClientV2 atlasClientV2, List<Entity> tableList) {
    return null;
  }

  /**
   * 仅更新
   * @param dbGuid atals中数据库guid
   * @param atlasClientV2 atals操作对象
   * @param tableList 传入的待添加或更新的表
   * @return
   */
  private static List<Entity> getUpdateOnly(String dbGuid, AtlasClientV2 atlasClientV2, List<Entity> tableList) {
    return null;
  }

  /**
   * 查询atlas中对应类型的数据库
   * @param atalsEnum
   * @param atlasClientV2
   * @return
   */
  private static Map<String,String> getDbList(String atalsEnum, AtlasClientV2 atlasClientV2)
      throws AtlasServiceException {
    Map<String,String> map  = new HashMap<>();
    AtlasBaseSearchVO atlasBaseSearchVO = new AtlasBaseSearchVO();
    String[] type = new String[]{atalsEnum};
    atlasBaseSearchVO.setTypeNameValue(type);
      AtlasSearchResult atlasSearchResult = atlasClientV2.basicSearch(atlasBaseSearchVO.getTypeName(), getFilterCriteria(atlasBaseSearchVO), atlasBaseSearchVO.getClassification(), atlasBaseSearchVO.getQuery(),
              true, atlasBaseSearchVO.getLimit(), atlasBaseSearchVO.getOffset());
      if (Objects.nonNull(atlasSearchResult.getEntities())){
        atlasSearchResult.getEntities().forEach(e->{
          String dbName = (String) e.getAttribute(SourcesUtil.DB_NAME);
          String guid = e.getGuid();
          map.put(dbName,guid);
        });
      }
    return map;
  }

  private static SearchParameters.FilterCriteria getFilterCriteria(
      AtlasBaseSearchVO mtdAtlasParamsVO) {
    SearchParameters.FilterCriteria entityFilters = new SearchParameters.FilterCriteria();
    entityFilters.setCondition(SearchParameters.FilterCriteria.Condition.AND);
    List<SearchParameters.FilterCriteria> filterCriteriaList = new ArrayList<>();
    if (mtdAtlasParamsVO.getTypeNameValue() != null
        && mtdAtlasParamsVO.getTypeNameValue().length > 0) {
      filterCriteriaList.add(
          getFilterCriterias(
              mtdAtlasParamsVO.getTypeNameValue(),
              SourcesUtil.TYPENAME,
              SourcesUtil.EQ,
              SearchParameters.FilterCriteria.Condition.OR));
    }
    if (mtdAtlasParamsVO.getNameValue() != null && mtdAtlasParamsVO.getNameValue().length > 0) {
      filterCriteriaList.add(
          getFilterCriterias(
              mtdAtlasParamsVO.getNameValue(),
              SourcesUtil.NAME,
              SourcesUtil.CONTAINS,
              SearchParameters.FilterCriteria.Condition.AND));
    }
    if (mtdAtlasParamsVO.getLabelsValue() != null && mtdAtlasParamsVO.getLabelsValue().length > 0) {
      SearchParameters.FilterCriteria entity = new SearchParameters.FilterCriteria();
      entity.setCondition(SearchParameters.FilterCriteria.Condition.AND);
      entity.setCriterion(
          Arrays.asList(
              getFilterCriterias(
                  mtdAtlasParamsVO.getLabelsValue(),
                  SourcesUtil.LABELS,
                  SourcesUtil.CONTAINS,
                  SearchParameters.FilterCriteria.Condition.OR)));
      filterCriteriaList.add(entity);
    }
    entityFilters.setCriterion(filterCriteriaList);
    return entityFilters;
  }

  private static SearchParameters.FilterCriteria getFilterCriterias(
      String[] typeNameValue, String typename, String eq,
      SearchParameters.FilterCriteria.Condition or) {
    SearchParameters.FilterCriteria entity = new SearchParameters.FilterCriteria();
    entity.setCriterion(getFilterCriteriaList(typeNameValue, typename, eq));
    entity.setCondition(or);
    return entity;
  }

  private static List<SearchParameters.FilterCriteria> getFilterCriteriaList(
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