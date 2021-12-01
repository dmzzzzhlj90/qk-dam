package com.qk.dm.datamodel.service.impl;


import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dam.model.constant.ModelStatus;
import com.qk.dam.model.constant.ModelType;
import com.qk.dam.sqlbuilder.SqlBuilderFactory;
import com.qk.dam.sqlbuilder.model.Column;
import com.qk.dam.sqlbuilder.model.Table;
import com.qk.dm.datamodel.entity.ModelFactTable;
import com.qk.dm.datamodel.entity.QModelFactTable;
import com.qk.dm.datamodel.mapstruct.mapper.ModelFactTableMapper;
import com.qk.dm.datamodel.params.dto.ModelFactColumnDTO;
import com.qk.dm.datamodel.params.dto.ModelFactTableDTO;
import com.qk.dm.datamodel.params.dto.ModelSqlDTO;
import com.qk.dm.datamodel.params.vo.ModelFactTableVO;
import com.qk.dm.datamodel.repositories.ModelFactTableRepository;
import com.qk.dm.datamodel.service.ModelFactColumnService;
import com.qk.dm.datamodel.service.ModelFactTableService;
import com.qk.dm.datamodel.service.ModelSqlService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ModelFactTableServiceImpl implements ModelFactTableService {

    private JPAQueryFactory jpaQueryFactory;
    private final ModelFactTableRepository modelFactTableRepository;
    private final EntityManager entityManager;
    private final QModelFactTable qModelFactTable = QModelFactTable.modelFactTable;
    private final ModelFactColumnService modelFactColumnService;
    private final ModelSqlService modelSqlService;

    public ModelFactTableServiceImpl(ModelFactTableRepository modelFactTableRepository,
                                     EntityManager entityManager,ModelFactColumnService modelFactColumnService,
                                     ModelSqlService modelSqlService){
        this.modelFactTableRepository = modelFactTableRepository;
        this.entityManager = entityManager;
        this.modelFactColumnService = modelFactColumnService;
        this.modelSqlService = modelSqlService;
    }

    @PostConstruct
    public void initFactory() {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }


    @Override
    public void insert(ModelFactTableDTO modelFactTableDTO) {
        ModelFactTable modelFactTable = ModelFactTableMapper.INSTANCE.of(modelFactTableDTO);
        ModelFactTable modelFact = modelFactTableRepository.save(modelFactTable);
        List<ModelFactColumnDTO> modelFactColumnList = modelFactTableDTO.getModelFactColumnList();
        if(!modelFactColumnList.isEmpty()){
            if(checkRepeat(modelFactColumnList)){
                throw new BizException("存在重复的字段！！！");
            }
            modelFactColumnList.forEach(e->e.setFactId(modelFact.getId()));
            modelFactColumnService.insert(modelFactColumnList);
            //组装建表SQL,添加到数据库中
            ModelSqlDTO modelSql = ModelSqlDTO.builder().sqlSentence(generateSql(modelFactTable.getFactName(), modelFactColumnList))
                    .tableId(modelFact.getId()).type(ModelType.FACT_TABLE).build();
            modelSqlService.insert(modelSql);
        }
    }

    @Override
    public ModelFactTableVO detail(Long id) {
        Optional<ModelFactTable> modelFactTable = modelFactTableRepository.findById(id);
        if(modelFactTable.isEmpty()){
            throw new BizException("当前要查找的事实表id为"+id+"的数据不存在");
        }
        return ModelFactTableMapper.INSTANCE.of(modelFactTable.get());
    }

    @Override
    public void update(Long id, ModelFactTableDTO modelFactTableDTO) {
        ModelFactTable modelFactTable = modelFactTableRepository.findById(id).orElse(null);
        if(Objects.isNull(modelFactTable)){
            throw new BizException("当前要查找的事实表id为"+id+"的数据不存在");
        }
        ModelFactTableMapper.INSTANCE.from(modelFactTableDTO,modelFactTable);
        modelFactTableRepository.saveAndFlush(modelFactTable);
        List<ModelFactColumnDTO> modelFactColumnList = modelFactTableDTO.getModelFactColumnList();
        if(!modelFactColumnList.isEmpty()){
            if(checkRepeat(modelFactColumnList)){
                throw new BizException("存在重复的字段！！！");
            }
            modelFactColumnService.update(id,modelFactColumnList);
            //组装建表SQL,添加到数据库中
            ModelSqlDTO modelSql = ModelSqlDTO.builder().sqlSentence(generateSql(modelFactTable.getFactName(), modelFactColumnList))
                    .tableId(modelFactTable.getId()).type(ModelType.FACT_TABLE).build();
            modelSqlService.update(modelSql);
        }

    }

    @Override
    public void delete(String ids) {
        List<ModelFactTable> modelFactTableList = getModelFactTableList(ids);
        modelFactTableRepository.deleteAll(modelFactTableList);
    }

    @Override
    public PageResultVO<ModelFactTableVO> list(ModelFactTableDTO modelFactTableDTO) {
        Map<String, Object> map;
        try {
            map = queryByParams(modelFactTableDTO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("查询失败!!!");
        }
        List<ModelFactTable> list = (List<ModelFactTable>) map.get("list");
        List<ModelFactTableVO> voList = ModelFactTableMapper.INSTANCE.of(list);
        return new PageResultVO<>(
                (long) map.get("total"),
                modelFactTableDTO.getPagination().getPage(),
                modelFactTableDTO.getPagination().getSize(),
                voList);
    }

    @Override
    public void publish(String ids) {
        List<ModelFactTable> modelFactTableList = getModelFactTableList(ids);
        modelFactTableList.forEach(e->e.setStatus(ModelStatus.PUBLISH));
        modelFactTableRepository.saveAllAndFlush(modelFactTableList);

    }

    @Override
    public void offline(String ids) {
        List<ModelFactTable> modelFactTableList = getModelFactTableList(ids);
        modelFactTableList.forEach(e->e.setStatus(ModelStatus.OFFLINE));
        modelFactTableRepository.saveAllAndFlush(modelFactTableList);
    }

    @Override
    public String previewSql(Long tableId) {
        return modelSqlService.detail(ModelType.FACT_TABLE,tableId).getSqlSentence();
    }

    /**
     * 组装建表SQL语句
     * @param tableName
     * @param modelFactColumnList
     * @return
     */
    private String generateSql(String tableName,List<ModelFactColumnDTO> modelFactColumnList){
        List<Column> columns = new ArrayList<>();
        modelFactColumnList.forEach(column->{
            columns.add(Column.builder().name(column.getColumnName())
                    .dataType(column.getColumnType())
                    .comments(column.getDescription()).build());
        });
        return SqlBuilderFactory.creatTableSQL(Table.builder().name(tableName).columns(columns).build());
    }

    private Boolean checkRepeat(List<ModelFactColumnDTO> modelFactColumnList){
        Map<String, Long> collect = modelFactColumnList.stream()
                .collect(Collectors.groupingBy(ModelFactColumnDTO::getColumnName, Collectors.counting()));
        List<String> list = collect.keySet().stream().
                filter(key -> collect.get(key) > 1).collect(Collectors.toList());
        return !CollectionUtils.isEmpty(list);
    }
    private List<ModelFactTable> getModelFactTableList(String ids){
        Iterable<Long> idSet = Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toList());
        List<ModelFactTable> modelFactTableList = modelFactTableRepository.findAllById(idSet);
        if(modelFactTableList.isEmpty()){
            throw new BizException("当前要操作的事实表id为："+ids+"的数据不存在！！！");
        }
        return modelFactTableList;
    }

    private Map<String, Object> queryByParams(ModelFactTableDTO modelFactTableDTO) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        checkCondition(booleanBuilder, modelFactTableDTO);
        Map<String, Object> result = new HashMap<>();
        long count =
                jpaQueryFactory.select(qModelFactTable.count()).from(qModelFactTable).where(booleanBuilder).fetchOne();
        List<ModelFactTable> modelFactTableList = jpaQueryFactory
                .select(qModelFactTable)
                .from(qModelFactTable)
                .where(booleanBuilder)
                .orderBy(qModelFactTable.id.asc())
                .offset(
                        (long) (modelFactTableDTO.getPagination().getPage() - 1)
                                * modelFactTableDTO.getPagination().getSize())
                .limit(modelFactTableDTO.getPagination().getSize())
                .fetch();
        result.put("list", modelFactTableList);
        result.put("total", count);
        return result;
    }

    public void checkCondition(BooleanBuilder booleanBuilder, ModelFactTableDTO modelFactTableDTO) {
        if (!StringUtils.isEmpty(modelFactTableDTO.getFactName())) {
            booleanBuilder.and(
                    qModelFactTable
                            .factName
                            .contains(modelFactTableDTO.getFactName()));
        }
    }
}
