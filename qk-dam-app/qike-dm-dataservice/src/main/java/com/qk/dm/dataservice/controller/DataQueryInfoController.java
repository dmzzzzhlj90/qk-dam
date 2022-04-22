package com.qk.dm.dataservice.controller;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataservice.service.DasDataQueryInfoService;
import com.qk.dm.dataservice.vo.DataQueryInfoVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 *
 * @author zhudaoming
 */
@RestController
public class DataQueryInfoController {
    private final DasDataQueryInfoService dasDataQueryInfoService;

    public DataQueryInfoController(DasDataQueryInfoService dasDataQueryInfoService) {
        this.dasDataQueryInfoService = dasDataQueryInfoService;
    }

    @PostMapping("/data/query/info")
    public DefaultCommonResult<List<DataQueryInfoVO>> dataQueryInfo(){
       return DefaultCommonResult.success(ResultCodeEnum.OK,
               dasDataQueryInfoService.dataQueryInfo());
    }

}
