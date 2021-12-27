package com.qk.dm.reptile.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.reptile.constant.RptConstant;
import com.qk.dm.reptile.entity.QRptDimensionColumnInfo;
import com.qk.dm.reptile.entity.QRptDimensionInfo;
import com.qk.dm.reptile.entity.RptDimensionInfo;
import com.qk.dm.reptile.mapstruct.mapper.RptDimensionInfoMapper;
import com.qk.dm.reptile.params.dto.RptDimensionInfoDTO;
import com.qk.dm.reptile.params.vo.RptDimensionInfoParamsVO;
import com.qk.dm.reptile.params.vo.RptDimensionInfoVO;
import com.qk.dm.reptile.repositories.RptDimensionColumnInfoRepository;
import com.qk.dm.reptile.repositories.RptDimensionInfoRepository;
import com.qk.dm.reptile.service.RptDimensionInfoService;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 维度目录server
 * @author zys
 * @date 2021/12/8 14:39
 * @since 1.0.0
 */@Service
public class RptDimensionInfoServerImpl implements RptDimensionInfoService {
  private JPAQueryFactory jpaQueryFactory;
  private final EntityManager entityManager;
  private final QRptDimensionInfo qRptDimensionInfo = QRptDimensionInfo.rptDimensionInfo;
  private final RptDimensionInfoRepository rptDimensionInfoRepository;
  private final RptDimensionColumnInfoRepository rptDimensionColumnInfoRepository;
  private final QRptDimensionColumnInfo qRptDimensionColumnInfo = QRptDimensionColumnInfo.rptDimensionColumnInfo;

  @PostConstruct
  public void initFactory() {
    jpaQueryFactory = new JPAQueryFactory(entityManager);
  }

  public RptDimensionInfoServerImpl(EntityManager entityManager,
      RptDimensionInfoRepository rptDimensionInfoRepository,
      RptDimensionColumnInfoRepository rptDimensionColumnInfoRepository){

    this.entityManager = entityManager;
    this.rptDimensionInfoRepository = rptDimensionInfoRepository;
    this.rptDimensionColumnInfoRepository = rptDimensionColumnInfoRepository;
  }
  /**
   * 查询目录
   * @return
   */
  @Override
  public List<RptDimensionInfoVO> qyeryRptDir() {
    List<RptDimensionInfoVO> rptDimensionInfoVOList = new ArrayList<>();
    List<RptDimensionInfo> rptDimensionInfoList = rptDimensionInfoRepository.findAll();
    if (!CollectionUtils.isEmpty(rptDimensionInfoList)){
      rptDimensionInfoVOList= RptDimensionInfoMapper.INSTANCE.of(rptDimensionInfoList);
    }
    return buildByRecursive(rptDimensionInfoVOList);
  }

  private List<RptDimensionInfoVO> buildByRecursive(List<RptDimensionInfoVO> rptDimensionInfoVOList) {
    List<RptDimensionInfoVO> trees = new ArrayList<>();
      RptDimensionInfoVO rptDimensionInfoVO = RptDimensionInfoVO.builder().id(RptConstant.DIRID).dimensionName(RptConstant.DIRNAME).build();
      trees.add(findChildren(rptDimensionInfoVO, rptDimensionInfoVOList));
    return trees;
  }

  private RptDimensionInfoVO findChildren(RptDimensionInfoVO rptDimensionInfoVO, List<RptDimensionInfoVO> rptDimensionInfoVOList) {
    rptDimensionInfoVO.setChildrenList(new ArrayList<>());
    if (!CollectionUtils.isEmpty(rptDimensionInfoVOList)){
      rptDimensionInfoVOList.forEach(rptDimensionInfoVO1 -> {
        if (rptDimensionInfoVO.getId().equals(rptDimensionInfoVO1.getFid())){
          rptDimensionInfoVO.getChildrenList().add(findChildren(rptDimensionInfoVO1,rptDimensionInfoVOList));
        }
      });
    }
    return rptDimensionInfoVO;
  }

  /**
   * 新增目录
   * @param rptDimensionInfoDTO
   */
  @Override
  public void addRptDir(RptDimensionInfoDTO rptDimensionInfoDTO) {
    RptDimensionInfo rptDimensionInfo = RptDimensionInfoMapper.INSTANCE.userRptDimensionInfoDTO(rptDimensionInfoDTO);
    BooleanExpression predicate = qRptDimensionInfo.dimensionName.eq(rptDimensionInfoDTO.getDimensionName())
        .or(qRptDimensionInfo.dimensionCode.eq(rptDimensionInfo.getDimensionCode()));
    boolean exists = rptDimensionInfoRepository.exists(predicate);
    if (exists){
      throw new BizException("当前需要新增的维度目录名称为"+rptDimensionInfoDTO.getDimensionName()+"的数据已经存在");
    }
    rptDimensionInfoRepository.save(rptDimensionInfo);
  }

  /**
   * 删除目录
   * @param id
   */
  @Override
  public void deleteRptDir(Long id) {
    ArrayList<Long> ids = new ArrayList<>();
    RptDimensionInfo rptDimensionInfo = rptDimensionInfoRepository.findById(id).orElse(null);
    if (Objects.isNull(rptDimensionInfo)){
      throw new BizException("当前需删除的目录不存在");
    }
    //获取该目录下所有目录id
    ids.add(id);
    getIds(ids, id);
    if (!CollectionUtils.isEmpty(ids)){
      ids.forEach(dimensionId->{
        BooleanExpression predicate = qRptDimensionColumnInfo.dimensionId.eq(dimensionId);
        boolean exists = rptDimensionColumnInfoRepository.exists(predicate);
        if (exists){
          throw  new BizException("当前删除目录包含数据信息,请删除后再操作");
        }
      });
      rptDimensionInfoRepository.deleteAllById(ids);
    }
  }

  /**
   * 获取该目录下所有id集合
   * @param ids
   * @param id
   */
  private void getIds(ArrayList<Long> ids, Long id) {
    Iterable<RptDimensionInfo> rptDimensionInfoList = rptDimensionInfoRepository
        .findAll(qRptDimensionInfo.fid.eq(id));
    if (rptDimensionInfoList != null) {
      rptDimensionInfoList.forEach(
          rptDimensionInfo -> {
            ids.add(rptDimensionInfo.getId());
            this.getIds(ids, rptDimensionInfo.getId());
          });
    }
  }

  /**
   * 修改目录
   * @param rptDimensionInfoDTO
   */
  @Override
  public void updateRptDir(RptDimensionInfoDTO rptDimensionInfoDTO) {
    RptDimensionInfo rptDimensionInfo = rptDimensionInfoRepository.findById(rptDimensionInfoDTO.getId()).orElse(null);
    if (Objects.isNull(rptDimensionInfo)){
      throw new BizException("当前需修改的目录名称为"+rptDimensionInfoDTO.getDimensionName()+"的数据不存在");
    }
    RptDimensionInfoMapper.INSTANCE.of(rptDimensionInfoDTO, rptDimensionInfo);
    rptDimensionInfoRepository.saveAndFlush(rptDimensionInfo);
  }

  /**
   *获取所有目录名称
   * @return
   */
  @Override
  public List<RptDimensionInfoParamsVO> getDirName() {
    List<RptDimensionInfoParamsVO> rptDimensionInfoVOList = new ArrayList<>();
    List<RptDimensionInfo> rptDimensionInfoList= rptDimensionInfoRepository.findAll();
    if (!CollectionUtils.isEmpty(rptDimensionInfoList)){
      rptDimensionInfoVOList= RptDimensionInfoMapper.INSTANCE.paramsof(rptDimensionInfoList);
    }
    return rptDimensionInfoVOList;
  }
}