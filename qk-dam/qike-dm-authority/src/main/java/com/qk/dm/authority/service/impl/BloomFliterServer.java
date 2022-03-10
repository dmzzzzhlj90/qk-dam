package com.qk.dm.authority.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.MapUtils;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.qk.dam.authority.common.vo.RealmVO;
import com.qk.dam.authority.common.vo.user.AtyUserInfoVO;
import com.qk.dm.authority.constant.QxConstant;
import com.qk.dm.authority.entity.QxResources;
import com.qk.dm.authority.repositories.QkQxResourcesRepository;
import com.qk.dm.authority.service.AtyRealmService;
import com.qk.dm.authority.service.AtyUserService;
import com.qk.dm.authority.util.MultipartFileUtil;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 初始化Guava内存
 * @author zys
 * @date 2022/2/15 14:29
 * @since 1.0.0
 */
@Component
@Data
public class BloomFliterServer {
  private final QkQxResourcesRepository qkQxResourcesRepository;
  private final AtyRealmService atyRealmService;
  private final AtyUserService atyUserService;
  private BloomFilter<String> filter = null;

  public BloomFliterServer(QkQxResourcesRepository qkQxResourcesRepository,
      AtyRealmService atyRealmService, AtyUserService atyUserService) {
    this.qkQxResourcesRepository = qkQxResourcesRepository;
    this.atyRealmService = atyRealmService;
    this.atyUserService = atyUserService;
  }

  /**
   * 将库中的数据拼接成key存入guava中，分别是（根据资源名称、标识、服务id、资源类型）
   */
  @PostConstruct
  public void cacheData() {
    filter= BloomFilter.create(Funnels.stringFunnel(
        Charset.defaultCharset()), QxConstant.GUAVA_CAPACITY,0.01);
    List<QxResources> qxResourcesList = qkQxResourcesRepository.findAll();
    if (CollectionUtils.isNotEmpty(qxResourcesList)){
      qxResourcesList.forEach(qxResources -> {
        String key = MultipartFileUtil.removeDuplicate(qxResources);
        filter.put(key);
      });
    }
  }

  /**
   * 将库中的数据拼接成key存入guava中，分别是（根据资源名称、标识、服务id、资源类型）
   */
  @PostConstruct
  public void cacheUserData() {
    filter= BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()), QxConstant.GUAVA_CAPACITY,0.01);
    Map<String,List<AtyUserInfoVO>> userMap = getUserMap();
    if (MapUtils.isNotEmpty(userMap)){
      userMap.forEach((k,v)->{
        v.forEach(atyUserInfoVO -> {
            String key = MultipartFileUtil.createUserKey(k,atyUserInfoVO);
            filter.put(key);
        });
      });
    }
  }

  /**
   * 根据域获取所有用户
   * @return
   */
  private  Map<String,List<AtyUserInfoVO>> getUserMap() {
    Map<String,List<AtyUserInfoVO>> userMap = new HashMap<>();
    List<RealmVO> realmList = atyRealmService.getRealmList();
    realmList.forEach(realmVO -> {
      List<AtyUserInfoVO> users = atyUserService.getUsers(realmVO.getRealm(), null);
      userMap.put(realmVO.getRealm(),users);
    });
    return userMap;
  }

}