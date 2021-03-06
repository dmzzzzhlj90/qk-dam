package com.qk.dm.datasource.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.datasource.entity.*;
import com.qk.dam.datasource.enums.ConnTypeEnum;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datasource.entity.DsDatasource;
import com.qk.dm.datasource.entity.QDsDatasource;
import com.qk.dm.datasource.mapstruct.mapper.DSDatasourceMapper;
import com.qk.dm.datasource.repositories.DsDatasourceRepository;
import com.qk.dm.datasource.service.DsDataSourceService;
import com.qk.dm.datasource.service.DsDirService;
import com.qk.dm.datasource.util.DsDataSouurceConnectUtil;
import com.qk.dm.datasource.vo.DsDataSourceParamsVO;
import com.qk.dm.datasource.vo.DsDatasourceVO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.*;

/**
 * 数据源连接实现接口
 *
 * @author zys
 * @date 2021/8/2 10:50
 * @since 1.0.0
 */
@Service
@Transactional
public class DsDataSourceServiceImpl implements DsDataSourceService {
  private final DsDirService dsDirService;
  private final QDsDatasource qDsDatasource = QDsDatasource.dsDatasource;
  private final EntityManager entityManager;
  private final DsDatasourceRepository dsDatasourceRepository;
  private JPAQueryFactory jpaQueryFactory;

  @Autowired
  public DsDataSourceServiceImpl(
      DsDirService dsDirService,
      EntityManager entityManager,
      DsDatasourceRepository dsDatasourceRepository) {
    this.dsDirService = dsDirService;
    this.entityManager = entityManager;
    this.dsDatasourceRepository = dsDatasourceRepository;
  }

  @PostConstruct
  public void initFactory() {
    jpaQueryFactory = new JPAQueryFactory(entityManager);
  }

  @Override
  public PageResultVO<DsDatasourceVO> getDsDataSource(DsDataSourceParamsVO dsDataSourceParamsVO) {
    List<DsDatasourceVO> dsDataSourceVOList = new ArrayList<DsDatasourceVO>();
    Map<String, Object> map = null;
    try {
      map = queryDsDataSourceByParams(dsDataSourceParamsVO);
    } catch (ParseException e) {
      e.printStackTrace();
      throw new BizException("查询失败!!!");
    }
    List<DsDatasource> list = (List<DsDatasource>) map.get("list");
    long total = (long) map.get("total");

    list.forEach(
        dsd -> {
          DsDatasourceVO dsDatasourceVO = DSDatasourceMapper.INSTANCE.useDsDatasourceVO(dsd);
          setConnectBasicInfo(dsd, dsDatasourceVO);
          dsDataSourceVOList.add(dsDatasourceVO);
        });
    return new PageResultVO<>(
        total,
        dsDataSourceParamsVO.getPagination().getPage(),
        dsDataSourceParamsVO.getPagination().getSize(),
        dsDataSourceVOList);
  }

  private void setConnectBasicInfo(DsDatasource dsd, DsDatasourceVO dsDatasourceVO) {
    if (StringUtils.isNotBlank(dsd.getDataSourceValues())) {
      dsDatasourceVO.setConnectBasicInfo(getConnectInfo(dsd.getLinkType(), dsd));
    }
  }

  /**
   * 新增数据连接
   *
   * @param dsDatasourceVO
   */
  @Override
  public void addDsDataSource(DsDatasourceVO dsDatasourceVO) {
    DsDatasource dsDatasource = DSDatasourceMapper.INSTANCE.useDsDatasource(dsDatasourceVO);
    dsDatasource.setGmtCreate(new Date());
    dsDatasource.setId(UUID.randomUUID().toString().replaceAll("-", ""));
    // 将传入的数据源连接信息转换赋值
    setDataSourceValues(dsDatasource, dsDatasourceVO);
    // dsDatasource.setDataSourceValues(dsDatasourceVO.getConnectBasicInfoJson());
    BooleanExpression predicate = qDsDatasource.dataSourceName.eq(dsDatasource.getDataSourceName());
    boolean exists = dsDatasourceRepository.exists(predicate);
    if (exists) {
      throw new BizException(
          "当前要新增数据连接应用系统名称为:"
              + dsDatasource.getHomeSystem()
              + " 所属的节点层级目录id为:"
              + dsDatasource.getDicId()
              + " 的数据，已存在！！！");
    }
    dsDatasourceRepository.save(dsDatasource);
  }

  /**
   * 删除数据源连接
   *
   * @param id
   */
  @Override
  public void deleteDsDataSource(String id) {
    BooleanExpression predicate = qDsDatasource.id.eq(id);
    boolean exists = dsDatasourceRepository.exists(predicate);
    if (exists) {
      dsDatasourceRepository.deleteById(id);
    } else {
      throw new BizException("当前要删除的应用系统,id为:" + id + " 的数据不存在");
    }
  }

  /**
   * 修改数据连接
   *
   * @param dsDatasourceVO
   */
  @Override
  public void updateDsDataSource(DsDatasourceVO dsDatasourceVO) {
    DsDatasource dsDatasource = DSDatasourceMapper.INSTANCE.useDsDatasource(dsDatasourceVO);
    dsDatasource.setGmtModified(new Date());
    setDataSourceValues(dsDatasource, dsDatasourceVO);
    // dsDatasource.setDataSourceValues(dsDatasourceVO.getConnectBasicInfoJson());
    Predicate predicate = qDsDatasource.id.eq(dsDatasource.getId());
    boolean exists = dsDatasourceRepository.exists(predicate);
    if (exists) {
      dsDatasourceRepository.saveAndFlush(dsDatasource);
    } else {
      throw new BizException(
          "当前要更新的数据连接的ID为："
              + dsDatasource.getId()
              + "数据连接所属系统名称为:"
              + dsDatasource.getHomeSystem()
              + " 的数据，不存在！！！");
    }
  }

  private void setDataSourceValues(DsDatasource dsDatasource, DsDatasourceVO dsDatasourceVO) {
    Object baseDataSourceTypeInfo = dsDatasourceVO.getConnectBasicInfo();
    if (baseDataSourceTypeInfo != null) {
      String baseDataInfo =
          DsDataSouurceConnectUtil.addDriverinfo(
              baseDataSourceTypeInfo, dsDatasource.getLinkType());
      dsDatasource.setDataSourceValues(baseDataInfo);
    }
  }

  /**
   * 获取数据源连接类型
   *
   * @return
   */
  @Override
  public List<String> getDataType() {
    List<String> linkTypeList = dsDatasourceRepository.getlinkType();
    if (CollectionUtils.isNotEmpty(linkTypeList)) {
      return linkTypeList;
    } else {
      throw new BizException("获取数据库连接类型为空");
    }
  }

  /**
   * 创建数据库连接测试接口
   *
   * @param dsDatasourceVO
   * @return
   */
  @Override
  public void dataSourceConnect(DsDatasourceVO dsDatasourceVO) {
    if (dsDatasourceVO != null) {
      DsDataSouurceConnectUtil.getDataSourceConnect(dsDatasourceVO);
    } else {
      throw new BizException("数据链连接测试接口参数为空，请检测");
    }
  }

  @Override
  public List<DsDatasourceVO> getDataSourceByType(String type) {
    List<DsDatasourceVO> resultDataList = new ArrayList<>();
    Iterable<DsDatasource> dsDatasourceIterable =
        dsDatasourceRepository.findAll(qDsDatasource.linkType.eq(type));
    for (DsDatasource dsDatasource : dsDatasourceIterable) {
      DsDatasourceVO dsDatasourceVO = DSDatasourceMapper.INSTANCE.useDsDatasourceVO(dsDatasource);
      ConnectBasicInfo dsConnectBasicInfo = getConnectInfo(type, dsDatasource);
      dsDatasourceVO.setConnectBasicInfo(dsConnectBasicInfo);
      resultDataList.add(dsDatasourceVO);
    }
    return resultDataList;
  }

  @Override
  public ConnectBasicInfo getConnectInfo(String type, DsDatasource dsDatasource) {
    ConnectBasicInfo connectBasicInfo = null;
    if (type.equalsIgnoreCase(ConnTypeEnum.MYSQL.getName())) {
      String dataSourceValues = dsDatasource.getDataSourceValues();
      connectBasicInfo =
          GsonUtil.fromJsonString(dataSourceValues, new TypeToken<MysqlInfo>() {}.getType());
    }
    if (type.equalsIgnoreCase(ConnTypeEnum.HIVE.getName())) {
      String dataSourceValues = dsDatasource.getDataSourceValues();
      connectBasicInfo =
          GsonUtil.fromJsonString(dataSourceValues, new TypeToken<HiveInfo>() {}.getType());
    }
//    if (type.equalsIgnoreCase(ConnTypeEnum.ORACLE.getName())) {
//      String dataSourceValues = dsDatasource.getDataSourceValues();
//      connectBasicInfo =
//          GsonUtil.fromJsonString(dataSourceValues, new TypeToken<OracleInfo>() {}.getType());
//    }
//    if (type.equalsIgnoreCase(ConnTypeEnum.POSTGRESQL.getName())) {
//      String dataSourceValues = dsDatasource.getDataSourceValues();
//      connectBasicInfo =
//          GsonUtil.fromJsonString(dataSourceValues, new TypeToken<PostgresqlInfo>() {}.getType());
//    }
    if (type.equalsIgnoreCase(ConnTypeEnum.ELASTICSEARCH.getName())) {
      String dataSourceValues = dsDatasource.getDataSourceValues();
      connectBasicInfo =
              GsonUtil.fromJsonString(dataSourceValues, new TypeToken<ElasticSearchVO>() {}.getType());
    }
    return connectBasicInfo;
  }

  @Override
  public List<DsDatasourceVO> getDataSourceByDsname(String id) {
    List<DsDatasourceVO> resultDataList = new ArrayList<>();
    Optional<DsDatasource> datasourceList = dsDatasourceRepository.findById(id);
    if (!datasourceList.isEmpty()) {
      DsDatasource dsDatasource = datasourceList.get();
      DsDatasourceVO dsDatasourceVO = DSDatasourceMapper.INSTANCE.useDsDatasourceVO(dsDatasource);
      ConnectBasicInfo dsConnectBasicInfo =
          getConnectInfo(dsDatasource.getLinkType(), dsDatasource);
      dsDatasourceVO.setConnectBasicInfo(dsConnectBasicInfo);
      resultDataList.add(dsDatasourceVO);
      return resultDataList;
    } else {
      throw new BizException("根据数据源名称获取数据源连接信息为空");
    }
  }

  /**
   * 根据条件查询数据源连接
   *
   * @param dsDataSourceParamsVO
   * @return
   */
  private Map<String, Object> queryDsDataSourceByParams(DsDataSourceParamsVO dsDataSourceParamsVO) {
    QDsDatasource qDsDatasource = QDsDatasource.dsDatasource;
    Map<String, Object> result = new HashMap<>();
    BooleanBuilder booleanBuilder = new BooleanBuilder();
    checkCondition(booleanBuilder, qDsDatasource, dsDataSourceParamsVO);
    long count =
        jpaQueryFactory
            .select(qDsDatasource.count())
            .from(qDsDatasource)
            .where(booleanBuilder)
            .fetchOne();
    List<DsDatasource> dsdBasicInfoList =
        jpaQueryFactory
            .select(qDsDatasource)
            .from(qDsDatasource)
            .where(booleanBuilder)
            .orderBy(qDsDatasource.id.asc())
            .offset(
                (dsDataSourceParamsVO.getPagination().getPage() - 1)
                    * dsDataSourceParamsVO.getPagination().getSize())
            .limit(dsDataSourceParamsVO.getPagination().getSize())
            .fetch();
    result.put("list", dsdBasicInfoList);
    result.put("total", count);
    return result;
  }
  /**
   * 创建查询条件
   *
   * @param booleanBuilder
   * @param qDsDatasource
   * @param dsDataSourceParamsVO
   */
  private void checkCondition(
      BooleanBuilder booleanBuilder,
      QDsDatasource qDsDatasource,
      DsDataSourceParamsVO dsDataSourceParamsVO) {
    if (!StringUtils.isEmpty(dsDataSourceParamsVO.getDicId())) {
      Set<String> dsDicIdSet = new HashSet<>();
      dsDicIdSet.add(dsDataSourceParamsVO.getDicId());
      dsDirService.getDsdId(dsDicIdSet, dsDataSourceParamsVO.getDicId());
      booleanBuilder.and(qDsDatasource.dicId.in(dsDicIdSet));
    }
    if (!StringUtils.isEmpty(dsDataSourceParamsVO.getDataSourceName())) {
      booleanBuilder.and(
          qDsDatasource.dataSourceName.contains(dsDataSourceParamsVO.getDataSourceName()));
    }
    if (!StringUtils.isEmpty(dsDataSourceParamsVO.getLinkType())) {
      booleanBuilder.and(qDsDatasource.linkType.contains(dsDataSourceParamsVO.getLinkType()));
    }
    if (!StringUtils.isEmpty(dsDataSourceParamsVO.getBeginDay())
        && !StringUtils.isEmpty(dsDataSourceParamsVO.getEndDay())) {
      StringTemplate dateExpr =
          Expressions.stringTemplate(
              "DATE_FORMAT({0},'%Y-%m-%d %H:%i:%S')", qDsDatasource.gmtModified);
      booleanBuilder.and(
          dateExpr.between(dsDataSourceParamsVO.getBeginDay(), dsDataSourceParamsVO.getEndDay()));
    }
  }
}
