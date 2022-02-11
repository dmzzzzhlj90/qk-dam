package com.qk.dm.reptile.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.qk.dm.reptile.client.ClientUserInfo;
import com.qk.dm.reptile.constant.RptConstant;
import com.qk.dm.reptile.entity.RptBaseInfo;
import com.qk.dm.reptile.repositories.RptBaseInfoRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
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
  public List<RptBaseInfo> saveRptBasicInfo(List<RptBaseInfo> prtBasicInfoList) {
    List<RptBaseInfo> lsit = deal(prtBasicInfoList);
    for (RptBaseInfo rptBaseInfo : prtBasicInfoList) {
      //todo 加入操作人员id
      //创建人名称
      rptBaseInfo.setCreateUsername(ClientUserInfo.getUserName());
      //状态
      rptBaseInfo.setStatus(RptConstant.WAITING);
      //运行状态
      rptBaseInfo.setRunStatus(RptConstant.OFF_STARTED);

      entityManager.persist(rptBaseInfo); // insert插入操作
    }
    entityManager.flush();
    entityManager.clear();
    LOG.info(prtBasicInfoList.size()+"成功保存待配信息个数 【{}】");
    return lsit;
  }

  private List<RptBaseInfo> deal(List<RptBaseInfo> prtBasicInfoList) {
    List<RptBaseInfo> list = new ArrayList<>();
    if (CollectionUtils.isNotEmpty(prtBasicInfoList)){
      List<RptBaseInfo> allList = rptBaseInfoRepository.findAll();
      if (CollectionUtils.isNotEmpty(allList)){
        Map<String,RptBaseInfo> collect = allList.stream().collect(Collectors
            .toMap(this::removeDuplicate, k -> k, (k1, k2) -> k1));
        Iterator<RptBaseInfo> iterator = prtBasicInfoList.iterator();
        while (iterator.hasNext()){
          RptBaseInfo rptBaseInfo = iterator.next();
          RptBaseInfo rptBaseInfo1 = collect.get(removeDuplicate(rptBaseInfo));
          if (!Objects.isNull(rptBaseInfo1)){
            list.add(rptBaseInfo);
            iterator.remove();
          }
        }
      }
    }
    return list;
  }

  /**
   * 根据字段去重
   * @param rptBaseInfo
   * @return
   */
  private String removeDuplicate(RptBaseInfo rptBaseInfo){
    String key = String.join("->",rptBaseInfo.getWebsiteUrl(),rptBaseInfo.getSecondSiteType(),rptBaseInfo.getListPageAddress());
    if(StringUtils.isNotBlank(rptBaseInfo.getWebsiteNameCorrection())){
      key = String.join("->",key, rptBaseInfo.getWebsiteNameCorrection());
    }
    if(StringUtils.isNotBlank(rptBaseInfo.getWebsiteUrlCorrection())){
      key = String.join("->",key, rptBaseInfo.getWebsiteUrlCorrection());
    }
    return key;
  }
}