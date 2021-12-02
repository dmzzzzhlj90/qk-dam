package com.qk.dm.datamodel.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dam.model.constant.ModelType;
import com.qk.dam.sqlbuilder.SqlBuilderFactory;
import com.qk.dam.sqlbuilder.model.Column;
import com.qk.dam.sqlbuilder.model.Table;
import com.qk.dm.datamodel.entity.ModelDimTable;
import com.qk.dm.datamodel.entity.QModelDimTable;
import com.qk.dm.datamodel.mapstruct.mapper.ModelDimTableMapper;
import com.qk.dm.datamodel.params.dto.ModelDimTableColumnDTO;
import com.qk.dm.datamodel.params.dto.ModelDimTableDTO;
import com.qk.dm.datamodel.params.dto.ModelSqlDTO;
import com.qk.dm.datamodel.params.vo.ModelDimTableVO;
import com.qk.dm.datamodel.repositories.ModelDimTableRepository;
import com.qk.dm.datamodel.service.ModelDimTableColumnService;
import com.qk.dm.datamodel.service.ModelDimTableService;
import com.qk.dm.datamodel.service.ModelSqlService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ModelDimTableServiceImpl implements ModelDimTableService {

    private JPAQueryFactory jpaQueryFactory;
    private final ModelDimTableRepository modelDimTableRepository;
    private final EntityManager entityManager;
    private final QModelDimTable qModelDimTable = QModelDimTable.modelDimTable;
    private final ModelSqlService modelSqlService;
    private final ModelDimTableColumnService modelDimTableColumnService;

    public ModelDimTableServiceImpl(ModelDimTableRepository modelDimTableRepository,
                                    EntityManager entityManager,
                                    ModelSqlService modelSqlService,
                                    ModelDimTableColumnService modelDimTableColumnService){
        this.modelDimTableRepository = modelDimTableRepository;
        this.entityManager = entityManager;
        this.modelSqlService = modelSqlService;
        this.modelDimTableColumnService = modelDimTableColumnService;
    }
    @PostConstruct
    public void initFactory() {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public void insert(ModelDimTableDTO modelDimTableDTO) {
        ModelDimTable modelDimTable = ModelDimTableMapper.INSTANCE.of(modelDimTableDTO);
        ModelDimTable dimTable = modelDimTableRepository.save(modelDimTable);
        List<ModelDimTableColumnDTO> modelDimTableDTOColumnList = modelDimTableDTO.getColumnList();
        if(!modelDimTableDTOColumnList.isEmpty()){
            modelDimTableDTOColumnList.forEach(e->e.setDimTableId(dimTable.getId()));
            modelDimTableColumnService.insert(modelDimTableDTO.getColumnList());
            //组装建表SQL,添加到数据库中
            ModelSqlDTO modelSql = ModelSqlDTO.builder().sqlSentence(generateSql(dimTable.getDimName(), modelDimTableDTOColumnList))
                    .tableId(dimTable.getId()).type(ModelType.DIM_TABLE).build();
            modelSqlService.insert(modelSql);
        }

    }

    @Override
    public ModelDimTableVO detail(Long id) {
        Optional<ModelDimTable> modelDimTable = modelDimTableRepository.findById(id);
        if(modelDimTable.isEmpty()){
            throw new BizException("当前查找的维度表id为"+id+"的数据不存在");
        }
       return ModelDimTableMapper.INSTANCE.of(modelDimTable.get());
    }

    @Override
    public void update(Long id, ModelDimTableDTO modelDimTableDTO) {
        ModelDimTable modelDimTable = modelDimTableRepository.findById(id).orElse(null);
        if(Objects.isNull(modelDimTable)){
            throw new BizException("当前查找的维度表id为"+id+"的数据不存在");
        }
        ModelDimTableMapper.INSTANCE.from(modelDimTableDTO,modelDimTable);
        modelDimTableRepository.saveAndFlush(modelDimTable);

        List<ModelDimTableColumnDTO> modelDimTableDTOColumnList = modelDimTableDTO.getColumnList();
        if(!modelDimTableDTOColumnList.isEmpty()){
            modelDimTableDTOColumnList.forEach(e->e.setDimTableId(modelDimTable.getId()));
            modelDimTableColumnService.update(modelDimTable.getId(),modelDimTableDTO.getColumnList());
            //修改表SQL,添加到数据库中
            ModelSqlDTO modelSql = ModelSqlDTO.builder().sqlSentence(generateSql(modelDimTable.getDimName(), modelDimTableDTOColumnList))
                    .tableId(modelDimTable.getId()).type(ModelType.DIM_TABLE).build();
            modelSqlService.update(modelSql);
        }
    }

    @Override
    public void delete(String ids) {
        Iterable<Long> idSet = Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toList());
        List<ModelDimTable> modelDimTableList = modelDimTableRepository.findAllById(idSet);
        if(modelDimTableList.isEmpty()){
            throw new BizException("当前要删除的维度表id为："+ids+"的数据不存在！！！");
        }
        modelDimTableRepository.deleteAll(modelDimTableList);
    }

    @Override
    public PageResultVO<ModelDimTableVO> list(ModelDimTableDTO modelDimTableDTO) {
        Map<String, Object> map;
        try {
            map = queryByParams(modelDimTableDTO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("查询失败!!!");
        }
        List<ModelDimTable> list = (List<ModelDimTable>) map.get("list");
        List<ModelDimTableVO> voList = ModelDimTableMapper.INSTANCE.of(list);
        return new PageResultVO<>(
                (long) map.get("total"),
                modelDimTableDTO.getPagination().getPage(),
                modelDimTableDTO.getPagination().getSize(),
                voList);
    }

    @Override
    public String previewSql(Long tableId) {
        return modelSqlService.detail(ModelType.DIM_TABLE,tableId).getSqlSentence();
    }

    /**
     * 组装建表SQL语句
     * @param tableName
     * @param modelDimTableDTOColumnList
     * @return
     */
    private String generateSql(String tableName,List<ModelDimTableColumnDTO> modelDimTableDTOColumnList){
        List<Column> columns = new ArrayList<>();
        modelDimTableDTOColumnList.forEach(column->{
            columns.add(Column.builder().name(column.getColumnName())
                    .dataType(column.getColumnType())
                    .comments(column.getDescription()).build());
        });
        return SqlBuilderFactory.creatTableSQL(Table.builder().name(tableName).columns(columns).build());
    }

    private Map<String, Object> queryByParams(ModelDimTableDTO modelDimTableDTO) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        checkCondition(booleanBuilder, modelDimTableDTO);
        Map<String, Object> result = new HashMap<>();
        long count =
                jpaQueryFactory.select(qModelDimTable.count()).from(qModelDimTable).where(booleanBuilder).fetchOne();
        List<ModelDimTable> modelDimTableList = jpaQueryFactory
                .select(qModelDimTable)
                .from(qModelDimTable)
                .where(booleanBuilder)
                .orderBy(qModelDimTable.id.asc())
                .offset(
                        (long) (modelDimTableDTO.getPagination().getPage() - 1)
                                * modelDimTableDTO.getPagination().getSize())
                .limit(modelDimTableDTO.getPagination().getSize())
                .fetch();
        result.put("list", modelDimTableList);
        result.put("total", count);
        return result;
    }

    public void checkCondition(BooleanBuilder booleanBuilder, ModelDimTableDTO modelDimTableDTO) {
        if (!StringUtils.isEmpty(modelDimTableDTO.getDimName())) {
            booleanBuilder.and(
                    qModelDimTable
                            .dimName
                            .contains(modelDimTableDTO.getDimName()));
        }
    }
}
