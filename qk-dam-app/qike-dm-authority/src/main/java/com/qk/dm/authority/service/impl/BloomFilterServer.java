package com.qk.dm.authority.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.MapUtils;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.qk.dam.authority.common.vo.RealmVO;
import com.qk.dam.authority.common.vo.user.AtyUserInfoVO;
import com.qk.dm.authority.constant.QxConstant;
import com.qk.dm.authority.entity.QkQxResourcesApi;
import com.qk.dm.authority.entity.QkQxResourcesMenu;
import com.qk.dm.authority.repositories.QkQxResourcesApiRepository;
import com.qk.dm.authority.repositories.QkQxResourcesMenuRepository;
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
public class BloomFilterServer {
  private final QkQxResourcesMenuRepository qkQxResourcesMenuRepository;
  private final QkQxResourcesApiRepository qkQxResourcesApiRepository;
  private final AtyRealmService atyRealmService;
  private final AtyUserService atyUserService;
  private BloomFilter<String> filter = BloomFilter.create(Funnels.stringFunnel(
      Charset.defaultCharset()), QxConstant.GUAVA_CAPACITY,0.01);

  public BloomFilterServer(
      QkQxResourcesMenuRepository qkQxResourcesMenuRepository,
      QkQxResourcesApiRepository qkQxResourcesApiRepository, AtyRealmService atyRealmService, AtyUserService atyUserService) {
    this.qkQxResourcesMenuRepository = qkQxResourcesMenuRepository;
    this.qkQxResourcesApiRepository = qkQxResourcesApiRepository;
    this.atyRealmService = atyRealmService;
    this.atyUserService = atyUserService;
  }

  /**
   * 将库中的数据拼接成key存入guava中，分别是（根据资源名称、服务uuid、页面）
   */
  @PostConstruct
  public void cacheData() {
    List<QkQxResourcesMenu> qkQxResourcesMenuList = qkQxResourcesMenuRepository.findAll();
    if (CollectionUtils.isNotEmpty(qkQxResourcesMenuList)){
      qkQxResourcesMenuList.forEach(qkQxResourcesMenu -> {
        String key = MultipartFileUtil.removeDuplicate(qkQxResourcesMenu);
        filter.put(key);
      });
    }
  }

  /**
   *将数据库中的api数据信息存储在guava中key为（名称、服务uuid）
   */
  @PostConstruct
  public void cacheRsApiData() {
    List<QkQxResourcesApi> qkQxResourcesApiList = qkQxResourcesApiRepository.findAll();
    if (CollectionUtils.isNotEmpty(qkQxResourcesApiList)){
      qkQxResourcesApiList.forEach(qkQxResourcesApi -> {
        String key = MultipartFileUtil.createRsApiKey(qkQxResourcesApi);
        filter.put(key);
      });
    }
  }

  /**
   * 将库中的数据拼接成key存入guava中，分别是（域名、用户名）
   */
  @PostConstruct
  public void cacheUserData() {
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