package com.qk.dm.metadata.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.metadata.config.AtlasConfig;
import com.qk.dm.metadata.service.MtdAtlasService;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.instance.AtlasClassification;
import org.apache.atlas.model.typedef.AtlasClassificationDef;
import org.apache.atlas.model.typedef.AtlasTypesDef;
import org.springframework.stereotype.Service;

/**
 * @author spj
 * @date 2021/8/3 8:12 下午
 * @since 1.0.0
 */
@Service
public class MtdAtlasServiceImpl implements MtdAtlasService {

  @Override
  public void setLabels(String guid, String labels) {
    try {
      Set<String> labelSet = Arrays.stream(labels.split(",")).collect(Collectors.toSet());
      AtlasConfig.getAtlasClientV2().setLabels(guid, labelSet);
    } catch (AtlasServiceException atlasServiceException) {
      throw new BizException("当前atlas：" + guid + "，绑定标签：" + labels + " 的操作失败！！！");
    }
  }

  @Override
  public void removeLabels(String guid, String labels) {
    try {
      Set<String> labelSet = Arrays.stream(labels.split(",")).collect(Collectors.toSet());
      AtlasConfig.getAtlasClientV2().removeLabels(guid, labelSet);
    } catch (AtlasServiceException atlasServiceException) {
      throw new BizException("当前atlas：" + guid + "，解除绑定标签：" + labels + " 的操作失败！！！");
    }
  }

  @Override
  public void typedefsByPost(Map<String, String> map) {
    try {
      List<AtlasClassificationDef> classificationDefs = new ArrayList<>();
      for (Map.Entry<String, String> typeMap : map.entrySet()) {
        classificationDefs.add(new AtlasClassificationDef(typeMap.getKey(), typeMap.getValue()));
      }
      AtlasTypesDef typesDef = new AtlasTypesDef();
      typesDef.setClassificationDefs(classificationDefs);
      AtlasConfig.getAtlasClientV2().createAtlasTypeDefs(typesDef);
    } catch (AtlasServiceException atlasServiceException) {
      throw new BizException("为所有 atlas 类型，批量创建失败！！！" + map.keySet());
    }
  }

  @Override
  public void typedefsByDelete(Map<String, String> map) {
    try {
      List<AtlasClassificationDef> classificationDefs = new ArrayList<>();
      for (Map.Entry<String, String> typeMap : map.entrySet()) {
        classificationDefs.add(new AtlasClassificationDef(typeMap.getKey(), typeMap.getValue()));
      }
      AtlasTypesDef typesDef = new AtlasTypesDef();
      typesDef.setClassificationDefs(classificationDefs);
      AtlasConfig.getAtlasClientV2().deleteAtlasTypeDefs(typesDef);
    } catch (AtlasServiceException atlasServiceException) {
      throw new BizException("为所有 atlas 类型，批量删除失败！！！" + map.keySet());
    }
  }

  @Override
  public void addClassifications(String guid, String classifiy) {
    try {
      List<AtlasClassification> classificationList =
          Arrays.stream(classifiy.split(","))
              .map(AtlasClassification::new)
              .collect(Collectors.toList());
      AtlasConfig.getAtlasClientV2().addClassifications(guid, classificationList);
    } catch (AtlasServiceException atlasServiceException) {
      throw new BizException("当前分类:" + classifiy + ",元数据：" + guid + " 的绑定操作失败！！！");
    }
  }
}
