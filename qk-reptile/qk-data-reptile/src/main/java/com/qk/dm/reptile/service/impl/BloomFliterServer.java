package com.qk.dm.reptile.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.qk.dm.reptile.constant.RptConstant;
import com.qk.dm.reptile.entity.RptBaseInfo;
import com.qk.dm.reptile.repositories.RptBaseInfoRepository;
import com.qk.dm.reptile.utils.MultipartFileUtil;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;
import java.util.List;

/**
 * 初始化Guava内存
 * @author zys
 * @date 2022/2/15 14:29
 * @since 1.0.0
 */
@Component
@Data
public class BloomFliterServer {
  private final RptBaseInfoRepository rptBaseInfoRepository;
  private BloomFilter<String> filter = null;

  public BloomFliterServer(RptBaseInfoRepository rptBaseInfoRepository) {
    this.rptBaseInfoRepository = rptBaseInfoRepository;
  }

  /**
   * 将库中的数据拼接成key存入guava中，分别是（连接、二期站点类型、列表页地址、站点官网（修正）、站点官网（修正））
   */
  @PostConstruct
  public void cacheData() {
    filter= BloomFilter.create(Funnels.stringFunnel(
        Charset.defaultCharset()), RptConstant.GUAVA_CAPACITY,0.01);
    List<RptBaseInfo> allList = rptBaseInfoRepository.findAll();
    if (CollectionUtils.isNotEmpty(allList)){
      allList.forEach(rptBaseInfo -> {
        String key = MultipartFileUtil.removeDuplicate(rptBaseInfo);
        filter.put(key);
      });
    }
  }
}