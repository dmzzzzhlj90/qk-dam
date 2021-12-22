package com.qk.dm.reptile.service.impl;

import com.qk.dm.reptile.entity.RptBaseInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author zys
 * @date 2021/12/22 11:49
 * @since 1.0.0
 */
@Service
public class RptExcelBatchService {
  private static final Log LOG = LogFactory.getLog("rptExcelBatchService");
  @PersistenceContext
  private EntityManager entityManager;
  @Transactional(rollbackFor = Exception.class)
  public void saveRptBasicInfo(List<RptBaseInfo> prtBasicInfoList) {

    for (RptBaseInfo rptBaseInfo : prtBasicInfoList) {
      //todo 加入操作人员id
      entityManager.persist(rptBaseInfo); // insert插入操作
    }
    entityManager.flush();
    entityManager.clear();
    LOG.info(prtBasicInfoList.size()+"成功保存待配信息个数 【{}】");
  }
}