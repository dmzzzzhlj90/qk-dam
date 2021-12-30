package com.qk.dm.metadata.service.impl;

import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.metedata.config.AtlasConfig;
import com.qk.dam.metedata.entity.*;
import com.qk.dam.metedata.property.SearchResultProperty;
import com.qk.dam.metedata.property.SynchStateProperty;
import com.qk.dam.metedata.util.AtlasSearchUtil;
import com.qk.dm.metadata.service.MtdApiService;
import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.SearchFilter;
import org.apache.atlas.model.discovery.AtlasSearchResult;
import org.apache.atlas.model.discovery.SearchParameters;
import org.apache.atlas.model.instance.AtlasEntity;
import org.apache.atlas.model.instance.AtlasEntityHeader;
import org.apache.atlas.model.instance.AtlasStruct;
import org.apache.atlas.model.typedef.AtlasTypeDefHeader;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
  public List<MtdAtlasEntityType> getAllEntityType() {
    List<MtdAtlasEntityType> mtdAtlasEntityTypeVOList = new ArrayList<>();
    try {
      SearchFilter searchFilter = new SearchFilter();
      searchFilter.setParam("type", "entity");
      List<AtlasTypeDefHeader> allTypeDefHeaders = atlasClientV2.getAllTypeDefHeaders(searchFilter);
      mtdAtlasEntityTypeVOList =
          GsonUtil.fromJsonString(
              GsonUtil.toJsonString(allTypeDefHeaders),
              new TypeToken<List<MtdAtlasEntityType>>() {}.getType());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return mtdAtlasEntityTypeVOList;
  }

  @Override
  public MtdApi mtdDetail(String typeName, String dbName, String tableName, String server) {
    if (StringUtils.isBlank(dbName)
        && StringUtils.isBlank(tableName)
        && StringUtils.isNotBlank(server)) {
      return  MtdApi.builder().entities(buildMataDataList(AtlasSearchUtil.getDataBaseList(typeName,server))).build();
    } else {
      return getDetail(typeName,dbName,tableName,server);
    }
  }

  @Override
  public List<MtdTables> getTables(String typeName, String classification) {
    List<MtdTables> mtdApiTableVOList = new ArrayList<>();
    try {
      AtlasSearchResult atlasSearchResult =
          atlasClientV2.basicSearch(typeName, classification, null, true, 2000, 0);
      List<AtlasEntityHeader> entities = atlasSearchResult.getEntities();
      entities.forEach(
          e -> {
            mtdApiTableVOList.add(
                MtdTables.builder()
                    .guid(e.getGuid())
                    .typeName(e.getTypeName())
                    .displayText(e.getDisplayText())
                    .build());
          });
    } catch (Exception e) {
      e.printStackTrace();
    }
    return mtdApiTableVOList;
  }

  public List<Map<String, Object>> getColumns(String guid) {
    List<Map<String, Object>> collect = null;
    try {
      AtlasEntity.AtlasEntityWithExtInfo detail = atlasClientV2.getEntityByGuid(guid, true, false);
      List<AtlasEntity> atlasEntityList = new ArrayList<>(detail.getReferredEntities().values());
      collect =
          atlasEntityList.stream().map(AtlasStruct::getAttributes).collect(Collectors.toList());
    } catch (AtlasServiceException e) {
      e.printStackTrace();
    }
    return collect;
  }


  /**
   * 测试dbs
   * @param typeName 类型名称
   * @param attrValue 属性值
   * @return MtdApi
   */
  public MtdApi getDbs(String typeName,String attrValue){
    MtdApi mtdApi = new MtdApi();
    AtlasSearchResult atlasSearchResult = null;
    try {
      SearchParameters.FilterCriteria filterCriteria = new SearchParameters.FilterCriteria();
      filterCriteria.setOperator(SearchParameters.Operator.EQ);
      if(Objects.equals(SynchStateProperty.TypeName.MYSQL_DB,typeName)){
        filterCriteria.setAttributeName("serverInfo");
        filterCriteria.setAttributeValue(attrValue);
         atlasSearchResult = atlasClientV2.basicSearch(typeName, filterCriteria, null, null,false, 1000, 0);
      }else if(Objects.equals(SynchStateProperty.TypeName.HIVE_DB,typeName)){
        filterCriteria.setAttributeName("clusterName");
        filterCriteria.setAttributeValue(attrValue);
        atlasSearchResult = atlasClientV2.basicSearch(typeName, filterCriteria, null, null,false, 1000, 0);
      }
      if(Objects.nonNull(atlasSearchResult)){
        List<AtlasEntityHeader> atlasEntityHeaderList = atlasSearchResult.getEntities();
        mtdApi.setEntities(buildMataDataList(atlasEntityHeaderList));
      }
    } catch (AtlasServiceException e) {
      e.printStackTrace();
    }
    return mtdApi;
  }

<<<<<<< HEAD
  public Integer getExistData(String typeName, String dbName, String tableName, String server){
    List<Map<String, String>> uniqAttributesList = new ArrayList<>();
    Map<String, String> map = new HashMap<>();
    map.put("qualifiedName", dbName+ "." + tableName+ "@" + server);
    uniqAttributesList.add(map);
    try {
      AtlasEntity.AtlasEntitiesWithExtInfo result =
              atlasClientV2.getEntitiesByAttribute(typeName, uniqAttributesList);
      List<AtlasEntity> entities = result.getEntities();
      if(Objects.isNull(entities)){
        return SearchResultProperty.NO_EXIST;
      }else if(Objects.equals(String.valueOf(entities.get(0).getAttributes().get("dataLength")), "0")){
        return SearchResultProperty.EXIST_NO_DATA;
      }else {
        return SearchResultProperty.EXIST_DATA;
      }
    } catch (AtlasServiceException e) {
      e.printStackTrace();
    }
    return SearchResultProperty.EXIST_DATA;
  }

  private MtdApi getDbs(String typeName) {
    MtdApi mtdApi = new MtdApi();
    try {
      AtlasSearchResult atlasSearchResult =
          atlasClientV2.basicSearch(typeName, null, null, true, 1000, 0);
      List<AtlasEntityHeader> atlasEntityHeaderList = atlasSearchResult.getEntities();
      mtdApi.setEntities(buildMataDataList(atlasEntityHeaderList));
    } catch (AtlasServiceException e) {
      e.printStackTrace();
    }
    return mtdApi;
  }

=======
>>>>>>> dev
  private List<MtdApiDb> buildMataDataList(List<AtlasEntityHeader> entities) {
    List<MtdApiDb> mtdApiDbVOList = new ArrayList<>();
    entities.forEach(
        e -> {
          mtdApiDbVOList.add(
              MtdApiDb.builder()
                  .guid(e.getGuid())
                  .typeName(e.getTypeName())
                  .displayText(e.getDisplayText())
                  .description(String.valueOf(e.getAttribute("description")))
                  .build());
        });
    return mtdApiDbVOList;
  }

  private MtdApi getDetail(String typeName, String dbName, String tableName, String server) {
      MtdApi mtdApi = MtdApi.builder().build();
      List<AtlasEntityHeader> atlasEntityHeaderList = AtlasSearchUtil.getEntitiesByAttr(typeName, dbName, tableName, server);
      if(!StringUtils.isBlank(tableName)){
        mtdApi.setColumns(builderMtdAttributes(atlasEntityHeaderList));
      }else {
        mtdApi.setTables(builderMtdTables(atlasEntityHeaderList));
      }
    return mtdApi;
  }

  private List<MtdAttributes> builderMtdAttributes(List<AtlasEntityHeader> atlasEntityHeaderList){
    List<MtdAttributes> columns = new ArrayList<>();
    atlasEntityHeaderList.forEach(e->{
      columns.add(MtdAttributes.builder().type(e.getTypeName())
              .owner(String.valueOf(e.getAttribute("owner")))
              .comment(String.valueOf(e.getAttribute("description")))
              .name(e.getDisplayText())
              .build()
      );
    });
    return columns;
  }
  private List<MtdTables>  builderMtdTables(List<AtlasEntityHeader> atlasEntityHeaderList){
    List<MtdTables> tablesList = new ArrayList<>();
    atlasEntityHeaderList.forEach(e->{
      tablesList.add(MtdTables.builder().displayText(e.getDisplayText())
              .guid(e.getGuid())
              .typeName(e.getTypeName())
              .comment(String.valueOf(e.getAttribute("description")))
              .entityStatus(String.valueOf(e.getAttribute("status"))).build()
      );
    });
   return tablesList;
  }
}
