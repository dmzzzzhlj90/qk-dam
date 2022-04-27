package com.qk.dm.datacollect.vo;

import com.qk.dam.commons.util.GsonUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shenpj
 * @date 2022/4/21 17:44
 * @since 1.0.0
 */
@Data
public class HttpParamsVO {
    String prop;
    String httpParametersType = "BODY";
    String value;

    public HttpParamsVO(String prop, String httpParametersType, String value) {
        this.prop = prop;
        this.httpParametersType = httpParametersType;
        this.value = value;
    }

    public HttpParamsVO(String prop, String value) {
        this.prop = prop;
        this.value = value;
    }

    public static List<HttpParamsVO> createList(DctSchedulerBasicInfoVO dctSchedulerBasicInfoVO) {
        List<HttpParamsVO> list = new ArrayList<>();
        list.add(new HttpParamsVO("dirId", dctSchedulerBasicInfoVO.getDirId()));
        list.add(new HttpParamsVO("schedulerRules", GsonUtil.toJsonString(dctSchedulerBasicInfoVO.getSchedulerRules())));
        list.add(new HttpParamsVO("schedulerConfig", GsonUtil.toJsonString(dctSchedulerBasicInfoVO.getSchedulerConfig())));
        return list;
    }

}
