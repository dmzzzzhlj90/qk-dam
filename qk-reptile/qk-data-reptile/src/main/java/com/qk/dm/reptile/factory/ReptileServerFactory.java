package com.qk.dm.reptile.factory;

import com.qk.dam.commons.util.GsonUtil;
import com.qk.dm.reptile.params.builder.RptConfigBuilder;
import com.qk.dm.reptile.params.builder.RptSelectorBuilder;
import com.qk.dm.reptile.params.vo.RptConfigInfoVO;
import com.qk.dm.reptile.params.vo.RptSelectorColumnInfoVO;
import com.qk.dm.reptile.utils.HttpClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 调用爬虫服务接口工厂
 * @author wangzp
 * @date 2021/12/8 14:32
 * @since 1.0.0
 */
public class ReptileServerFactory {
    private static final Logger LOG = LoggerFactory.getLogger(ReptileServerFactory.class);

    private static final Integer CONN_TIMEOUT = 5000;
    private static final Integer READ_TIMEOUT = 5000;
    private static final String PROJECT = "project";
    private static final String SPIDER = "spider";
    private static final String ORG_VALUE = "arg_value";
    private static final String MODE = "mode";
    private static final String START_URL = "start_url";
    private static final String LIST = "list";
    private static final String EMPTY = "";
    //todo 目前在测试，地址暂时写此，之后提到Nacos上
    private static final String REQUEST_URL = "http://172.21.3.202:6800/schedule.json";

    public static String requestServer(List<RptConfigInfoVO> rptConfigInfoList) {
        String resultInfo = null;
        try {
            resultInfo =  HttpClientUtils.postForm(
                    REQUEST_URL,
                    requestPara(rptConfigInfoList),
                    null,
                    CONN_TIMEOUT,
                    READ_TIMEOUT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOG.info("调用爬虫接口的返回数据:{}",resultInfo);
        return resultInfo;
    }

    /**
     * 请求参数组装
     * @return
     */
    private static Map<String,String> requestPara(List<RptConfigInfoVO> rptConfigInfoList){
        Map<String,String> requestPara = new HashMap<>();
        requestPara.put(PROJECT,"model");
        requestPara.put(SPIDER,"mode");
        requestPara.put(ORG_VALUE, GsonUtil.toJsonString(assembleData(rptConfigInfoList)));
        return requestPara;
    }

    /**
     * 组装数据
     * @return
     */
    private static Map<String,Object> assembleData(List<RptConfigInfoVO> rptConfigInfoList){
        if(CollectionUtils.isEmpty(rptConfigInfoList)){
            return null;
        }
        List<RptConfigBuilder> rptConfigBuilderList = rptConfigInfoList.stream().map(ReptileServerFactory::rptConfigBuilder).collect(Collectors.toList());
        Map<String,Object> map = new HashMap<>();
        map.put(MODE,0);
        map.put(START_URL,rptConfigInfoList.get(0).getRequestUrl());
        map.put(LIST, rptConfigBuilderList);
        return map;
    }

    /**
     * 组装爬虫配置对象
     * @param rptConfigInfoVO
     * @return
     */
    private static RptConfigBuilder rptConfigBuilder(RptConfigInfoVO rptConfigInfoVO){
        return RptConfigBuilder.builder()
                .cookies(rptConfigInfoVO.getCookies())
                .headers(rptConfigInfoVO.getHeaders())
                .ip_start(rptConfigInfoVO.getStartoverIp())
                .js_start(rptConfigInfoVO.getStartoverJs())
                .request(rptConfigInfoVO.getRequestType())
                .columns(selectorBuilder(rptConfigInfoVO.getSelectorList()))
                .build();
    }

    /**
     * 组装选择器
     * @param selectorList
     * @return
     */
    private static Map<String, RptSelectorBuilder> selectorBuilder(List<RptSelectorColumnInfoVO> selectorList){
        if(CollectionUtils.isEmpty(selectorList)){
            return null;
        }
        return selectorList.stream().collect(Collectors.toMap(RptSelectorColumnInfoVO::getColumnCode, selector-> RptSelectorBuilder.builder()
                .method(selector.getSelector())
                .val(Objects.isNull(selector.getSelectorVal())?EMPTY:selector.getSelectorVal())
                .num(selector.getElementType())
                .build()));
    }

}