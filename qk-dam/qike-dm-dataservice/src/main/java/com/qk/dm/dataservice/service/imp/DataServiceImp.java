package com.qk.dm.dataservice.service.imp;

import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.qk.dm.dataservice.service.DataService;
import com.qk.dm.dataservice.utils.RestTemplateUtil;
import com.qk.dm.dataservice.vo.DataPushVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

/**
 * @author shen
 * @version 1.0
 * @description:
 * @date 2021-07-12 13:57
 */
@Service
@RefreshScope
public class DataServiceImp implements DataService {

  @Autowired RestTemplateUtil restTemplateUtil;

  @Value("${spring.push.url}")
  private String url;

  private static final Log LOG = LogFactory.get("数据转换");

  @Override
  public String dataPush(DataPushVO dataPushVO) {
    return restTemplateUtil.postByRestTemplate(url, JSONUtil.parseObj(dataPushVO));
  }
}
