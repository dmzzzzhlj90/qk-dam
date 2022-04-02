package com.qk.dm.metadata.lineage;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.google.common.collect.Lists;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dam.jpa.pojo.Pagination;
import com.qk.dam.metedata.AtlasClient;
import com.qk.dam.metedata.config.AtlasConfig;
import com.qk.dm.metadata.vo.LineageSearchVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.SortOrder;
import org.apache.atlas.model.discovery.AtlasSearchResult;
import org.apache.atlas.model.discovery.SearchParameters;
import org.apache.atlas.model.instance.AtlasEntity;
import org.apache.atlas.model.instance.AtlasEntityHeader;
import org.apache.atlas.type.AtlasTypeUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

import static com.qk.dam.metedata.type.AtlasEntityConstant.*;


/**
 * 血缘管理器
 *
 * @author zhudaoming
 */
@Slf4j
@Component
public class LineageManagerImpl implements LineageService {
    private final AtlasClient atlasClient;
    private final Process process;

    public LineageManagerImpl(AtlasClient atlasClient, Process process) {
        this.atlasClient = atlasClient;
        this.process = process;
    }


    @Override
    public PageResultVO<AtlasEntityHeader> lineageProcess(LineageSearchVO lineageSearchVO) throws AtlasServiceException {
        Pagination pagination = lineageSearchVO.getPagination();

        SearchParameters.FilterCriteria entityFilter = new SearchParameters.FilterCriteria();

        entityFilter.setCondition(SearchParameters.FilterCriteria.Condition.AND);

        //tip 以下条件参数逻辑为 typeName and (name or qualifiedName)
        List<SearchParameters.FilterCriteria> criterions = Lists.newArrayList();
        if (Objects.nonNull(lineageSearchVO.getTypeName())){
            // typename 过滤条件不为空
            SearchParameters.FilterCriteria criterion = new SearchParameters.FilterCriteria();
            criterion.setAttributeName("__typeName");
            criterion.setOperator(SearchParameters.Operator.EQ);
            criterion.setAttributeValue(lineageSearchVO.getTypeName());
            criterions.add(criterion);
            entityFilter.setCriterion(criterions);
        }

        if (Objects.nonNull(lineageSearchVO.getQualifiedName())){
            SearchParameters.FilterCriteria groupCti = new SearchParameters.FilterCriteria();
            List<SearchParameters.FilterCriteria> groupCriterions = Lists.newArrayList();
            groupCti.setCondition(SearchParameters.FilterCriteria.Condition.OR);

            // name 按名称检索
            SearchParameters.FilterCriteria criterion = new SearchParameters.FilterCriteria();
            criterion.setAttributeName("qualifiedName");
            criterion.setOperator(SearchParameters.Operator.CONTAINS);
            criterion.setAttributeValue(lineageSearchVO.getQualifiedName());
            groupCriterions.add(criterion);

            SearchParameters.FilterCriteria nameCriterion = new SearchParameters.FilterCriteria();
            nameCriterion.setAttributeName("name");
            nameCriterion.setOperator(SearchParameters.Operator.CONTAINS);
            nameCriterion.setAttributeValue(lineageSearchVO.getQualifiedName());
            groupCriterions.add(nameCriterion);

            groupCti.setCriterion(groupCriterions);
            criterions.add(groupCti);
            entityFilter.setCriterion(criterions);
        }



        SearchParameters parameters = new SearchParameters();
        parameters.setAttributes(Set.of("__timestamp"));
        parameters.setTypeName("Process");
        parameters.setExcludeDeletedEntities(true);
        parameters.setSortOrder(SortOrder.DESCENDING);
        parameters.setSortBy("__timestamp");
        parameters.setLimit(pagination.getSize());
        parameters.setOffset((pagination.getPage() - 1));
        parameters.setEntityFilters(entityFilter);
        AtlasSearchResult atlasSearchResult = atlasClient.instance().callAPI(AtlasClientV2.API_V2.BASIC_SEARCH,
                AtlasSearchResult.class,
                parameters);



        if (Objects.nonNull(atlasSearchResult.getEntities())){
            return new PageResultVO<>(
                    atlasSearchResult.getApproximateCount(),
                    pagination.getPage(), pagination.getSize(),
                    atlasSearchResult.getEntities());
        }
        return  new PageResultVO<>(
                atlasSearchResult.getApproximateCount(),
                pagination.getPage(),pagination.getSize(),
                Lists.newArrayList());
    }

    /**
     * 导入血缘
     * @param excelFile excel stream
     * @throws AtlasServiceException e
     */
    @Override
    public  void lineageImport(InputStream excelFile) throws AtlasServiceException {
        List<DataConnect> dataConnects = Lists.newArrayList();
        List<LineageTemplateVO> lineageTemplate = Lists.newArrayList();
        extractedVO(excelFile, dataConnects, lineageTemplate);

        Map<String, DataConnect> dataConnectMap = dataConnects.stream().distinct().collect(Collectors.toMap(DataConnect::getName, v -> v,(a,b)->a));
        createProcess(lineageTemplate, dataConnectMap);

    }

    /**
     * 清理血缘
     * @param excelFile excel stream
     * @throws AtlasServiceException e
     */
    @Override
    public  void lineageClear(InputStream excelFile) throws AtlasServiceException {
        List<DataConnect> dataConnects = new ArrayList<>();
        List<LineageTemplateVO> lineageTemplate =  new ArrayList<>();
        extractedVO(excelFile, dataConnects, lineageTemplate);

        Map<String, DataConnect> dataConnectMap = dataConnects.stream().distinct().collect(Collectors.toMap(DataConnect::getName, v -> v,(a,b)->a));
        deleteProcess(lineageTemplate, dataConnectMap);

    }
    @Override
    public void deleteEntitiesByGuids(List<String> guids) throws AtlasServiceException {
        atlasClient.instance().deleteEntitiesByGuids(guids);

        // fixme 清理delete实体太慢，需要异步处理并优化
        // realCleanEntities();
    }

    private void extractedVO(InputStream excelFile, List<DataConnect> dataConnects, List<LineageTemplateVO> lineageTemplate) {
        ExcelReader excelReader = EasyExcelFactory.read(excelFile).build();
        excelReader.read(
                EasyExcelFactory.readSheet(0).head(LineageTemplateVO.class).registerReadListener(new ReadListener<LineageTemplateVO>() {
                            @Override
                            public void invoke(LineageTemplateVO data, AnalysisContext context) {
                                if (Objects.nonNull(data.getSourceEntity())&&
                                        Objects.nonNull(data.getTargetEntity())){
                                    lineageTemplate.add(data);
                                }

                            }

                            @Override
                            public void doAfterAllAnalysed(AnalysisContext context) {
                                log.info("完成血缘数据读取");
                            }
                        })
                .build(),
                EasyExcelFactory.readSheet(1).head(DataConnect.class).registerReadListener(new ReadListener<DataConnect>() {
                            @Override
                            public void invoke(DataConnect data, AnalysisContext context) {
                                if (Objects.nonNull(data.getName())){
                                    dataConnects.add(data);
                                }

                            }

                            @Override
                            public void doAfterAllAnalysed(AnalysisContext context) {
                                log.info("完成数据连接读取");
                            }
                        })
                        .build());
    }

    private void createProcess(final List<LineageTemplateVO> lineages, final Map<String, DataConnect> dataConnectMap) throws AtlasServiceException {

        AtlasEntity.AtlasEntitiesWithExtInfo atlasEntitiesWithExtInfo = new AtlasEntity.AtlasEntitiesWithExtInfo();
        // 生产转换 process entity
        lineages.stream().map(lineageTemplateVO ->
                // 获取模板数据为atlas entity
                processEntity(dataConnectMap, lineageTemplateVO))
                .forEach(atlasEntitiesWithExtInfo::addEntity);

        // 创建 process entity
        createProcessEntity(atlasEntitiesWithExtInfo);
    }

    private void deleteProcess(final List<LineageTemplateVO> lineages, final Map<String, DataConnect> dataConnectMap) throws AtlasServiceException {

        AtlasEntity.AtlasEntitiesWithExtInfo atlasEntitiesWithExtInfo = new AtlasEntity.AtlasEntitiesWithExtInfo();
        // 生产转换 process entity
        lineages.stream().map(lineageTemplateVO ->
                        processEntity(dataConnectMap, lineageTemplateVO))
                .forEach(atlasEntitiesWithExtInfo::addEntity);

        // 删除 process entity
        deleteProcessEntity(atlasEntitiesWithExtInfo);
    }

    private void createProcessEntity(AtlasEntity.AtlasEntitiesWithExtInfo atlasEntitiesWithExtInfo) throws AtlasServiceException {
        atlasClient.instance().createEntities(atlasEntitiesWithExtInfo);
    }
    private void deleteProcessEntity(AtlasEntity.AtlasEntitiesWithExtInfo atlasEntitiesWithExtInfo) throws AtlasServiceException {

        atlasEntitiesWithExtInfo.getEntities().stream()
                .map(a -> a.getAttribute(AtlasTypeUtil.ATTRIBUTE_QUALIFIED_NAME))
                .filter(Objects::nonNull)
                .map(Object::toString).forEach(q-> {
                    try {
                        atlasClient.instance().deleteEntityByAttribute("Process",Map.of(AtlasTypeUtil.ATTRIBUTE_QUALIFIED_NAME,q));
                    } catch (AtlasServiceException e) {
                        e.printStackTrace();
                    }
                });


        // fixme 清理delete实体太慢，需要异步处理并优化
        // realCleanEntities();
    }

    private AtlasEntity processEntity(Map<String, DataConnect> dataConnectMap, LineageTemplateVO lineageTemplateVO) {
        AtlasEntity atlasEntity = new AtlasEntity();
        DataConnect sourceDataConnect = dataConnectMap.get(lineageTemplateVO.getSourceConnectName());
        DataConnect targetDataConnect = dataConnectMap.get(lineageTemplateVO.getTargetConnectName());

        String sourceEntity = lineageTemplateVO.getSourceEntity();
        String targetEntity = lineageTemplateVO.getTargetEntity();
        String uniqName = StringUtils.join(
                List.of(sourceEntity, targetEntity),
                '>');

        //uniq qualified
        atlasEntity.setAttribute(AtlasTypeUtil.ATTRIBUTE_QUALIFIED_NAME,  StringUtils.join(
                List.of(lineageTemplateVO.getProcessId(), targetDataConnect.getHost()),
                '@'));

        atlasEntity.setTypeName(targetDataConnect.getType());
        atlasEntity.setAttribute(ENTITY_NAME, lineageTemplateVO.getProcessId());
        atlasEntity.setAttribute(ENTITY_DESCRIPTION, lineageTemplateVO.getDescription());
        atlasEntity.setAttribute(ENTITY_OWNER, lineageTemplateVO.getUser());
        atlasEntity.setAttribute(ENTITY_CREATE_TIME, System.currentTimeMillis());
        atlasEntity.setAttribute(PROCESS_USER_NAME, lineageTemplateVO.getUser());
        // hive_process
        atlasEntity.setAttribute(PROCESS_START_TIME,System.currentTimeMillis());
        atlasEntity.setAttribute(PROCESS_END_TIME,System.currentTimeMillis());
        atlasEntity.setAttribute(OPERATIONTYPE,"linkTable");
        atlasEntity.setAttribute(QUERYTEXT, lineageTemplateVO.getDescription());
        atlasEntity.setAttribute(QUERYPLAN,"{}");
        atlasEntity.setAttribute(QUERYID,uniqName);

        //fixme sourceEntity 为源实体 规则时多个实体id用回车换行符分割多个entity targetEntity同理
        //process
        atlasEntity.setRelationshipAttribute(PROCESS_ATTRIBUTE_INPUTS,
                process.inputIds(
                        sourceDataConnect.getAtlasType(),
                        entityQualified(sourceEntity.split("\r|\n|\r\n"), sourceDataConnect)
                ));

        atlasEntity.setRelationshipAttribute(PROCESS_ATTRIBUTE_OUTPUTS,
                process.outputIds(
                        targetDataConnect.getAtlasType(),
                        entityQualified(targetEntity.split("\r|\n|\r\n"), targetDataConnect)
                ));

        return atlasEntity;
    }

    private static String[] entityQualified(String[] entityArray, DataConnect dataConnect) {
        return Arrays.stream(entityArray).map(e -> StringUtils.join(
                List.of(e, dataConnect.getHost()),
                '@')).toArray(String[]::new);

    }

    @Override
    public void realCleanEntities() throws AtlasServiceException {
        SearchParameters.FilterCriteria entityFilter = new SearchParameters.FilterCriteria();

        entityFilter.setCondition(SearchParameters.FilterCriteria.Condition.AND);
        SearchParameters.FilterCriteria criterion = new SearchParameters.FilterCriteria();
        criterion.setAttributeName("__state");
        criterion.setAttributeValue("DELETED");
        criterion.setOperator(SearchParameters.Operator.EQ);
        entityFilter.setCriterion(List.of(criterion));


        AtlasSearchResult atlasSearchResult = atlasClient.instance().basicSearch("Process", entityFilter, null, null, false, 50, 0);
        if (Objects.nonNull(atlasSearchResult.getEntities())){
            Set<String> collect = atlasSearchResult.getEntities()
                    .stream().map(AtlasEntityHeader::getGuid)
                    .collect(Collectors.toSet());
            atlasClient.instance().purgeEntitiesByGuids(collect);
        }

    }




}
