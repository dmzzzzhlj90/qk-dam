package com.qk.dm.reptile.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.qk.dam.commons.exception.BizException;
import com.qk.dm.reptile.entity.RptBaseInfo;
import com.qk.dm.reptile.mapstruct.mapper.RptBaseInfoMapper;
import com.qk.dm.reptile.params.vo.RptBaseInfoVO;
import com.qk.dm.reptile.service.impl.RptExcelBatchService;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**待配数据监听器
 * @author zys
 * @date 2021/12/22 11:44
 * @since 1.0.0
 */
@Component
public class RptBasicInfoUploadDataListener extends AnalysisEventListener<RptBaseInfoVO> {
  private static final Log LOG = LogFactory.getLog("rptBasicInfoUploadDataListener.saveData()");
  private final RptExcelBatchService rptExcelBatchService;
  /** 每隔1000条存储数据库，然后清理list ，方便内存回收 */
  private static final int BATCH_COUNT = 1000;

  List<RptBaseInfoVO> list = new ArrayList<>();
  List<RptBaseInfo> returnList = new ArrayList<>();
  public RptBasicInfoUploadDataListener(RptExcelBatchService rptExcelBatchService) {
    this.rptExcelBatchService = rptExcelBatchService;
  }
  /**
   * 这个每一条数据解析都会来调用
   *
   * @param data one row value. Is is same as {@link AnalysisContext#readRowHolder()}
   * @param context
   */
  @Override
  public void invoke(RptBaseInfoVO data, AnalysisContext context) {
    LOG.info("======开始校验excel中的待配数据!======");
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
    LOG.info("======结束校验excel中的待配数据!======");
    // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
    if (list.size() >= BATCH_COUNT) {
      List<RptBaseInfo> rptBaseInfos = saveData();
      returnList.addAll(rptBaseInfos);
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
    List<RptBaseInfo> rptBaseInfos = saveData();
    returnList.addAll(rptBaseInfos);
  }

  private List<RptBaseInfo> saveData() {
    LOG.info(list.size()+"解析excel待配数据个数 【{}】,开始导入");
    List<RptBaseInfo> prtBasicInfoList = new ArrayList<>();
      LOG.info("======匹配excel待配数据导入======");
      getBasicInfoDataAll(prtBasicInfoList);
      LOG.info("======成功获取到待配数据======");
    return  rptExcelBatchService.saveRptBasicInfo(prtBasicInfoList);
  }

  private void getBasicInfoDataAll(List<RptBaseInfo> rptBasicInfoList) {
      if (list != null && list.size() > 0) {
        list.forEach(
            rptBaseInfoVO -> {
              RptBaseInfo rptBaseInfo = RptBaseInfoMapper.INSTANCE.userVoToRtpBaseInfo(rptBaseInfoVO);
              rptBasicInfoList.add(rptBaseInfo);
            });
      }
  }
 public List<RptBaseInfo> getList(){
    return returnList;
 }
}