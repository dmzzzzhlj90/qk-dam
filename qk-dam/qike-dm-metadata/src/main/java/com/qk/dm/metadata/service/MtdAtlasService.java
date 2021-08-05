package com.qk.dm.metadata.service;

import java.util.Map;

/** @author shenpengjie */
public interface MtdAtlasService {

  /**
   * 绑定atlas标签，会清空原有标签
   *
   * @param guid
   * @param labels
   */
  void setLabels(String guid, String labels);

  /**
   * 删除给定实体的给定标签
   *
   * @param guid
   * @param labels
   */
  void removeLabels(String guid, String labels);

  /**
   * 批量创建 atlas 类型 ：分类
   *
   * @param map
   */
  void typedefsByPost(Map<String, String> map);

  /**
   * 批量删除 atlas 类型 ：分类
   *
   * @param map
   */
  void typedefsByDelete(Map<String, String> map);

  /**
   * @param guid
   * @param classifiy
   */
  void addClassifications(String guid, String classifiy);
}
