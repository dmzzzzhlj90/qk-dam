package com.qk.dm.dataquality.dolphinapi.service.impl;

import com.qk.datacenter.api.DefaultApi;
import com.qk.datacenter.client.ApiClient;
import com.qk.datacenter.client.Configuration;
import com.qk.dm.dataquality.dolphinapi.service.ProcessDefinitionApiService;
import com.qk.dm.dataquality.vo.DqcSchedulerInfoVO;
import org.springframework.stereotype.Service;

/**
 * @author wjq
 * @date 2021/11/16
 * @since 1.0.0
 */
@Service
public class ProcessDefinitionApiServiceImpl implements ProcessDefinitionApiService {

    @Override
    public void save(DqcSchedulerInfoVO dqcSchedulerInfoVO) {
        //构建ProcessData对象


        //构建规则流程实例

        //构建同步条件流程实例

        //构建回调接口流程实例

        //创建工作流实例
        ApiClient defaultClient = Configuration.getDefaultApiClient().setRequestInterceptor((r)->{
            r.header("token","2b29f18d15f3be6642814355f3dc9229");
        });
//        DefaultApi apiInstance = new DefaultApi(defaultClient);
//
//        apiInstance.createProcessDefinitionUsingPOST();

    }
}
