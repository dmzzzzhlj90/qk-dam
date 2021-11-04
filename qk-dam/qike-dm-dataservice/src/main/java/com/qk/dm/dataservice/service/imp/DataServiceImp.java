package com.qk.dm.dataservice.service.imp;

import cn.hutool.json.JSONUtil;
import com.qk.dm.dataservice.service.DataService;
import com.qk.dm.dataservice.utils.RestTemplateUtil;
import com.qk.dm.dataservice.vo.DataPushVO;
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

  private final RestTemplateUtil restTemplateUtil;

  public DataServiceImp(RestTemplateUtil restTemplateUtil) {
    this.restTemplateUtil = restTemplateUtil;
  }

  @Value("${spring.push.url}")
  private String url;

  @Value("${spring.push.token}")
  private String token;

  @Override
  public String dataPush(DataPushVO dataPushVO) {
    return restTemplateUtil.postByToken(url, JSONUtil.parseObj(dataPushVO), token);
  }
}
