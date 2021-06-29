package com.qk.dm.datastandards.easyexcel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.qk.dm.datastandards.entity.DsdBasicinfo;
import com.qk.dm.datastandards.mapstruct.mapper.DsdBasicInfoMapper;
import com.qk.dm.datastandards.service.impl.DsdExcelBatchService;
import com.qk.dm.datastandards.vo.DsdBasicinfoVO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 数据标准基础信息excel 监听器
 *
 * @author wjq
 * @date 2021/6/7
 * @since 1.0.0
 */
public class DsdBasicInfoUploadDataListener extends AnalysisEventListener<DsdBasicinfoVO> {
  private final DsdExcelBatchService dsdExcelBatchService;

  /** 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收 */
  private static final int BATCH_COUNT = 1000;

  List<DsdBasicinfoVO> list = new ArrayList<DsdBasicinfoVO>();

  public DsdBasicInfoUploadDataListener(DsdExcelBatchService dsdExcelBatchService) {
    this.dsdExcelBatchService = dsdExcelBatchService;
  }

  /**
   * 这个每一条数据解析都会来调用
   *
   * @param data one row value. Is is same as {@link AnalysisContext#readRowHolder()}
   * @param context
   */
  @Override
  public void invoke(DsdBasicinfoVO data, AnalysisContext context) {
    list.add(data);
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

  /** 加上存储数据库 */
  private void saveData() {
    Set<String> codeSet = new HashSet<>();
    Set<String> nameSet = new HashSet<>();

    List<DsdBasicinfo> dsdBasicInfoList = new ArrayList<>();
    list.forEach(
        dsdBasicInfoVO -> {
          DsdBasicinfo dsdBasicInfo = DsdBasicInfoMapper.INSTANCE.useDsdBasicInfo(dsdBasicInfoVO);
          codeSet.add(dsdBasicInfo.getDsdCode());
          nameSet.add(dsdBasicInfo.getColName());
          dsdBasicInfoList.add(dsdBasicInfo);
        });
    // 批量新增
    dsdExcelBatchService.addDsdBasicInfoBatch(dsdBasicInfoList, codeSet, nameSet);
  }
}
