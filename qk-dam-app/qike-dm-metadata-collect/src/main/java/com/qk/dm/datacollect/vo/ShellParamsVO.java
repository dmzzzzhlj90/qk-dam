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
public class ShellParamsVO {
    String prop;
    String direct = "IN";
    String type = "VARCHAR";
    String value;

    public ShellParamsVO(String prop, String value) {
        this.prop = prop;
        this.value = value;
    }

    public static List<ShellParamsVO> createList(DctSchedulerBasicInfoVO dctSchedulerBasicInfoVO) {
        List<ShellParamsVO> list = new ArrayList<>();
        list.add(new ShellParamsVO("dirId", dctSchedulerBasicInfoVO.getDirId()));
        list.add(new ShellParamsVO("schedulerRules", GsonUtil.toJsonString(dctSchedulerBasicInfoVO.getSchedulerRules())));
        list.add(new ShellParamsVO("schedulerConfig", GsonUtil.toJsonString(dctSchedulerBasicInfoVO.getSchedulerConfig())));
        return list;
    }

}
