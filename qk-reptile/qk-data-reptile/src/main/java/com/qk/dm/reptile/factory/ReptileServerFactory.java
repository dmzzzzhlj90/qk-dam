package com.qk.dm.reptile.factory;

import com.google.common.collect.Maps;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dm.reptile.params.builder.RptConfigBuilder;
import com.qk.dm.reptile.params.builder.RptSelectorBuilder;
import com.qk.dm.reptile.params.vo.RptConfigInfoVO;
import com.qk.dm.reptile.params.vo.RptSelectorColumnInfoVO;
import com.qk.dm.reptile.utils.HttpClientUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 调用爬虫服务接口工厂
 *
 * @author wangzp
 * @date 2021/12/8 14:32
 * @since 1.0.0
 */
public class ReptileServerFactory {
    private static final Integer CONN_TIMEOUT = 30000;
    private static final Integer READ_TIMEOUT = 30000;
    private static final String PROJECT = "project";
    private static final String SPIDER = "spider";
    private static final String ORG_VALUE = "arg_value";
    private static final String MODE = "mode";
    private static final String IN = "in";//是否入库 0代表不入库 1代表入库
    private static final String START_URL = "start_url";
    private static final String LIST = "list";
    private static final String EMPTY = "";
    //todo 目前在测试，地址暂时写此，之后提到Nacos上
    private static final String TIMING_URL = "http://172.21.3.211:6800/schedule.json";
    //手动抓取地址
    private static final String MANUAL_URL = "http://172.21.3.191:6800/schedule.json";

    /**
     * 手动执行
     *
     * @param rptConfigInfoList
     * @return
     */
    public static String manual(List<RptConfigInfoVO> rptConfigInfoList) {
        return requestServer(rptConfigInfoList, MANUAL_URL, 0);
    }

    /**
     * 定时
     *
     * @param rptConfigInfoList
     * @return
     */
    public static String timing(List<RptConfigInfoVO> rptConfigInfoList) {
        return requestServer(rptConfigInfoList, TIMING_URL, 1);
    }

    private static String requestServer(List<RptConfigInfoVO> rptConfigInfoList, String url, Integer in) {
        try {
            return HttpClientUtils.postForm(
                    url,
                    requestPara(rptConfigInfoList, in),
                    null,
                    CONN_TIMEOUT,
                    READ_TIMEOUT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Strings.EMPTY;
    }

    /**
     * 请求参数组装
     *
     * @return
     */
    private static Map<String, String> requestPara(List<RptConfigInfoVO> rptConfigInfoList, Integer in) {
        Map<String, String> requestPara = Maps.newHashMap();
        requestPara.put(PROJECT, "model");
        requestPara.put(SPIDER, "mode");
        requestPara.put(ORG_VALUE, GsonUtil.toJsonString(assembleData(rptConfigInfoList, in)));
        return requestPara;
    }

    /**
     * 组装数据
     *
     * @return
     */
    private static Map<String, Object> assembleData(List<RptConfigInfoVO> rptConfigInfoList, Integer in) {
        if (CollectionUtils.isEmpty(rptConfigInfoList)) {
            return Collections.emptyMap();
        }
        List<RptConfigBuilder> rptConfigBuilderList = rptConfigInfoList.stream().map(ReptileServerFactory::rptConfigBuilder).collect(Collectors.toList());
        Map<String, Object> map = Maps.newHashMap();
        map.put(MODE, 0);
        map.put(IN, in);
        map.put(START_URL, rptConfigInfoList.get(0).getRequestUrl());
        map.put(LIST, rptConfigBuilderList);
        return map;
    }

    /**
     * 组装爬虫配置对象
     *
     * @param rptConfigInfoVO
     * @return
     */
    private static RptConfigBuilder rptConfigBuilder(RptConfigInfoVO rptConfigInfoVO) {
        return RptConfigBuilder.builder()
                .cookies(rptConfigInfoVO.getCookies())
                .headers(rptConfigInfoVO.getHeaders())
                .form_data(rptConfigInfoVO.getFormData())
                .form_urlencoded(rptConfigInfoVO.getFormUrlencoded())
                .raw(rptConfigInfoVO.getRaw())
                .ip_start(rptConfigInfoVO.getStartoverIp())
                .js_start(rptConfigInfoVO.getStartoverJs())
                .request(rptConfigInfoVO.getRequestType())
                .columns(selectorBuilder(rptConfigInfoVO.getSelectorList()))
                .build();
    }

    /**
     * 组装选择器
     *
     * @param selectorList
     * @return
     */
    private static Map<String, RptSelectorBuilder> selectorBuilder(List<RptSelectorColumnInfoVO> selectorList) {
        if (CollectionUtils.isEmpty(selectorList)) {
            return Collections.emptyMap();
        }

        return selectorList.stream().collect(Collectors.toMap(RptSelectorColumnInfoVO::getColumnCode, selector -> RptSelectorBuilder.builder()
                .method(selector.getSelector())
                .val(Objects.requireNonNullElse(selector.getSelectorVal(), EMPTY))
                .num(selector.getElementType())
                .request_before(Objects.requireNonNullElse(selector.getRequestBeforePrefix(), EMPTY))
                .request_after(Objects.requireNonNullElse(selector.getRequestAfterPrefix(), EMPTY))
                .source_before(Objects.requireNonNullElse(selector.getSourceBeforePrefix(), EMPTY))
                .source_after(Objects.requireNonNullElse(selector.getSourceAfterPrefix(), EMPTY))
                .before(Objects.requireNonNullElse(selector.getBeforePrefix(), EMPTY))
                .after(Objects.requireNonNullElse(selector.getAfterPrefix(), EMPTY))
                .build(), (k1, k2) -> k1));
    }

}
