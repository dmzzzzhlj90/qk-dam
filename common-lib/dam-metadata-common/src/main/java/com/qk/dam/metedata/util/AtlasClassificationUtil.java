package com.qk.dam.metedata.util;

import com.qk.dam.metedata.config.AtlasConfig;
import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.SearchFilter;
import org.apache.atlas.model.discovery.AtlasSearchResult;
import org.apache.atlas.model.instance.AtlasEntityHeader;
import org.apache.atlas.model.typedef.AtlasClassificationDef;
import org.apache.atlas.model.typedef.AtlasTypesDef;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AtlasClassificationUtil {
    private AtlasClassificationUtil() {
        throw new IllegalStateException("Utility class");
    }
    private static final AtlasClientV2 atlasClientV2 = AtlasConfig.getAtlasClientV2();
    private static final  String CLASSIFICATION = "classification";

    /**
     * 删除实体上标签
     * @param classificationName 标签名称
     * @throws AtlasServiceException
     */
    public void delEntitiesTags(String classificationName) throws AtlasServiceException {
        List<AtlasEntityHeader> atlasEntityHeaders = getEntitiesByTags(classificationName);
        for (AtlasEntityHeader atlasEntityHeader : atlasEntityHeaders) {
            atlasClientV2.deleteClassification(atlasEntityHeader.getGuid(), classificationName);
        }
    }

    /***
     * 删除标签
     * 前提是所属实体上的标签已被删除
     * @param classificationName 标签名称
     * @throws AtlasServiceException
     */
    public void delTag(String classificationName) throws AtlasServiceException {
        AtlasTypesDef typesDef = new AtlasTypesDef();
        typesDef.setClassificationDefs(getTags(classificationName));
        atlasClientV2.deleteAtlasTypeDefs(typesDef);
    }

    public static List<AtlasClassificationDef> getTags() throws AtlasServiceException {
        SearchFilter searchFilter = new SearchFilter();
        searchFilter.setParam("type", CLASSIFICATION);
        AtlasTypesDef allTypeDefs = atlasClientV2.getAllTypeDefs(searchFilter);
        return allTypeDefs.getClassificationDefs();
    }

    public static List<AtlasClassificationDef> getTags(String name) throws AtlasServiceException {
        SearchFilter searchFilter = new SearchFilter();
        searchFilter.setParam("type", CLASSIFICATION);
        searchFilter.setParam("name", name);
        AtlasTypesDef allTypeDefs = atlasClientV2.getAllTypeDefs(searchFilter);
        return allTypeDefs.getClassificationDefs();
    }

    public static List<AtlasEntityHeader> getEntitiesByTags() throws AtlasServiceException {
        return getEntitiesByTags(getTags());
    }
    public static List<AtlasEntityHeader> getEntitiesByTags(String classificationName) throws AtlasServiceException {
        List<AtlasClassificationDef> tags = getTags(classificationName);
        return getEntitiesByTags(tags);
    }

    private static List<AtlasEntityHeader> getEntitiesByTags(List<AtlasClassificationDef> tags) {
        return tags.stream()
                .map(atlasClassificationDef -> {
                    try {
                        return atlasClientV2.basicSearch(null, atlasClassificationDef.getName(), null, true, 1000, 0);
                    } catch (AtlasServiceException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).filter(Objects::nonNull)
                .map(AtlasSearchResult::getEntities)
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
