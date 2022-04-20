package com.qk.dm.authority.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.qk.dm.authority.service.impl.ResourceExcelBatchService;
import com.qk.dm.authority.vo.powervo.ResourceMenuExcelVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限管理资源导入监听
 * @author zys
 * @date 2022/3/1 18:15
 * @since 1.0.0
 */
@Component
public class RsUploadDataListener extends AnalysisEventListener<ResourceMenuExcelVO> {
  private static final Log LOG = LogFactory.getLog("ResourceBasicInfoUploadDataListener");
  private final ResourceExcelBatchService resourceExcelBatchService;
  /** 每隔1000条存储数据库，然后清理list ，方便内存回收 */
  private static final int BATCH_COUNT = 1000;

  List<ResourceMenuExcelVO> list = new ArrayList<>();
  List<ResourceMenuExcelVO> returnList = new ArrayList<>();

  public RsUploadDataListener(
      ResourceExcelBatchService resourceExcelBatchService) {
    this.resourceExcelBatchService = resourceExcelBatchService;
  }
  /**
   * 这个每一条数据解析都会来调用
   *
   * @param data one row value. Is is same as {@link AnalysisContext#readRowHolder()}
   * @param context
   */
  @Override
  public void invoke(ResourceMenuExcelVO data, AnalysisContext context) {
      list.add(data);
    // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
    if (list.size() >= BATCH_COUNT) {
      List<ResourceMenuExcelVO> rptBaseInfos = saveData();
      returnList.addAll(rptBaseInfos);
      // 存储完成清理 list
      list.clear();
    }
  }

  /**
   * 所有数据解析完成了都会来调用
   *
   * @param context
   */
  @Override
  public void doAfterAllAnalysed(AnalysisContext context) {
    // 这里也要保存数据，确保最后遗留的数据也存储到数据库
    List<ResourceMenuExcelVO> rptBaseInfos = saveData();
    returnList.addAll(rptBaseInfos);
  }

  private List<ResourceMenuExcelVO> saveData() {
    return  resourceExcelBatchService.saveResources(list);
  }

  public List<ResourceMenuExcelVO> getList(){
    return returnList;
  }
}