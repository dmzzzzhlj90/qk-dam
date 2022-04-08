package com.qk.dm.datamodel.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.datamodel.service.CommonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 数据模型公共接口
 */
@RequestMapping("/common")
@RestController
public class CommonController {
    private final CommonService commonService;

    public CommonController(CommonService commonService) {
        this.commonService = commonService;
    }

    /**
     * 获取所有的数据类型
     * @return DefaultCommonResult<Map<String, String>>
     */
    @GetMapping(value = "/data/type")
    public DefaultCommonResult<Map<String, String>> getDataType() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, commonService.getDataType());
    }
}
