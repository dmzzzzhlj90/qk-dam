package com.qk.dm.datamodel.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.MapUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.entity.DataStandardInfoVO;
import com.qk.dam.entity.DataStandardTreeVO;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dam.metedata.entity.MtdAttributes;
import com.qk.dam.metedata.entity.MtdTables;
import com.qk.dam.model.constant.ModelStatus;
import com.qk.dam.model.constant.ModelType;
import com.qk.dam.model.enums.DataTypeEnum;
import com.qk.dam.sqlbuilder.SqlBuilderFactory;
import com.qk.dam.sqlbuilder.model.Column;
import com.qk.dam.sqlbuilder.model.Table;
import com.qk.dm.datamodel.entity.*;
import com.qk.dm.datamodel.mapstruct.mapper.ModelDirMapper;
import com.qk.dm.datamodel.mapstruct.mapper.ModelPhysicalColumnMapper;
import com.qk.dm.datamodel.mapstruct.mapper.ModelPhysicalRelationMapper;
import com.qk.dm.datamodel.mapstruct.mapper.ModelPhysicalTableMapper;
import com.qk.dm.datamodel.params.dto.*;
import com.qk.dm.datamodel.params.vo.*;
import com.qk.dm.datamodel.repositories.ModelPhysicalColumnRepository;
import com.qk.dm.datamodel.repositories.ModelPhysicalRelationRepository;
import com.qk.dm.datamodel.repositories.ModelPhysicalTableRepository;
import com.qk.dm.datamodel.repositories.ModelSqlRepository;
import com.qk.dm.datamodel.service.ModelSqlService;
import com.qk.dm.datamodel.service.PhysicalService;
import com.qk.dm.datamodel.util.CheckUtil;
import com.qk.dm.service.DataBaseService;
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
  private final ModelSqlService modelSqlService;
  private final ModelPhysicalTableRepository modelPhysicalTableRepository;
  private final ModelPhysicalColumnRepository modelPhysicalColumnRepository;
  private final ModelPhysicalRelationRepository modelPhysicalRelationRepository;
  private final ModelSqlRepository modelSqlRepository;
  private JPAQueryFactory jpaQueryFactory;
  private final EntityManager entityManager;
  private final DataBaseService dataBaseService;
  @PostConstruct
  public void initFactory() {
    jpaQueryFactory = new JPAQueryFactory(entityManager);
  }

  public PhysicalServiceImpl(ModelSqlService modelSqlService,
      ModelPhysicalTableRepository modelPhysicalTableRepository,
      ModelPhysicalColumnRepository modelPhysicalColumnRepository,
      ModelPhysicalRelationRepository modelPhysicalRelationRepository,
      ModelSqlRepository modelSqlRepository, EntityManager entityManager,
      DataBaseService dataBaseService) {
    this.modelSqlService = modelSqlService;
    this.modelPhysicalTableRepository = modelPhysicalTableRepository;
    this.modelPhysicalColumnRepository = modelPhysicalColumnRepository;
    this.modelPhysicalRelationRepository = modelPhysicalRelationRepository;
    this.modelSqlRepository = modelSqlRepository;
    this.entityManager = entityManager;
    this.dataBaseService = dataBaseService;
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
    BooleanExpression predicate = qModelPhysicalTable.dataBaseName.eq(modelPhysicalTable.getDataBaseName()).and(qModelPhysicalTable.tableName.eq(modelPhysicalTable.getTableName()));
    boolean exists = modelPhysicalTableRepository.exists(predicate);
    if (exists){
      throw new BizException(
          "当前要新增的关系建模名称为:"
              + modelPhysicalTable.getDataBaseName()
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
    //状态判断是否为发布操作,判断元数据中是否存在该表数据，如果不是则不用管
    if (modelPhysicalTable.getStatus()==ModelStatus.PUBLISH){
      int tables = getTables(modelPhysicalTable.getDataConnection(),
          modelPhysicalTable.getDataSourceName(),
          modelPhysicalTable.getDataBaseName(),
          modelPhysicalTable.getTableName());
      dealExistData(tables,modelPhysicalDTO,modelPhysicalTable);
    }else{
      dataModelPhysical(modelPhysicalDTO,modelPhysicalTable);
    }
  }

  /**
   * 处理元数据表查询结果
   * @param tables
   * @param modelPhysicalDTO
   * @param modelPhysicalTable
   */
  private void dealExistData(int tables, ModelPhysicalDTO modelPhysicalDTO,
      ModelPhysicalTable modelPhysicalTable) {
    switch (tables){
      case ModelStatus.EXIST_DATA:
        throw new BizException("发布失败,该表已经存在并且存在数据");
      case ModelStatus.EXIST_NO_DATA:
        //生成sql并执行
        String sqlv1 = builderSql(modelPhysicalDTO,modelPhysicalTable);
        //todo(需要完善)
        //判断sql执行成功，将数据保存
        dataModelPhysical(modelPhysicalDTO, modelPhysicalTable);
        break;
      case ModelStatus.NO_EXIST:
        //生成sql并执行
        String sqlv0 = builderSql(modelPhysicalDTO,modelPhysicalTable);
        //todo(需要完善)
        //判断sql执行成功，将数据保存
        dataModelPhysical(modelPhysicalDTO, modelPhysicalTable);
        break;
      default:
        throw new BizException("元数据表查询有误请联系相关人员");
    }
  }


  /**
   * 构建sql
   * @param modelPhysicalDTO
   * @param modelPhysicalTable
   * @return
   */
  private String builderSql(ModelPhysicalDTO modelPhysicalDTO, ModelPhysicalTable modelPhysicalTable) {
    List<ModelPhysicalColumn> columnList = ModelPhysicalColumnMapper.INSTANCE.use(modelPhysicalDTO.getModelColumnDtoList());
    Table table = getTable(modelPhysicalTable,columnList);
    return SqlBuilderFactory.creatTableSQL(table);
  }

  /**
   * 修改发布
   * @param modelPhysicalDTO
   * @param modelPhysicalTable
   * @return
   */
  private String dataModelPhysical(ModelPhysicalDTO modelPhysicalDTO, ModelPhysicalTable modelPhysicalTable) {
    String sqls = null;
    //1存储基本信息
    //todo 添加创建人和负责人
    ModelPhysicalTable modelPhysicalTable1 = modelPhysicalTableRepository.save(modelPhysicalTable);
    //2存储字段信息
    List<ModelPhysicalColumn> physicalColumnList = modelPhysicalColumnRepository.findAllByTableId(modelPhysicalTable.getId());
    if (CollectionUtils.isNotEmpty(physicalColumnList)){
      modelPhysicalColumnRepository.deleteAllInBatch(physicalColumnList);
    }
    List<ModelPhysicalColumn> columnList = ModelPhysicalColumnMapper.INSTANCE.use(modelPhysicalDTO.getModelColumnDtoList());
    List<ModelPhysicalColumn> modelPhysicalColumnList = columnList.stream()
        .map(modelPhysicalColumn -> { modelPhysicalColumn.setTableId(
            modelPhysicalTable1.getId());
          return modelPhysicalColumn;
        }).collect(Collectors.toList());
    modelPhysicalColumnRepository.saveAll(modelPhysicalColumnList);
    //3存关系数据
    List<ModelPhysicalRelation> modelPhysicalRelationList = modelPhysicalRelationRepository.findAllByTableId(modelPhysicalTable.getId());
    if (CollectionUtils.isNotEmpty(modelPhysicalRelationList)){
      modelPhysicalRelationRepository.deleteAllInBatch(modelPhysicalRelationList);
    }
    if (modelPhysicalDTO.getModelRelationDtoList()!=null){
      List<ModelPhysicalRelation> relationLists = ModelPhysicalRelationMapper.INSTANCE.use(modelPhysicalDTO.getModelRelationDtoList());
      List<ModelPhysicalRelation> relationList = relationLists.stream()
          .map(modelPhysicalRelation -> {
            modelPhysicalRelation.setTableId(modelPhysicalTable1.getId());
            return modelPhysicalRelation;
          }).collect(Collectors.toList());
      modelPhysicalRelationRepository.saveAll(relationList);
    }
    //4生成sql并存入数据库
    ModelSql modelSql = modelSqlRepository.findByTableIdAndType(modelPhysicalTable.getId(), ModelType.PHYSICAL_TABLE);
    if (Objects.nonNull(modelSql)){
      modelSqlRepository.delete(modelSql);
    }
    sqls = createSql(modelPhysicalTable, modelPhysicalColumnList,modelPhysicalTable1.getId());
    return sqls;
  }

  /**
   *根据基础表和字段表创建建表sql
   * @param modelPhysicalTable
   * @param modelPhysicalColumnList
   * @param id
   */
  private String createSql(ModelPhysicalTable modelPhysicalTable,
      List<ModelPhysicalColumn> modelPhysicalColumnList, Long id) {

    ModelSql modelSql = new ModelSql();
    Table table = getTable(modelPhysicalTable,modelPhysicalColumnList);
    String sql = SqlBuilderFactory.creatTableSQL(table);
    if (StringUtils.isNotBlank(sql)){
      //赋值基础数据id
      modelSql.setTableId(id);
      //1,逻辑表2物理表 3 维度表 4 汇总表
      modelSql.setType(ModelType.PHYSICAL_TABLE);
      //建表sql
      modelSql.setSqlSentence(sql);
      modelSqlRepository.save(modelSql);
    }else {
      throw new BizException("生成sql为空");
    }
    return sql;
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
          column.setEmpty(
              transFormation(modelPhysicalColumn.getItsNull()));
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
    return StringUtils.isNotBlank(itsPrimaryKey) && itsPrimaryKey.equals(ModelStatus.CHECK);

  }

  /**
   * 字段是否为空，字段是否重复，关系是否存在，关系是否重复
   * @param modelPhysicalDTO
   * @return
   */
  private Boolean checkModelPhysical(ModelPhysicalDTO modelPhysicalDTO) {
    //判断新加字段是否存在重复
    List<ModelPhysicalColumnDTO> modelColumnDtoList = modelPhysicalDTO.getModelColumnDtoList();
    if (CollectionUtils.isEmpty(modelColumnDtoList)){
      throw new BizException("建模表字段为空，请添加字段");
    }
    //校验新建模型字段是否重复
    List<String> columnList =modelColumnDtoList.stream().
        collect(Collectors.groupingBy(ModelPhysicalColumnDTO::getColumnName,Collectors.counting()))
        .entrySet().stream()
        .filter(entry->entry.getValue()>1)
        .map(Map.Entry::getKey)
        .collect(Collectors.toList());
    if (columnList.size()>0){
      throw new BizException("建模表字段不可重复");
    }
    List<ModelPhysicalRelationDTO> modelRelationDtoList = modelPhysicalDTO.getModelRelationDtoList();
    if (CollectionUtils.isNotEmpty(modelRelationDtoList)) {
      List<String> relationList =modelRelationDtoList.stream().
          collect(Collectors.groupingBy(ModelPhysicalRelationDTO::getColumnName,Collectors.counting()))
          .entrySet().stream()
          .filter(entry->entry.getValue()>1)
          .map(Map.Entry::getKey)
          .collect(Collectors.toList());
      if (relationList.size()>0) {
        throw new BizException("建模关系不可重复");
      }
      modelRelationDtoList.stream().peek(modelPhysicalRelationDTO -> {
        if (modelPhysicalRelationDTO.getChildTableName().equals(modelPhysicalRelationDTO.getFatherTableName())){
          throw new BizException("建模关系子表和父表不能重复");
        }
      }).collect(Collectors.toList());
    }
    return true;
  }


  /**
   * 删除建模（批量下线）
   * @param ids
   */
  @Override
  public void delete(List<Long> ids) {
    List<ModelPhysicalTable> modelPhysicalTableList = new ArrayList<>();
   ids.forEach(
       id->{
        //根据id判断当前需要删除的数据是否存在
         Optional<ModelPhysicalTable> modelPhysicalTable = modelPhysicalTableRepository.findById(id);
         if (modelPhysicalTable.isEmpty()){
           throw new BizException("当前需要下线的，id为"+id+"的数据不存在");
         }
           ModelPhysicalTable modelPhysicalTable1 = modelPhysicalTable.get();
         if (!modelPhysicalTable1.getStatus().equals(ModelStatus.PUBLISH)){
          throw  new BizException("表名称为:"+modelPhysicalTable1.getTableName()+"不是发布状态，不能进行下线操作");
         }
           modelPhysicalTable1.setStatus(ModelStatus.OFFLINE);
          //todo 添加修改人
           modelPhysicalTableList.add(modelPhysicalTable1);
       }
   );
    modelPhysicalTableRepository.saveAllAndFlush(modelPhysicalTableList);
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
      //todo 添加修改人
      if (check){
        updateModelPhysical(modelPhysicalDTO,modelPhysicalTable);
      }
    }else{
      throw new BizException(
          "当前要修改的关系建模名称为:"
              + modelPhysicalTable.getDataBaseName()
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
      throw  new BizException("id为"+id+"的基础信息不存在");
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
    return CheckUtil.getDataTypes();
  }

  /**
   * 预览sql
   * @param tableId
   * @return
   */
  @Override
  public String getSql(Long tableId) {
    String sql = null;
    ModelSqlVO detail = modelSqlService.detail(ModelType.PHYSICAL_TABLE, tableId);
    if (detail!=null){
      sql=detail.getSqlSentence();
    }
    return sql;
  }


  /**
   * 手动同步
   * @param physicalIds
   */
  @Override
  public void synchronization(List<Long> physicalIds) {
    Map<Long,ModelPhysicalTable> map = new HashMap<>();
      checkSynchroniZtion(physicalIds,map);
      physicalIds.forEach(id->{
        //1根据基础表id查询基础信息，判断元数据中是否存在该表信息
        ModelPhysicalTable modelPhysicalTable = map.get(id);
        //2如果存在就不用同步如果不存在就查询创建sql调用sdk发布建表任务
        if (modelPhysicalTable==null){
          throw new BizException("当前需要同步的表id为"+id+"不存在");
        }
        int tables = getTables(modelPhysicalTable.getDataConnection(),
            modelPhysicalTable.getDataSourceName(), modelPhysicalTable.getDataBaseName(),
            modelPhysicalTable.getTableName());
        dealSynzData(tables,modelPhysicalTable,id);
      });
  }

  private void checkSynchroniZtion(List<Long> physicalIds,Map<Long,ModelPhysicalTable> map) {
    List<ModelPhysicalTable> physicalTableList = modelPhysicalTableRepository.findAllById(physicalIds);
    physicalTableList.forEach(modelPhysicalTable -> {
      if (!modelPhysicalTable.getStatus().equals(ModelStatus.PUBLISH)){
        throw new BizException("已发布的数据才能手动同步");
      }
      int tables = getTables(modelPhysicalTable.getDataConnection(),
          modelPhysicalTable.getDataSourceName(), modelPhysicalTable.getDataBaseName(),
          modelPhysicalTable.getTableName());
      if (tables==ModelStatus.EXIST_DATA){
        throw new BizException(modelPhysicalTable.getTableName()+"库中存在且有数据");
      }
    });
    Map<Long,ModelPhysicalTable> tableMap = physicalTableList.stream().collect(
        Collectors.toMap(ModelPhysicalTable::getId,
            modelPhysicalTable -> modelPhysicalTable));
    map.putAll(tableMap);
  }

  /**
   *处理手动同步
   * @param tables
   * @param modelPhysical
   * @param id
   */
  private void dealSynzData(int tables, ModelPhysicalTable modelPhysical, Long id) {
    switch (tables){
      case ModelStatus.EXIST_DATA:
        buildSynzData(modelPhysical);
        break;
      case ModelStatus.EXIST_NO_DATA:
        buildSynzData(modelPhysical);
        break;
      case ModelStatus.NO_EXIST:
        ModelSql sql = modelSqlRepository.findByTableId(id);
        //todo(需要完善)执行sql成功后修改数据状态

        buildSynzData(modelPhysical);
        break;
      default:
        throw new BizException("手动同步,元数据表查询有误请联系相关人员");
    }
  }

  /**
   * 手动同步修改状态
   * @param modelPhysical
   */
  private void buildSynzData(ModelPhysicalTable modelPhysical) {
    modelPhysical.setSyncStatus(ModelStatus.SYNCHRONIZATION);
    modelPhysicalTableRepository.saveAndFlush(modelPhysical);
  }

  /**
   * 单个发布和批量发布
   * @param tables
   * @param modelPhysical
   * @param id
   */
  private void dealPushData(int tables, ModelPhysicalTable modelPhysical, Long id) {
    switch (tables){
      case ModelStatus.EXIST_DATA:
        throw new BizException("发布失败,该表已经存在并且存在数据");
      case ModelStatus.EXIST_NO_DATA:
        ModelSql sqlv1 = modelSqlRepository.findByTableId(id);
        //todo(需要完善)执行sql成功后修改数据状态

        builPushData(modelPhysical);
        break;
      case ModelStatus.NO_EXIST:
        ModelSql sqlv0 = modelSqlRepository.findByTableId(id);
        //todo(需要完善)执行sql成功后修改数据状态

        builPushData(modelPhysical);
        break;
      default:
        throw new BizException("发布失败,元数据表查询有误请联系相关人员");
    }
  }

  /**
   * 单个、批量发布成功修改数据状态
   * @param modelPhysical
   */
  private void builPushData(ModelPhysicalTable modelPhysical) {
    modelPhysical.setStatus(ModelStatus.PUBLISH);
    modelPhysicalTableRepository.saveAndFlush(modelPhysical);
  }

  /**
   * 根据id批量发布
   * @param idList
   */
  @Override
  public void push(List<Long> idList) {
    checkStatus(idList);
    idList.forEach(
        id->{
          //1根据基础表id查询基础信息，判断元数据中是否存在该表信息
          ModelPhysicalTable modelPhysicalTable = modelPhysicalTableRepository.findById(id).orElse(null);
          //2校验基础信息是否完善
          Boolean check = checkPhysicalTable(modelPhysicalTable);
          //3如果存在就不用同步如果不存在就查询创建sql调用sdk发布建表任务
          if (!check){
            throw new BizException("id为"+id+"的信息不完善请完善后发布");
          }
          int tables = getTables(modelPhysicalTable.getDataConnection(),
              modelPhysicalTable.getDataSourceName(), modelPhysicalTable.getDataBaseName(),
              modelPhysicalTable.getTableName());
          dealPushData(tables,modelPhysicalTable,id);
        }
    );
  }

  /**
   * 校验发布数据是否存在已发布数据
   * @param idList
   */
  private void checkStatus(List<Long> idList) {
    List<ModelPhysicalTable> modelPhysicalTableList = modelPhysicalTableRepository.findAllById(idList);
    if (CollectionUtils.isEmpty(modelPhysicalTableList)){
      throw new BizException("当前发布数据不存在");
    }
    modelPhysicalTableList.forEach(modelPhysicalTable -> {
      if (modelPhysicalTable.getStatus().equals(ModelStatus.PUBLISH)){
        throw new BizException("存在已发布数据请去除后重新发布");
      }
      int tables = getTables(modelPhysicalTable.getDataConnection(),
          modelPhysicalTable.getDataSourceName(), modelPhysicalTable.getDataBaseName(),
          modelPhysicalTable.getTableName());
      if (tables==ModelStatus.EXIST_DATA){
        throw new BizException(modelPhysicalTable.getTableName()+"库中存在且有数据");
      }
    });
  }

  /**
   * 校验数据的基础信息是否完善
   * @param modelPhysical
   * @return
   */
  private Boolean checkPhysicalTable(ModelPhysicalTable modelPhysical) {
    Boolean check = true;
    //1校验主题和主题id、表名、数据连接、数据库
    if (StringUtils.isEmpty(modelPhysical.getTheme())||StringUtils.isEmpty(modelPhysical.getThemeId())||StringUtils.isEmpty(modelPhysical.getTableName())||
        StringUtils.isEmpty(modelPhysical.getDataConnection())||StringUtils.isEmpty(modelPhysical.getDataBaseName())){
      check=false;
    }
    //2当数据库连接是HIVE时候判断表类型、数据格式、hdfs路径是不是为空
    if (modelPhysical.getDataConnection().equals(ModelStatus.CONNECT)){
      if (StringUtils.isEmpty(modelPhysical.getDataFormat())||StringUtils.isEmpty(modelPhysical.getTableType())||StringUtils.isEmpty(modelPhysical.getHftsRoute())){
        check=false;
      }
    }
  return check;
  }

  /**
   * 逆向数据库
   * @param modelReverseBaseDTO
   */
  @Override
  public void reverseBase(ModelReverseBaseDTO modelReverseBaseDTO) {
    List<String> tableList = getTableList(modelReverseBaseDTO);
    tableList.forEach(
        tableName -> {
          //1根据表明成获取数据库表，判断库中经存在这个这个表判断是否需要更新，如果更新就将库中数据更新，如果不需要更新就直接结束
          handelReverseBase(tableName,modelReverseBaseDTO);
        }
    );
  }

  /**
   * 取逆向表名称
   * @param modelReverseBaseDTO
   * @return
   */
  private List<String> getTableList(ModelReverseBaseDTO modelReverseBaseDTO) {
    List <String> tableList = new ArrayList<>();
    switch (modelReverseBaseDTO.getAllChoice()) {
      case ModelStatus.ALLCHOICE:
        List<MtdTables> allTableList = dataBaseService.getAllTable(modelReverseBaseDTO.getDataConnection(), modelReverseBaseDTO.getDataSourceName(), modelReverseBaseDTO.getDataBaseName());
        if (CollectionUtils.isEmpty(allTableList)){
          throw  new BizException("所选库中不存在表");
        }
        tableList = allTableList.stream().map(MtdTables::getDisplayText).collect(Collectors.toList());
        break;
      case ModelStatus.PARTCHOICE:
        if (CollectionUtils.isEmpty(modelReverseBaseDTO.getTableList())){
          throw new BizException("部分逆向表名称不能为空");
        }
        tableList=modelReverseBaseDTO.getTableList();
        break;
      default:
        throw new BizException("请选择逆向类型");
    }
        return tableList;
  }

  /**
   * 查询数据格式
   * @return
   */
  @Override
  public List<String> queryDataType() {
    return DataTypeEnum.getDataTypeName();
  }

  @Override
  public List<DataStandardInfoVO> getTree() {
    List<DataStandardTreeVO> tree = dataBaseService.getTree();
    List<DataStandardTreeVO> dealList = tree.stream().peek(dataStandardTreeVO -> {
          if (StringUtils.isEmpty(dataStandardTreeVO.getParentId())) {
            dataStandardTreeVO.setDirDsdName(ModelStatus.DIRNAME);
          }
        }).collect(Collectors.toList());
    List<DataStandardInfoVO> list = ModelDirMapper.INSTANCE.list(dealList);
    return list;
  }

  /**
   *获取基本信息，数据连接类型、数据源名称
   * @return
   */
  @Override
  public ModelDataSourceinfoVO getDataSourceinfo() {
    ModelDataSourceinfoVO modelDataSourceinfoVO = ModelDataSourceinfoVO.builder()
        .dataConnection(ModelStatus.DATACONNECTION)
        .dataSourceName(ModelStatus.DATASOURCENAME).build();
    System.out.println(modelDataSourceinfoVO);
    return modelDataSourceinfoVO;
  }

  /**
   * 处理逆向表
   * @param tableName
   * @param modelReverseBaseDTO
   */
  private void handelReverseBase(String tableName, ModelReverseBaseDTO modelReverseBaseDTO) {
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    checkReverseBase(booleanBuilder, tableName);
    List<ModelPhysicalTable> modelPhysicalTableList = jpaQueryFactory
            .select(qModelPhysicalTable)
            .from(qModelPhysicalTable)
            .where(booleanBuilder)
            .orderBy(qModelPhysicalTable.id.asc())
            .fetch();
    if (CollectionUtils.isNotEmpty(modelPhysicalTableList)){
      if (modelReverseBaseDTO.getReplace().equals(ModelStatus.REPLACE)){
        ModelPhysicalTable updateModelPhysicalTable = getUpdateModelphyTable(modelPhysicalTableList,modelReverseBaseDTO);
        ModelPhysicalDTO updateModelPhysicalDTO = getUpdateModelPhysicalDTO(tableName,updateModelPhysicalTable,modelReverseBaseDTO);
        //赋值入仓类型、数据源名称、表名称、数据库名称设置为空
        updateModelPhysicalTable.setDataConnection(ModelStatus.DATACONNECTION);
        updateModelPhysicalTable.setDataSourceName(ModelStatus.DATASOURCENAME);
        updateModelPhysicalTable.setTableName(tableName);
        updateModelPhysicalTable.setDataBaseName(null);
        //调用修改方法修改建模信息
        dataUpdateModelPhysical(updateModelPhysicalDTO,updateModelPhysicalTable);
      }
    }else{
      ModelPhysicalTable createModelPhysicalTable = getCreateModelphyTable(modelReverseBaseDTO);
      ModelPhysicalDTO createModelPhysicalDTO = getcreateModelPhysicalDTO(tableName,modelReverseBaseDTO);
      //赋值入仓类型、数据源名称、表名称、数据库名称设置为空
      createModelPhysicalTable.setDataConnection(ModelStatus.DATACONNECTION);
      createModelPhysicalTable.setDataSourceName(ModelStatus.DATASOURCENAME);
      createModelPhysicalTable.setTableName(tableName);
      createModelPhysicalTable.setDataBaseName(null);
      //调用新增方法新增建模信息
      dataModelPhysical(createModelPhysicalDTO,createModelPhysicalTable);
    }

  }

  /**
   * 逆向数据库获取新增入参信息
   *
   * @param tableName
   * @param modelReverseBaseDTO
   * @return
   */
  private ModelPhysicalDTO getcreateModelPhysicalDTO(String tableName,
      ModelReverseBaseDTO modelReverseBaseDTO) {
    //根据表名称查和数据库询元数据字段信息
    ModelPhysicalDTO modelPhysicalDTO = new ModelPhysicalDTO();
    //获取表字段信息
    List<MtdAttributes> columnList = dataBaseService.getAllColumn(modelReverseBaseDTO.getDataConnection(), modelReverseBaseDTO.getDataSourceName(), modelReverseBaseDTO.getDataBaseName(), tableName);
    if (CollectionUtils.isEmpty(columnList)){
      throw new BizException("表"+tableName+"字段为空");
    }
    List<ModelPhysicalColumnDTO> modelPhysicalColumnDTOList = columnList.stream().map(column -> {
      ModelPhysicalColumnDTO modelPhysicalColumnDTO = new ModelPhysicalColumnDTO();
      //赋值字段名称
      modelPhysicalColumnDTO.setColumnName(column.getName());
      //赋值字段类型
      modelPhysicalColumnDTO.setColumnType(column.getDataType());
      //赋值字段描述
      modelPhysicalColumnDTO.setDescription(column.getComment());
      //todo 目前是否分区、是否是主键、是否为空都默认给否
      //是否是主键
      modelPhysicalColumnDTO.setItsPrimaryKey(ModelStatus.SETDEFAULT);
      //是否分区
      modelPhysicalColumnDTO.setItsPartition(ModelStatus.SETDEFAULT);
      //是否为空
      modelPhysicalColumnDTO.setItsNull(ModelStatus.SETDEFAULT);
      return modelPhysicalColumnDTO;
    }).collect(Collectors.toList());
    modelPhysicalDTO.setModelColumnDtoList(modelPhysicalColumnDTOList);
    return modelPhysicalDTO;
  }

  /**
   * 逆向数据库获新增取基本信息
   * @param modelReverseBaseDTO
   * @return
   */
  private ModelPhysicalTable getCreateModelphyTable(ModelReverseBaseDTO modelReverseBaseDTO) {
    ModelPhysicalTable modelPhysicalTable = new ModelPhysicalTable();
    getModelPhysicalTable(modelPhysicalTable,modelReverseBaseDTO);
    return modelPhysicalTable;
  }

  /**
   * 逆向数据库获取修改入参类
   *
   * @param tableName
   * @param updateModelPhysicalTable
   * @param modelReverseBaseDTO
   * @return
   */
  private ModelPhysicalDTO getUpdateModelPhysicalDTO(String tableName,ModelPhysicalTable updateModelPhysicalTable, ModelReverseBaseDTO modelReverseBaseDTO) {
    //根据表名称查和数据库询元数据字段信息
    ModelPhysicalDTO modelPhysicalDTO = new ModelPhysicalDTO();
    //获取表字段信息
    List<MtdAttributes> columnList = dataBaseService.getAllColumn(modelReverseBaseDTO.getDataConnection(), modelReverseBaseDTO.getDataSourceName(), modelReverseBaseDTO.getDataBaseName(), tableName);
    if (CollectionUtils.isEmpty(columnList)){
      throw new BizException("表"+tableName+"字段为空");
    }
    List<ModelPhysicalColumnDTO> modelPhysicalColumnDTOList = columnList.stream().map(column -> {
      ModelPhysicalColumnDTO modelPhysicalColumnDTO = new ModelPhysicalColumnDTO();
      //赋值物理表id
      modelPhysicalColumnDTO.setTableId(updateModelPhysicalTable.getId());
      //赋值字段名称
      modelPhysicalColumnDTO.setColumnName(column.getName());
      //赋值字段类型
      modelPhysicalColumnDTO.setColumnType(column.getDataType());
      //赋值字段描述
      modelPhysicalColumnDTO.setDescription(column.getComment());
      //todo 目前是否分区、是否是主键、是否为空都默认给否
      //是否是主键
      modelPhysicalColumnDTO.setItsPrimaryKey(ModelStatus.SETDEFAULT);
      //是否分区
      modelPhysicalColumnDTO.setItsPartition(ModelStatus.SETDEFAULT);
      //是否为空
      modelPhysicalColumnDTO.setItsNull(ModelStatus.SETDEFAULT);
      return modelPhysicalColumnDTO;
    }).collect(Collectors.toList());
    modelPhysicalDTO.setModelColumnDtoList(modelPhysicalColumnDTOList);
    return modelPhysicalDTO;
  }

  /**
   * 逆向库获取基础信息（库中已经存在表）
   * @param modelPhysicalTableList
   * @param modelReverseBaseDTO
   * @return
   */
  private ModelPhysicalTable getUpdateModelphyTable(List<ModelPhysicalTable> modelPhysicalTableList, ModelReverseBaseDTO modelReverseBaseDTO) {
    ModelPhysicalTable modelPhysicalTable = modelPhysicalTableList.get(0);
    getModelPhysicalTable(modelPhysicalTable,modelReverseBaseDTO);
    return modelPhysicalTable;
  }

  /**
   * 逆向库创建基础信息
   * @param modelPhysicalTable
   * @param modelReverseBaseDTO
   */
  private void getModelPhysicalTable(ModelPhysicalTable modelPhysicalTable, ModelReverseBaseDTO modelReverseBaseDTO) {
    ModelPhysicalTableMapper.INSTANCE.copy(modelReverseBaseDTO,modelPhysicalTable);
    //0草稿 1已发布2 已下线
    modelPhysicalTable.setStatus(ModelStatus.DRAFT);
    //数据库和系统定义的sql是否同步（0表示已经同步，1表示未同步）
    modelPhysicalTable.setSyncStatus(ModelStatus.SYNCHRONIZATION);
  }

  private void checkReverseBase(BooleanBuilder booleanBuilder, String tableName) {
    if (StringUtils.isNotBlank(tableName)) {
      booleanBuilder.and(qModelPhysicalTable.tableName.eq(tableName));
    }
  }

  /**
   * 根据数据库名称和表明成获取元数据信息
   * @param dataConnection 数据源连接类型
   * @parm dataSourceName 数据源连接名称
   * @param databaseName 数据库名称
   * @param tableName 表名称
   * @return
   */
  private int getTables(String dataConnection, String dataSourceName,
      String databaseName, String tableName) {
     int resultTable = ModelStatus.EXIST_DEFAULT_DATA;
    if (StringUtils.isNotBlank(tableName) && StringUtils.isNotBlank(databaseName)
        && StringUtils.isNotBlank(dataSourceName) && StringUtils.isNotBlank(dataConnection)){
      resultTable = dataBaseService.getExistData(dataConnection, dataSourceName, databaseName,tableName);
    }
    return resultTable;
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
            .select(qModelPhysicalColumn)
            .from(qModelPhysicalColumn)
            .where(booleanBuilder)
            .orderBy(qModelPhysicalColumn.id.asc())
            .fetch();
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
          .filter(item -> StringUtils.isNotBlank(item.getStandardsCode()))
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
        modelPhysicalVO.setModelPhysicalTableDTO(modelPhysicalTableVO);
        //根据基础表id获取字段信息
          List<ModelPhysicalColumnVO> modelPhysicalColumnVOList = getModelPhysicalColumn(modelPhysicalTableVO.getId());
          if (CollectionUtils.isNotEmpty(modelPhysicalColumnVOList)){
            modelPhysicalVO.setModelColumnDtoList(modelPhysicalColumnVOList);
          }
        //根据基础表id获取关系信息
          List<ModelPhysicalRelationVO> modelPhysicalRelationVOList = getModelPhysicalRelation(modelPhysicalTableVO.getId());
          if (CollectionUtils.isNotEmpty(modelPhysicalRelationVOList)){
            modelPhysicalVO.setModelRelationDtoList(modelPhysicalRelationVOList);
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
    if (!Objects.isNull(queryModelPhysicalDTO.getModelId())) {
      booleanBuilder.and(qModelPhysicalTable.modelId.eq(queryModelPhysicalDTO.getModelId()));
    }
    if (CollectionUtils.isNotEmpty(queryModelPhysicalDTO.getThemeIdList()) && !queryModelPhysicalDTO.getThemeIdList().get(ModelStatus.FIRSTDIR).equals(ModelStatus.DIRNAMEID)){
      booleanBuilder.and(qModelPhysicalTable.themeId.in(queryModelPhysicalDTO.getThemeIdList()));
    }
  }

  /**
   * 修改建模数据
   * @param modelPhysicalDTO
   * @param modelPhysicalTable
   */
  private void updateModelPhysical(ModelPhysicalDTO modelPhysicalDTO,ModelPhysicalTable modelPhysicalTable) {
    //判断修改之后点击的是保存还是发布状态
      if (modelPhysicalTable.getStatus().equals(ModelStatus.PUBLISH)){
        int tables = getTables(modelPhysicalTable.getDataConnection(),
            modelPhysicalTable.getDataSourceName(),
            modelPhysicalTable.getDataBaseName(),
            modelPhysicalTable.getTableName());
        dealExistData(tables,modelPhysicalDTO,modelPhysicalTable);
      }else{
        dataUpdateModelPhysical(modelPhysicalDTO,modelPhysicalTable);
      }
  }

  /**
   * 创建修改关系建模信息方法
   * @param modelPhysicalDTO
   * @param modelPhysicalTable
   * @return
   */
  private String dataUpdateModelPhysical(ModelPhysicalDTO modelPhysicalDTO, ModelPhysicalTable modelPhysicalTable) {
    //1修改基础信息
    modelPhysicalTableRepository.saveAndFlush(modelPhysicalTable);
    //2修改字段配置
    List<ModelPhysicalColumn> physicalColumnList = modelPhysicalColumnRepository.findAllByTableId(modelPhysicalTable.getId());
    if (CollectionUtils.isNotEmpty(physicalColumnList)){
      modelPhysicalColumnRepository.deleteAllInBatch(physicalColumnList);
    }
    List<ModelPhysicalColumn> columnList = ModelPhysicalColumnMapper.INSTANCE.use(modelPhysicalDTO.getModelColumnDtoList());
    modelPhysicalColumnRepository.saveAll(columnList);
    //3修改模型关系
      List<ModelPhysicalRelation> modelPhysicalRelationList = modelPhysicalRelationRepository.findAllByTableId(modelPhysicalTable.getId());
      if (CollectionUtils.isNotEmpty(modelPhysicalRelationList)){
        modelPhysicalRelationRepository.deleteAllInBatch(modelPhysicalRelationList);
      }
    if (CollectionUtils.isNotEmpty(modelPhysicalDTO.getModelRelationDtoList())){
      List<ModelPhysicalRelation> relationLists = ModelPhysicalRelationMapper.INSTANCE.use(modelPhysicalDTO.getModelRelationDtoList());
      modelPhysicalRelationRepository.saveAll(relationLists);
    }
    //4创建新增语句
    ModelSql modelSql = modelSqlRepository.findByTableIdAndType(modelPhysicalTable.getId(), ModelType.PHYSICAL_TABLE);
    if (Objects.nonNull(modelSql)){
      modelSqlRepository.delete(modelSql);
    }
    //生成表的修改语句（等待调整）
    String sqls = createSql(modelPhysicalTable,columnList,modelPhysicalTable.getId());
    return sqls;
  }
}