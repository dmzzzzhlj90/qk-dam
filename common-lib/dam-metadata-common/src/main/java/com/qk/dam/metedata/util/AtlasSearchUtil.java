package com.qk.dam.metedata.util;

import com.qk.dam.metedata.config.AtlasConfig;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.discovery.AtlasSearchResult;
import org.apache.atlas.model.discovery.SearchParameters;
import org.apache.atlas.model.instance.AtlasEntity;
import org.apache.atlas.model.instance.AtlasEntityHeader;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AtlasSearchUtil {

  private static final String QUALIFIED_NAME = "qualifiedName";
  private static final String EMPTY = "";
  private static final String SPOT = ".";

  private AtlasSearchUtil() {
    throw new IllegalStateException("Utility search");
  }



  public static List<AtlasEntityHeader> getAtlasEntityHeaderListByAttr(String typeName,SearchParameters.FilterCriteria filterCriteria, Integer limit, Integer offset){
    return getPageEntities(typeName,filterCriteria,limit,offset);
  }

  /**
   * 获取数据库
   *
   * @param typeName
   * @param server
   * @return
   */
  public static List<AtlasEntityHeader> getDataBaseList(String typeName,  String server) {
    List<SearchParameters.FilterCriteria> criterion = new ArrayList<>();
    criterion.add(getFilterCriteria(QUALIFIED_NAME,server,SearchParameters.Operator.ENDS_WITH));
    return getPageEntities(typeName, getFilterCriteria(criterion,SearchParameters.FilterCriteria.Condition.AND),null,null);
  }

  /**
   * 获取数据库
   *
   * @param typeName
   * @param server
   * @return
   */
  public static List<AtlasEntityHeader> getDataBaseList(String typeName,  String server,Integer limit, Integer offset) {
    List<SearchParameters.FilterCriteria> criterion = new ArrayList<>();
    criterion.add(getFilterCriteria(QUALIFIED_NAME,server,SearchParameters.Operator.ENDS_WITH));
    return getPageEntities(typeName, getFilterCriteria(criterion,SearchParameters.FilterCriteria.Condition.AND),limit,offset);
  }

  /**
   * 获取数据库下所有的表
   *
   * @param typeName
   * @param dbName
   * @param server
   * @return
   */
  public static List<AtlasEntityHeader> getTableList(String typeName, String dbName, String server,
                                                     Integer limit, Integer offset) {
    List<SearchParameters.FilterCriteria> criterion = new ArrayList<>();
    criterion.add(getFilterCriteria(QUALIFIED_NAME,String.join(SPOT,dbName,EMPTY),
            SearchParameters.Operator.STARTS_WITH));
    criterion.add(getFilterCriteria(QUALIFIED_NAME,server,SearchParameters.Operator.ENDS_WITH));
    return getPageEntities(typeName, getFilterCriteria(criterion,SearchParameters.FilterCriteria.Condition.AND),limit,offset);
  }

  /**
   * 获取某张表中所有的字段
   * @param typeName
   * @param dbName
   * @param tableName
   * @param server
   * @return
   */
  public static List<AtlasEntityHeader> getColumnList(String typeName, String dbName, String tableName,String server,
                                                      Integer limit, Integer offset){
      List<SearchParameters.FilterCriteria> criterion = new ArrayList<>();
      criterion.add(getFilterCriteria(QUALIFIED_NAME,String.join(SPOT, dbName, tableName,EMPTY),
              SearchParameters.Operator.STARTS_WITH));
      criterion.add(getFilterCriteria(QUALIFIED_NAME,server,SearchParameters.Operator.ENDS_WITH));
      return getPageEntities(typeName, getFilterCriteria(criterion,SearchParameters.FilterCriteria.Condition.AND),
              limit,offset);
  }

  private static SearchParameters.FilterCriteria getFilterCriteria(List<SearchParameters.FilterCriteria> filterCriteriaList,
                                                                   SearchParameters.FilterCriteria.Condition condition){
    SearchParameters.FilterCriteria filterCriteria = new SearchParameters.FilterCriteria();
    filterCriteria.setCondition(condition);
    filterCriteria.setCriterion(filterCriteriaList);
    return filterCriteria;
  }

  public static SearchParameters.FilterCriteria  getFilterCriteria(String attrName, String attrValue, SearchParameters.Operator operator){
    SearchParameters.FilterCriteria filterCriteria = new SearchParameters.FilterCriteria();
    filterCriteria.setOperator(operator);
    filterCriteria.setAttributeName(attrName);
    filterCriteria.setAttributeValue(attrValue);
    return filterCriteria;
  }

  /**
   * 获取表或字段信息
   * @param typeName
   * @param dbName
   * @param tableName
   * @param server
   * @return
   */
  public static List<AtlasEntityHeader> getEntitiesByAttr(
      String typeName, String dbName, String tableName, String server) {
    if (Objects.nonNull(tableName)) {
       return getColumnList(typeName,dbName,tableName,server,null,null);
    } else {
      return  getTableList(typeName,dbName,server,null,null);
    }
  }

  public static AtlasEntity.AtlasEntityWithExtInfo getAtlasEntity(String guid){

    try {
       return  AtlasConfig.getAtlasClientV2().getEntityByGuid(guid, true, false);
    } catch (AtlasServiceException e) {
      e.printStackTrace();
    }
    return new AtlasEntity.AtlasEntityWithExtInfo();
  }
  /**
   * atlas根据属性查询（分页）
   * @param typeName
   * @param filterCriteria
   * @return
   */
  private static List<AtlasEntityHeader> getPageEntities(String typeName, SearchParameters.FilterCriteria filterCriteria,
                                                         Integer limit, Integer offset){
    try {
      AtlasSearchResult atlasSearchResult =
              AtlasConfig.getAtlasClientV2().basicSearch(typeName, filterCriteria, null, null, false,
                      Objects.isNull(limit)?1000:limit, Objects.isNull(offset)?0:offset);
      return atlasSearchResult.getEntities();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
