package com.qk.dam.metedata.util;

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
 * 标签与Atlas绑定工具类
 * @author spj
 * @date 2021/8/5 4:36 下午
 * @since 1.0.0
 */
public class AtlasLabelsUtil {
  private AtlasLabelsUtil() {
    throw new IllegalStateException("Utility labels");
  }

  private static final AtlasClientV2 atlasClientV2 = AtlasConfig.getAtlasClientV2();

  /**
   * 查询实体上标签
   *
   * @param guid
   * @return
   * @throws AtlasServiceException
   */
  public static Set<String> getLabels(String guid) throws AtlasServiceException {
    AtlasEntityHeader entityHeaderByGuid = atlasClientV2.getEntityHeaderByGuid(guid);
    return entityHeaderByGuid.getLabels();
  }

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
    Set<String> labels = getLabels(guid);
    if (!labels.isEmpty()) {
      atlasClientV2.removeLabels(guid, labels);
    }
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
   * 修改实体类上具体分类
   *
   * @param guid
   * @param classify
   * @throws AtlasServiceException
   */
  public static void upEntitiesClassis(String guid, String classify) throws AtlasServiceException {
    List<AtlasClassification> atlasClassifications = getAtlasClassifications(guid);
    List<String> classify2 = Arrays.asList(classify.split(","));
    List<String> classify1 =
        atlasClassifications.stream().map(AtlasStruct::getTypeName).collect(Collectors.toList());
    List<String> classifyList = getClassifyList(classify2, classify1);
    if (!classifyList.isEmpty()) {
      delEntitiesClassis(guid, classifyList);
    }
    List<String> addClassifyList = getClassifyList(classify1, classify2);
    if (!addClassifyList.isEmpty()) {
      addEntitiesClassis(guid, addClassifyList);
    }
  }

  /**
   * 删除实体类上具体分类
   *
   * @param guid
   * @param classify
   * @throws AtlasServiceException
   */
  public static void delEntitiesClassis(String guid, List<String> classify)
      throws AtlasServiceException {
    List<AtlasClassification> classificationList = getAtlasClassificationList(classify);
    atlasClientV2.deleteClassifications(guid, classificationList);
  }

  /**
   * 添加实体类上具体分类
   *
   * @param guid
   * @param classify
   * @throws AtlasServiceException
   */
  public static void addEntitiesClassis(String guid, List<String> classify)
      throws AtlasServiceException {
    List<AtlasClassification> classificationList = getAtlasClassificationList(classify);
    atlasClientV2.addClassifications(guid, classificationList);
  }

  /**
   * 批量新增分类
   *
   * @param map
   * @throws AtlasServiceException
   */
  public static void addTypedefs(Map<String, String> map) throws AtlasServiceException {
    atlasClientV2.createAtlasTypeDefs(getTypesDef(map));
  }

  /**
   * 单个新增分类
   * @param name
   * @param description
   * @throws AtlasServiceException
   */
  public static void addTypedefs(String name, String description) throws AtlasServiceException {
    atlasClientV2.createAtlasTypeDefs(getTypesDef(Map.of(name, description)));
  }

  /**
   * 批量删除分类
   *
   * @param map
   * @throws AtlasServiceException
   */
  public static void delTypedefs(Map<String, String> map) throws AtlasServiceException {
    atlasClientV2.deleteAtlasTypeDefs(getTypesDef(map));
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

  private static List<AtlasClassification> getAtlasClassifications(String guid)
      throws AtlasServiceException {
    AtlasEntityHeader entityHeaderByGuid = atlasClientV2.getEntityHeaderByGuid(guid);
    return entityHeaderByGuid.getClassifications();
  }

  private static List<String> getClassifyList(List<String> a, List<String> b) {
    return a.stream().filter(cfy -> !b.contains(cfy)).collect(Collectors.toList());
  }

  private static List<AtlasClassification> getAtlasClassificationList(List<String> classify) {
    List<AtlasClassification> classificationList = new ArrayList<>();
    classify.forEach(atlas -> classificationList.add(new AtlasClassification(atlas)));
    return classificationList;
  }
}
