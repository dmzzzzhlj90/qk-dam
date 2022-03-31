package com.qk.dm.authority.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.qk.dm.authority.entity.QkQxResourcesApi;
import com.qk.dm.authority.mapstruct.QxResourcesApiMapper;
import com.qk.dm.authority.repositories.QkQxResourcesApiRepository;
import com.qk.dm.authority.util.MultipartFileUtil;
import com.qk.dm.authority.vo.powervo.ResourceApiVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * api资源
 * @author zys
 * @date 2022/3/1 18:19
 * @since 1.0.0
 */
@Service
public class ApiRsExcelBatchService {
  private static final Log LOG = LogFactory.getLog("resourceExcelBatchService");
  private final QkQxResourcesApiRepository qkQxResourcesApiRepository;
  private final BloomFilterServer bloomFilterServer;

  public ApiRsExcelBatchService(
      QkQxResourcesApiRepository qkQxResourcesApiRepository, BloomFilterServer bloomFilterServer) {
    this.qkQxResourcesApiRepository = qkQxResourcesApiRepository;

    this.bloomFilterServer = bloomFilterServer;
  }

  public List<ResourceApiVO> saveResources(List<ResourceApiVO> qxResourcesList) {
    List<ResourceApiVO> lsit = deal(qxResourcesList);
    saveAllApiResources(qxResourcesList);
    LOG.info(qxResourcesList.size()+"成功保存api资源信息个数 【{}】");
    return lsit;
  }

  /**
   * 存储api资源
   * @param qxResourcesList
   */
  private void saveAllApiResources(List<ResourceApiVO> qxResourcesList) {
    List<QkQxResourcesApi> resourseList =  QxResourcesApiMapper.INSTANCE.ofResourcesApi(qxResourcesList);
    qkQxResourcesApiRepository.saveAll(resourseList);
  }

  private List<ResourceApiVO> deal(List<ResourceApiVO> qxResourcesList) {
    List<ResourceApiVO> list = new ArrayList<>();
    if (CollectionUtils.isNotEmpty(qxResourcesList)){
      Iterator<ResourceApiVO> iterator = qxResourcesList.iterator();
      while (iterator.hasNext()){
        ResourceApiVO resourceApiVO = iterator.next();
        resourceApiVO.setResourcesid(UUID.randomUUID().toString());
        //todo 加入操作人员id
        String key = MultipartFileUtil.getApiExcelKey(resourceApiVO);
        if (bloomFilterServer.getFilter()!=null){
          boolean b = bloomFilterServer.getFilter().mightContain(key);
          if (b){
            list.add(resourceApiVO);
            iterator.remove();
          }else {
            bloomFilterServer.getFilter().put(key);
          }
        }
      }
    }
    return list;
  }
}