package com.qk.dm.dataservice.utils;

import cn.hutool.json.JSONObject;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author shen
 * @version 1.0
 * @description:
 * @date 2021-07-12 14:17
 */
@Component
public class RestTemplateUtil {

    private static final Log LOG = LogFactory.get("远程调用");

    @Autowired
    RestTemplate restTemplate;

    /**
     * 通用get接口
     *
     * @param url
     * @return
     */
    public String getJsonArrayByRest(String url) {
        try {
            LOG.info("通用get接口URL->{}", url);
            ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            LOG.info("通用接口get返回结果->{}", exchange.getBody());
            if(exchange.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("远程get调用接口失败！");
            }
            return exchange.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("远程get调用接口失败！");
        }
    }

    /**
     * 通用post接口
     *
     * @param url
     * @param hashMapList
     * @return
     */
    public String postByRestTemplate(String url, JSONObject hashMapList) {
        LOG.info("通用post接口URL->{}", url);
        try {
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(type);
            HttpEntity<JSONObject> httpEntity = new HttpEntity<>(hashMapList, headers);
            LOG.info("通用post接口发送内容->{}", httpEntity.toString());
            ResponseEntity<String> exchange = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    httpEntity,
                    String.class
            );
            LOG.info("通用post接口返回结果->{}", exchange.getBody());
            if(exchange.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("远程post调用接口失败！");
            }
            return exchange.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("远程post调用接口失败！");
        }
    }
}
