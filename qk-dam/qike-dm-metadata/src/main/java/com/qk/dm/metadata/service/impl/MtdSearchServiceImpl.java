package com.qk.dm.metadata.service.impl;

import com.qk.dam.metedata.entity.*;
import com.qk.dam.metedata.property.AtlasBaseProperty;
import com.qk.dam.metedata.property.AtlasSearchProperty;
import com.qk.dam.metedata.property.SynchStateProperty;
import com.qk.dam.metedata.util.AtlasSearchUtil;
import com.qk.dam.metedata.vo.MtdColumnSearchVO;
import com.qk.dam.metedata.vo.MtdDbSearchVO;
import com.qk.dam.metedata.vo.MtdTableSearchVO;
import com.qk.dm.metadata.service.MtdSearchService;
import com.qk.dm.metadata.vo.MtdClassifyAtlasVO;
import com.qk.dm.metadata.vo.MtdLabelsAtlasVO;
import org.apache.atlas.model.discovery.SearchParameters;
import org.apache.atlas.model.instance.AtlasEntity;
import org.apache.atlas.model.instance.AtlasEntityHeader;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 元数据查找相关接口（库、表、字段）
 *
 * @author wangzp
 * @date 2021/12/15 17:36
 * @since 1.0.0
 */
@Service
public class MtdSearchServiceImpl implements MtdSearchService {

    @Override
    public List<MtdApiDb> getDataBaseList(MtdDbSearchVO mtdDbSearchVO) {
        List<AtlasEntityHeader> atlasEntityHeaderList = AtlasSearchUtil.getDataBaseList(mtdDbSearchVO.getTypeName(),mtdDbSearchVO.getServer(),mtdDbSearchVO.getLimit(),mtdDbSearchVO.getOffset());
        return buildMataDataList(atlasEntityHeaderList);
    }

    @Override
    public List<MtdTables> getTableList(MtdTableSearchVO mtdTableSearchVO) {
        List<AtlasEntityHeader> atlasEntityHeaderList = AtlasSearchUtil.getTableList(mtdTableSearchVO.getTypeName(), mtdTableSearchVO.getDbName(), mtdTableSearchVO.getServer(),
                mtdTableSearchVO.getLimit(), mtdTableSearchVO.getOffset());
        return builderMtdTables(atlasEntityHeaderList);
    }

    @Override
    public List<MtdAttributes> getColumnList(MtdColumnSearchVO mtdColumnSearchVO) {
        List<AtlasEntityHeader> atlasEntityHeaderList = AtlasSearchUtil.getColumnList(mtdColumnSearchVO.getTypeName(), mtdColumnSearchVO.getDbName(),
                mtdColumnSearchVO.getTableName(), mtdColumnSearchVO.getServer(),
                mtdColumnSearchVO.getLimit(), mtdColumnSearchVO.getOffset());
        return builderMtdAttributes(atlasEntityHeaderList);
    }

    @Override
    public List<MtdApiDb> getDataBaseListByAttr(MtdApiAttrParams mtdApiAttrParams) {
        if (Objects.equals(SynchStateProperty.TypeName.MYSQL_DB, mtdApiAttrParams.getTypeName())) {
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

    @Override
    public List<MtdAttributes> getColumnListByTableGuid(String guid) {
        AtlasEntity.AtlasEntityWithExtInfo atlasEntity = AtlasSearchUtil.getAtlasEntity(guid);
        List<AtlasEntity> atlasEntityList = new ArrayList<>(atlasEntity.getReferredEntities().values());
        return atlasEntityList.stream().map(e -> buildColumnDetail(e.getAttributes(),e.getTypeName()))
                .collect(Collectors.toList());
    }

    private MtdAttributes buildColumnDetail(Map<String, Object> attr, String typeName){
        return MtdAttributes.builder().dataType(getNullElse(attr.get(AtlasBaseProperty.DATA_TYPE),attr.get(AtlasBaseProperty.TYPE)))
                .name(transformation(attr.get(AtlasBaseProperty.NAME)))
                .owner(transformation(attr.get(AtlasBaseProperty.OWNER)))
                .type(typeName)
                .comment(transformation(attr.get(AtlasSearchProperty.AttributeName.DESCRIPTION)))
                .build();
    }

    private List<MtdApiDb> buildMataDataList(List<AtlasEntityHeader> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return null;
        }
        return entities.stream().map(e -> MtdApiDb.builder()
                .guid(e.getGuid())
                .typeName(e.getTypeName())
                .displayText(e.getDisplayText())
                .description(Objects.requireNonNullElse(e.getAttribute(AtlasSearchProperty.AttributeName.DESCRIPTION), AtlasBaseProperty.EMPTY).toString())
                .build()).collect(Collectors.toList());
    }

    private List<MtdTables> builderMtdTables(List<AtlasEntityHeader> atlasEntityHeaderList) {
        if (CollectionUtils.isEmpty(atlasEntityHeaderList)) { return null; }
        return atlasEntityHeaderList.stream().map(e -> MtdTables.builder()
                .displayText(e.getDisplayText())
                .guid(e.getGuid())
                .typeName(e.getTypeName())
                .comment(Objects.requireNonNullElse(e.getAttribute(AtlasSearchProperty.AttributeName.DESCRIPTION),AtlasBaseProperty.EMPTY).toString())
                .entityStatus(Objects.requireNonNullElse(e.getAttribute(AtlasSearchProperty.AttributeName.STATUS),AtlasBaseProperty.EMPTY).toString())
                .build()).collect(Collectors.toList());
    }

    private List<MtdAttributes> builderMtdAttributes(List<AtlasEntityHeader> atlasEntityHeaderList) {
        if (CollectionUtils.isEmpty(atlasEntityHeaderList)) { return null; }
        return atlasEntityHeaderList.stream().map(e -> MtdAttributes.builder()
                .type(e.getTypeName())
                .owner(Objects.requireNonNullElse(e.getAttribute(AtlasSearchProperty.AttributeName.OWNER),AtlasBaseProperty.EMPTY).toString())
                .comment(getNullElse(e.getAttribute(AtlasSearchProperty.AttributeName.DESCRIPTION),
                        e.getAttribute(AtlasSearchProperty.AttributeName.COMMENT))
                 )
                .name(e.getDisplayText())
                .dataType(getNullElse(e.getAttribute(AtlasBaseProperty.DATA_TYPE),e.getAttribute(AtlasBaseProperty.TYPE)))
                .build()).collect(Collectors.toList());
    }

    public String getNullElse(Object sourceValue,Object defaultValue){
        return transformation(Objects.isNull(sourceValue)? defaultValue:sourceValue);
    }

    private String transformation(Object obj){
        return String.valueOf(Objects.requireNonNullElse(obj,AtlasBaseProperty.EMPTY));
    }

}
