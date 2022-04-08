package com.qk.dm.datamodel.service.impl;

import com.qk.dam.commons.enums.DataTypeEnum;
import com.qk.dm.datamodel.service.CommonService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CommonServiceImpl implements CommonService {
    @Override
    public Map<String, String> getDataType() {
        return DataTypeEnum.getAllValue();
    }
}
