package com.qk.plugin.dataservice.apisix;

import com.qk.dam.commons.util.RestTemplateUtils;
import com.qk.dam.dataservice.spi.consunmer.ConsumerContext;
import com.qk.dam.dataservice.spi.consunmer.ConsumerService;
import com.qk.plugin.dataservice.apisix.consumer.ApiSixConsumerInfo;
import com.qk.plugin.dataservice.apisix.route.constant.ApiSixConstant;
import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

/**
 * @author wjq
 * @date 2021/8/19 10:38
 * @since 1.0.0
 */
public class ApiSixConsumerService implements ConsumerService {

  private ConsumerContext consumerContext;

  public ApiSixConsumerService() {}

  public ApiSixConsumerService(ConsumerContext consumerContext) {
    this.consumerContext = consumerContext;
  }

  @Override
  public void initConsumersAuth() {
    ApiSixConsumerInfo consumerInfo = (ApiSixConsumerInfo) consumerContext.getConsumerInfo();
    HttpEntity httpEntity = setHttpEntity(consumerInfo, consumerContext.getParams());
    RestTemplateUtils.exchange(
        consumerContext.getParams().get(ApiSixConstant.API_SIX_ADMIN_CONSUMER_URL_KEY),
        HttpMethod.PUT,
        httpEntity,
        String.class);
  }

  private HttpEntity setHttpEntity(ApiSixConsumerInfo consumerInfo, Map<String, String> params) {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());
    headers.add("Accept", MediaType.APPLICATION_JSON.toString());
    headers.add(ApiSixConstant.API_SIX_HEAD_KEY, params.get(ApiSixConstant.API_SIX_HEAD_KEY));
    return new HttpEntity<>(consumerInfo, headers);
  }
}
