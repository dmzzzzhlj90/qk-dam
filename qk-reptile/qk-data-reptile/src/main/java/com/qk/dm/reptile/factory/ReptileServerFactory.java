package com.qk.dm.reptile.factory;

import com.google.common.collect.Maps;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dm.reptile.params.builder.RptConfigBuilder;
import com.qk.dm.reptile.params.builder.RptSelectorBuilder;
import com.qk.dm.reptile.params.dto.RptFindSourceDTO;
import com.qk.dm.reptile.params.vo.RptConfigInfoVO;
import com.qk.dm.reptile.params.vo.RptSelectorColumnInfoVO;
import com.qk.dm.reptile.utils.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 调用爬虫服务接口工厂
 *
 * @author wangzp
 * @date 2021/12/8 14:32
 * @since 1.0.0
 */
@Slf4j
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

    /**
     * 手动执行
     *
     * @param rptConfigInfoList
     * @return
     */
    public static String manual(String manualUrl,List<RptConfigInfoVO> rptConfigInfoList) {
        return requestServer(rptConfigInfoList, manualUrl, 0);
    }

    /**
     * 定时
     *
     * @param rptConfigInfoList
     * @return
     */
    public static String timing(String timingUrl,List<RptConfigInfoVO> rptConfigInfoList) {
        return requestServer(rptConfigInfoList, timingUrl, 1);
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

    /**
     * 根据标题查询数据是否已经在库中存在
     * @return 返回结果
     */
    public static Boolean dataCheck(String dataCheckUrl,String title,Date publishTime){
        log.info("dataCheck 数据对比：title【{}】,发布时间【{}】",title,publishTime);
        try {
            String result = HttpClientUtils.postForm(
                    dataCheckUrl,
                    Map.of("search_field", "1",
                            "search_type", "1",
                            "search_keyword", GsonUtil.toJsonString(List.of(title)),
                            "bid_pubtime_start", new SimpleDateFormat("yyyy-MM-dd").format(publishTime),
                            "bid_pubtime_end", new SimpleDateFormat("yyyy-MM-dd").format(publishTime)),
                    Map.of("Content-Type", "application/x-www-form-urlencoded",
                            "apikey","$2a$10$ucmK9tcKdyLpzeYYeNxBfeDWgtLjFq5UYiyLFfaJYMyHloaOi3AB."),
                    CONN_TIMEOUT,
                    READ_TIMEOUT);
            Map<String,Object> map = GsonUtil.fromJsonString(result, Map.class);
            log.info("dataCheck 数据对比接口返回结果【{}】",map);
            return Double.parseDouble(map.getOrDefault("total",0).toString())>0;

         } catch (Exception e) {
            log.error("dataCheck 数据对比接口异常【{}】",e.getMessage());
            e.printStackTrace();
        }

        return Boolean.FALSE;
    }

    /**
     * 抓取数据接口
     * @return
     */
    public static String grabData(String manualUrl,RptFindSourceDTO rptFindSourceDTO){
        try {
            log.info("爬虫竞品数据抓取参数【{}】",rptFindSourceDTO);
            String result = HttpClientUtils.postForm(
                    manualUrl,
                    grabDataPara(rptFindSourceDTO),
                    null,
                    CONN_TIMEOUT,
                    READ_TIMEOUT);
            log.info("爬虫竞品爬虫接口抓取结果【{}】",result);
            return result;
        }catch (Exception e){
            log.error("grabData 爬虫数据接口异常【{}】",e.getMessage());
            e.printStackTrace();
        }
        return Strings.EMPTY;
    }

    private static Map<String, String> grabDataPara(RptFindSourceDTO rptFindSourceDTO) {
        Map<String, String> requestPara = Maps.newHashMap();
        requestPara.put(PROJECT, "horse");
        requestPara.put(SPIDER, "qianlima3");
        requestPara.put(ORG_VALUE, GsonUtil.toJsonString(findSourceData(rptFindSourceDTO)));
        return requestPara;
    }

    private static Map<String,Object> findSourceData(RptFindSourceDTO rptFindSourceDTO){
        Map<String,Object> map = new HashMap<>();
        map.put("newAreas",Objects.requireNonNullElse(String.join(",",rptFindSourceDTO.getProvinceCode(),
                rptFindSourceDTO.getCityCode()), EMPTY));
        map.put("allType",Objects.requireNonNullElse(rptFindSourceDTO.getInfoType(),EMPTY));
        map.put("keywords",Objects.requireNonNullElse(rptFindSourceDTO.getKeywords(),EMPTY));
        map.put("timeType",Objects.requireNonNullElse(rptFindSourceDTO.getTimeType(),EMPTY));
        map.put("types",Objects.requireNonNullElse(rptFindSourceDTO.getInfoType(),EMPTY));
        return map;
    }


}
