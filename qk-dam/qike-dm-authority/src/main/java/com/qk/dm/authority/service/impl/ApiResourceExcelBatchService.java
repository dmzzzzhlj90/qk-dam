package com.qk.dm.authority.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.qk.dm.authority.entity.QxResources;
import com.qk.dm.authority.mapstruct.QxResourcesMapper;
import com.qk.dm.authority.repositories.QkQxResourcesRepository;
import com.qk.dm.authority.util.MultipartFileUtil;
import com.qk.dm.authority.vo.powervo.ResourceVO;
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
public class ApiResourceExcelBatchService {
  private static final Log LOG = LogFactory.getLog("resourceExcelBatchService");
  private final QkQxResourcesRepository qkQxResourcesRepository;
  private final BloomFliterServer bloomFliterServer;

  public ApiResourceExcelBatchService(
      QkQxResourcesRepository qkQxResourcesRepository,
      BloomFliterServer bloomFliterServer) {
    this.qkQxResourcesRepository = qkQxResourcesRepository;
    this.bloomFliterServer = bloomFliterServer;
  }

  public List<ResourceVO> saveResources(List<ResourceVO> qxResourcesList) {
    List<ResourceVO> lsit = deal(qxResourcesList);
    saveAllApiResources(qxResourcesList);
    LOG.info(qxResourcesList.size()+"成功保存api资源信息个数 【{}】");
    return lsit;
  }

  /**
   * 存储api资源
   * @param qxResourcesList
   */
  private void saveAllApiResources(List<ResourceVO> qxResourcesList) {
    List<QxResources> resourseList = QxResourcesMapper.INSTANCE.ofResourcesVO(qxResourcesList);
    qkQxResourcesRepository.saveAll(resourseList);
  }

  private List<ResourceVO> deal(List<ResourceVO> qxResourcesList) {
    List<ResourceVO> list = new ArrayList<>();
    if (CollectionUtils.isNotEmpty(qxResourcesList)){
      Iterator<ResourceVO> iterator = qxResourcesList.iterator();
      while (iterator.hasNext()){
        ResourceVO resourceVO = iterator.next();
        resourceVO.setResourcesid(UUID.randomUUID().toString());
        //todo 加入操作人员id
        String key = MultipartFileUtil.getApiExcelKey(resourceVO);
        if (bloomFliterServer.getFilter()!=null){
          boolean b = bloomFliterServer.getFilter().mightContain(key);
          if (b){
            list.add(resourceVO);
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