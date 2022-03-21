package com.qk.dm.reptile.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.qk.dm.reptile.client.ClientUserInfo;
import com.qk.dm.reptile.constant.RptConstant;
import com.qk.dm.reptile.entity.RptBaseInfo;
import com.qk.dm.reptile.repositories.RptBaseInfoRepository;
import com.qk.dm.reptile.utils.MultipartFileUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author zys
 * @date 2021/12/22 11:49
 * @since 1.0.0
 */
@Service
public class RptExcelBatchService {
  private static final Log LOG = LogFactory.getLog("rptExcelBatchService");
  private final RptBaseInfoRepository rptBaseInfoRepository;
  private final BloomFliterServer bloomFliterServer;
  @Autowired
  public RptExcelBatchService(RptBaseInfoRepository rptBaseInfoRepository,
      BloomFliterServer bloomFliterServer) {
    this.rptBaseInfoRepository = rptBaseInfoRepository;
    this.bloomFliterServer = bloomFliterServer;
  }

  @Transactional(rollbackFor = Exception.class)
  public List<RptBaseInfo> saveRptBasicInfo(List<RptBaseInfo> prtBasicInfoList) {
    List<RptBaseInfo> list = deal(prtBasicInfoList);
    rptBaseInfoRepository.saveAllAndFlush(prtBasicInfoList);
    LOG.info(prtBasicInfoList.size()+"成功保存待配信息个数 【{}】");
    return list;
  }

  private List<RptBaseInfo> deal(List<RptBaseInfo> prtBasicInfoList) {
    List<RptBaseInfo> list = new ArrayList<>();
    if (CollectionUtils.isNotEmpty(prtBasicInfoList)){
        Iterator<RptBaseInfo> iterator = prtBasicInfoList.iterator();
        while (iterator.hasNext()){
          RptBaseInfo rptBaseInfo = iterator.next();
          //创建人名称
          rptBaseInfo.setCreateUsername(ClientUserInfo.getUserName());
          //状态
          rptBaseInfo.setStatus(RptConstant.WAITING);
          //运行状态
          rptBaseInfo.setRunStatus(RptConstant.OFF_STARTED);
          String key = MultipartFileUtil.removeDuplicate(rptBaseInfo);
          if (bloomFliterServer.getFilter()!=null){
            boolean b = bloomFliterServer.getFilter().mightContain(key);
            if (b){
              list.add(rptBaseInfo);
              iterator.remove();
            }else {
              bloomFliterServer.getFilter().put(key);
            }
          }
        }
    }
    return list;
  }
}