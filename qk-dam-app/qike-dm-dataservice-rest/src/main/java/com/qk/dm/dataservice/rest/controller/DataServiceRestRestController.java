package com.qk.dm.dataservice.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.model.HttpDataParamModel;
import com.qk.dm.dataquery.feign.DataBackendQueryFeign;
import com.qk.dm.dataservice.rest.controller.base.BaseRestController;
import com.qk.dm.dataservice.vo.DasApiBasicInfoVO;
import com.qk.dm.dataservice.vo.DataQueryInfoVO;
import com.qk.dm.feign.DataQueryInfoFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 数据查询服务接口层
 *
 * @author zhudaoming
 */
@Slf4j
@RestController
public class DataServiceRestRestController extends BaseRestController {

    final DataBackendQueryFeign dataBackendQueryFeign;
    final DataQueryInfoFeign dataQueryInfoFeign;

    private final List<DataQueryInfoVO> dataQueryInfoVOList = new ArrayList<>();

    public DataServiceRestRestController(DataBackendQueryFeign dataBackendQueryFeign, DataQueryInfoFeign dataQueryInfoFeign) {
        this.dataBackendQueryFeign = dataBackendQueryFeign;
        this.dataQueryInfoFeign = dataQueryInfoFeign;
    }

    @PostConstruct
    public void init(){
        DefaultCommonResult<List<DataQueryInfoVO>> listDefaultCommonResult = dataQueryInfoFeign.dataQueryInfo();
        dataQueryInfoVOList.addAll(listDefaultCommonResult.getData());
    }

    /**
     * 处理所有rest请求的handler
     *
     * @param request            请求 request
     * @param httpDataParamModel http传递的参数集合
     */
    @Override
    protected Object restHandler(HttpServletRequest request, HttpDataParamModel httpDataParamModel) {
        ObjectMapper om = new ObjectMapper();

        DataQueryInfoVO queryInfoVO = dataQueryInfoVOList.stream().filter(dataQueryInfoVO ->
                {
                    DasApiBasicInfoVO dasApiBasicInfo = dataQueryInfoVO.getDasApiBasicInfo();
                    return request.getMethod().equals(dasApiBasicInfo.getRequestType())
                            &&
                            matchUriPath(request.getRequestURI(),dasApiBasicInfo.getApiPath());
                }

        ).findFirst().orElse(null);

        Objects.requireNonNull(queryInfoVO);

        String apiId = queryInfoVO.getDasApiBasicInfo().getApiId();
        httpDataParamModel.setApiId(apiId);

        try {
            return om.readTree(dataBackendQueryFeign.dataBackendQuery(httpDataParamModel));
        } catch (Exception e) {
            throw new BizException(e);
        }
    }


}
