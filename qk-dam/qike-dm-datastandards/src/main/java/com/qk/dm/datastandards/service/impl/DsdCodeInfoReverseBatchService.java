package com.qk.dm.datastandards.service.impl;

import com.google.common.collect.Maps;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dm.datastandards.constant.DsdConstant;
import com.qk.dm.datastandards.datasource_connect.extractor.DataSourceExtractor;
import com.qk.dm.datastandards.datasource_connect.pojo.MysqlColumn;
import com.qk.dm.datastandards.datasource_connect.pojo.MysqlDb;
import com.qk.dm.datastandards.datasource_connect.pojo.MysqlTable;
import com.qk.dm.datastandards.entity.DsdCodeInfo;
import com.qk.dm.datastandards.entity.DsdCodeInfoExt;
import com.qk.dm.datastandards.entity.QDsdCodeInfo;
import com.qk.dm.datastandards.repositories.DsdCodeDirRepository;
import com.qk.dm.datastandards.repositories.DsdCodeInfoExtRepository;
import com.qk.dm.datastandards.repositories.DsdCodeInfoRepository;
import com.qk.dm.datastandards.vo.DsdCodeInfoReverseDBVO;
import com.querydsl.core.types.Predicate;
import java.util.*;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 码表信息&&码值 数据逆向生成批处理
 *
 * @author wjq
 * @date 20210807
 * @since 1.0.0
 */
@Service
public class DsdCodeInfoReverseBatchService {
  private final QDsdCodeInfo qDsdCodeInfo = QDsdCodeInfo.dsdCodeInfo;
  @PersistenceContext private EntityManager entityManager;

  private final DsdCodeInfoExtRepository dsdCodeInfoExtRepository;
  private final DsdCodeInfoRepository dsdCodeInfoRepository;
  private final DsdCodeDirRepository dsdCodeDirRepository;

  @Autowired
  public DsdCodeInfoReverseBatchService(
      DsdCodeInfoExtRepository dsdCodeInfoExtRepository,
      DsdCodeInfoRepository dsdCodeInfoRepository,
      DsdCodeDirRepository dsdCodeDirRepository) {
    this.dsdCodeInfoExtRepository = dsdCodeInfoExtRepository;
    this.dsdCodeInfoRepository = dsdCodeInfoRepository;
    this.dsdCodeDirRepository = dsdCodeDirRepository;
  }

  @Transactional(rollbackFor = Exception.class)
  public void reverseCreateCodeInfo(
      DsdCodeInfoReverseDBVO dsdCodeInfoReverseDBVO, MysqlDb mysqlDb) {
    String codeDirId = dsdCodeInfoReverseDBVO.getCodeDirId();
    String codeDirLevel = dsdCodeInfoReverseDBVO.getCodeDirLevel();
    String isUpdate = dsdCodeInfoReverseDBVO.getIsUpdate();

    List<MysqlTable> mysqlTables = mysqlDb.getMysqlTables();
    List<DsdCodeInfo> codeInfoList =
        mysqlTables.stream()
            .map(
                mysqlTable -> {
                  DsdCodeInfo dsdCodeInfo = new DsdCodeInfo();
                  dsdCodeInfo.setCodeDirId(codeDirId);
                  dsdCodeInfo.setCodeDirLevel(codeDirLevel);
                  dsdCodeInfo.setTableCode(mysqlTable.getName());
                  dsdCodeInfo.setTableName(mysqlTable.getComment());
                  dsdCodeInfo.setTableDesc(
                      "逆向生成，表名称:" + mysqlTable.getName() + "_" + mysqlTable.getComment());
                  dsdCodeInfo.setTableConfFields(setCodeTableFieldsByMetaData(mysqlTable));
                  dsdCodeInfo.setGmtModified(new Date());
                  dsdCodeInfo.setGmtCreate(new Date());
                  return dsdCodeInfo;
                })
            .collect(Collectors.toList());
    bulkSaveCodeInfoReverseData(codeInfoList, codeDirId, isUpdate);
  }

  private void bulkSaveCodeInfoReverseData(
      List<DsdCodeInfo> codeInfoList, String codeDirId, String isUpdate) {
    // 更新已有表: 0 :不更新, 1: 更新
    if (Integer.parseInt(isUpdate) == DsdConstant.CODE_INFO_INSERT) {
      bulkInsertCodeInfoData(codeInfoList, codeDirId);
    } else {
      bulkUpdateCodeInfoData(codeInfoList, codeDirId);
    }
    entityManager.flush();
    entityManager.clear();
  }

  private void bulkInsertCodeInfoData(List<DsdCodeInfo> codeInfoList, String codeDirId) {
    // 清除已存在的码表信息列表
    List<String> tableCodeList =
        codeInfoList.stream().map(DsdCodeInfo::getTableCode).collect(Collectors.toList());
    Predicate predicate =
        qDsdCodeInfo.codeDirId.eq(codeDirId).and(qDsdCodeInfo.tableCode.in(tableCodeList));
    Iterable<DsdCodeInfo> existDsdCodeInfos = dsdCodeInfoRepository.findAll(predicate);
    dsdCodeInfoRepository.deleteAll(existDsdCodeInfos);
    // 由于码表新建,需要清除码值
    Set<Long> codeInfoIds = new HashSet<>();
    for (DsdCodeInfo dsdCodeInfo : existDsdCodeInfos) {
      codeInfoIds.add(dsdCodeInfo.getId());
    }
    dsdCodeInfoExtRepository.deleteByDsdCodeInfoIdBatch(codeInfoIds);
    // 新增码表信息
    for (DsdCodeInfo dsdCodeInfo : codeInfoList) {
      entityManager.persist(dsdCodeInfo); // insert 更新操作
    }
  }

  private void bulkUpdateCodeInfoData(List<DsdCodeInfo> codeInfoList, String codeDirId) {
    List<String> tableCodeList =
        codeInfoList.stream().map(DsdCodeInfo::getTableCode).collect(Collectors.toList());
    Predicate predicate =
        qDsdCodeInfo.codeDirId.eq(codeDirId).and(qDsdCodeInfo.tableCode.in(tableCodeList));
    Iterable<DsdCodeInfo> existDsdCodeInfos = dsdCodeInfoRepository.findAll(predicate);

    HashMap<String, Long> primaryIDMap = Maps.newHashMap();
    for (DsdCodeInfo dsdCodeInfo : existDsdCodeInfos) {
      primaryIDMap.put(
          dsdCodeInfo.getCodeDirLevel() + "_" + dsdCodeInfo.getTableCode(), dsdCodeInfo.getId());
    }
    Set<String> primaryIDKeySet = primaryIDMap.keySet();
    for (DsdCodeInfo dsdCodeInfo : codeInfoList) {
      if (primaryIDKeySet.contains(
          dsdCodeInfo.getCodeDirLevel() + "_" + dsdCodeInfo.getTableCode())) {
        dsdCodeInfo.setId(
            primaryIDMap.get(dsdCodeInfo.getCodeDirLevel() + "_" + dsdCodeInfo.getTableCode()));
        entityManager.merge(dsdCodeInfo); // update 更新操作
      } else {
        entityManager.persist(dsdCodeInfo); // insert 更新操作
      }
    }
  }

  @Transactional(rollbackFor = Exception.class)
  public void reverseCreateCodeValues(
      DsdCodeInfoReverseDBVO dsdCodeInfoReverseDBVO, MysqlDb mysqlDb) {
    String isReverseData = dsdCodeInfoReverseDBVO.getIsReverseData();
    // 逆向表数据: 0 :不逆向, 1: 覆盖
    if (Integer.parseInt(isReverseData) == DsdConstant.CODE_INFO_VALUES_UPDATE) {
      List<DsdCodeInfoExt> dsdCodeInfoExtList = new ArrayList<>();
      Set<Long> codeInfoIds = new HashSet<>();
      for (MysqlTable mysqlTable : mysqlDb.getMysqlTables()) {
        Predicate predicate =
            qDsdCodeInfo
                .codeDirId
                .eq(dsdCodeInfoReverseDBVO.getCodeDirId())
                .and(qDsdCodeInfo.tableCode.eq(mysqlTable.getName()));
        Optional<DsdCodeInfo> dsdCodeInfo = dsdCodeInfoRepository.findOne(predicate);
        if (dsdCodeInfo.isPresent()) {
          Long codeInfoId = dsdCodeInfo.get().getId();
          codeInfoIds.add(codeInfoId);
          List<DsdCodeInfoExt> codeInfoExtValues =
              DataSourceExtractor.searchCodeInfoExtValues(
                  dsdCodeInfoReverseDBVO.getDataSourceJobVO(), mysqlTable, codeInfoId);
          dsdCodeInfoExtList.addAll(codeInfoExtValues);
        } else {
          throw new BizException(
              "逆向同步码值时,未匹配到层级为:"
                  + dsdCodeInfoReverseDBVO.getCodeDirLevel()
                  + " 表编码为:"
                  + mysqlTable.getName()
                  + ",所对应的码表信息!!!");
        }
      }
      bulkSaveCodeValuesReverseData(dsdCodeInfoExtList, codeInfoIds, isReverseData);
    }
  }

  private void bulkSaveCodeValuesReverseData(
      List<DsdCodeInfoExt> dsdCodeInfoExtList, Set<Long> codeInfoIds, String isReverseData) {
    dsdCodeInfoExtRepository.deleteByDsdCodeInfoIdBatch(codeInfoIds);
    for (int i = 0; i < dsdCodeInfoExtList.size(); i++) {
      entityManager.persist(dsdCodeInfoExtList.get(i));
    }
    entityManager.flush();
    entityManager.clear();
  }

  private String setCodeTableFieldsByMetaData(MysqlTable mysqlTable) {
    List<Map<String, String>> fieldList = new ArrayList();
//    setDefaultConfigCodeAndValue(fieldList);
    for (MysqlColumn mysqlColumn : mysqlTable.getMysqlColumns()) {
      if (filterField(mysqlColumn)) {
        Map<String, String> fieldMap = new HashMap<>();
        fieldMap.put(DsdConstant.CODE_INFO_TABLE_ID, mysqlColumn.getColName());
        fieldMap.put(DsdConstant.CODE_INFO_NAME_CH, mysqlColumn.getDisplayName());
        fieldMap.put(DsdConstant.CODE_INFO_NAME_EN, mysqlColumn.getColName());
        fieldMap.put(DsdConstant.CODE_INFO_DATA_TYPE, mysqlColumn.getData_type());
        fieldList.add(fieldMap);
      }
    }
    return GsonUtil.toJsonString(fieldList);
  }

  private boolean filterField(MysqlColumn mysqlColumn) {
    boolean flag = true;
    if (DsdConstant.CODE_INFO_FILTER_ID.equalsIgnoreCase(mysqlColumn.getColName())) flag = false;
//    if (DsdConstant.CODE_INFO_CODE_EN_NAME.equalsIgnoreCase(mysqlColumn.getColName())) flag = false;
//    if (DsdConstant.CODE_INFO_VALUE_EN_NAME.equalsIgnoreCase(mysqlColumn.getColName())) flag = false;
    return flag;
  }

  private void setDefaultConfigCodeAndValue(List<Map<String, String>> fieldList) {
    Map<String, String> configFieldCodedMap = new LinkedHashMap<>();
    configFieldCodedMap.put(DsdConstant.CODE_INFO_TABLE_ID, DsdConstant.CODE_INFO_CODE_EN_NAME);
    configFieldCodedMap.put(DsdConstant.CODE_INFO_NAME_CH, DsdConstant.CODE_INFO_CODE_CH_NAME);
    configFieldCodedMap.put(DsdConstant.CODE_INFO_NAME_EN, DsdConstant.CODE_INFO_CODE_EN_NAME);
    configFieldCodedMap.put(DsdConstant.CODE_INFO_DATA_TYPE, DsdConstant.CODE_INFO_CODE_TYPE);
    fieldList.add(configFieldCodedMap);

    Map<String, String> configFieldValueMap = new LinkedHashMap<>();
    configFieldValueMap.put(DsdConstant.CODE_INFO_TABLE_ID, DsdConstant.CODE_INFO_VALUE_EN_NAME);
    configFieldValueMap.put(DsdConstant.CODE_INFO_NAME_CH, DsdConstant.CODE_INFO_VALUE_CH_NAME);
    configFieldValueMap.put(DsdConstant.CODE_INFO_NAME_EN, DsdConstant.CODE_INFO_VALUE_EN_NAME);
    configFieldValueMap.put(DsdConstant.CODE_INFO_DATA_TYPE, DsdConstant.CODE_INFO_VALUE_TYPE);
    fieldList.add(configFieldValueMap);
  }
}
