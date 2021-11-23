package com.qk.dm.dataquality.dolphinapi.manager;

import com.qk.datacenter.api.DefaultApi;
import com.qk.datacenter.client.ApiException;
import com.qk.datacenter.model.Result;
import org.apache.dolphinscheduler.common.enums.ResourceType;
import org.apache.dolphinscheduler.dao.entity.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * DolphinScheduler 文件资源信息
 *
 * @author wjq
 * @date 2021/11/22
 * @since 1.0.0
 */
@Component
public class ResourceFileManager {


    public static void queryResourceList() {

    }


    public static Resource queryMySqlScriptResource(DefaultApi defaultApi) {
        try {
            Result result = defaultApi.queryResourceListUsingGET(ResourceType.FILE);
            ArrayList<LinkedHashMap> data = (ArrayList<LinkedHashMap>) result.getData();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
