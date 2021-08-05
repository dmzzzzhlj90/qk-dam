package com.qk.dm.metadata.service.impl;

import com.qk.dam.metedata.config.AtlasConfig;
import com.qk.dm.metadata.service.MtdAtlasService;
import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.instance.AtlasClassification;
import org.apache.atlas.model.typedef.AtlasClassificationDef;
import org.apache.atlas.model.typedef.AtlasTypesDef;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author spj
 * @date 2021/8/3 8:12 下午
 * @since 1.0.0
 */
@Service
public class MtdAtlasServiceImpl implements MtdAtlasService {
    private static final AtlasClientV2 atlasClientV2 = AtlasConfig.getAtlasClientV2();

    @Override
    public void setLabels(String guid, String labels) throws AtlasServiceException {
        atlasClientV2.setLabels(guid, getLabelSet(labels));
    }

    @Override
    public void removeLabels(String guid, String labels) throws AtlasServiceException {
        atlasClientV2.removeLabels(guid, getLabelSet(labels));
    }

    @Override
    public void postTypedefs(Map<String, String> map) throws AtlasServiceException {
        atlasClientV2.createAtlasTypeDefs(getTypesDef(map));
    }

    @Override
    public void deleteTypedefs(Map<String, String> map) throws AtlasServiceException {
        atlasClientV2.deleteAtlasTypeDefs(getTypesDef(map));
    }

    @Override
    public void addClassifications(String guid, String classifiy) throws AtlasServiceException {
        List<AtlasClassification> classificationList = getAtlasClassification(classifiy);
        atlasClientV2.addClassifications(guid, classificationList);
    }

    public Set<String> getLabelSet(String labels) {
        return Arrays.stream(labels.split(",")).collect(Collectors.toSet());
    }

    public AtlasTypesDef getTypesDef(Map<String, String> map) {
        AtlasTypesDef typesDef = new AtlasTypesDef();
        typesDef.setClassificationDefs(getAtlasClassificationDef(map));
        return typesDef;
    }

    public List<AtlasClassificationDef> getAtlasClassificationDef(Map<String, String> map) {
        List<AtlasClassificationDef> classificationDefs = new ArrayList<>();
        for (Map.Entry<String, String> typeMap : map.entrySet()) {
            classificationDefs.add(new AtlasClassificationDef(typeMap.getKey(), typeMap.getValue()));
        }
        return classificationDefs;
    }

    public List<AtlasClassification> getAtlasClassification(String classifiy) {
        return Arrays.stream(classifiy.split(","))
                .map(AtlasClassification::new).collect(Collectors.toList());
    }
}
