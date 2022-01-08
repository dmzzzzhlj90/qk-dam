package com.qk.dm.metadata.service.impl;

import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.metedata.config.AtlasConfig;
import com.qk.dam.metedata.entity.*;
import com.qk.dam.metedata.property.AtlasBaseProperty;
import com.qk.dam.metedata.property.SearchResultProperty;
import com.qk.dam.metedata.util.AtlasSearchUtil;
import com.qk.dm.metadata.service.MtdApiService;
import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.SearchFilter;
import org.apache.atlas.model.instance.AtlasEntity;
import org.apache.atlas.model.instance.AtlasEntityHeader;
import org.apache.atlas.model.typedef.AtlasTypeDefHeader;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

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
    try {
      SearchFilter searchFilter = new SearchFilter();
      searchFilter.setParam(AtlasBaseProperty.TYPE, AtlasBaseProperty.ENTITY);
      List<AtlasTypeDefHeader> allTypeDefHeaders = atlasClientV2.getAllTypeDefHeaders(searchFilter);
      return GsonUtil.fromJsonString(GsonUtil.toJsonString(allTypeDefHeaders),
              new TypeToken<List<MtdAtlasEntityType>>() {}.getType());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Collections.emptyList();
  }

  @Override
  public MtdApi mtdDetail(String typeName, String dbName, String tableName, String server) {
    if (StringUtils.isBlank(dbName) && StringUtils.isBlank(tableName) && StringUtils.isNotBlank(server)) {
      return  MtdApi.builder().entities(buildMataDataList(AtlasSearchUtil.getDataBaseList(typeName,server))).build();
    } else {
      return getDetail(typeName,dbName,tableName,server);
    }
  }


  public Integer getExistData(String typeName, String dbName, String tableName, String server){
    List<Map<String, String>> uniqAttributesList = new ArrayList<>();
    Map<String, String> map = new HashMap<>();
    map.put(AtlasBaseProperty.QUALIFIEDNAME, dbName+ "." + tableName+ "@" + server);
    uniqAttributesList.add(map);
    try {
      AtlasEntity.AtlasEntitiesWithExtInfo result = atlasClientV2.getEntitiesByAttribute(typeName, uniqAttributesList);
      List<AtlasEntity> entities = result.getEntities();
      if(Objects.isNull(entities)){
        return SearchResultProperty.NO_EXIST;
      }else if(Objects.equals(String.valueOf(entities.get(0).getAttributes().get(AtlasBaseProperty.DATA_LENGTH)), "0")){
        return SearchResultProperty.EXIST_NO_DATA;
      }else {
        return SearchResultProperty.EXIST_DATA;
      }
    } catch (AtlasServiceException e) {
      e.printStackTrace();
    }
    return SearchResultProperty.EXIST_DATA;
  }


  private List<MtdApiDb> buildMataDataList(List<AtlasEntityHeader> entities) {
    List<MtdApiDb> mtdApiDbVOList = new ArrayList<>();
    entities.forEach(e -> { mtdApiDbVOList.add(
            MtdApiDb.builder()
                    .guid(e.getGuid())
                    .typeName(e.getTypeName())
                    .displayText(e.getDisplayText())
                    .description(String.valueOf(Objects.requireNonNullElse(
                            e.getAttribute(AtlasBaseProperty.DESCRIPTION), AtlasBaseProperty.EMPTY)))
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
              .owner(String.valueOf(Objects.requireNonNullElse(e.getAttribute(AtlasBaseProperty.OWNER),AtlasBaseProperty.EMPTY)))
              .comment(String.valueOf(Objects.requireNonNullElse(e.getAttribute(AtlasBaseProperty.DESCRIPTION),AtlasBaseProperty.EMPTY)))
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
              .comment(String.valueOf(Objects.requireNonNullElse(e.getAttribute(AtlasBaseProperty.DESCRIPTION),AtlasBaseProperty.EMPTY)))
              .entityStatus(String.valueOf(Objects.requireNonNullElse(e.getAttribute(AtlasBaseProperty.STATUS),AtlasBaseProperty.EMPTY))).build()
      );
    });
    return tablesList;
  }
}
