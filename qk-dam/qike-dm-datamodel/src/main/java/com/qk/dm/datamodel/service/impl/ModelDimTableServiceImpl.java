package com.qk.dm.datamodel.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dam.model.constant.ModelStatus;
import com.qk.dam.model.constant.ModelType;
import com.qk.dam.sqlbuilder.SqlBuilderFactory;
import com.qk.dam.sqlbuilder.model.Column;
import com.qk.dam.sqlbuilder.model.Table;
import com.qk.dm.datamodel.entity.ModelDim;
import com.qk.dm.datamodel.entity.ModelDimTable;
import com.qk.dm.datamodel.entity.QModelDimTable;
import com.qk.dm.datamodel.mapstruct.mapper.ModelDimTableColumnMapper;
import com.qk.dm.datamodel.mapstruct.mapper.ModelDimTableMapper;
import com.qk.dm.datamodel.params.dto.ModelDimTableColumnDTO;
import com.qk.dm.datamodel.params.dto.ModelDimTableDTO;
import com.qk.dm.datamodel.params.dto.ModelDimTableQueryDTO;
import com.qk.dm.datamodel.params.dto.ModelSqlDTO;
import com.qk.dm.datamodel.params.vo.ModelDimTableDetailVO;
import com.qk.dm.datamodel.params.vo.ModelDimTableVO;
import com.qk.dm.datamodel.repositories.ModelDimColumnRepository;
import com.qk.dm.datamodel.repositories.ModelDimRepository;
import com.qk.dm.datamodel.repositories.ModelDimTableRepository;
import com.qk.dm.datamodel.service.ModelDimTableColumnService;
import com.qk.dm.datamodel.service.ModelDimTableService;
import com.qk.dm.datamodel.service.ModelSqlService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
    private final ModelDimRepository modelDimRepository;
    private final ModelDimColumnRepository modelDimColumnRepository;

    public ModelDimTableServiceImpl(ModelDimTableRepository modelDimTableRepository,
                                    EntityManager entityManager,
                                    ModelSqlService modelSqlService,
                                    ModelDimTableColumnService modelDimTableColumnService, ModelDimRepository modelDimRepository, ModelDimColumnRepository modelDimColumnRepository){
        this.modelDimTableRepository = modelDimTableRepository;
        this.entityManager = entityManager;
        this.modelSqlService = modelSqlService;
        this.modelDimTableColumnService = modelDimTableColumnService;
        this.modelDimRepository = modelDimRepository;
        this.modelDimColumnRepository = modelDimColumnRepository;
    }
    @PostConstruct
    public void initFactory() {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(ModelDimTableDTO modelDimTableDTO) {
        ModelDimTable modelDimTable = ModelDimTableMapper.INSTANCE.of(modelDimTableDTO);
        ModelDimTable dimTable = modelDimTableRepository.save(modelDimTable);
        List<ModelDimTableColumnDTO> modelDimTableDTOColumnList = modelDimTableDTO.getColumnList();
        if(!modelDimTableDTOColumnList.isEmpty()){
            modelDimTableDTOColumnList.forEach(e->e.setDimTableId(dimTable.getId()));
            modelDimTableColumnService.insert(modelDimTableDTOColumnList);
            //组装建表SQL,添加到数据库中
            ModelSqlDTO modelSql = ModelSqlDTO.builder().sqlSentence(generateSql(dimTable.getDimName(), modelDimTableDTOColumnList))
                    .tableId(dimTable.getId()).type(ModelType.DIM_TABLE).build();
            modelSqlService.insert(modelSql);
        }

    }

    @Override
    public ModelDimTableDetailVO detail(Long id) {
        Optional<ModelDimTable> modelDimTable = modelDimTableRepository.findById(id);
        if(modelDimTable.isEmpty()){
            throw new BizException("当前查找的维度表id为"+id+"的数据不存在");
        }
        ModelDimTableDetailVO modelDimTableDetailVO = ModelDimTableMapper.INSTANCE.ofDetail(modelDimTable.get());
        modelDimTableDetailVO.setColumnList(modelDimTableColumnService.list(modelDimTableDetailVO.getId()));
       return modelDimTableDetailVO;
    }

    @Override
    public void updateDim(Long modelDimId, ModelDimTableDTO modelDimTableDTO) {
        Predicate predicate = qModelDimTable.modelDimId.eq(modelDimId);
        Optional<ModelDimTable> modelDimTable = modelDimTableRepository.findOne(predicate);
        if(modelDimTable.isEmpty()){
            //添加维度表
            insert(modelDimTableDTO);
        }else {
            updateDimTable(modelDimTableDTO,modelDimTable.get());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ModelDimTableDTO modelDimTableDTO) {
        if(Objects.isNull(modelDimTableDTO.getId())){
            throw new BizException("修改维度表id不能为空！！！");
        }
        Optional<ModelDimTable> modelDimTable = modelDimTableRepository.findById(modelDimTableDTO.getId());
        if(modelDimTable.isEmpty()){
            throw new BizException("当前要修改的维度表id为"+modelDimTableDTO.getId()+"的数据不存在");
         }
        updateDimTable(modelDimTableDTO,modelDimTable.get());

    }
    private void updateDimTable(ModelDimTableDTO modelDimTableDTO,ModelDimTable modelDimTable) {
        ModelDimTableMapper.INSTANCE.from(modelDimTableDTO, modelDimTable);
        modelDimTableRepository.saveAndFlush(modelDimTable);

        List<ModelDimTableColumnDTO> modelDimTableDTOColumnList = modelDimTableDTO.getColumnList();
        if (!modelDimTableDTOColumnList.isEmpty()) {
            modelDimTableDTOColumnList.forEach(e -> e.setDimTableId(modelDimTable.getId()));
            modelDimTableColumnService.update(modelDimTable.getId(), modelDimTableDTO.getColumnList());
            // 修改表SQL,添加到数据库中
            ModelSqlDTO modelSql = ModelSqlDTO.builder()
                            .sqlSentence(generateSql(modelDimTable.getDimName(), modelDimTableDTOColumnList))
                            .tableId(modelDimTable.getId())
                            .type(ModelType.DIM_TABLE)
                            .build();
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
        modelDimTableList = modelDimTableList.stream().peek(e -> {
            if (e.getStatus() == ModelStatus.PUBLISH) {
                throw new BizException(e.getDimName() + "只有当维度处于草稿或已线下状态才可删除");
            }
        }).collect(Collectors.toList());
        modelDimTableRepository.deleteAll(modelDimTableList);
        modelDimTableColumnService.delete(ids);
    }

    @Override
    public PageResultVO<ModelDimTableVO> list(ModelDimTableQueryDTO modelDimTableQueryDTO) {
        Map<String, Object> map;
        try {
            map = queryByParams(modelDimTableQueryDTO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("查询失败!!!");
        }
        List<ModelDimTable> list = (List<ModelDimTable>) map.get("list");
        List<ModelDimTableVO> voList = ModelDimTableMapper.INSTANCE.of(list);
        return new PageResultVO<>(
                (long) map.get("total"),
                modelDimTableQueryDTO.getPagination().getPage(),
                modelDimTableQueryDTO.getPagination().getSize(),
                voList);
    }

    /**
     * 根据维度id查找维度表
     * @param modelDimId
     */
    private ModelDim getModelTable(Long modelDimId){
        Optional<ModelDim> modelDimTable = modelDimRepository.findById(modelDimId);
        if(modelDimTable.isEmpty()){
            throw new BizException("当前查找的维度id为"+modelDimId+"的数据不存在");
        }
        return modelDimTable.get();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sync(String ids) {
        Iterable<Long> idSet = Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toList());
        List<ModelDimTable> dimTableList = modelDimTableRepository.findAllById(idSet);
        if(CollectionUtils.isEmpty(dimTableList)){return; }
        dimTableList.forEach(e->{
            //更新基本信息
            ModelDimTableMapper.INSTANCE.of(getModelTable(e.getModelDimId()),e);
            modelDimTableRepository.saveAndFlush(e);
            modelDimTableColumnService.deleteByDimTableId(e.getId());
            List<ModelDimTableColumnDTO> columnList = ModelDimTableColumnMapper.INSTANCE.trans(modelDimColumnRepository.findAllByDimId(e.getModelDimId()));
            if(!CollectionUtils.isEmpty(columnList)){
                columnList.forEach(column->{column.setDimTableId(e.getId());});
            }
            modelDimTableColumnService.insert(columnList);
        });

    }

    @Override
    public void fallLibrary(List<Long> idList) {
        //todo 逻辑待实现
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

    private Map<String, Object> queryByParams(ModelDimTableQueryDTO modelDimTableQueryDTO) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        checkCondition(booleanBuilder, modelDimTableQueryDTO);
        Map<String, Object> result = new HashMap<>();
        long count =
                jpaQueryFactory.select(qModelDimTable.count()).from(qModelDimTable).where(booleanBuilder).fetchOne();
        List<ModelDimTable> modelDimTableList = jpaQueryFactory
                .select(qModelDimTable)
                .from(qModelDimTable)
                .where(booleanBuilder)
                .orderBy(qModelDimTable.id.asc())
                .offset(
                        (long) (modelDimTableQueryDTO.getPagination().getPage() - 1)
                                * modelDimTableQueryDTO.getPagination().getSize())
                .limit(modelDimTableQueryDTO.getPagination().getSize())
                .fetch();
        result.put("list", modelDimTableList);
        result.put("total", count);
        return result;
    }

    public void checkCondition(BooleanBuilder booleanBuilder, ModelDimTableQueryDTO modelDimTableQueryDTO) {
        if (!StringUtils.isEmpty(modelDimTableQueryDTO.getDimName())) {
            booleanBuilder.and(qModelDimTable.dimName.contains(modelDimTableQueryDTO.getDimName()));
        }
        if(!StringUtils.isEmpty(modelDimTableQueryDTO.getThemeName())){
            booleanBuilder.and(qModelDimTable.themeName.contains(modelDimTableQueryDTO.getThemeName()));
        }
    }
}
