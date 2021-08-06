package com.qk.dm.metadata;

import com.qk.dam.metedata.config.AtlasConfig;
import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.instance.AtlasClassification;
import org.apache.atlas.model.instance.AtlasEntityHeader;
import org.apache.atlas.model.instance.AtlasStruct;
import org.apache.atlas.model.typedef.AtlasClassificationDef;
import org.apache.atlas.model.typedef.AtlasTypesDef;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author spj
 * @date 2021/8/5 4:36 下午
 * @since 1.0.0
 */
public class AtlasLabelsUtil {
    public AtlasLabelsUtil() {
        throw new IllegalStateException("Utility labels");
    }

    private static final AtlasClientV2 atlasClientV2 = AtlasConfig.getAtlasClientV2();

    /**
     * 更新实体上标签
     *
     * @param guid
     * @param labels
     * @throws AtlasServiceException
     */
    public static void setLabels(String guid, String labels) throws AtlasServiceException {
        atlasClientV2.setLabels(guid, getLabelSet(labels));
    }

    /**
     * 删除实体上所有标签
     *
     * @param guid
     * @throws AtlasServiceException
     */
    public static void removeLabels(String guid) throws AtlasServiceException {
        AtlasEntityHeader entityHeaderByGuid = atlasClientV2.getEntityHeaderByGuid(guid);
        atlasClientV2.removeLabels(guid, entityHeaderByGuid.getLabels());
    }

    /**
     * 删除实体上指定标签
     *
     * @param guid
     * @param labels
     * @throws AtlasServiceException
     */
    public static void removeLabels(String guid, String labels) throws AtlasServiceException {
        atlasClientV2.removeLabels(guid, getLabelSet(labels));
    }

    public static Set<String> getLabelSet(String labels) {
        return Arrays.stream(labels.split(",")).collect(Collectors.toSet());
    }

    /***********************分类**************************************/

    /**
     * 删除实体上所有分类
     *
     * @param guid
     * @throws AtlasServiceException
     */
    public static void delEntitiesClassis(String guid) throws AtlasServiceException {
        List<AtlasClassification> classifications = getAtlasClassifications(guid);
        for (AtlasClassification classification : classifications) {
            atlasClientV2.deleteClassification(guid, classification.getTypeName());
        }
    }

    /**
     * 删除实体类上具体分类
     *
     * @param guid
     * @param classify
     * @throws AtlasServiceException
     */
    public static void upEntitiesClassis(String guid, String classify) throws AtlasServiceException {
        List<String> classifyList = getClassifyList(classify, getAtlasClassifications(guid));
        for (String className : classifyList) {
            atlasClientV2.deleteClassification(guid, className);
        }
    }

    /**
     * 批量新增分类
     *
     * @param map
     * @throws AtlasServiceException
     */
    public static void postTypedefs(Map<String, String> map) throws AtlasServiceException {
        atlasClientV2.createAtlasTypeDefs(getTypesDef(map));
    }

    public static AtlasTypesDef getTypesDef(Map<String, String> map) {
        AtlasTypesDef typesDef = new AtlasTypesDef();
        typesDef.setClassificationDefs(getAtlasClassificationDef(map));
        return typesDef;
    }

    public static List<AtlasClassificationDef> getAtlasClassificationDef(Map<String, String> map) {
        List<AtlasClassificationDef> classificationDefs = new ArrayList<>();
        for (Map.Entry<String, String> typeMap : map.entrySet()) {
            classificationDefs.add(new AtlasClassificationDef(typeMap.getKey(), typeMap.getValue()));
        }
        return classificationDefs;
    }

    private static List<AtlasClassification> getAtlasClassifications(String guid) throws AtlasServiceException {
        AtlasEntityHeader entityHeaderByGuid = atlasClientV2.getEntityHeaderByGuid(guid);
        return entityHeaderByGuid.getClassifications();
    }

    private static List<String> getClassifyList(String classify, List<AtlasClassification> classifications) {
        return classifications.stream()
                .map(AtlasStruct::getTypeName)
                .filter(cfy -> !Arrays.asList(classify.split(",")).contains(cfy))
                .collect(Collectors.toList());
    }
}
