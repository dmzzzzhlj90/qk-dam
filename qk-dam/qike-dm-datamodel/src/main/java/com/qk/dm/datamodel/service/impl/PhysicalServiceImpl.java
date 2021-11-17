package com.qk.dm.datamodel.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.MapUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dam.model.constant.ModelStatus;
import com.qk.dam.sqlbuilder.SqlBuilderFactory;
import com.qk.dam.sqlbuilder.model.Column;
import com.qk.dam.sqlbuilder.model.Table;
import com.qk.dm.datamodel.entity.*;
import com.qk.dm.datamodel.mapstruct.mapper.ModelPhysicalColumnMapper;
import com.qk.dm.datamodel.mapstruct.mapper.ModelPhysicalRelationMapper;
import com.qk.dm.datamodel.mapstruct.mapper.ModelPhysicalTableMapper;
import com.qk.dm.datamodel.params.dto.*;
import com.qk.dm.datamodel.params.vo.*;
import com.qk.dm.datamodel.repositories.ModelPhysicalColumnRepository;
import com.qk.dm.datamodel.repositories.ModelPhysicalRelationRepository;
import com.qk.dm.datamodel.repositories.ModelPhysicalTableRepository;
import com.qk.dm.datamodel.repositories.ModelSqlRepository;
import com.qk.dm.datamodel.service.PhysicalService;
import com.qk.dm.datamodel.util.CheckUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zys
 * @date 2021/11/10 17:28
 * @since 1.0.0
 */
@Service
public class PhysicalServiceImpl implements PhysicalService {
  private final QModelPhysicalTable qModelPhysicalTable = QModelPhysicalTable.modelPhysicalTable;
  private final QModelPhysicalColumn qModelPhysicalColumn = QModelPhysicalColumn.modelPhysicalColumn;
  private final ModelPhysicalTableRepository modelPhysicalTableRepository;
  private final ModelPhysicalColumnRepository modelPhysicalColumnRepository;
  private final ModelPhysicalRelationRepository modelPhysicalRelationRepository;
  private final ModelSqlRepository modelSqlRepository;
  private JPAQueryFactory jpaQueryFactory;
  private final EntityManager entityManager;
  private SqlBuilderFactory sqlBuilderFactory;
  @PostConstruct
  public void initFactory() {
    jpaQueryFactory = new JPAQueryFactory(entityManager);
  }

  public PhysicalServiceImpl(
      ModelPhysicalTableRepository modelPhysicalTableRepository,
      ModelPhysicalColumnRepository modelPhysicalColumnRepository,
      ModelPhysicalRelationRepository modelPhysicalRelationRepository,
      ModelSqlRepository modelSqlRepository, EntityManager entityManager) {
    this.modelPhysicalTableRepository = modelPhysicalTableRepository;
    this.modelPhysicalColumnRepository = modelPhysicalColumnRepository;
    this.modelPhysicalRelationRepository = modelPhysicalRelationRepository;
    this.modelSqlRepository = modelSqlRepository;
    this.entityManager = entityManager;
  }

  /**
   * 新增关系建模
   * @param modelPhysicalDTO
   */
  @Override
  public void insert(ModelPhysicalDTO modelPhysicalDTO) {
    //根据表名判断表是否存在
    ModelPhysicalTableDTO modelPhysicalTableDTO = modelPhysicalDTO.getModelPhysicalTableDTO();
    ModelPhysicalTable modelPhysicalTable = ModelPhysicalTableMapper.INSTANCE.of(modelPhysicalTableDTO);
    BooleanExpression predicate = qModelPhysicalTable.databaseName.eq(modelPhysicalTable.getDatabaseName()).and(qModelPhysicalTable.tableName.eq(modelPhysicalTable.getTableName()));
    boolean exists = modelPhysicalTableRepository.exists(predicate);
    if (exists){
      throw new BizException(
          "当前要新增的关系建模名称为:"
              + modelPhysicalTable.getDatabaseName()
              + " 的数据，已存在！！！");
    }else{
      Boolean check = checkModelPhysical(modelPhysicalDTO);
      if (check){
        saveModelPhysical(modelPhysicalDTO,modelPhysicalTable);
      }
    }
  }

  /**
   * 新增关系建模
   * @param modelPhysicalDTO
   * @param modelPhysicalTable
   */
  private void saveModelPhysical(ModelPhysicalDTO modelPhysicalDTO,
      ModelPhysicalTable modelPhysicalTable) {
    //1存储基本信息
    ModelPhysicalTable modelPhysicalTable1 = modelPhysicalTableRepository.save(modelPhysicalTable);
    //2存储字段信息
    List<ModelPhysicalColumn> columnList = ModelPhysicalColumnMapper.INSTANCE.use(modelPhysicalDTO.getModelColumnDtoList());
    List<ModelPhysicalColumn> modelPhysicalColumnList = columnList.stream()
        .map(modelPhysicalColumn -> { modelPhysicalColumn.setTableId(
            modelPhysicalTable1.getId());
            return modelPhysicalColumn;
        }).collect(Collectors.toList());
    modelPhysicalColumnRepository.saveAll(modelPhysicalColumnList);
    //3存关系数据
    if (modelPhysicalDTO.getModelRelationDtoList()!=null){
      List<ModelPhysicalRelation> relationLists = ModelPhysicalRelationMapper.INSTANCE.use(modelPhysicalDTO.getModelRelationDtoList());
      List<ModelPhysicalRelation> relationList = relationLists.stream()
          .map(modelPhysicalRelation -> {
            modelPhysicalRelation.setTableId(modelPhysicalTable1.getId());
            return modelPhysicalRelation;
          }).collect(Collectors.toList());
      modelPhysicalRelationRepository.saveAll(relationList);
    }
    //生成sql并存入数据库
    ModelSql modelSql = new ModelSql();
    Table table = getTable(modelPhysicalTable,modelPhysicalColumnList);
    String Sql = SqlBuilderFactory.creatTableSQL(table);
    if (StringUtils.isNotBlank(Sql)){
      //赋值基础数据id
      modelSql.setTableId(modelPhysicalTable1.getId());
      //1,逻辑表2物理表 3 维度表 4 汇总表
      modelSql.setType(1);
      //建表sql
      modelSql.setSqlSentence(Sql);
      modelSqlRepository.save(modelSql);
    }else {
      throw  new BizException("生成sql为空");
    }
  }

  private Table getTable(ModelPhysicalTable modelPhysicalTable, List<ModelPhysicalColumn> modelPhysicalColumnList) {
    Table table = new Table();
    table.setName(modelPhysicalTable.getTableName());
    List<Column> columnList = modelPhysicalColumnList.stream()
        .map(modelPhysicalColumn -> {
          Column column = new Column();
          //字段名称
          column.setName(modelPhysicalColumn.getColumnName());
          //字段类型类型
          column.setDataType(modelPhysicalColumn.getColumnType());
          //是否是主键
          column.setPrimaryKey(
              transFormation(modelPhysicalColumn.getItsPrimaryKey()));
          //是否自增(目前跟着主键走，如果是主键就自增)
          column.setAutoIncrement(
              transFormation(modelPhysicalColumn.getItsPrimaryKey()));
          //是否不为空
          column.setEmpty(transFormation(modelPhysicalColumn.getItsNull()));
          //字段注释
          column.setComments(modelPhysicalColumn.getDescription());
          return column;
        }).collect(Collectors.toList());
    table.setColumns(columnList);
    return table;
  }

  /**
   * 转换String类型数据到boolean
   * @param itsPrimaryKey
   * @return
   */
  private Boolean transFormation(String itsPrimaryKey) {
    Boolean booleans=false;
    if (StringUtils.isNotBlank(itsPrimaryKey) && itsPrimaryKey.equals("1")){
      booleans=true;
    }
    return booleans;
  }

  /**
   * 字段是否为空，字段是否重复，关系是否存在，关系是否重复
   * @param modelPhysicalDTO
   * @return
   */
  private Boolean checkModelPhysical(ModelPhysicalDTO modelPhysicalDTO) {
    Boolean check = false;
    //判断新加字段是否存在重复
    List<ModelPhysicalColumnDTO> modelColumnDtoList = modelPhysicalDTO.getModelColumnDtoList();
    if (CollectionUtils.isNotEmpty(modelColumnDtoList)) {
      //校验新建模型字段是否重复
      List<String> columnList =modelColumnDtoList.stream().
          collect(Collectors.groupingBy(modelPhysicalColumnDTO->modelPhysicalColumnDTO.getColumnName(),Collectors.counting()))
          .entrySet().stream()
          .filter(entry->entry.getValue()>1)
          .map(entry->entry.getKey())
          .collect(Collectors.toList());
      if (columnList.size()<=0) {
        //校验关系是否重复
        List<ModelPhysicalRelationDTO> modelRelationDtoList = modelPhysicalDTO.getModelRelationDtoList();
        if (CollectionUtils.isNotEmpty(modelRelationDtoList)) {
          List<String> relationList =modelColumnDtoList.stream().
              collect(Collectors.groupingBy(modelPhysicalRelationDTO->modelPhysicalRelationDTO.getColumnName(),Collectors.counting()))
              .entrySet().stream()
              .filter(entry->entry.getValue()>1)
              .map(entry->entry.getKey())
              .collect(Collectors.toList());
          if (relationList.size()<=0) {
            check=true;
          } else {
            throw new BizException("建模关系不可重复");
          }
        } else {
          check=true;
        }
      } else {
        throw new BizException("建模表字段不可重复");
      }
    } else {
      throw new BizException("建模表字段为空，请添加字段");
    }
    return check;
  }


  /**
   * 删除建模（批量下线）
   * @param ids
   */
  @Override
  public void delete(List<Long> ids) {
   ids.forEach(
       id->{
        //根据id判断当前需要删除的数据是否存在
         Optional<ModelPhysicalTable> modelPhysicalTable = modelPhysicalTableRepository.findById(id);
         if (modelPhysicalTable.isEmpty()){
           throw new BizException("当前需要下线的，id为"+id+"的数据不存在");
         }else{
           ModelPhysicalTable modelPhysicalTable1 = modelPhysicalTable.get();
           modelPhysicalTable1.setStatus(ModelStatus.OFFLINE);
           modelPhysicalTableRepository.save(modelPhysicalTable1);
         }
       }
   );
  }

  /**
   * 修改建模
   * @param modelPhysicalDTO
   */
  @Override
  public void update(ModelPhysicalDTO modelPhysicalDTO) {
    //根据表名判断表是否存在
    ModelPhysicalTableDTO modelPhysicalTableDTO = modelPhysicalDTO.getModelPhysicalTableDTO();
    ModelPhysicalTable modelPhysicalTable = ModelPhysicalTableMapper.INSTANCE.of(modelPhysicalTableDTO);
    BooleanExpression predicate = qModelPhysicalTable.id.eq(modelPhysicalTable.getId());
    boolean exists = modelPhysicalTableRepository.exists(predicate);
    if (exists){
      Boolean check = checkModelPhysical(modelPhysicalDTO);
      if (check){
        updateModelPhysical(modelPhysicalDTO,modelPhysicalTable);
      }
    }else{
      throw new BizException(
          "当前要修改的关系建模名称为:"
              + modelPhysicalTable.getDatabaseName()
              + " 的数据，不已存在！！！");
    }
  }

  /**
   * 分页查询建模信息
   * @return
   * @param queryModelPhysicalDTO
   */
  @Override
  public PageResultVO<ModelPhysicalVO> query(QueryModelPhysicalDTO queryModelPhysicalDTO) {
    Map<String, Object> map;
    List<ModelPhysicalVO> modelPhysicalVOList = new ArrayList<>();
    try {
      map = queryByParams(queryModelPhysicalDTO);
    } catch (Exception e) {
      e.printStackTrace();
      throw new BizException("查询失败!!!");
    }
    if (MapUtils.isNotEmpty(map)){
      List<ModelPhysicalTable> list = (List<ModelPhysicalTable>) map.get("list");
      if (CollectionUtils.isNotEmpty(list)){
        List<ModelPhysicalTableVO> voList = ModelPhysicalTableMapper.INSTANCE.of(list);
        modelPhysicalVOList = getModelPhysicalVO(voList);
      }
    }
    return new PageResultVO<>(
        (long) map.get("total"),
        queryModelPhysicalDTO.getPagination().getPage(),
        queryModelPhysicalDTO.getPagination().getSize(),
        modelPhysicalVOList);

  }

  /**
   * 根据id查询基础信息
   * @param id
   * @return
   */
  @Override
  public ModelPhysicalTableVO getModelPhysical(Long id) {
    ModelPhysicalTableVO modelPhysicalTableVO = new ModelPhysicalTableVO();
    ModelPhysicalTable modelPhysicalTable = modelPhysicalTableRepository.findById(id).orElse(null);
    if (modelPhysicalTable!=null){
      modelPhysicalTableVO= ModelPhysicalTableMapper.INSTANCE.of(modelPhysicalTable);
    }else{
      throw  new BizException("查询详情为空");
    }
    return modelPhysicalTableVO;
  }

  /**
   * 根据层级和主题查询数据相关统计信息
   * @param queryModelPhysicalDTO
   * @return
   */
  @Override
  public CensusDataVO getCensusData(QueryModelPhysicalDTO queryModelPhysicalDTO) {
    CensusDataVO censusDataVO = new CensusDataVO();
    Map<String, Object> map;
    List<ModelPhysicalVO> modelPhysicalVOList = new ArrayList<>();
    try {
      map = queryCensusByParams(queryModelPhysicalDTO);
    } catch (Exception e) {
      e.printStackTrace();
      throw new BizException("查询失败!!!");
    }
    if (MapUtils.isNotEmpty(map)){
      List<ModelPhysicalTable> list = (List<ModelPhysicalTable>) map.get("list");
      if (list.size()>0){
        censusDataVO = getCensusDataVO(list);
      }else{
        return censusDataVO;
      }
    }
    return censusDataVO;
  }

  /**
   * 数据类型
   * @return
   */
  @Override
  public List<Map<String,String>> getDataTypes() {
    List<Map<String, String>> list =CheckUtil.getDataTypes();
    return list;
  }

  /**
   * 预览sql
   * @param tableId
   * @return
   */
  @Override
  public String getSql(Long tableId) {
    String sql = null;
    ModelSql modelSql = modelSqlRepository.findByTableId(tableId);
    if (modelSql != null){
      sql=modelSql.getSqlSentence();
    }else {
      throw  new BizException("当前预览的Sql不存在");
    }
    return sql;
  }

  /**
   * 根据基础信息查询统计数据
   * @param list
   * @return
   */
  private CensusDataVO getCensusDataVO(List<ModelPhysicalTable> list) {
    //定义用户返回数据VO
    CensusDataVO censusDataVO = new CensusDataVO();
    //定义覆盖率
    String coverage="0%";
    //定义表发布数量
    int pushnums=0;
    //定义发布字段数量
    int fieldnums=0;
    //赋值表总数
    censusDataVO.setTotlenums(list.size());
    //判断发布表是否为空
    //发布表的id为idList
    List<Long> idList = new ArrayList<>();
    //所有表的id存在totleList
    List<Long> totleList = new ArrayList<>();
    idList = list.stream().filter(modelPhysicalTable -> modelPhysicalTable.getStatus().equals(ModelStatus.PUBLISH))
        .map(ModelPhysicalTable::getId).collect(Collectors.toList());
    totleList = list.stream().map(ModelPhysicalTable::getId).collect(Collectors.toList());
    deleteData(totleList,idList,coverage,pushnums,fieldnums,censusDataVO);
    return censusDataVO;
  }

  /**
   * 根据发布表id获取覆盖率、发布字段、发布表数量
   * @param list
   * @param idList
   * @param coverage
   * @param pushnums
   * @param fieldnums
   * @param censusDataVO
   */
  private void deleteData(List<Long> list, List<Long> idList, String coverage,
      int pushnums, int fieldnums, CensusDataVO censusDataVO) {
    //获取覆盖率
    coverage = getCoverage(list);
    if (CollectionUtils.isNotEmpty(idList)){
      //发布字段数量、表发布数量
      pushnums=idList.size();
      //获取已发布字段数量
      fieldnums=getFieldNums(idList);
    }
    censusDataVO.setCoverage(coverage);
    censusDataVO.setFieldnums(fieldnums);
    censusDataVO.setPushnums(pushnums);
  }

  /**
   * 根据基础id获取字段发布数量
   * @param idList
   * @return
   */
  private int getFieldNums(List<Long> idList) {
    int fieldNums = 0;
    //根据基本信息id查询字段数据
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    checkFieldNums(booleanBuilder, idList);
    List<ModelPhysicalColumn> modelPhysicalColumnList =
        jpaQueryFactory
            .select(qModelPhysicalColumn).from(qModelPhysicalColumn)
            .where(booleanBuilder).orderBy(qModelPhysicalColumn.id.asc()).fetch();
    if (CollectionUtils.isNotEmpty(modelPhysicalColumnList)) {
      fieldNums=modelPhysicalColumnList.size();
    }
    return fieldNums;
  }

  /**
   * 根据发布表id获取发布字段
   * @param booleanBuilder
   * @param idList
   */
  private void checkFieldNums(BooleanBuilder booleanBuilder, List<Long> idList) {
    booleanBuilder.and(qModelPhysicalColumn.tableId.in(idList));
  }

  /**
   * 获取标准覆盖率
   * @param list
   * @return
   */
  private String getCoverage(List<Long> list) {
    String coverage ="0%";
    //根据基本信息id查询字段数据
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    checkCoverage(booleanBuilder, list);
    List<ModelPhysicalColumn> modelPhysicalColumnList =
        jpaQueryFactory
        .select(qModelPhysicalColumn).from(qModelPhysicalColumn)
        .where(booleanBuilder).orderBy(qModelPhysicalColumn.id.asc()).fetch();
    if (CollectionUtils.isNotEmpty(modelPhysicalColumnList)){
        //获取含有标准的字段数量
      List<ModelPhysicalColumn> columnList = modelPhysicalColumnList.stream()
          .filter(item -> item.getStandardsId() != null)
          .collect(Collectors.toList());
      if (CollectionUtils.isNotEmpty(columnList)){
        coverage=CheckUtil.getCoverage(columnList.size(),modelPhysicalColumnList.size());
      }
    }
    return coverage;
  }

  private void checkCoverage(BooleanBuilder booleanBuilder, List<Long> list) {
     booleanBuilder.and(qModelPhysicalColumn.tableId.in(list));
  }

  private Map<String,Object> queryCensusByParams(QueryModelPhysicalDTO queryModelPhysicalDTO) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    checkCondition(booleanBuilder, queryModelPhysicalDTO);
    Map<String, Object> result = new HashMap<>();
    List<ModelPhysicalTable> modelPhysicalTableList =
        jpaQueryFactory
            .select(qModelPhysicalTable)
            .from(qModelPhysicalTable)
            .where(booleanBuilder)
            .orderBy(qModelPhysicalTable.id.asc())
            .fetch();
    result.put("list", modelPhysicalTableList);
    return result;
  }

  /**
   * 拼装建模信息
   * @param voList
   * @return
   */
  private List<ModelPhysicalVO> getModelPhysicalVO(List<ModelPhysicalTableVO> voList) {
    List<ModelPhysicalVO> modelPhysicalVOList = new ArrayList<>();
    voList.forEach(
        modelPhysicalTableVO -> {
        ModelPhysicalVO modelPhysicalVO = new ModelPhysicalVO();
        modelPhysicalVO.setModelPhysicalTableVO(modelPhysicalTableVO);
        //根据基础表id获取字段信息
          List<ModelPhysicalColumnVO> modelPhysicalColumnVOList = getModelPhysicalColumn(modelPhysicalTableVO.getId());
          if (CollectionUtils.isNotEmpty(modelPhysicalColumnVOList)){
            modelPhysicalVO.setModelPhysicalColumnVOList(modelPhysicalColumnVOList);
          }
        //根据基础表id获取关系信息
          List<ModelPhysicalRelationVO> modelPhysicalRelationVOList = getModelPhysicalRelation(modelPhysicalTableVO.getId());
          if (CollectionUtils.isNotEmpty(modelPhysicalRelationVOList)){
            modelPhysicalVO.setModelPhysicalRelationVOList(modelPhysicalRelationVOList);
          }
          modelPhysicalVOList.add(modelPhysicalVO);
        }
    );
    return modelPhysicalVOList;
  }

  /**
   * 根据基础信息id查询关系信息
   * @param id
   * @return
   */
  private List<ModelPhysicalRelationVO> getModelPhysicalRelation(Long id) {
    List<ModelPhysicalRelationVO> modelPhysicalRelationVOList = new ArrayList<>();
    List<ModelPhysicalRelation> allByTableIdList = modelPhysicalRelationRepository.findAllByTableId(id);
    if (CollectionUtils.isNotEmpty(allByTableIdList)){
      modelPhysicalRelationVOList = ModelPhysicalRelationMapper.INSTANCE.of(allByTableIdList);
    }
    return modelPhysicalRelationVOList;
  }

  /**
   * 根据基础信息id查询字段信息
   * @param id
   * @return
   */
  private List<ModelPhysicalColumnVO> getModelPhysicalColumn(Long id) {
    List<ModelPhysicalColumnVO> modelPhysicalColumnVOList = new ArrayList<>();
    List<ModelPhysicalColumn> allByTableIdList = modelPhysicalColumnRepository.findAllByTableId(id);
    if (CollectionUtils.isNotEmpty(allByTableIdList)){
      modelPhysicalColumnVOList = ModelPhysicalColumnMapper.INSTANCE.of(allByTableIdList);
    }
    return modelPhysicalColumnVOList;
  }

  /**
   * 根据条件查询建模基本信息
   * @param queryModelPhysicalDTO
   * @return
   */
  private Map<String,Object> queryByParams(QueryModelPhysicalDTO queryModelPhysicalDTO) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    checkCondition(booleanBuilder, queryModelPhysicalDTO);
    Map<String, Object> result = new HashMap<>();
    long count =jpaQueryFactory.select(qModelPhysicalTable.count()).from(qModelPhysicalTable).where(booleanBuilder).fetchOne();
    List<ModelPhysicalTable> modelPhysicalTableList =
        jpaQueryFactory
            .select(qModelPhysicalTable)
            .from(qModelPhysicalTable)
            .where(booleanBuilder)
            .orderBy(qModelPhysicalTable.id.asc())
            .offset(
                (long) (queryModelPhysicalDTO.getPagination().getPage() - 1)
                    * queryModelPhysicalDTO.getPagination().getSize())
            .limit(queryModelPhysicalDTO.getPagination().getSize())
            .fetch();
    result.put("list", modelPhysicalTableList);
    result.put("total", count);
    return result;
  }

  private void checkCondition(BooleanBuilder booleanBuilder, QueryModelPhysicalDTO queryModelPhysicalDTO) {
    if (!Objects.isNull(queryModelPhysicalDTO.getModelID())) {
      booleanBuilder.and(qModelPhysicalTable.modelId.eq(queryModelPhysicalDTO.getModelID()));
    }
    if (!Objects.isNull(queryModelPhysicalDTO.getThemeId())){
      booleanBuilder.and(qModelPhysicalTable.themeId.eq(queryModelPhysicalDTO.getThemeId()));
    }
  }

  /**
   * 修改建模数据
   * @param modelPhysicalDTO
   * @param modelPhysicalTable
   */
  private void updateModelPhysical(ModelPhysicalDTO modelPhysicalDTO,ModelPhysicalTable modelPhysicalTable) {
    //1修改基础信息
    modelPhysicalTableRepository.saveAndFlush(modelPhysicalTable);
    //2修改字段配置
    List<ModelPhysicalColumn> physicalColumnList = modelPhysicalColumnRepository.findAllByTableId(modelPhysicalTable.getId());
    if (CollectionUtils.isNotEmpty(physicalColumnList)){
      physicalColumnList.forEach(
          modelPhysicalColumn -> {
            modelPhysicalColumn.setDelFlag(1);
          }
      );
      modelPhysicalColumnRepository.saveAllAndFlush(physicalColumnList);
    }
    List<ModelPhysicalColumn> columnList = ModelPhysicalColumnMapper.INSTANCE.use(modelPhysicalDTO.getModelColumnDtoList());
    modelPhysicalColumnRepository.saveAll(columnList);
    //3修改模型关系
    if (CollectionUtils.isNotEmpty(modelPhysicalDTO.getModelRelationDtoList())){
      List<ModelPhysicalRelation> modelPhysicalRelationList = modelPhysicalRelationRepository.findAllByTableId(modelPhysicalTable.getId());
      if (CollectionUtils.isNotEmpty(modelPhysicalRelationList)){
        modelPhysicalRelationList.forEach(
            modelPhysicalRelation -> {
              modelPhysicalRelation.setDelFlag(1);
            }
        );
        modelPhysicalRelationRepository.saveAllAndFlush(modelPhysicalRelationList);
      }
      List<ModelPhysicalRelation> relationLists = ModelPhysicalRelationMapper.INSTANCE.use(modelPhysicalDTO.getModelRelationDtoList());
      modelPhysicalRelationRepository.saveAll(relationLists);
    }
  }
}