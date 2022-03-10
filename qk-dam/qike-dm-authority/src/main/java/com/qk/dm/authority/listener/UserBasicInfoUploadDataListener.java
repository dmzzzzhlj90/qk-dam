package com.qk.dm.authority.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.qk.dam.authority.common.vo.user.AtyUserInputExceVO;
import com.qk.dm.authority.service.impl.UserExcelBatchService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户列表导入监听
 * @author zys
 * @date 2022/3/4 15:46
 * @since 1.0.0
 */
@Component
public class UserBasicInfoUploadDataListener extends AnalysisEventListener<AtyUserInputExceVO> {
  private static final Log LOG = LogFactory.getLog("ResourceBasicInfoUploadDataListener");
  private final UserExcelBatchService userExcelBatchService;
  private String relame;

  /** 每隔1000条存储数据库，然后清理list ，方便内存回收 */
  private static final int BATCH_COUNT = 1000;

  public void setRelame(String relame) {
    this.relame = relame;
  }

  public String getRelame() {
    return relame;
  }

  List<AtyUserInputExceVO> list = new ArrayList<>();
  List<AtyUserInputExceVO> returnList = new ArrayList<>();


  public UserBasicInfoUploadDataListener(
      UserExcelBatchService userExcelBatchService) {
    this.userExcelBatchService = userExcelBatchService;
  }

  /**
   * 这个每一条数据解析都会来调用
   *
   * @param data one row value. Is is same as {@link AnalysisContext#readRowHolder()}
   * @param context
   */
  @Override
  public void invoke(AtyUserInputExceVO data, AnalysisContext context) {
    list.add(data);
    // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
    if (list.size() >= BATCH_COUNT) {
      List<AtyUserInputExceVO> rptBaseInfos = saveData();
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
    List<AtyUserInputExceVO> rptBaseInfos = saveData();
    returnList.addAll(rptBaseInfos);
  }

  private List<AtyUserInputExceVO> saveData() {
    return  userExcelBatchService.saveUser(list,relame);
  }

  public List<AtyUserInputExceVO> getList(){
    return returnList;
  }
}