package com.qk.dm.reptile.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.qk.dm.reptile.constant.RptConstant;
import com.qk.dm.reptile.entity.RptBaseInfo;
import com.qk.dm.reptile.repositories.RptBaseInfoRepository;
import com.qk.dm.reptile.utils.UserInfoUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
  private final RptBaseInfoRepository rptBaseInfoRepository;

  public RptExcelBatchService(RptBaseInfoRepository rptBaseInfoRepository) {
    this.rptBaseInfoRepository = rptBaseInfoRepository;
  }

  @Transactional(rollbackFor = Exception.class)
  public void saveRptBasicInfo(List<RptBaseInfo> prtBasicInfoList) {
    deal(prtBasicInfoList);
    for (RptBaseInfo rptBaseInfo : prtBasicInfoList) {
      //todo 加入操作人员id
      //创建人名称
      rptBaseInfo.setCreateUsername(Objects.requireNonNullElse(UserInfoUtil.getUserName(),"").toString());
      //状态
      rptBaseInfo.setStatus(RptConstant.WAITING);
      //运行状态
      rptBaseInfo.setRunStatus(RptConstant.OFF_STARTED);

      entityManager.persist(rptBaseInfo); // insert插入操作
    }
    entityManager.flush();
    entityManager.clear();
    LOG.info(prtBasicInfoList.size()+"成功保存待配信息个数 【{}】");
  }

  private void deal(List<RptBaseInfo> prtBasicInfoList) {
    if (CollectionUtils.isNotEmpty(prtBasicInfoList)){
      List<RptBaseInfo> allList = rptBaseInfoRepository.findAll();
      if (CollectionUtils.isNotEmpty(allList)){
        Map<String,RptBaseInfo> collect = allList.stream().collect(Collectors
            .toMap(k -> k.getWebsiteUrl() + "->"+k.getSecondSiteType()+"->" + k.getListPageAddress(),
                k -> k, (k1, k2) -> k1));
        Iterator<RptBaseInfo> iterator = prtBasicInfoList.iterator();
        while (iterator.hasNext()){
          RptBaseInfo rptBaseInfo = iterator.next();
          String s = rptBaseInfo.getWebsiteUrl() +"->"+rptBaseInfo.getSecondSiteType()+"->"+ rptBaseInfo.getListPageAddress();
          RptBaseInfo rptBaseInfo1 = collect.get(s);
          if (!Objects.isNull(rptBaseInfo1)){
            iterator.remove();
          }
        }
      }
    }
  }
}