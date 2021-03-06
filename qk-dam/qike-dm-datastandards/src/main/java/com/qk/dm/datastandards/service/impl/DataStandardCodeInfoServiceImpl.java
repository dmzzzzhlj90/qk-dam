package com.qk.dm.datastandards.service.impl;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datastandards.constant.DsdConstant;
import com.qk.dm.datastandards.datasource_connect.extractor.DataSourceExtractor;
import com.qk.dm.datastandards.datasource_connect.pojo.MysqlDb;
import com.qk.dm.datastandards.entity.DsdCodeInfo;
import com.qk.dm.datastandards.entity.DsdCodeInfoExt;
import com.qk.dm.datastandards.entity.QDsdCodeInfo;
import com.qk.dm.datastandards.entity.QDsdCodeInfoExt;
import com.qk.dm.datastandards.mapstruct.mapper.DsdCodeInfoExtMapper;
import com.qk.dm.datastandards.mapstruct.mapper.DsdCodeInfoMapper;
import com.qk.dm.datastandards.repositories.DsdCodeInfoExtRepository;
import com.qk.dm.datastandards.repositories.DsdCodeInfoRepository;
import com.qk.dm.datastandards.service.DataStandardCodeDirService;
import com.qk.dm.datastandards.service.DataStandardCodeInfoService;
import com.qk.dm.datastandards.vo.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wjq
 * @date 20210726
 * @since 1.0.0 码表信息实现类
 */
@Service
public class DataStandardCodeInfoServiceImpl implements DataStandardCodeInfoService {
  private static final Log LOG = LogFactory.get("码表信息操作");

  private final QDsdCodeInfo qDsdCodeInfo = QDsdCodeInfo.dsdCodeInfo;
  private final QDsdCodeInfoExt qDsdCodeInfoExt = QDsdCodeInfoExt.dsdCodeInfoExt;

  private final DsdCodeInfoRepository dsdCodeInfoRepository;
  private final DsdCodeInfoExtRepository dsdCodeInfoExtRepository;
  private final DataStandardCodeDirService dataStandardCodeDirService;
  private final DsdCodeInfoReverseBatchService dsdCodeInfoReverseBatchService;

  private final EntityManager entityManager;
  private JPAQueryFactory jpaQueryFactory;

  @Autowired
  public DataStandardCodeInfoServiceImpl(
      DsdCodeInfoRepository dsdCodeInfoRepository,
      DsdCodeInfoExtRepository dsdCodeInfoExtRepository,
      DataStandardCodeDirService dataStandardCodeDirService,
      DsdCodeInfoReverseBatchService dsdCodeInfoReverseBatchService,
      EntityManager entityManager) {
    this.dsdCodeInfoRepository = dsdCodeInfoRepository;
    this.dsdCodeInfoExtRepository = dsdCodeInfoExtRepository;
    this.dataStandardCodeDirService = dataStandardCodeDirService;
    this.dsdCodeInfoReverseBatchService = dsdCodeInfoReverseBatchService;
    this.entityManager = entityManager;
  }

  @PostConstruct
  public void initFactory() {
    jpaQueryFactory = new JPAQueryFactory(entityManager);
  }

  /** 码表基本信息_表 */
  @Override
  public PageResultVO<DsdCodeInfoVO> getDsdCodeInfo(DsdCodeInfoParamsVO dsdCodeInfoParamsVO) {
    List<DsdCodeInfoVO> dsdCodeInfoVOList = new ArrayList<>();

    Map<String, Object> map = null;
    try {
      map = queryDsdCodeInfoVOByParams(dsdCodeInfoParamsVO);
    } catch (Exception e) {
      e.printStackTrace();
      throw new BizException("查询失败!!!");
    }
    List<DsdCodeInfo> list = (List<DsdCodeInfo>) map.get("list");
    long total = (long) map.get("total");

    list.forEach(
        dsdCodeInfo -> {
          DsdCodeInfoVO dsdCodeInfoVO = DsdCodeInfoMapper.INSTANCE.useDsdCodeInfoVO(dsdCodeInfo);
          setCodeTableFields(dsdCodeInfo, dsdCodeInfoVO);
          dsdCodeInfoVOList.add(dsdCodeInfoVO);
        });
    return new PageResultVO<>(
        total,
        dsdCodeInfoParamsVO.getPagination().getPage(),
        dsdCodeInfoParamsVO.getPagination().getSize(),
        dsdCodeInfoVOList);
  }

  @Override
  public List<DsdCodeInfoVO> getDsdCodeInfoAll() {
    List<DsdCodeInfoVO> dsdCodeInfoVOList = new ArrayList<>();

    List<DsdCodeInfo> dsdCodeInfoList = dsdCodeInfoRepository.findAll();
    dsdCodeInfoList.forEach(
        dsdCodeInfo -> {
          DsdCodeInfoVO dsdCodeInfoVO = DsdCodeInfoMapper.INSTANCE.useDsdCodeInfoVO(dsdCodeInfo);
          setCodeTableFields(dsdCodeInfo, dsdCodeInfoVO);
          dsdCodeInfoVOList.add(dsdCodeInfoVO);
        });
    return dsdCodeInfoVOList;
  }

  public Map<String, Object> queryDsdCodeInfoVOByParams(DsdCodeInfoParamsVO dsdCodeInfoParamsVO) {
    HashMap<String, Object> result = new HashMap<>();
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    basicCheckCondition(booleanBuilder, qDsdCodeInfo, dsdCodeInfoParamsVO);
    long count =
        jpaQueryFactory
            .select(qDsdCodeInfo.count())
            .from(qDsdCodeInfo)
            .where(booleanBuilder)
            .fetchOne();
    List<DsdCodeInfo> dsdCodeInfoVOList =
        jpaQueryFactory
            .select(qDsdCodeInfo)
            .from(qDsdCodeInfo)
            .where(booleanBuilder)
            .orderBy(qDsdCodeInfo.gmtCreate.desc())
            .offset(
                (dsdCodeInfoParamsVO.getPagination().getPage() - 1)
                    * dsdCodeInfoParamsVO.getPagination().getSize())
            .limit(dsdCodeInfoParamsVO.getPagination().getSize())
            .fetch();
    result.put("list", dsdCodeInfoVOList);
    result.put("total", count);
    return result;
  }

  public void basicCheckCondition(
      BooleanBuilder booleanBuilder,
      QDsdCodeInfo qDsdCodeInfo,
      DsdCodeInfoParamsVO dsdCodeInfoParamsVO) {
    if (!StringUtils.isEmpty(dsdCodeInfoParamsVO.getCodeDirId())) {
      Set<String> codeDirIdSet = new HashSet<>();
      dataStandardCodeDirService.getCodeDirId(codeDirIdSet, dsdCodeInfoParamsVO.getCodeDirId());
      booleanBuilder.and(qDsdCodeInfo.codeDirId.in(codeDirIdSet));
    }
    if (!StringUtils.isEmpty(dsdCodeInfoParamsVO.getTableName())) {
      booleanBuilder.and(qDsdCodeInfo.tableName.contains(dsdCodeInfoParamsVO.getTableName()));
    }
    if (!StringUtils.isEmpty(dsdCodeInfoParamsVO.getTableCode())) {
      booleanBuilder.and(qDsdCodeInfo.tableCode.contains(dsdCodeInfoParamsVO.getTableCode()));
    }
    if (!StringUtils.isEmpty(dsdCodeInfoParamsVO.getBeginDay())
        && !StringUtils.isEmpty(dsdCodeInfoParamsVO.getEndDay())) {
      StringTemplate dateExpr =
          Expressions.stringTemplate(
              "DATE_FORMAT({0},'%Y-%m-%d %H:%i:%S')", qDsdCodeInfo.gmtModified);
      booleanBuilder.and(
          dateExpr.between(dsdCodeInfoParamsVO.getBeginDay(), dsdCodeInfoParamsVO.getEndDay()));
    }
  }

  @Override
  public void addDsdCodeInfo(DsdCodeInfoVO dsdCodeInfoVO) {
    DsdCodeInfo dsdCodeInfo = DsdCodeInfoMapper.INSTANCE.useDsdCodeInfo(dsdCodeInfoVO);
    setCodeTableFieldsStr(dsdCodeInfo, dsdCodeInfoVO);
    dsdCodeInfo.setGmtCreate(new Date());
    dsdCodeInfo.setGmtModified(new Date());
    dsdCodeInfo.setDelFlag(0);
    Predicate predicate = qDsdCodeInfo.tableCode.eq(dsdCodeInfo.getTableCode());
    boolean exists = dsdCodeInfoRepository.exists(predicate);
    if (exists) {
      throw new BizException(
          "当前要新增的码表信息,表编码为:"
              + dsdCodeInfo.getTableCode()
              + "，层级目录为:"
              + dsdCodeInfo.getCodeDirLevel()
              + "的数据 已存在！！！");
    }
    if (dsdCodeInfoVO.getCodeTableFieldsList().size() == 0
        && dsdCodeInfo.getTableConfFields() == null) {
      dsdCodeInfo.setTableConfFields(DsdConstant.defaultTableConfFields());
    }

    dsdCodeInfoRepository.save(dsdCodeInfo);
  }

  @Override
  public DsdCodeInfoVO getDsdCodeInfoById(long id) {
    Optional<DsdCodeInfo> dsdCodeInfo = dsdCodeInfoRepository.findById(id);
    if (dsdCodeInfo.isPresent()) {
      DsdCodeInfoVO dsdCodeInfoVO = DsdCodeInfoMapper.INSTANCE.useDsdCodeInfoVO(dsdCodeInfo.get());
      setCodeTableFields(dsdCodeInfo.get(), dsdCodeInfoVO);
      return dsdCodeInfoVO;
    }
    return null;
  }

  @Override
  public DsdCodeInfoVO getBasicDsdCodeInfoByTableCode(String tableCode) {
    Optional<DsdCodeInfo> dsdCodeInfoOptional =
        dsdCodeInfoRepository.findOne(qDsdCodeInfo.tableCode.eq(tableCode));
    if (dsdCodeInfoOptional.isEmpty()) {
      throw new BizException("未查询到tableCode : " + tableCode + "的码表!!!");
    }
    DsdCodeInfo dsdCodeInfo = dsdCodeInfoOptional.get();
    DsdCodeInfoVO dsdCodeInfoVO = DsdCodeInfoMapper.INSTANCE.useDsdCodeInfoVO(dsdCodeInfo);
    setCodeTableFields(dsdCodeInfo, dsdCodeInfoVO);
    return dsdCodeInfoVO;
  }

  @Override
  public void modifyDsdCodeInfo(DsdCodeInfoVO dsdCodeInfoVO) {
    if (StringUtils.isEmpty(String.valueOf(dsdCodeInfoVO.getId()))) {
      throw new BizException("编辑失败!数据id为空!!!");
    }
    DsdCodeInfo dsdCodeInfo = DsdCodeInfoMapper.INSTANCE.useDsdCodeInfo(dsdCodeInfoVO);
    setCodeTableFieldsStr(dsdCodeInfo, dsdCodeInfoVO);
    dsdCodeInfo.setGmtCreate(new Date());
    dsdCodeInfo.setGmtModified(new Date());
    dsdCodeInfo.setDelFlag(0);

    Predicate predicate = qDsdCodeInfo.id.eq(dsdCodeInfoVO.getId());
    boolean exists = dsdCodeInfoRepository.exists(predicate);
    if (exists) {
      dsdCodeInfoRepository.saveAndFlush(dsdCodeInfo);
    } else {
      throw new BizException(
          "当前要新增的码表信息,目录为："
              + dsdCodeInfo.getCodeDirLevel()
              + "表名为:"
              + dsdCodeInfo.getTableCode()
              + "_"
              + dsdCodeInfo.getTableName()
              + " 的数据，不存在！！！");
    }
  }

  @Transactional
  @Override
  public void deleteDsdCodeInfo(long id) {
    dsdCodeInfoRepository.deleteById(id);
    dsdCodeInfoExtRepository.deleteByDsdCodeInfoId(id);
  }

  @Transactional
  @Override
  public void deleteBulkDsdCodeInfo(String ids) {
    List<String> idList = Arrays.asList(ids.split(","));
    Set<Long> idSet = new HashSet<>();
    idList.forEach(id -> idSet.add(Long.parseLong(id)));
    Iterable<DsdCodeInfo> codeInfos = dsdCodeInfoRepository.findAll(qDsdCodeInfo.id.in(idSet));
    dsdCodeInfoRepository.deleteInBatch(codeInfos);
    dsdCodeInfoExtRepository.deleteByDsdCodeInfoIdBatch(idSet);
  }

  @Override
  public List<Map<String, String>> getDataTypes() {
    return DsdConstant.getDataTypes();
  }

  private void setCodeTableFieldsStr(DsdCodeInfo dsdCodeInfo, DsdCodeInfoVO dsdCodeInfoVO) {
    List<CodeTableFieldsVO> codeTableFieldsList = dsdCodeInfoVO.getCodeTableFieldsList();
    if (codeTableFieldsList != null && codeTableFieldsList.size() > 0) {
      dsdCodeInfo.setTableConfFields(GsonUtil.toJsonString(codeTableFieldsList));
    }
  }

  private void setCodeTableFields(DsdCodeInfo dsdCodeInfo, DsdCodeInfoVO dsdCodeInfoVO) {
    if (!StringUtils.isEmpty(dsdCodeInfo.getTableConfFields())) {
      dsdCodeInfoVO.setCodeTableFieldsList(
          GsonUtil.fromJsonString(
              dsdCodeInfo.getTableConfFields(),
              new TypeToken<List<CodeTableFieldsVO>>() {}.getType()));
    } else {
      dsdCodeInfoVO.setCodeTableFieldsList(new ArrayList<>());
    }
  }

  /**
   * 码表扩展信息_码表数值
   *
   * @param dsdCodeInfoExtParamsVO
   */
  @Override
  public Map<String, Object> getDsdCodeInfoExt(DsdCodeInfoExtParamsVO dsdCodeInfoExtParamsVO) {
    Map<String, Object> result = new HashMap<>();
    List<DsdCodeInfoExtVO> dsdCodeInfoExtVOList = new ArrayList<>();
    Map<String, Object> map = null;
    try {
      map = queryDsdCodeInfoVOExtByParams(dsdCodeInfoExtParamsVO);
    } catch (Exception e) {
      e.printStackTrace();
      throw new BizException("查询失败!!!");
    }
    List<DsdCodeInfoExt> list = (List<DsdCodeInfoExt>) map.get("list");
    long total = (long) map.get("total");

    list.forEach(
        dsdCodeInfoExt -> {
          DsdCodeInfoExtVO dsdCodeInfoExtVO =
              DsdCodeInfoExtMapper.INSTANCE.useDsdCodeInfoExtVO(dsdCodeInfoExt);
          setCodeTableValues(dsdCodeInfoExt, dsdCodeInfoExtVO);
          dsdCodeInfoExtVOList.add(dsdCodeInfoExtVO);
        });
    PageResultVO<DsdCodeInfoExtVO> pageResultVO =
        new PageResultVO<>(
            total,
            dsdCodeInfoExtParamsVO.getPagination().getPage(),
            dsdCodeInfoExtParamsVO.getPagination().getSize(),
            dsdCodeInfoExtVOList);

    Optional<DsdCodeInfo> dsdCodeInfo =
        dsdCodeInfoRepository.findById(Long.valueOf(dsdCodeInfoExtParamsVO.getDsdCodeInfoId()));
    String tableConfFieldsStr = dsdCodeInfo.get().getTableConfFields();
    List<CodeTableFieldsVO> codeTableFieldsVOList =
        GsonUtil.fromJsonString(
            tableConfFieldsStr, new TypeToken<List<CodeTableFieldsVO>>() {}.getType());
    result.put("headList", codeTableFieldsVOList);
    result.put("dataList", pageResultVO);
    return result;
  }

  public Map<String, Object> queryDsdCodeInfoVOExtByParams(
      DsdCodeInfoExtParamsVO dsdCodeInfoExtParamsVO) {
    HashMap<String, Object> result = new HashMap<>();
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    extCheckCondition(booleanBuilder, qDsdCodeInfoExt, dsdCodeInfoExtParamsVO);
    long count =
        jpaQueryFactory
            .select(qDsdCodeInfoExt.count())
            .from(qDsdCodeInfoExt)
            .where(booleanBuilder)
            .fetchOne();
    List<DsdCodeInfoExt> dsdCodeInfoExtList =
        jpaQueryFactory
            .select(qDsdCodeInfoExt)
            .from(qDsdCodeInfoExt)
            .where(booleanBuilder)
            .orderBy(qDsdCodeInfoExt.gmtCreate.desc())
            .offset(
                (dsdCodeInfoExtParamsVO.getPagination().getPage() - 1)
                    * dsdCodeInfoExtParamsVO.getPagination().getSize())
            .limit(dsdCodeInfoExtParamsVO.getPagination().getSize())
            .fetch();
    result.put("list", dsdCodeInfoExtList);
    result.put("total", count);
    return result;
  }

  public void extCheckCondition(
      BooleanBuilder booleanBuilder,
      QDsdCodeInfoExt qDsdCodeInfoExt,
      DsdCodeInfoExtParamsVO dsdCodeInfoExtParamsVO) {
    if (!StringUtils.isEmpty(dsdCodeInfoExtParamsVO.getDsdCodeInfoId())) {
      booleanBuilder.and(
          qDsdCodeInfoExt.dsdCodeInfoId.eq(
              Long.valueOf(dsdCodeInfoExtParamsVO.getDsdCodeInfoId())));
    }

    if (!StringUtils.isEmpty(dsdCodeInfoExtParamsVO.getSearchCode())) {
      booleanBuilder.and(
          qDsdCodeInfoExt.searchCode.contains(dsdCodeInfoExtParamsVO.getSearchCode()));
    }
    if (!StringUtils.isEmpty(dsdCodeInfoExtParamsVO.getSearchValue())) {
      booleanBuilder.and(
          qDsdCodeInfoExt.searchValue.contains(dsdCodeInfoExtParamsVO.getSearchValue()));
    }
    if (!StringUtils.isEmpty(dsdCodeInfoExtParamsVO.getBeginDay())
        && !StringUtils.isEmpty(dsdCodeInfoExtParamsVO.getEndDay())) {
      StringTemplate dateExpr =
          Expressions.stringTemplate(
              "DATE_FORMAT({0},'%Y-%m-%d %H:%i:%S')", qDsdCodeInfoExt.gmtModified);
      booleanBuilder.and(
          dateExpr.between(
              dsdCodeInfoExtParamsVO.getBeginDay(), dsdCodeInfoExtParamsVO.getEndDay()));
    }
  }

  @Override
  public void addDsdCodeInfoExt(DsdCodeInfoExtVO dsdCodeInfoExtVO) {
    DsdCodeInfoExt dsdCodeInfoExt =
        DsdCodeInfoExtMapper.INSTANCE.useDsdCodeInfoExt(dsdCodeInfoExtVO);
    setTableConfValuesJson(dsdCodeInfoExt, dsdCodeInfoExtVO);
    dsdCodeInfoExt.setGmtCreate(new Date());
    dsdCodeInfoExt.setGmtModified(new Date());
    dsdCodeInfoExt.setDelFlag(0);

    Predicate predicate =
        qDsdCodeInfoExt
            .dsdCodeInfoId
            .eq(dsdCodeInfoExt.getDsdCodeInfoId())
            .and(qDsdCodeInfoExt.searchCode.eq(dsdCodeInfoExt.getSearchCode()));
    boolean exists = dsdCodeInfoExtRepository.exists(predicate);
    if (exists) {
      throw new BizException(
          "当前要新增的码表数值,编码code为:" + dsdCodeInfoExt.getSearchCode() + " 的数据，已存在！！！");
    }
    dsdCodeInfoExtRepository.save(dsdCodeInfoExt);
  }

  @Override
  public DsdCodeInfoExtVO getBasicDsdCodeInfoExtById(long id) {
    Optional<DsdCodeInfoExt> dsdCodeInfoExt = dsdCodeInfoExtRepository.findById(id);
    if (dsdCodeInfoExt.isPresent()) {
      DsdCodeInfoExtVO dsdCodeInfoExtVO =
          DsdCodeInfoExtMapper.INSTANCE.useDsdCodeInfoExtVO(dsdCodeInfoExt.get());
      setCodeTableValues(dsdCodeInfoExt.get(), dsdCodeInfoExtVO);
      return dsdCodeInfoExtVO;
    }
    return null;
  }

  @Override
  public void modifyDsdCodeInfoExt(DsdCodeInfoExtVO dsdCodeInfoExtVO) {
    if (StringUtils.isEmpty(String.valueOf(dsdCodeInfoExtVO.getId()))) {
      throw new BizException("编辑失败!码表数值ID为空!!!");
    }

    DsdCodeInfoExt dsdCodeInfoExt =
        DsdCodeInfoExtMapper.INSTANCE.useDsdCodeInfoExt(dsdCodeInfoExtVO);
    setTableConfValuesJson(dsdCodeInfoExt, dsdCodeInfoExtVO);
    dsdCodeInfoExt.setGmtCreate(new Date());
    dsdCodeInfoExt.setGmtModified(new Date());
    dsdCodeInfoExt.setDelFlag(0);

    Predicate predicate = qDsdCodeInfoExt.id.eq(dsdCodeInfoExtVO.getId());
    boolean exists = dsdCodeInfoExtRepository.exists(predicate);
    if (exists) {
      dsdCodeInfoExtRepository.saveAndFlush(dsdCodeInfoExt);
    } else {
      throw new BizException(
          "当前要新增的码表数值,编码code为:" + dsdCodeInfoExt.getSearchCode() + " 的数据，不存在！！！");
    }
  }

  @Override
  public void deleteDsdCodeInfoExt(long id) {
    dsdCodeInfoExtRepository.deleteById(id);
  }

  @Override
  public void deleteBulkDsdCodeInfoExt(String ids) {
    List<String> idList = Arrays.asList(ids.split(","));
    Set<Long> idSet = new HashSet<>();
    idList.forEach(id -> idSet.add(Long.valueOf(id)));
    Iterable<DsdCodeInfoExt> codeInfoExtIter =
        dsdCodeInfoExtRepository.findAll(qDsdCodeInfoExt.id.in(idSet));
    dsdCodeInfoExtRepository.deleteInBatch(codeInfoExtIter);
  }

  @Override
  public void dsdCodeInfoReverseDB(DsdCodeInfoReverseDBVO dsdCodeInfoReverseDBVO) {
    LOG.info("=======开启,码表数据逆向数据库功能!======");
    // 1.获取码表元数据信息,数据源====>DB级别元数据信息
    MysqlDb mysqlDb =
        DataSourceExtractor.mysqlMetaData(dsdCodeInfoReverseDBVO.getDataSourceJobVO());
    LOG.info(
        "1.获取码表元数据信息,数据源====>DB级别元数据信息成功! db: 【{}】; table个数: 【{}】",
        mysqlDb.getName(),
        mysqlDb.getMysqlTables().size());
    // 2.元数据====>生成码表基本信息
    dsdCodeInfoReverseBatchService.reverseCreateCodeInfo(dsdCodeInfoReverseDBVO, mysqlDb);
    LOG.info("2.元数据====>生成码表基本信息成功");
    // 3.字典数据====>获取并存储码表码值信息
    dsdCodeInfoReverseBatchService.reverseCreateCodeValues(dsdCodeInfoReverseDBVO, mysqlDb);
    LOG.info("3.码表数据====>获取并存储码表码值信息成功!");
    LOG.info("=======结束,码表数据逆向数据库功能!======");
  }

  private void setTableConfValuesJson(
      DsdCodeInfoExt dsdCodeInfoExt, DsdCodeInfoExtVO dsdCodeInfoExtVO) {
    Map<String, String> codeTableFieldExtValues = dsdCodeInfoExtVO.getCodeTableFieldExtValues();
    if (codeTableFieldExtValues != null && codeTableFieldExtValues.size() > 0) {
      dsdCodeInfoExt.setTableConfExtValues(GsonUtil.toJsonString(codeTableFieldExtValues));
    }
  }

  private void setCodeTableValues(
      DsdCodeInfoExt dsdCodeInfoExt, DsdCodeInfoExtVO dsdCodeInfoExtVO) {
    if (!StringUtils.isEmpty(dsdCodeInfoExt.getTableConfExtValues())) {
      dsdCodeInfoExtVO.setCodeTableFieldExtValues(
          GsonUtil.fromJsonString(
              dsdCodeInfoExt.getTableConfExtValues(),
              new TypeToken<LinkedHashMap<String, String>>() {}.getType()));
    }
  }
}
