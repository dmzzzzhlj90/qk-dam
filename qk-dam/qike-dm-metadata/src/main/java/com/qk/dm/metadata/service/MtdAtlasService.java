package com.qk.dm.metadata.service;

import org.apache.atlas.AtlasServiceException;

import java.util.Map;

/**
 * @author shenpengjie
 */
public interface MtdAtlasService {

    /**
     * 绑定atlas标签，会清空原有标签
     *
     * @param guid
     * @param labels
     */
    void setLabels(String guid, String labels) throws AtlasServiceException;

    /**
     * 删除给定实体的给定标签
     *
     * @param guid
     * @param labels
     */
    void removeLabels(String guid, String labels) throws AtlasServiceException;

    /**
     * 删除给定实体的所有标签
     * @param guid
     * @throws AtlasServiceException
     */
    void removeLabels(String guid) throws AtlasServiceException;

    /**
     * 批量创建 atlas 类型 ：分类
     *
     * @param map
     */
    void postTypedefs(Map<String, String> map) throws AtlasServiceException;

    /**
     * 删除实体类上的全部分类
     * @param guid
     * @throws AtlasServiceException
     */
    void delEntitiesClassis(String guid) throws AtlasServiceException;

    /**
     * 批量删除 atlas 类型 ：分类
     *
     * @param map
     */
    void deleteTypedefs(Map<String, String> map) throws AtlasServiceException;

    /**
     * 单独删除
     * @param name
     * @param description
     * @throws AtlasServiceException
     */
    void postTypedefs(String name,String description) throws AtlasServiceException;

    /**
     * @param guid
     * @param classifiy
     */
    void addClassifications(String guid, String classifiy) throws AtlasServiceException;
}
