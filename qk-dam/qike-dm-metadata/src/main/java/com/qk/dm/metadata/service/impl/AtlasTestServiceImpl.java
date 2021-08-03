package com.qk.dm.metadata.service.impl;

import com.google.gson.Gson;
import com.qk.dm.metadata.config.AtlasConfig;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.SearchFilter;
import org.apache.atlas.model.discovery.AtlasQuickSearchResult;
import org.apache.atlas.model.discovery.AtlasSearchResult;
import org.apache.atlas.model.discovery.QuickSearchParameters;
import org.apache.atlas.model.instance.*;
import org.apache.atlas.model.typedef.AtlasClassificationDef;
import org.apache.atlas.model.typedef.AtlasTypeDefHeader;
import org.apache.atlas.model.typedef.AtlasTypesDef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author spj
 * @date 2021/8/2 10:22 上午
 * @since 1.0.0
 */
public class AtlasTestServiceImpl {

    private static final Logger LOG = LoggerFactory.getLogger(AtlasTestServiceImpl.class);

    /**
     * 关系搜索以搜索满足搜索参数的相关实体
     *
     * @throws AtlasServiceException
     */
    public void relationship() throws AtlasServiceException {
        AtlasSearchResult atlasSearchResult = AtlasConfig.getAtlasClientV2().relationshipSearch(
                "3fa72e1b-3b8d-434f-a216-06f274fbf07b",
                null,
                null,
                null,
                false,
                5,
                0);
        LOG.info("fulltext 调取远程接口[{}]", new Gson().toJson(atlasSearchResult));
    }

    /**
     * 检索指定全文查询的数据
     *
     * @throws AtlasServiceException
     */
    public void fulltext() throws AtlasServiceException {
        AtlasSearchResult hive_db = AtlasConfig.getAtlasClientV2().fullTextSearch("mysql_db");
        LOG.info("fulltext 调取远程接口[{}]", new Gson().toJson(hive_db));

        AtlasSearchResult hive_db2 = AtlasConfig.getAtlasClientV2().fullTextSearchWithParams(
                "hive_db",
                5,
                0);
        LOG.info("fulltext 调取远程接口[{}]", new Gson().toJson(hive_db2));

    }

    /**
     * dsl
     *
     * @throws AtlasServiceException
     */
    public void dsl() throws AtlasServiceException {
        AtlasSearchResult hive_db = AtlasConfig.getAtlasClientV2().dslSearch("mysql_db");
        LOG.info("dsl 调取远程接口[{}]", new Gson().toJson(hive_db));

        AtlasSearchResult hive_db1 = AtlasConfig.getAtlasClientV2().dslSearchWithParams(
                "hive_table",
                5,
                0);
        LOG.info("dsl WithParams 调取远程接口[{}]", new Gson().toJson(hive_db1));
    }

    /**
     * 对满足搜索参数的实体进行基于属性的搜索
     *
     * @throws AtlasServiceException
     */
    public void basic() throws AtlasServiceException {
        AtlasSearchResult hive_db = AtlasConfig.getAtlasClientV2().basicSearch(
                "hive_table",
                "test123",
                "test",
                false,
                5,
                0);
        LOG.info("baseic 调取远程接口[{}]", new Gson().toJson(hive_db));

//        SearchParameters.FilterCriteria entityFilters = new SearchParameters.FilterCriteria();
//        AtlasSearchResult hive_db1 = AtlasConfig.getAtlasClientV2().basicSearch(
//                "hive_db",
//                entityFilters,
//                null,
//                null,
//                false,
//                5,
//                0);
//        LOG.info("baseic 调取远程接口[{}]", new Gson().toJson(hive_db1));
    }

    /**
     * 进行基于属性的快速搜索
     *
     * @throws AtlasServiceException
     */
    public void quick() throws AtlasServiceException {
        //guid:3202e270-eeaf-48a3-9d29-9ee7b4529ff1
        AtlasQuickSearchResult atlasQuickSearchResult = AtlasConfig.getAtlasClientV2().quickSearch(
                "test",
                "hive_table",
                false,
                5,
                0);
        LOG.info("quick 调取远程接口[{}]", new Gson().toJson(atlasQuickSearchResult));
        //guid:483c95fc-f134-4f25-aff0-45d6ebbe82bd
        QuickSearchParameters quickSearchParameters = new QuickSearchParameters();
        quickSearchParameters.setQuery("dba");
        quickSearchParameters.setTypeName("mysql_db");
        quickSearchParameters.setLimit(5);
        quickSearchParameters.setOffset(0);
        AtlasQuickSearchResult atlasQuickSearchResult1 = AtlasConfig.getAtlasClientV2().quickSearch(quickSearchParameters);
        LOG.info("quick 调取远程接口[{}]", new Gson().toJson(atlasQuickSearchResult1));
    }

    /**
     * 获取给定 GUID 的实体的完整定义
     */
    public void entityGuid() throws AtlasServiceException{
        AtlasEntity.AtlasEntityWithExtInfo entityByGuid = AtlasConfig.getAtlasClientV2()
                .getEntityByGuid("a16f051d-4756-42c8-acc6-d6925f0a3900", true, false);
        LOG.info("entityGuid 调取远程接口[{}]", new Gson().toJson(entityByGuid));
    }

    /**
     * 根据其 GUID 获取实体标头
     *
     * @throws AtlasServiceException
     */
    public void guidHeader() throws AtlasServiceException {
        AtlasEntityHeader entityHeaderByGuid = AtlasConfig.getAtlasClientV2()
                .getEntityHeaderByGuid("3202e270-eeaf-48a3-9d29-9ee7b4529ff1");
        LOG.info("entityHeaderByGuid 调取远程接口[{}]", new Gson().toJson(entityHeaderByGuid));
    }

    public void setLabels() throws AtlasServiceException {
        //定义标签 label should contain alphanumeric characters, _ or -
        Set<String> labels = Stream.of("test1-3").collect(Collectors.toSet());
        //为给定实体设置标签 POST
        AtlasConfig.getAtlasClientV2().setLabels("3202e270-eeaf-48a3-9d29-9ee7b4529ff1", labels);
    }

    public void addLabels() throws AtlasServiceException {
        //定义标签 label should contain alphanumeric characters, _ or -
        Set<String> labels = Stream.of("test1-2").collect(Collectors.toSet());
        //将给定的标签添加到给定的实体 PUT
        AtlasConfig.getAtlasClientV2().addLabels("3202e270-eeaf-48a3-9d29-9ee7b4529ff1", labels);
    }

    public void removeLabels() throws AtlasServiceException {
        //定义标签
        Set<String> labels = Stream.of("test1-").collect(Collectors.toSet());
        //删除给定实体的给定标签
        AtlasConfig.getAtlasClientV2().removeLabels("3202e270-eeaf-48a3-9d29-9ee7b4529ff1", labels);
    }

    /**
     * 树形返回
     * 用于检索 Atlas 中所有类型定义的批量检索 API
     */
    public void typedefs() throws AtlasServiceException {
        SearchFilter searchFilter = new SearchFilter();
        AtlasTypesDef allTypeDefs = AtlasConfig.getAtlasClientV2().getAllTypeDefs(searchFilter);
        LOG.info("entityHeaderByGuid 调取远程接口[{}]", new Gson().toJson(allTypeDefs.getClassificationDefs()));
    }

    /**
     * 列表返回
     * 所有类型定义的批量检索 API 作为最小信息标头列表返回
     */
    public void typedefsHeaders() throws AtlasServiceException {
        SearchFilter searchFilter = new SearchFilter();
        List<AtlasTypeDefHeader> allTypeDefHeaders = AtlasConfig.getAtlasClientV2().getAllTypeDefHeaders(searchFilter);
        LOG.info("entityHeaderByGuid 调取远程接口[{}]",new Gson().toJson(allTypeDefHeaders));
    }

    /**
     * 为所有 atlas 类型定义批量创建 API，只会创建新定义
     * @throws AtlasServiceException
     */
    public void createAtlasTypeDefs() throws AtlasServiceException {
        AtlasTypesDef typesDef = new AtlasTypesDef();
        AtlasClassificationDef atlasClassificationDef = new AtlasClassificationDef("test1234","test1234");
        typesDef.setClassificationDefs(Stream.of(atlasClassificationDef).collect(Collectors.toList()));
        AtlasConfig.getAtlasClientV2().createAtlasTypeDefs(typesDef);
    }

    /**
     * 所有类型的批量更新 API，类型定义中检测到的更改将被持久化
     * @throws AtlasServiceException
     */
    public void updateAtlasTypeDefs() throws AtlasServiceException {
        AtlasTypesDef typesDef = new AtlasTypesDef();
        AtlasClassificationDef atlasClassificationDef = new AtlasClassificationDef("test1234","test1234");
        typesDef.setClassificationDefs(Stream.of(atlasClassificationDef).collect(Collectors.toList()));
        AtlasConfig.getAtlasClientV2().updateAtlasTypeDefs(typesDef);
    }

    /**
     * 删除由其名称标识的类型的 API
     * @throws AtlasServiceException
     */
    public void deleteTypeByName() throws AtlasServiceException {
        AtlasConfig.getAtlasClientV2().deleteTypeByName("test1234");
    }

    /**
     * 适用于所有类型的批量删除 API
     * @throws AtlasServiceException
     */
    public void deleteAtlasTypeDefs() throws AtlasServiceException {
        AtlasTypesDef typesDef = new AtlasTypesDef();
        AtlasClassificationDef atlasClassificationDef = new AtlasClassificationDef("test1234","test1234");
        typesDef.setClassificationDefs(Stream.of(atlasClassificationDef).collect(Collectors.toList()));
        AtlasConfig.getAtlasClientV2().deleteAtlasTypeDefs(typesDef);
    }

    /**
     * 获取由 guid 表示的给定实体的分类列表
     *
     * @throws AtlasServiceException
     */
    public void guidClass() throws AtlasServiceException {
        AtlasClassification.AtlasClassifications classifications = AtlasConfig.getAtlasClientV2()
                .getClassifications("a16f051d-4756-42c8-acc6-d6925f0a3900");
        LOG.info("classifications 调取远程接口[{}]", new Gson().toJson(classifications));
    }

    public void addClassifications() throws AtlasServiceException {
        AtlasClassification atlasClassification = new AtlasClassification("test123");
        //向由 guid 表示的现有实体添加分类
        List<AtlasClassification> classificationList =
                Stream.of(atlasClassification).collect(Collectors.toList());
        LOG.info("classificationList 调取远程接口[{}]", new Gson().toJson(classificationList));
        //添加 483c95fc-f134-4f25-aff0-45d6ebbe82bd
        AtlasConfig.getAtlasClientV2().addClassifications("a16f051d-4756-42c8-acc6-d6925f0a3900", classificationList);
    }

    public void updateClassifications() throws AtlasServiceException {
        AtlasClassification atlasClassification = new AtlasClassification("test123");

        //向由 guid 表示的现有实体添加分类
        List<AtlasClassification> classificationList =
                Stream.of(atlasClassification).collect(Collectors.toList());
        //将分类更新为由 guid 表示的现有实体
        AtlasConfig.getAtlasClientV2().updateClassifications("a16f051d-4756-42c8-acc6-d6925f0a3900", classificationList);
    }

    public void deleteClassification() throws AtlasServiceException {
        //删除给定分类
        AtlasConfig.getAtlasClientV2().deleteClassification("a16f051d-4756-42c8-acc6-d6925f0a3900", "test123");
    }

    public void removeClassification() throws AtlasServiceException {
        //向由 guid 表示的现有实体添加分类
        List<AtlasClassification> classificationList =
                Stream.of(new AtlasClassification())
                        .collect(Collectors.toList());
        //删除给定分类及关联实体分类
        AtlasConfig.getAtlasClientV2().removeClassification("483c95fc-f134-4f25-aff0-45d6ebbe82bd", "分类名称", "关联实体guid");
    }

    /**
     * 将标签关联到多个实体的批量 API
     * @throws AtlasServiceException
     */
    public void addClassification() throws AtlasServiceException {
        //向由 guid 表示的现有实体添加分类
        AtlasClassification atlasClassification = new AtlasClassification("test123");
        //批量设置分类标签
        ClassificationAssociateRequest request = new ClassificationAssociateRequest();
        request.setClassification(atlasClassification);
        request.setEntityGuids(Stream.of("a16f051d-4756-42c8-acc6-d6925f0a3900").collect(Collectors.toList()));
        AtlasConfig.getAtlasClientV2().addClassification(request);
    }

    /**
     * 批量设置分类标签
     * @throws AtlasServiceException
     */
    public void classifications() throws AtlasServiceException {
        AtlasEntityHeaders entityHeaders = new AtlasEntityHeaders();
        Map<String, AtlasEntityHeader> guidEntityHeaderMap = new HashMap<>();
        AtlasEntityHeader atlasEntityHeader = new AtlasEntityHeader();
//        atlasEntityHeader.setClassifications();
//        atlasEntityHeader.setLabels();
//        atlasEntityHeader.setGuid();
        guidEntityHeaderMap.put("test",atlasEntityHeader);
        entityHeaders.setGuidHeaderMap(guidEntityHeaderMap);
        AtlasConfig.getAtlasClientV2().setClassifications(entityHeaders);
    }

    public static void main(String[] args) throws AtlasServiceException {
        AtlasTestServiceImpl atlasTestService = new AtlasTestServiceImpl();
//        //进行基于属性的快速搜索
//        atlasTestService.quick();
//        //对满足搜索参数的实体进行基于属性的搜索
//        atlasTestService.basic();
//        //dsl
//        atlasTestService.dsl();
//        //检索指定全文查询的数据
//        atlasTestService.fulltext();
//        //关系搜索以搜索满足搜索参数的相关实体
//        atlasTestService.relationship();
        //获取由 guid 表示的给定实体的分类列表
//        atlasTestService.entityGuid();

        //标签
        //为给定实体设置标签-会清空原有标签
//        atlasTestService.setLabels();
        //将给定的标签添加到给定的实体
//        atlasTestService.addLabels();
        //删除给定实体的给定标签
//        atlasTestService.removeLabels();
        //根据其 GUID 获取实体标头
//        atlasTestService.guidHeader();

        //分类
        //用于检索 Atlas 中所有类型定义的批量检索 API
//        atlasTestService.typedefs();
        //所有类型定义的批量检索 API 作为最小信息标头列表返回
//        atlasTestService.typedefsHeaders();
        //为所有 atlas 类型定义批量创建 API，只会创建新定义
//        atlasTestService.createAtlasTypeDefs();
        //所有类型的批量更新 API，类型定义中检测到的更改将被持久化
//        atlasTestService.updateAtlasTypeDefs();
        //删除由其名称标识的类型的 API
//        atlasTestService.deleteTypeByName();
        //适用于所有类型的批量删除 API
//        atlasTestService.deleteAtlasTypeDefs();

        //分类与实体
        //添加分类
//        atlasTestService.addClassifications();
        //修改
//        atlasTestService.updateClassifications();
        //删除给定分类关系
//        atlasTestService.deleteClassification();
        //删除给定分类及关联实体分类
//        atlasTestService.removeClassification();
        //将标签关联到多个实体的批量 API
//        atlasTestService.addClassification();
        //批量设置分类标签
//        atlasTestService.classifications();
        //获取由 guid 表示的给定实体的分类列表
//        atlasTestService.guidClass();
    }

}
