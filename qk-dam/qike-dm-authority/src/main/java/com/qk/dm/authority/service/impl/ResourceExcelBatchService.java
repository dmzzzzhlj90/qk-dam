package com.qk.dm.authority.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.qk.dm.authority.entity.QxResources;
import com.qk.dm.authority.mapstruct.QxResourcesMapper;
import com.qk.dm.authority.repositories.QkQxResourcesRepository;
import com.qk.dm.authority.util.MultipartFileUtil;
import com.qk.dm.authority.vo.powervo.ResourceExcelVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 资源
 * @author zys
 * @date 2022/3/1 18:19
 * @since 1.0.0
 */
@Service
public class ResourceExcelBatchService {
  private static final Log LOG = LogFactory.getLog("resourceExcelBatchService");
  private final QkQxResourcesRepository qkQxResourcesRepository;
  private final BloomFliterServer bloomFliterServer;

  public ResourceExcelBatchService(
      QkQxResourcesRepository qkQxResourcesRepository,
      BloomFliterServer bloomFliterServer) {
    this.qkQxResourcesRepository = qkQxResourcesRepository;
    this.bloomFliterServer = bloomFliterServer;
  }

  public List<ResourceExcelVO> saveResources(List<ResourceExcelVO> qxResourcesList) {
    List<ResourceExcelVO> lsit = deal(qxResourcesList);
    saveAllResources(qxResourcesList);
    LOG.info(qxResourcesList.size()+"成功保存资源信息个数 【{}】");
    return lsit;
  }

  /**
   * 存储资源
   * @param qxResourcesList
   */
  private void saveAllResources(List<ResourceExcelVO> qxResourcesList) {
    List<QxResources> resourcesList = qkQxResourcesRepository.findAll();
    if (CollectionUtils.isNotEmpty(resourcesList)){
      Map<String,QxResources> resourceMap = resourcesList.stream().collect(
          Collectors.toMap(k -> k.getName() + "->" + k.getServiceId() + "->" + k.getType(), k -> k, (k1, k2) -> k1));
          qxResourcesList.forEach(resourceExcelVO-> {
        //根据key获取父级信息
        String key = MultipartFileUtil.getKey(resourceExcelVO);
        //生成新的资源key
        String createKey = MultipartFileUtil.createKey(resourceExcelVO);
        QxResources qxResources1 = resourceMap.get(key);
        QxResources resources =null;
        if (Objects.isNull(qxResources1)){
          resources = QxResourcesMapper.INSTANCE.qxResourcesExcel(resourceExcelVO);
          //设置父级id为0
          resources.setPid(0l);
        }else{
          resources = QxResourcesMapper.INSTANCE.qxResourcesExcel(resourceExcelVO);
          resources.setPid(qxResources1.getId());
        }
        qkQxResourcesRepository.save(resources);
        resourceMap.put(createKey,resources);
      });
    }
  }

  private List<ResourceExcelVO> deal(List<ResourceExcelVO> qxResourcesList) {
    List<ResourceExcelVO> list = new ArrayList<>();
    if (CollectionUtils.isNotEmpty(qxResourcesList)){
      Iterator<ResourceExcelVO> iterator = qxResourcesList.iterator();
      while (iterator.hasNext()){
        ResourceExcelVO resourceExcelVO = iterator.next();
        resourceExcelVO.setResourcesid(UUID.randomUUID().toString());
        //todo 加入操作人员id
        String key = MultipartFileUtil.getExcelKey(resourceExcelVO);
        if (bloomFliterServer.getFilter()!=null){
          boolean b = bloomFliterServer.getFilter().mightContain(key);
          if (b){
            list.add(resourceExcelVO);
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