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
import java.util.stream.Stream;

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

  public static void main(String[] args) throws AtlasServiceException {
    AtlasLabelsUtil.setLabels("483c95fc-f134-4f25-aff0-45d6ebbe82bd", "test1-1");
    AtlasLabelsUtil.removeLabels("483c95fc-f134-4f25-aff0-45d6ebbe82bd");
    System.out.println(
        AtlasLabelsUtil.getLabels("483c95fc-f134-4f25-aff0-45d6ebbe82bd").toArray().toString());
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
    List<AtlasClassification> atlasClassifications = getAtlasClassifications(guid);
    List<String> classifyList = getDeleteClassifyList(classify, atlasClassifications);
    for (String className : classifyList) {
      atlasClientV2.deleteClassification(guid, className);
    }
    List<String> addClassifyList = getAddClassifyList(classify, atlasClassifications);
    if (!addClassifyList.isEmpty()) {
      addEntitiesClassis(guid, addClassifyList);
    }
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
    AtlasConfig.getAtlasClientV2().addClassifications(guid, classificationList);
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

  /**
   * 批量删除分类
   *
   * @param map
   * @throws AtlasServiceException
   */
  public static void deleteTypedefs(Map<String, String> map) throws AtlasServiceException {
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

  private static List<String> getDeleteClassifyList(
      String classify, List<AtlasClassification> classifications) {
    return classifications.stream()
        .map(AtlasStruct::getTypeName)
        .filter(cfy -> !Arrays.asList(classify.split(",")).contains(cfy))
        .collect(Collectors.toList());
  }

  private static List<String> getAddClassifyList(
      String classify, List<AtlasClassification> classifications) {
    List<String> list =
        classifications.stream().map(AtlasStruct::getTypeName).collect(Collectors.toList());
    return Stream.of(classify.split(","))
        .filter(cfy -> !list.contains(cfy))
        .collect(Collectors.toList());
  }

  private static List<AtlasClassification> getAtlasClassificationList(List<String> classify) {
    List<AtlasClassification> classificationList = new ArrayList<>();
    classify.forEach(atlas -> classificationList.add(new AtlasClassification(atlas)));
    return classificationList;
  }
}
