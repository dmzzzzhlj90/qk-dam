package com.qk.dm.metadata.service.impl;

import com.qk.dm.metadata.config.AtlasConfig;
import com.qk.dm.metadata.service.AtlasMetaDataService;
import com.qk.dm.metadata.util.GsonUtil;
import com.qk.dm.metadata.vo.AtlasBaseMainDataDetailVO;
import com.qk.dm.metadata.vo.AtlasBaseMainDataVO;
import org.apache.atlas.model.discovery.AtlasQuickSearchResult;
import org.apache.atlas.model.instance.AtlasEntity;
import org.apache.atlas.model.instance.AtlasEntityHeader;

import java.util.*;
import java.util.stream.Collectors;

public class AtlasMetaDataServiceImpl implements AtlasMetaDataService {

    @Override
    public List<AtlasBaseMainDataVO> searchList(String query, String typeName, boolean excludeDeletedEntities, int limit, int offse) {
        List<AtlasBaseMainDataVO> atlasBaseMainDataVOList = new ArrayList<>();
        try {
            AtlasQuickSearchResult atlasQuickSearchResult = AtlasConfig.getAtlasClientV2().quickSearch(query,typeName,excludeDeletedEntities,limit,offse);
            List<AtlasEntityHeader> entities = atlasQuickSearchResult.getSearchResults().getEntities();

            entities.forEach(e->{atlasBaseMainDataVOList.add(AtlasBaseMainDataVO.builder().guid(e.getGuid()).typeName(e.getTypeName())
                    .displayName(e.getDisplayText()).qualifiedName(e.getAttributes().get("qualifiedName")
                            .toString()).createTime(new Date((Long)e.getAttributes().get("createTime"))).build());
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return atlasBaseMainDataVOList;
    }

    @Override
    public AtlasBaseMainDataDetailVO getEntityByGuid(String guid) {
        AtlasBaseMainDataDetailVO atlasBaseMainDataDetailVO = null;
        try {
            AtlasEntity.AtlasEntityWithExtInfo detail = AtlasConfig.getAtlasClientV2().getEntityByGuid(guid,true,false);
            Map<String, Object> attributes = detail.getEntity().getAttributes();
            atlasBaseMainDataDetailVO = GsonUtil.fromMap(attributes, AtlasBaseMainDataDetailVO.class);
            Map<String, AtlasEntity> map = detail.getReferredEntities();
            List<AtlasEntity> atlasEntityList = new ArrayList<>(map.values());
            List<Map<String, Object>> collect = atlasEntityList.stream().map(e->{
                Map<String, Object> attr = e.getAttributes();
                attr.put("guid",e.getGuid());
                return  attr;
            }).collect(Collectors.toList());
            atlasBaseMainDataDetailVO.setColumnList(collect);
        }catch (Exception e){
            e.printStackTrace();
        }
        return atlasBaseMainDataDetailVO;
    }
}
