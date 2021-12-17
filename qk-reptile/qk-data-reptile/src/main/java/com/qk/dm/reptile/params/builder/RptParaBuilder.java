package com.qk.dm.reptile.params.builder;

import com.qk.dam.commons.util.GsonUtil;
import com.qk.dm.reptile.params.vo.RptConfigInfoVO;
import com.qk.dm.reptile.params.vo.RptSelectorColumnInfoVO;
import com.qk.dm.reptile.utils.HttpClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 组装爬虫接口参数
 */
public class RptParaBuilder {
    private static final Logger LOG = LoggerFactory.getLogger(RptParaBuilder.class);

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

  public static String rptConfigInfoList(List<RptConfigInfoVO> rptConfigInfoList) {
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
        requestPara.put(ORG_VALUE,GsonUtil.toJsonString(assembleData(rptConfigInfoList)));
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
        List<RptConfigBuilder> rptConfigBuilderList = rptConfigInfoList.stream().map(RptParaBuilder::rptConfigBuilder).collect(Collectors.toList());
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

    public static void main(String[] args) {
        List<RptConfigInfoVO> rptConfigInfoList = new ArrayList<>();
        RptConfigInfoVO vo = new RptConfigInfoVO();
        vo.setRequestUrl("http://www.zfcg.edu.cn/purchase/portal/purchasetList/1");
        vo.setRequestType("GET");
        Map<String,Object> header = new HashMap<>();
        header.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36");
        vo.setHeaders(header);
        vo.setStartoverJs(0);
        vo.setStartoverIp(1);
        List<RptSelectorColumnInfoVO> rptSelectorColumnInfoDTOList = new ArrayList<>();
        RptSelectorColumnInfoVO dto = new RptSelectorColumnInfoVO();
        dto.setSelector(0);
        dto.setSelectorVal("//div[@class=\"list pagelist font\"]/ul/li/a/@href");
        dto.setElementType(0);
        dto.setColumnCode("more_url");
        rptSelectorColumnInfoDTOList.add(dto);
        vo.setSelectorList(rptSelectorColumnInfoDTOList);
        rptConfigInfoList.add(vo);


        RptConfigInfoVO vo1 = new RptConfigInfoVO();
        vo1.setRequestType("GET");
        Map<String,Object> header1 = new HashMap<>();
        header1.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36");
        vo.setHeaders(header1);
        vo1.setStartoverJs(0);
        vo1.setStartoverIp(1);
        List<RptSelectorColumnInfoVO> rptSelectorColumnInfoDTOList1 = new ArrayList<>();
        RptSelectorColumnInfoVO dto1 = new RptSelectorColumnInfoVO();
        dto1.setSelector(0);
        dto1.setSelectorVal("//h1[@class=\"page-title\"]");
        dto1.setElementType(0);
        dto1.setColumnCode("title");
        rptSelectorColumnInfoDTOList1.add(dto1);
        vo1.setSelectorList(rptSelectorColumnInfoDTOList);
        RptSelectorColumnInfoVO dto2 = new RptSelectorColumnInfoVO();
        dto2.setSelector(99);
        dto2.setSelectorVal("");
        dto2.setElementType(0);
        dto2.setColumnCode("pro_name");
        rptSelectorColumnInfoDTOList1.add(dto2);
        RptSelectorColumnInfoVO dto3 = new RptSelectorColumnInfoVO();
        dto3.setSelector(99);
        dto3.setSelectorVal("");
        dto3.setElementType(0);
        dto3.setColumnCode("pro_no");
        rptSelectorColumnInfoDTOList1.add(dto3);
        RptSelectorColumnInfoVO dto4 = new RptSelectorColumnInfoVO();
        dto4.setSelector(99);
        dto4.setElementType(0);
        dto4.setColumnCode("in_type");
        rptSelectorColumnInfoDTOList1.add(dto4);
        RptSelectorColumnInfoVO dto5 = new RptSelectorColumnInfoVO();
        dto5.setSelector(99);
        dto5.setSelectorVal("采购公告");
        dto5.setElementType(0);
        dto5.setColumnCode("gonggao_type");
        rptSelectorColumnInfoDTOList1.add(dto5);
        RptSelectorColumnInfoVO dto6 = new RptSelectorColumnInfoVO();
        dto6.setSelector(1);
        dto6.setSelectorVal(">发布时间：([\\s\\S]*?)</span>");
        dto6.setElementType(0);
        dto6.setColumnCode("gonggao_date");
        rptSelectorColumnInfoDTOList1.add(dto6);
        RptSelectorColumnInfoVO dto7 = new RptSelectorColumnInfoVO();
        dto7.setSelector(99);
        dto7.setSelectorVal("北京");
        dto7.setElementType(0);
        dto7.setColumnCode("sheng");
        rptSelectorColumnInfoDTOList1.add(dto7);
        RptSelectorColumnInfoVO dto8 = new RptSelectorColumnInfoVO();
        dto8.setSelector(99);
        dto8.setSelectorVal("北京");
        dto8.setElementType(0);
        dto8.setColumnCode("shi");
        rptSelectorColumnInfoDTOList1.add(dto8);
        RptSelectorColumnInfoVO dto9 = new RptSelectorColumnInfoVO();
        dto9.setSelector(99);
        dto9.setSelectorVal("");
        dto9.setElementType(0);
        dto9.setColumnCode("xian");
        rptSelectorColumnInfoDTOList1.add(dto9);
        RptSelectorColumnInfoVO dto10 = new RptSelectorColumnInfoVO();
        dto10.setSelector(1);
        dto10.setSelectorVal("");
        dto10.setElementType(0);
        dto10.setColumnCode("url");
        rptSelectorColumnInfoDTOList1.add(dto10);
        RptSelectorColumnInfoVO dto11 = new RptSelectorColumnInfoVO();
        dto11.setSelector(0);
        dto11.setSelectorVal("//div[@id=\"page\"]/div[1]");
        dto11.setElementType(0);
        dto11.setColumnCode("HTML");
        rptSelectorColumnInfoDTOList1.add(dto11);
        RptSelectorColumnInfoVO dto12 = new RptSelectorColumnInfoVO();
        dto12.setSelector(99);
        dto12.setSelectorVal("");
        dto12.setElementType(0);
        dto12.setColumnCode("industry");
        rptSelectorColumnInfoDTOList1.add(dto12);
        RptSelectorColumnInfoVO dto13 = new RptSelectorColumnInfoVO();
        dto13.setSelector(99);
        dto13.setSelectorVal("教育部政府采购网");
        dto13.setElementType(0);
        dto13.setColumnCode("website");
        rptSelectorColumnInfoDTOList1.add(dto13);
        RptSelectorColumnInfoVO dto14 = new RptSelectorColumnInfoVO();
        dto14.setSelector(99);
        dto14.setSelectorVal("");
        dto14.setElementType(0);
        dto14.setColumnCode("classify_show");
        rptSelectorColumnInfoDTOList1.add(dto14);
        vo1.setSelectorList(rptSelectorColumnInfoDTOList1);
        rptConfigInfoList.add(vo1);
//        Map<String, Object> map = rptConfigInfoList(rptConfigInfoList);
//        Map<String,String> map1 = new HashMap<>();
//        map1.put("project","model");
//        map1.put("spider","mode");
//        map1.put("arg_value",GsonUtil.toJsonString(map));
//        String str= HttpClientUtils.postForm("http://172.21.3.202:6800/schedule.json",map1,null, 30000, 30000);
        System.out.println(rptConfigInfoList(rptConfigInfoList));

    }
}
