package com.qk.dm.reptile.params.dto;

import com.qk.dam.commons.util.GsonUtil;
import com.qk.dm.reptile.params.vo.RptConfigInfoVO;
import com.qk.dm.reptile.utils.HttpClientUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 组装爬虫接口参数
 */
public class RptParaBuilder {

    public static String rptConfigInfoList(List<RptConfigInfoVO> rptConfigInfoList){
        String result = null;
        List<RptConfigBuilder> rptConfigBuilderList = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        if(!CollectionUtils.isEmpty(rptConfigInfoList)){
            map.put("mode",0);
            map.put("start_url",rptConfigInfoList.get(0).getRequestUrl());
            rptConfigInfoList.forEach(e->{
                List<RptSelectorColumnInfoDTO> selectorList = e.getSelectorList();
                Map<String,RptSelectorBuilder> rptSelectorBuilderMap = new HashMap<>();
                if(!CollectionUtils.isEmpty(selectorList)){
                    selectorList.forEach(selector->{
                        RptSelectorBuilder rptSelector = RptSelectorBuilder.builder()
                                .method(selector.getSelector())
                                .val(selector.getSelectorVal())
                                .num(selector.getElementType())
                                .build();
                        rptSelectorBuilderMap.put(selector.getColumnCode(),rptSelector);
                    });
                }
                RptConfigBuilder rptConfigBuilder = RptConfigBuilder.builder()
                        .cookies(e.getCookies())
                        .headers(e.getHeaders())
                        .ip_start(e.getStartoverIp())
                        .js_start(e.getStartoverJs())
                        .request(e.getRequestType())
                        .columns(rptSelectorBuilderMap)
                        .build();
                rptConfigBuilderList.add(rptConfigBuilder);
            });
            map.put("list", rptConfigBuilderList);

            Map<String,String> requestPara = new HashMap<>();
            requestPara.put("project","model");
            requestPara.put("spider","mode");
            requestPara.put("arg_value",GsonUtil.toJsonString(map));
            try {
                result= HttpClientUtils.postForm("http://172.21.3.202:6800/schedule.json",requestPara,null, 30000, 30000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        List<RptConfigInfoVO> rptConfigInfoList = new ArrayList<>();
        RptConfigInfoVO vo = new RptConfigInfoVO();
        vo.setRequestUrl("http://www.zfcg.edu.cn/purchase/portal/purchasetList/1");
        vo.setRequestType("GET");
        Map<String,Object> header = new HashMap<>();
        header.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36");
        vo.setHeaders(header);
        vo.setStartoverJs(0);
        vo.setStartoverIp(1);
        List<RptSelectorColumnInfoDTO> rptSelectorColumnInfoDTOList = new ArrayList<>();
        RptSelectorColumnInfoDTO dto = new RptSelectorColumnInfoDTO();
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
        List<RptSelectorColumnInfoDTO> rptSelectorColumnInfoDTOList1 = new ArrayList<>();
        RptSelectorColumnInfoDTO dto1 = new RptSelectorColumnInfoDTO();
        dto1.setSelector(0);
        dto1.setSelectorVal("//h1[@class=\"page-title\"]");
        dto1.setElementType(0);
        dto1.setColumnCode("title");
        rptSelectorColumnInfoDTOList1.add(dto1);
        vo1.setSelectorList(rptSelectorColumnInfoDTOList);
        RptSelectorColumnInfoDTO dto2 = new RptSelectorColumnInfoDTO();
        dto2.setSelector(99);
        dto2.setSelectorVal("");
        dto2.setElementType(0);
        dto2.setColumnCode("pro_name");
        rptSelectorColumnInfoDTOList1.add(dto2);
        RptSelectorColumnInfoDTO dto3 = new RptSelectorColumnInfoDTO();
        dto3.setSelector(99);
        dto3.setSelectorVal("");
        dto3.setElementType(0);
        dto3.setColumnCode("pro_no");
        rptSelectorColumnInfoDTOList1.add(dto3);
        RptSelectorColumnInfoDTO dto4 = new RptSelectorColumnInfoDTO();
        dto4.setSelector(99);
        dto4.setElementType(0);
        dto4.setColumnCode("in_type");
        rptSelectorColumnInfoDTOList1.add(dto4);
        RptSelectorColumnInfoDTO dto5 = new RptSelectorColumnInfoDTO();
        dto5.setSelector(99);
        dto5.setSelectorVal("采购公告");
        dto5.setElementType(0);
        dto5.setColumnCode("gonggao_type");
        rptSelectorColumnInfoDTOList1.add(dto5);
        RptSelectorColumnInfoDTO dto6 = new RptSelectorColumnInfoDTO();
        dto6.setSelector(1);
        dto6.setSelectorVal(">发布时间：([\\s\\S]*?)</span>");
        dto6.setElementType(0);
        dto6.setColumnCode("gonggao_date");
        rptSelectorColumnInfoDTOList1.add(dto6);
        RptSelectorColumnInfoDTO dto7 = new RptSelectorColumnInfoDTO();
        dto7.setSelector(99);
        dto7.setSelectorVal("北京");
        dto7.setElementType(0);
        dto7.setColumnCode("sheng");
        rptSelectorColumnInfoDTOList1.add(dto7);
        RptSelectorColumnInfoDTO dto8 = new RptSelectorColumnInfoDTO();
        dto8.setSelector(99);
        dto8.setSelectorVal("北京");
        dto8.setElementType(0);
        dto8.setColumnCode("shi");
        rptSelectorColumnInfoDTOList1.add(dto8);
        RptSelectorColumnInfoDTO dto9 = new RptSelectorColumnInfoDTO();
        dto9.setSelector(99);
        dto9.setSelectorVal("");
        dto9.setElementType(0);
        dto9.setColumnCode("xian");
        rptSelectorColumnInfoDTOList1.add(dto9);
        RptSelectorColumnInfoDTO dto10 = new RptSelectorColumnInfoDTO();
        dto10.setSelector(1);
        dto10.setSelectorVal("");
        dto10.setElementType(0);
        dto10.setColumnCode("url");
        rptSelectorColumnInfoDTOList1.add(dto10);
        RptSelectorColumnInfoDTO dto11 = new RptSelectorColumnInfoDTO();
        dto11.setSelector(0);
        dto11.setSelectorVal("//div[@id=\"page\"]/div[1]");
        dto11.setElementType(0);
        dto11.setColumnCode("HTML");
        rptSelectorColumnInfoDTOList1.add(dto11);
        RptSelectorColumnInfoDTO dto12 = new RptSelectorColumnInfoDTO();
        dto12.setSelector(99);
        dto12.setSelectorVal("");
        dto12.setElementType(0);
        dto12.setColumnCode("industry");
        rptSelectorColumnInfoDTOList1.add(dto12);
        RptSelectorColumnInfoDTO dto13 = new RptSelectorColumnInfoDTO();
        dto13.setSelector(99);
        dto13.setSelectorVal("教育部政府采购网");
        dto13.setElementType(0);
        dto13.setColumnCode("website");
        rptSelectorColumnInfoDTOList1.add(dto13);
        RptSelectorColumnInfoDTO dto14 = new RptSelectorColumnInfoDTO();
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
