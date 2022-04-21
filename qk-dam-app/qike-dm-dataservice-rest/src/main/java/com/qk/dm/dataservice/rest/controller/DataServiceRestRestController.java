package com.qk.dm.dataservice.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.model.HttpDataParamModel;
import com.qk.dm.dataquery.feign.DataBackendQueryFeign;
import com.qk.dm.dataservice.rest.controller.base.BaseRestController;
import com.qk.dm.dataservice.vo.DataQueryInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据查询服务接口层
 *
 * @author zhudaoming
 */
@Slf4j
@RestController
public class DataServiceRestRestController extends BaseRestController {

    final DataBackendQueryFeign dataBackendQueryFeign;


    public DataServiceRestRestController(DataBackendQueryFeign dataBackendQueryFeign) {
        this.dataBackendQueryFeign = dataBackendQueryFeign;
    }



    /**
     * 处理所有rest请求的handler
     *
     * @param queryInfoVO            请求 queryInfoVO
     * @param httpDataParamModel http传递的参数集合
     */
    @Override
    protected Object restHandler(DataQueryInfoVO queryInfoVO, HttpDataParamModel httpDataParamModel) {
        String apiId = queryInfoVO.getDasApiBasicInfo().getApiId();
        httpDataParamModel.setApiId(apiId);

        try {
            ObjectMapper om = new ObjectMapper();
            return om.readTree(dataBackendQueryFeign.dataBackendQuery(httpDataParamModel));
        } catch (Exception e) {
            throw new BizException(e);
        }
    }


}
