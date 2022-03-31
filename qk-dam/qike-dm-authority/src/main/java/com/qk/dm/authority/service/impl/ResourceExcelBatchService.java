package com.qk.dm.authority.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.qk.dm.authority.constant.QxConstant;
import com.qk.dm.authority.entity.QkQxResourcesMenu;
import com.qk.dm.authority.mapstruct.QxResourcesMenuMapper;
import com.qk.dm.authority.repositories.QkQxResourcesMenuRepository;
import com.qk.dm.authority.util.MultipartFileUtil;
import com.qk.dm.authority.vo.powervo.ResourceMenuExcelVO;
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
  private final QkQxResourcesMenuRepository qkQxResourcesMenuRepository;
  private final BloomFilterServer bloomFilterServer;

  public ResourceExcelBatchService(
      QkQxResourcesMenuRepository qkQxResourcesMenuRepository, BloomFilterServer bloomFilterServer) {
    this.qkQxResourcesMenuRepository = qkQxResourcesMenuRepository;
    this.bloomFilterServer = bloomFilterServer;
  }

  public List<ResourceMenuExcelVO> saveResources(List<ResourceMenuExcelVO> qxResourcesList) {
    List<ResourceMenuExcelVO> lsit = deal(qxResourcesList);
    saveAllResources(qxResourcesList);
    LOG.info(qxResourcesList.size()+"成功保存资源信息个数 【{}】");
    return lsit;
  }

  /**
   * 存储资源
   * @param qxResourcesList
   */
  private void saveAllResources(List<ResourceMenuExcelVO> qxResourcesList) {
    List<QkQxResourcesMenu> qkQxResourcesMenuList = qkQxResourcesMenuRepository.findAll();
    if (CollectionUtils.isNotEmpty(qkQxResourcesMenuList)){
      Map<String,QkQxResourcesMenu> resourceMap = qkQxResourcesMenuList.stream().collect(
          Collectors.toMap(k -> k.getName() + "->" + k.getServiceId()+ "->" + k.getComponent(), k -> k, (k1, k2) -> k1));
          qxResourcesList.forEach(resourceMenuExcelVO-> {
        //根据key获取父级信息
        String key = MultipartFileUtil.getKey(resourceMenuExcelVO);
        //生成新的资源key
        String createKey = MultipartFileUtil.createKey(resourceMenuExcelVO);
            QkQxResourcesMenu qkQxResourcesMenu = resourceMap.get(key);
            QkQxResourcesMenu resources =null;
        if (Objects.isNull(qkQxResourcesMenu)){
          resources = QxResourcesMenuMapper.INSTANCE.qxResourcesMenuExcel(resourceMenuExcelVO);
          //设置父级id为0
          resources.setPid(QxConstant.DIRID);
        }else{
          resources = QxResourcesMenuMapper.INSTANCE.qxResourcesMenuExcel(resourceMenuExcelVO);
          resources.setPid(qkQxResourcesMenu.getId());
        }
        qkQxResourcesMenuRepository.save(resources);
        resourceMap.put(createKey,resources);
      });
    }
  }

  private List<ResourceMenuExcelVO> deal(List<ResourceMenuExcelVO> qxResourcesList) {
    List<ResourceMenuExcelVO> list = new ArrayList<>();
    if (CollectionUtils.isNotEmpty(qxResourcesList)){
      Iterator<ResourceMenuExcelVO> iterator = qxResourcesList.iterator();
      while (iterator.hasNext()){
        ResourceMenuExcelVO resourceMenuExcelVO = iterator.next();
        resourceMenuExcelVO.setResourcesid(UUID.randomUUID().toString());
        //todo 加入操作人员id
        String key = MultipartFileUtil.getExcelKey(resourceMenuExcelVO);
        if (bloomFilterServer.getFilter()!=null){
          boolean b = bloomFilterServer.getFilter().mightContain(key);
          if (b){
            list.add(resourceMenuExcelVO);
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