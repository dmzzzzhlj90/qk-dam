package com.qk.dm.metadata.service.impl;

import com.qk.dam.metedata.entity.*;
import com.qk.dam.metedata.property.AtlasSearchProperty;
import com.qk.dam.metedata.property.SynchStateProperty;
import com.qk.dam.metedata.util.AtlasSearchUtil;
import com.qk.dm.metadata.service.MtdSearchService;
import org.apache.atlas.model.discovery.SearchParameters;
import org.apache.atlas.model.instance.AtlasEntityHeader;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 元数据查找相关接口（库、表、字段）
 * @author wangzp
 * @date 2021/12/15 17:36
 * @since 1.0.0
 */
@Service
public class MtdSearchServiceImpl implements MtdSearchService {

    @Override
    public List<MtdApiDb> getDataBaseList(MtdApiParams mtdApiParams) {
        List<AtlasEntityHeader> atlasEntityHeaderList = AtlasSearchUtil.getDataBaseList(mtdApiParams.getTypeName(),
                mtdApiParams.getLimit(), mtdApiParams.getOffset());
        return buildMataDataList(atlasEntityHeaderList);
    }

    @Override
    public List<MtdTables> getTableList(MtdApiParams mtdApiParams) {
        List<AtlasEntityHeader> atlasEntityHeaderList = AtlasSearchUtil.getTableList(mtdApiParams.getTypeName(), mtdApiParams.getDbName(), mtdApiParams.getServer(),
                mtdApiParams.getLimit(), mtdApiParams.getOffset());
        return builderMtdTables(atlasEntityHeaderList);
    }

    @Override
    public List<MtdAttributes> getColumnList(MtdApiParams mtdApiParams) {
        List<AtlasEntityHeader> atlasEntityHeaderList = AtlasSearchUtil.getColumnList(mtdApiParams.getTypeName(), mtdApiParams.getDbName(),
                mtdApiParams.getTableName(), mtdApiParams.getServer(),
                mtdApiParams.getLimit(), mtdApiParams.getOffset());
        return builderMtdAttributes(atlasEntityHeaderList);
    }

    @Override
    public List<MtdApiDb> getDataBaseListByAttr(MtdApiAttrParams mtdApiAttrParams) {
        if(Objects.equals(SynchStateProperty.TypeName.MYSQL_DB,mtdApiAttrParams.getTypeName())){
           mtdApiAttrParams.setAttrName(AtlasSearchProperty.AttributeName.SERVER_INFO);
        } else if (Objects.equals(SynchStateProperty.TypeName.HIVE_DB, mtdApiAttrParams.getTypeName())) {
            mtdApiAttrParams.setAttrName(AtlasSearchProperty.AttributeName.CLUSTER_NAME);
        }
        List<AtlasEntityHeader> atlasEntityHeaderList = AtlasSearchUtil.getAtlasEntityHeaderListByAttr(mtdApiAttrParams.getAttrName(),
                AtlasSearchUtil.getFilterCriteria(mtdApiAttrParams.getAttrName(), mtdApiAttrParams.getAttrValue(),
                        SearchParameters.Operator.EQ),
                mtdApiAttrParams.getLimit(), mtdApiAttrParams.getOffset());
        return buildMataDataList(atlasEntityHeaderList);
    }

    private List<MtdApiDb> buildMataDataList(List<AtlasEntityHeader> entities) {
        if(CollectionUtils.isEmpty(entities)){return null;}
       return  entities.stream().map(e->MtdApiDb.builder()
                .guid(e.getGuid())
                .typeName(e.getTypeName())
                .displayText(e.getDisplayText())
                .description(String.valueOf(e.getAttribute("description")))
                .build()).collect(Collectors.toList());
    }

    private List<MtdTables>  builderMtdTables(List<AtlasEntityHeader> atlasEntityHeaderList){
        return atlasEntityHeaderList.stream().map(e->  MtdTables.builder()
                .displayText(e.getDisplayText())
                .guid(e.getGuid())
                .typeName(e.getTypeName())
                .comment(String.valueOf(e.getAttribute("description")))
                .entityStatus(String.valueOf(e.getAttribute("status")))
                .build()).collect(Collectors.toList());
        }

    private List<MtdAttributes> builderMtdAttributes(List<AtlasEntityHeader> atlasEntityHeaderList){
        return atlasEntityHeaderList.stream().map(e -> MtdAttributes.builder()
                .type(e.getTypeName())
                .owner(String.valueOf(e.getAttribute("owner")))
                .comment(String.valueOf(e.getAttribute("description")))
                .name(e.getDisplayText())
                .build()).collect(Collectors.toList());
    }

}
