package com.qk.dm.dataservice.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.model.HttpDataParamModel;
import com.qk.dm.dataquery.feign.DataBackendQueryFeign;
import com.qk.dm.dataservice.service.DataBackendQuerySerivce;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author zhudaoming
 */
@Service
@RequiredArgsConstructor
public class DataBackendQuerySerivceImpl implements DataBackendQuerySerivce {
    final DataBackendQueryFeign dataBackendQueryFeign;

    @Override
    public Object dataBackendQuery(HttpDataParamModel httpDataParamModel) {
        Object data;
        try {
            if (httpDataParamModel.getResultDataType() != 1) {
                // 单值
                data = dataBackendQueryFeign.dataBackendQueryOne(httpDataParamModel);
            } else {
                // 多值
                data = dataBackendQueryFeign.dataBackendQuery(httpDataParamModel);
            }
        } catch (Exception e) {
            throw new BizException(e);
        }
        return data;
    }
}
