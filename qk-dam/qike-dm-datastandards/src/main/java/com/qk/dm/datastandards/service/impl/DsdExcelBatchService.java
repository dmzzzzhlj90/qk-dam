package com.qk.dm.datastandards.service.impl;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.google.common.collect.Maps;
import com.qk.dam.commons.exception.BizException;
import com.qk.dm.datastandards.entity.*;
import com.qk.dm.datastandards.repositories.*;
import com.querydsl.core.types.Predicate;
import java.util.*;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 数据标准excel 批量导入导出
 *
 * @author wjq
 * @date 2021/6/20 16:10
 * @since 1.0.0
 */
@Service
public class DsdExcelBatchService {
  private static final Log LOG = LogFactory.get("dsdExcelBatchService");
  @PersistenceContext private EntityManager entityManager;

  private final DsdBasicinfoRepository dsdBasicinfoRepository;
  private final DsdDirRepository dsdDirRepository;
  private final DsdCodeInfoExtRepository dsdCodeInfoExtRepository;
  private final DsdCodeInfoRepository dsdCodeInfoRepository;
  private final DsdCodeDirRepository dsdCodeDirRepository;

  @Autowired
  public DsdExcelBatchService(
      DsdBasicinfoRepository dsdBasicinfoRepository,
      DsdDirRepository dsdDirRepository,
      DsdCodeInfoExtRepository dsdCodeInfoExtRepository,
      DsdCodeInfoRepository dsdCodeInfoRepository,
      DsdCodeDirRepository dsdCodeDirRepository) {
    this.dsdBasicinfoRepository = dsdBasicinfoRepository;
    this.dsdDirRepository = dsdDirRepository;
    this.dsdCodeInfoExtRepository = dsdCodeInfoExtRepository;
    this.dsdCodeInfoRepository = dsdCodeInfoRepository;
    this.dsdCodeDirRepository = dsdCodeDirRepository;
  }

  @Transactional(rollbackFor = Exception.class)
  public void saveDsdBasicInfo(
      List<DsdBasicinfo> dsdBasicInfoList,
      Set<String> dsdDirLevelSet,
      Map<String, String> codeDirLevelMap) {
    List<DsdDir> dsdDirAllList = dsdDirRepository.findAll();
    List<String> dsdDirLevels =
        dsdDirAllList.stream().map(DsdDir::getDsdDirLevel).collect(Collectors.toList());

    dsdDirLevelSet.forEach(
        dirDsdLevel -> {
          if (!dsdDirLevels.contains(dirDsdLevel)) {
            throw new BizException("Excel中输入的标准目录层级:" + dirDsdLevel + ",参数有误!!!");
          }
        });

    Map<String, List<DsdDir>> dsdDirLevelMap =
        dsdDirAllList.stream().collect(Collectors.groupingBy(DsdDir::getDsdDirLevel));
    Predicate existDataPredicate =
        QDsdBasicinfo.dsdBasicinfo
            .dsdLevel
            .in(dsdDirLevelSet)
            .and(QDsdBasicinfo.dsdBasicinfo.dsdCode.in(codeDirLevelMap.keySet()));
    Iterable<DsdBasicinfo> existList = dsdBasicinfoRepository.findAll(existDataPredicate);
    dsdBasicinfoRepository.deleteInBatch(existList);
    LOG.info("======成功清除标准信息======");

    for (DsdBasicinfo dsdBasicinfo : dsdBasicInfoList) {
      List<DsdDir> dsdDirList = dsdDirLevelMap.get(dsdBasicinfo.getDsdLevel());
      dsdBasicinfo.setDsdLevelId(dsdDirList.get(0).getDirDsdId());
      dsdBasicinfo.setDsdLevel(dsdDirList.get(0).getDsdDirLevel());
      dsdBasicinfo.setGmtCreate(new Date());
      dsdBasicinfo.setGmtModified(new Date());
      dsdBasicinfo.setDelFlag(0);
      entityManager.persist(dsdBasicinfo); // insert插入操作
    }
    entityManager.flush();
    entityManager.clear();
    LOG.info("成功保存标准信息个数 【{}】", dsdBasicInfoList.size());
  }

  @Transactional(rollbackFor = Exception.class)
  public void saveDsdBasicInfoByDirId(
      List<DsdBasicinfo> dsdBasicInfoList, Map<String, String> codeDirLevelMap, String dirDsdId) {
    Optional<DsdDir> dsdDir = dsdDirRepository.findOne(QDsdDir.dsdDir.dirDsdId.eq(dirDsdId));
    if (dsdDir.isPresent()) {
      LOG.info("标准层级目录【{}】", dsdDir.get().getDsdDirLevel());
      Predicate existDataPredicate =
          QDsdBasicinfo.dsdBasicinfo
              .dsdLevelId
              .eq(dirDsdId)
              .and(QDsdBasicinfo.dsdBasicinfo.dsdCode.in(codeDirLevelMap.keySet()));
      Iterable<DsdBasicinfo> existList = dsdBasicinfoRepository.findAll(existDataPredicate);
      dsdBasicinfoRepository.deleteInBatch(existList);
      LOG.info("======成功清除标准信息======");

      for (DsdBasicinfo dsdBasicinfo : dsdBasicInfoList) {
        dsdBasicinfo.setDsdLevelId(dsdDir.get().getDirDsdId());
        dsdBasicinfo.setDsdLevel(dsdDir.get().getDsdDirLevel());
        dsdBasicinfo.setGmtCreate(new Date());
        dsdBasicinfo.setGmtModified(new Date());
        dsdBasicinfo.setDelFlag(0);
        entityManager.persist(dsdBasicinfo); // insert插入操作
      }
    } else {
      throw new BizException("码表目录,参数有误!!!");
    }
    entityManager.flush();
    entityManager.clear();
    LOG.info("成功保存标准信息个数 【{}】", dsdBasicInfoList.size());
  }

  @Transactional(rollbackFor = Exception.class)
  public void addDsdCodeValuesBatch(List<DsdCodeInfoExt> saveDataList, long dsdCodeInfoId) {
    dsdCodeInfoExtRepository.deleteByDsdCodeInfoId(dsdCodeInfoId);
    for (int i = 0; i < saveDataList.size(); i++) {
      entityManager.persist(saveDataList.get(i));
    }
    entityManager.flush();
    entityManager.clear();
  }

  @Transactional(rollbackFor = Exception.class)
  public void saveCodeInfosByCodeDirId(List<DsdCodeInfo> dataList, Set<String> tableCodeSet, DsdCodeDir dsdCodeDir) {
    Iterable<DsdCodeInfo> existList = dsdCodeInfoRepository.findAll(QDsdCodeInfo.dsdCodeInfo.tableCode.in(tableCodeSet));
    HashMap<String, Long> primaryIDMap = Maps.newHashMap();
    for (DsdCodeInfo dsdCodeInfo : existList) {
      primaryIDMap.put(dsdCodeInfo.getTableCode(), dsdCodeInfo.getId());
    }
    Set<String> primaryIDKeySet = primaryIDMap.keySet();
    for (DsdCodeInfo dsdCodeInfo : dataList) {
      dsdCodeInfo.setCodeDirId(dsdCodeDir.getCodeDirId());
      dsdCodeInfo.setCodeDirLevel(dsdCodeDir.getCodeDirLevel());
      dsdCodeInfo.setGmtCreate(new Date());
      dsdCodeInfo.setGmtModified(new Date());
      dsdCodeInfo.setDelFlag(0);
      if (primaryIDKeySet.contains(dsdCodeInfo.getTableCode())) {
        dsdCodeInfo.setId(primaryIDMap.get(dsdCodeInfo.getTableCode()));
        entityManager.merge(dsdCodeInfo); // update 更新操作
      } else {
        entityManager.persist(dsdCodeInfo); // insert 更新操作
      }
    }
    entityManager.flush();
    entityManager.clear();
  }

  @Transactional(rollbackFor = Exception.class)
  public void saveCodeInfosAll(
      List<DsdCodeInfo> dataList, Set<String> tableCodeSet, Set<String> codeDirLevelSet) {
    List<DsdCodeDir> codeDirAllList = dsdCodeDirRepository.findAll();
    List<String> codeDirLevels =
        codeDirAllList.stream().map(DsdCodeDir::getCodeDirLevel).collect(Collectors.toList());

    codeDirLevelSet.forEach(
        codeDirLevel -> {
          if (!codeDirLevels.contains(codeDirLevel))
            throw new BizException("Excel中输入的码表目录层级:" + codeDirLevel + ",参数有误!!!");
        });

    Map<String, List<DsdCodeDir>> codeDirLevelMap =
        codeDirAllList.stream().collect(Collectors.groupingBy(DsdCodeDir::getCodeDirLevel));
    Iterable<DsdCodeInfo> existList = dsdCodeInfoRepository.findAll(QDsdCodeInfo.dsdCodeInfo.tableCode.in(tableCodeSet));
    HashMap<String, Long> primaryIDMap = Maps.newHashMap();
    for (DsdCodeInfo dsdCodeInfo : existList) {
      primaryIDMap.put(dsdCodeInfo.getTableCode(), dsdCodeInfo.getId());
    }
    Set<String> primaryIDKeySet = primaryIDMap.keySet();

    for (DsdCodeInfo dsdCodeInfo : dataList) {
      List<DsdCodeDir> codeDirList = codeDirLevelMap.get(dsdCodeInfo.getCodeDirLevel());
      dsdCodeInfo.setCodeDirId(codeDirList.get(0).getCodeDirId());
      dsdCodeInfo.setCodeDirLevel(codeDirList.get(0).getCodeDirLevel());
      dsdCodeInfo.setGmtCreate(new Date());
      dsdCodeInfo.setGmtModified(new Date());
      dsdCodeInfo.setDelFlag(0);
      if (primaryIDKeySet.contains(dsdCodeInfo.getTableCode())) {
        dsdCodeInfo.setId(primaryIDMap.get(dsdCodeInfo.getTableCode()));
        entityManager.merge(dsdCodeInfo); // update 更新操作
      } else {
        entityManager.persist(dsdCodeInfo); // insert 更新操作
      }
    }
    entityManager.flush();
    entityManager.clear();
  }

  public DsdCodeDir getCodeDir(String codeDirId) {
    Optional<DsdCodeDir> dsdCodeDirOptional =
        dsdCodeDirRepository.findOne(QDsdCodeDir.dsdCodeDir.codeDirId.eq(codeDirId));
    if (dsdCodeDirOptional.isEmpty()) {
      throw new BizException("码表层级目录不存在!!!");
    }
    return dsdCodeDirOptional.get();
  }

  //    @Transactional(rollbackFor = Exception.class)
  //    public void updateBatch(List<DsdCodeTerm> codeTermList, Set<String> codeSet, Set<String>
  // dirSet) {
  //        List<DsdCodeTerm> dsdCodeTermAll = dsdCodeTermRepository.findAllByDirAndCodeId(codeSet,
  // dirSet);
  //        dsdCodeTermRepository.deleteInBatch(dsdCodeTermAll);
  //
  //        for (DsdCodeTerm dsdCodeTerm : codeTermList) {
  //            entityManager.merge(dsdCodeTerm);//update插入操作
  //        }
  //        entityManager.flush();
  //        entityManager.clear();
  //    }
}
