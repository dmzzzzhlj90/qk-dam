package com.qk.dm.dataservice.service.imp;

import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.qk.dm.dataservice.service.DataService;
import com.qk.dm.dataservice.utils.RestTemplateUtil;
import com.qk.dm.dataservice.vo.DataPushVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author shen
 * @version 1.0
 * @description:
 * @date 2021-07-12 13:57
 */
@Service
public class DataServiceImp implements DataService {

    @Autowired
    RestTemplateUtil restTemplateUtil;

    private static final Log LOG = LogFactory.get("数据转换");

    private static final String url = "http://172.31.0.80:9080/qike/datapush";

    @Override
    public String dataPush(DataPushVO dataPushVO) {
        return restTemplateUtil.postByRestTemplate(url, JSONUtil.parseObj(dataPushVO));
    }
}
