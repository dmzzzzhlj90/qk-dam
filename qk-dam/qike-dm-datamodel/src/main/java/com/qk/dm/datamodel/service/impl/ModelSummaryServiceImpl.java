package com.qk.dm.datamodel.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dam.model.constant.ModelStatus;
import com.qk.dam.model.constant.ModelType;
import com.qk.dam.sqlbuilder.SqlBuilderFactory;
import com.qk.dam.sqlbuilder.model.Column;
import com.qk.dam.sqlbuilder.model.Table;
import com.qk.dm.datamodel.entity.ModelSummary;
import com.qk.dm.datamodel.entity.QModelSummary;
import com.qk.dm.datamodel.mapstruct.mapper.ModelSummaryMapper;
import com.qk.dm.datamodel.params.dto.*;
import com.qk.dm.datamodel.params.vo.ModelSummaryVO;
import com.qk.dm.datamodel.repositories.ModelSummaryRepository;
import com.qk.dm.datamodel.service.ModelSqlService;
import com.qk.dm.datamodel.service.ModelSummaryIdcService;
import com.qk.dm.datamodel.service.ModelSummaryService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;
/**
 * 汇总表
 * @author wangzp
 * @date 2021/11/9 10:21
 * @since 1.0.0
 */
@Service
public class ModelSummaryServiceImpl implements ModelSummaryService {

    private JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;
    private final ModelSummaryRepository modelSummaryRepository;
    private final QModelSummary qModelSummary = QModelSummary.modelSummary;
    private final ModelSummaryIdcService modelSummaryIdcService;
    private final ModelSqlService modelSqlService;

    @PostConstruct
    public void initFactory() {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    public ModelSummaryServiceImpl(EntityManager entityManager,ModelSummaryRepository modelSummaryRepository,
                                   ModelSummaryIdcService modelSummaryIdcService,
                                   ModelSqlService modelSqlService){
        this.entityManager = entityManager;
        this.modelSummaryRepository = modelSummaryRepository;
        this.modelSummaryIdcService = modelSummaryIdcService;
        this.modelSqlService = modelSqlService;
    }

    @Override
    public void insert(ModelSummaryDTO modelSummaryDTO) {
        ModelSummary modelSummary = ModelSummaryMapper.INSTANCE.of(modelSummaryDTO);
        ModelSummary summary = modelSummaryRepository.save(modelSummary);
        List<ModelSummaryIdcDTO> modelSummaryIdcList = modelSummaryDTO.getModelSummaryIdcList();
        if(!modelSummaryIdcList.isEmpty()){
            if(checkRepeat(modelSummaryIdcList)){
                throw new BizException("存在重复的字段！！！");
            }
            modelSummaryIdcList.forEach(e->e.setSummaryId(summary.getId()));
            modelSummaryIdcService.insert(modelSummaryIdcList);
            //组装建表SQL,添加到数据库中
            ModelSqlDTO modelSql = ModelSqlDTO.builder().sqlSentence(generateSql(modelSummary.getTableName(), modelSummaryIdcList))
                    .tableId(summary.getId()).type(ModelType.SUMMARY_TABLE).build();
            modelSqlService.insert(modelSql);
        }
    }

    @Override
    public ModelSummaryVO detail(Long id) {
        Optional<ModelSummary> modelSummary = modelSummaryRepository.findById(id);
        if(modelSummary.isEmpty()){
            throw new BizException("当前查询的汇总表 id为"+id+"的数据不存在");
        }
        return ModelSummaryMapper.INSTANCE.of(modelSummary.get());
    }

    @Override
    public void update(Long id, ModelSummaryDTO modelSummaryDTO) {
        ModelSummary modelSummary = modelSummaryRepository.findById(id).orElse(null);
         if(Objects.isNull(modelSummary)){
             throw new BizException("当前查询的汇总表 id为"+id+"的数据不存在");
         }
         ModelSummaryMapper.INSTANCE.from(modelSummaryDTO,modelSummary);
        modelSummaryRepository.saveAndFlush(modelSummary);
        List<ModelSummaryIdcDTO> modelSummaryIdcList = modelSummaryDTO.getModelSummaryIdcList();
        if(!modelSummaryIdcList.isEmpty()){
            if(checkRepeat(modelSummaryIdcList)){
                throw new BizException("存在重复的字段！！！");
            }
            modelSummaryIdcList.forEach(e->e.setSummaryId(id));
            modelSummaryIdcService.update(id,modelSummaryIdcList);
            //组装建表SQL,添加到数据库中
            ModelSqlDTO modelSql = ModelSqlDTO.builder().sqlSentence(generateSql(modelSummary.getTableName(), modelSummaryIdcList))
                    .tableId(modelSummary.getId()).type(ModelType.FACT_TABLE).build();
            modelSqlService.update(modelSql);
        }

    }

    @Override
    public void delete(String ids) {
        List<ModelSummary> modelSummaryList = getModelSummaryList(ids);
        modelSummaryRepository.deleteAll(modelSummaryList);
    }

    @Override
    public PageResultVO<ModelSummaryVO> list(ModelSummaryDTO modelSummaryDTO) {
        Map<String, Object> map;
        try {
            map = queryByParams(modelSummaryDTO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("查询失败!!!");
        }
        List<ModelSummary> list = (List<ModelSummary>) map.get("list");
        List<ModelSummaryVO> voList = ModelSummaryMapper.INSTANCE.of(list);
        return new PageResultVO<>(
                (long) map.get("total"),
                modelSummaryDTO.getPagination().getPage(),
                modelSummaryDTO.getPagination().getSize(),
                voList);
    }

    @Override
    public void publish(String ids) {
        List<ModelSummary> modelSummaryList = getModelSummaryList(ids);
        modelSummaryList.forEach(e->e.setStatus(ModelStatus.PUBLISH));
        modelSummaryRepository.saveAllAndFlush(modelSummaryList);
    }

    @Override
    public void offline(String ids) {
        List<ModelSummary> modelSummaryList = getModelSummaryList(ids);
        modelSummaryList.forEach(e->e.setStatus(ModelStatus.OFFLINE));
        modelSummaryRepository.saveAllAndFlush(modelSummaryList);
    }

    @Override
    public String previewSql(Long tableId) {
        return modelSqlService.detail(ModelType.SUMMARY_TABLE,tableId).getSqlSentence();
    }

    /**
     *校验重复字段
     * @param modelSummaryIdcList
     * @return
     */
    private Boolean checkRepeat(List<ModelSummaryIdcDTO> modelSummaryIdcList){
        Map<String, Long> collect = modelSummaryIdcList.stream()
                .collect(Collectors.groupingBy(ModelSummaryIdcDTO::getIndicatorsName, Collectors.counting()));
        List<String> list = collect.keySet().stream().
                filter(key -> collect.get(key) > 1).collect(Collectors.toList());
        return !CollectionUtils.isEmpty(list);
    }

    /**
     * 组装建表SQL语句
     * @param tableName
     * @param modelSummaryIdcList
     * @return
     */
    private String generateSql(String tableName,List<ModelSummaryIdcDTO> modelSummaryIdcList){
        List<Column> columns = new ArrayList<>();
        modelSummaryIdcList.forEach(column->{
            columns.add(Column.builder().name(column.getIndicatorsName())
                    .dataType(column.getDataType())
                    .comments(column.getDescription()).build());
        });
        return SqlBuilderFactory.creatTableSQL(Table.builder().name(tableName).columns(columns).build());
    }

    private List<ModelSummary> getModelSummaryList(String ids){
        Iterable<Long> idSet = Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toList());
        List<ModelSummary> modelSummaryList = modelSummaryRepository.findAllById(idSet);
        if(modelSummaryList.isEmpty()){
            throw new BizException("当前要操作的汇总表id为："+ids+"的数据不存在！！！");
        }
        return modelSummaryList;
    }

    private Map<String, Object> queryByParams(ModelSummaryDTO modelSummaryDTO) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        checkCondition(booleanBuilder, modelSummaryDTO);
        Map<String, Object> result = new HashMap<>();
        long count =
                jpaQueryFactory.select(qModelSummary.count()).from(qModelSummary).where(booleanBuilder).fetchOne();
        List<ModelSummary> modelPhysicalTableList =
                jpaQueryFactory
                        .select(qModelSummary)
                        .from(qModelSummary)
                        .where(booleanBuilder)
                        .orderBy(qModelSummary.id.asc())
                        .offset(
                                (long) (modelSummaryDTO.getPagination().getPage() - 1)
                                        * modelSummaryDTO.getPagination().getSize())
                        .limit(modelSummaryDTO.getPagination().getSize())
                        .fetch();
        result.put("list", modelPhysicalTableList);
        result.put("total", count);
        return result;
    }

    public void checkCondition(BooleanBuilder booleanBuilder, ModelSummaryDTO modelSummaryDTO) {
        if (!StringUtils.isEmpty(modelSummaryDTO.getTableName())) {
            booleanBuilder.and(
                    qModelSummary
                            .tableName
                            .contains(modelSummaryDTO.getTableName()));
        }
    }
}
