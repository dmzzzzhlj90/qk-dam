package com.qk.dm.reptile.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.reptile.entity.QRptDimensionColumnInfo;
import com.qk.dm.reptile.entity.QRptDimensionInfo;
import com.qk.dm.reptile.entity.RptDimensionColumnInfo;
import com.qk.dm.reptile.mapstruct.mapper.RptDimensionInfoColumnMapper;
import com.qk.dm.reptile.params.dto.RptDimensionInfoColumnDTO;
import com.qk.dm.reptile.params.dto.RptDimensionInfoColumnParamDTO;
import com.qk.dm.reptile.params.vo.RptDimensionInfoColumnVO;
import com.qk.dm.reptile.repositories.RptDimensionColumnInfoRepository;
import com.qk.dm.reptile.repositories.RptDimensionInfoRepository;
import com.qk.dm.reptile.service.RptDimensionInfoColumnService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 维度信息server
 * @author zys
 * @date 2021/12/8 14:55
 * @since 1.0.0
 */
@Service
public class RptDimensionInfoColumnServiceImpl implements
    RptDimensionInfoColumnService {
  private JPAQueryFactory jpaQueryFactory;
  private final EntityManager entityManager;
  private final QRptDimensionColumnInfo qRptDimensionColumnInfo = QRptDimensionColumnInfo.rptDimensionColumnInfo;
  private final QRptDimensionInfo qRptDimensionInfo = QRptDimensionInfo.rptDimensionInfo;
  private final RptDimensionColumnInfoRepository rptDimensionColumnInfoRepository;
  private final RptDimensionInfoRepository rptDimensionInfoRepository;


  @PostConstruct
  public void initFactory() {
    jpaQueryFactory = new JPAQueryFactory(entityManager);
  }

  public RptDimensionInfoColumnServiceImpl(EntityManager entityManager,
      RptDimensionColumnInfoRepository rptDimensionColumnInfoRepository,
      RptDimensionInfoRepository rptDimensionInfoRepository){

    this.entityManager = entityManager;
    this.rptDimensionColumnInfoRepository = rptDimensionColumnInfoRepository;
    this.rptDimensionInfoRepository = rptDimensionInfoRepository;
  }
  /**
   * 新增维度数据
   * @param rptDimensionInfoColumnDTO
   */
  @Override
  public void addRptDimensionInfoColumn(RptDimensionInfoColumnDTO rptDimensionInfoColumnDTO) {
    BooleanExpression predicate = qRptDimensionColumnInfo.dimensionId.eq(rptDimensionInfoColumnDTO.getDimensionId()).and(qRptDimensionColumnInfo.dimensionColumnName.eq(rptDimensionInfoColumnDTO.getDimensionColumnName()));
    boolean exists = rptDimensionColumnInfoRepository.exists(predicate);
    if (exists){
      throw new BizException("当前新增数据已经存在请查证");
    }
    RptDimensionColumnInfo rptDimensionColumnInfo = RptDimensionInfoColumnMapper.INSTANCE
        .userRptDimensionInfoColumnDTO(rptDimensionInfoColumnDTO);
    rptDimensionColumnInfoRepository.save(rptDimensionColumnInfo);
  }

  /**
   * 删除维度数据
   * @param ids
   */
  @Override
  public void deleteRptDimensionInfoColumn(List<Long> ids) {
    List<RptDimensionColumnInfo> rptDimensionColumnInfoList = rptDimensionColumnInfoRepository.findAllById(ids);
    if (rptDimensionColumnInfoList.isEmpty()){
      throw new BizException("当前需要删除的数据不存在");
    }
    rptDimensionColumnInfoRepository.deleteAllById(ids);
  }

  /**
   * 修改维度数据
   * @param rptDimensionInfoColumnDTO
   */
  @Override
  public void updateRptDimensionInfoColumn(
      RptDimensionInfoColumnDTO rptDimensionInfoColumnDTO) {
    RptDimensionColumnInfo rptDimensionColumnInfo = rptDimensionColumnInfoRepository
        .findById(rptDimensionInfoColumnDTO.getId()).orElse(null);
    if (Objects.isNull(rptDimensionColumnInfo)){
      throw new BizException("当前需修改的维度字段名称为"+rptDimensionColumnInfo.getDimensionColumnName()+"的数据不存在");
    }
    RptDimensionInfoColumnMapper.INSTANCE.of(rptDimensionInfoColumnDTO, rptDimensionColumnInfo);
    rptDimensionColumnInfoRepository.saveAndFlush(rptDimensionColumnInfo);
  }

  /**
   * 根据目录id和条件分页查询维度字段信息
   * @param rptDimensionInfoColumnParamDTO
   * @return
   */
  @Override
  public PageResultVO<RptDimensionInfoColumnVO> qyeryRptDimensionInfoColumn(
      RptDimensionInfoColumnParamDTO rptDimensionInfoColumnParamDTO) {
    Map<String, Object> map;
    try {
      map = queryByParams(rptDimensionInfoColumnParamDTO);
    } catch (Exception e) {
      e.printStackTrace();
      throw new BizException("查询失败!!!");
    }
    List<RptDimensionColumnInfo> list = (List<RptDimensionColumnInfo>) map.get("list");
    List<RptDimensionInfoColumnVO> voList = RptDimensionInfoColumnMapper.INSTANCE.of(list);
    return new PageResultVO<>(
        (long) map.get("total"),
        rptDimensionInfoColumnParamDTO.getPagination().getPage(),
        rptDimensionInfoColumnParamDTO.getPagination().getSize(),
        voList);
  }

  /**
   * 根据目录id返回字段信息
   * @param id
   * @return
   */
  @Override
  public Map<String,String> queryColumnByDirName(Long id) {
    BooleanExpression eq = qRptDimensionColumnInfo.dimensionId.eq(id);
    Iterable<RptDimensionColumnInfo> rptDimensionColumnInfoList = rptDimensionColumnInfoRepository.findAll(eq);
    Map<String,String> map  = new HashMap<>();
    rptDimensionColumnInfoList.forEach(rptDimensionColumnInfo -> {
      map.put(rptDimensionColumnInfo.getDimensionColumnName(),rptDimensionColumnInfo.getDimensionColumnCode());
    });
    return map;
  }

  private Map<String,Object> queryByParams(RptDimensionInfoColumnParamDTO rptDimensionInfoColumnParamDTO) {
      BooleanBuilder booleanBuilder = new BooleanBuilder();
      checkCondition(booleanBuilder, rptDimensionInfoColumnParamDTO);
      Map<String, Object> result = new HashMap<>();
      long count = jpaQueryFactory.select(qRptDimensionColumnInfo.count()).from(qRptDimensionColumnInfo).where(booleanBuilder).fetchOne();
      List<RptDimensionColumnInfo> rptDimensionColumnInfoList = jpaQueryFactory
        .select(qRptDimensionColumnInfo).from(qRptDimensionColumnInfo)
        .where(booleanBuilder).orderBy(qRptDimensionColumnInfo.id.asc()).offset(
            (long) (rptDimensionInfoColumnParamDTO.getPagination().getPage()
                - 1) * rptDimensionInfoColumnParamDTO.getPagination().getSize())
        .limit(rptDimensionInfoColumnParamDTO.getPagination().getSize())
        .fetch();
      result.put("list", rptDimensionColumnInfoList);
      result.put("total", count);
      return result;
  }

  /**
   * 构建查询条件
   * @param booleanBuilder
   * @param rptDimensionInfoColumnParamDTO
   */
  private void checkCondition(BooleanBuilder booleanBuilder, RptDimensionInfoColumnParamDTO rptDimensionInfoColumnParamDTO) {
    if (!StringUtils.isEmpty(rptDimensionInfoColumnParamDTO.getCreateUsername())) {
      booleanBuilder.and(qRptDimensionColumnInfo.createUsername.contains(rptDimensionInfoColumnParamDTO.getCreateUsername()));
    }
    if (!StringUtils.isEmpty(rptDimensionInfoColumnParamDTO.getDimensionColumnCode())) {
      booleanBuilder.and(qRptDimensionColumnInfo.dimensionColumnCode.contains(rptDimensionInfoColumnParamDTO.getDimensionColumnCode()));
    }
    if (!StringUtils.isEmpty(rptDimensionInfoColumnParamDTO.getDimensionColumnName())) {
      booleanBuilder.and(qRptDimensionColumnInfo.dimensionColumnName.contains(rptDimensionInfoColumnParamDTO.getDimensionColumnName()));
    }
    if (!Objects.isNull(rptDimensionInfoColumnParamDTO.getId())){
      booleanBuilder.and(qRptDimensionColumnInfo.dimensionId.eq(rptDimensionInfoColumnParamDTO.getId()));
    }
  }
}