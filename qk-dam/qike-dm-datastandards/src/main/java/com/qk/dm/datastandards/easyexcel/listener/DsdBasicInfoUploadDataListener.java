package com.qk.dm.datastandards.easyexcel.listener;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.qk.dam.commons.exception.BizException;
import com.qk.dm.datastandards.entity.DsdBasicinfo;
import com.qk.dm.datastandards.mapstruct.mapper.DsdBasicInfoMapper;
import com.qk.dm.datastandards.service.impl.DsdExcelBatchService;
import com.qk.dm.datastandards.vo.DsdBasicInfoVO;
import java.util.*;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;

/**
 * 数据标准基础信息excel 监听器
 *
 * @author wjq
 * @date 2021/6/7
 * @since 1.0.0
 */
public class DsdBasicInfoUploadDataListener extends AnalysisEventListener<DsdBasicInfoVO> {
  private static final Log LOG = LogFactory.get("dsdBasicInfoUploadDataListener.saveData()");
  private final DsdExcelBatchService dsdExcelBatchService;
  private final String dirDsdId;

  /** 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收 */
  private static final int BATCH_COUNT = 1000;

  List<DsdBasicInfoVO> list = new ArrayList<>();

  public DsdBasicInfoUploadDataListener(
      DsdExcelBatchService dsdExcelBatchService, String dirDsdId) {
    this.dsdExcelBatchService = dsdExcelBatchService;
    this.dirDsdId = dirDsdId;
  }

  /**
   * 这个每一条数据解析都会来调用
   *
   * @param data one row value. Is is same as {@link AnalysisContext#readRowHolder()}
   * @param context
   */
  @Override
  public void invoke(DsdBasicInfoVO data, AnalysisContext context) {
    LOG.info("======开始校验excel中的标准数据!======");
    String errMsg;
    try {
      errMsg = EasyExcelValidateHelper.validateEntity(data);
    } catch (NoSuchFieldException e) {
      errMsg = "解析数据出错";
    }
    if (StringUtils.isEmpty(errMsg)) {
      list.add(data);
    } else {
      throw new BizException(errMsg);
    }
    LOG.info("======结束校验excel中的标准数据!======");
    // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
    if (list.size() >= BATCH_COUNT) {
      saveData();
      // 存储完成清理 list
      list.clear();
    }
  }

  /**
   * 所有数据解析完成了 都会来调用
   *
   * @param context
   */
  @Override
  public void doAfterAllAnalysed(AnalysisContext context) {
    // 这里也要保存数据，确保最后遗留的数据也存储到数据库
    saveData();
  }

  /** 存储数据库 */
  private void saveData() {
    LOG.info("解析excel数据标准个数 【{}】,开始导入", list.size());
    Map<String, String> codeDirLevelMap = new HashMap<>();
    Set<String> dsdDirLevelSet = new HashSet<>();
    List<DsdBasicinfo> dsdBasicInfoList = new ArrayList<>();

    if (!StringUtils.isEmpty(dirDsdId)) {
      LOG.info("======带有层级dirDsdId参数导入======");
      getBasicInfoDataAllByDsdDirId(codeDirLevelMap, dsdBasicInfoList);
      LOG.info("======成功获取到标准信息======");
      dsdExcelBatchService.saveDsdBasicInfoByDirId(dsdBasicInfoList, codeDirLevelMap, dirDsdId);
    } else {
      LOG.info("======匹配excel中维护的层级目录导入======");
      getBasicInfoDataAll(codeDirLevelMap, dsdDirLevelSet, dsdBasicInfoList);
      LOG.info("======成功获取到标准+层级信息======");
      dsdExcelBatchService.saveDsdBasicInfo(dsdBasicInfoList, dsdDirLevelSet, codeDirLevelMap);
    }
  }

  private void getBasicInfoDataAllByDsdDirId(
      Map<String, String> codeDirLevelMap, List<DsdBasicinfo> dsdBasicInfoList) {
    if (list != null && list.size() > 0) {
      list.forEach(
          dsdBasicInfoVO -> {
            DsdBasicinfo dsdBasicInfo = DsdBasicInfoMapper.INSTANCE.useDsdBasicInfo(dsdBasicInfoVO);
            codeDirLevelMap.put(dsdBasicInfo.getDsdCode(), dsdBasicInfo.getDsdLevel());
            dsdBasicInfoList.add(dsdBasicInfo);
          });
    }
  }

  private void getBasicInfoDataAll(
      Map<String, String> codeDirLevelMap,
      Set<String> dsdDirLevelSet,
      List<DsdBasicinfo> dsdBasicInfoList) {
    if (list != null && list.size() > 0) {
      list.forEach(
          dsdBasicInfoVO -> {
            DsdBasicinfo dsdBasicInfo = DsdBasicInfoMapper.INSTANCE.useDsdBasicInfo(dsdBasicInfoVO);
            dsdDirLevelSet.add(dsdBasicInfo.getDsdLevel());
            codeDirLevelMap.put(dsdBasicInfo.getDsdCode(), dsdBasicInfo.getDsdLevel());
            dsdBasicInfoList.add(dsdBasicInfo);
          });
    }
  }
}
