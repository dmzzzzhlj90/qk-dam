package com.qk.dam.metedata.util;

import com.qk.dam.metedata.config.AtlasConfig;
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

  private static final String CLASSIFICATION = "classification";

  /**
   * 删除实体上标签
   *
   * @param classificationName 标签名称
   * @throws AtlasServiceException
   */
  public static void delEntitiesClassis(String classificationName) throws AtlasServiceException {
    List<AtlasEntityHeader> atlasEntityHeaders = getEntitiesByClassis(classificationName);
    for (AtlasEntityHeader atlasEntityHeader : atlasEntityHeaders) {
      AtlasConfig.getAtlasClientV2().deleteClassification(atlasEntityHeader.getGuid(), classificationName);
    }
  }

  /***
   * 删除标签
   * 前提是所属实体上的标签已被删除
   * @param classificationName 标签名称
   * @throws AtlasServiceException
   */
  public static void delClassi(String classificationName) throws AtlasServiceException {
    AtlasTypesDef typesDef = new AtlasTypesDef();
    typesDef.setClassificationDefs(getClassis(classificationName));
    AtlasConfig.getAtlasClientV2().deleteAtlasTypeDefs(typesDef);
  }

  public static List<AtlasClassificationDef> getClassis() throws AtlasServiceException {
    SearchFilter searchFilter = new SearchFilter();
    searchFilter.setParam("type", CLASSIFICATION);
    AtlasTypesDef allTypeDefs = AtlasConfig.getAtlasClientV2().getAllTypeDefs(searchFilter);
    return allTypeDefs.getClassificationDefs();
  }

  public static List<AtlasClassificationDef> getClassis(String name) throws AtlasServiceException {
    SearchFilter searchFilter = new SearchFilter();
    searchFilter.setParam("type", CLASSIFICATION);
    searchFilter.setParam("name", name);
    AtlasTypesDef allTypeDefs = AtlasConfig.getAtlasClientV2().getAllTypeDefs(searchFilter);
    return allTypeDefs.getClassificationDefs();
  }

  public static List<AtlasEntityHeader> getEntitiesByClassis() throws AtlasServiceException {
    return getEntitiesByClassis(getClassis());
  }

  public static List<AtlasEntityHeader> getEntitiesByClassis(String classificationName)
      throws AtlasServiceException {
    List<AtlasClassificationDef> tags = getClassis(classificationName);
    return getEntitiesByClassis(tags);
  }

  private static List<AtlasEntityHeader> getEntitiesByClassis(List<AtlasClassificationDef> tags) {
    return tags.stream()
        .map(
            atlasClassificationDef -> {
              try {
                return AtlasConfig.getAtlasClientV2().basicSearch(
                    null, atlasClassificationDef.getName(), null, true, 1000, 0);
              } catch (AtlasServiceException e) {
                e.printStackTrace();
              }
              return null;
            })
        .filter(Objects::nonNull)
        .map(AtlasSearchResult::getEntities)
        .filter(Objects::nonNull)
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
  }
}
