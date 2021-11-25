package com.qk.dm.dataquality.dolphinapi.manager;

import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.datacenter.api.DefaultApi;
import com.qk.datacenter.client.ApiException;
import com.qk.datacenter.model.Result;
import com.qk.dm.dataquality.dolphinapi.dto.ResourceComponentDTO;
import com.qk.dm.dataquality.dolphinapi.dto.TenantDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DolphinScheduler 租户信息
 *
 * @author wjq
 * @date 2021/11/23
 * @since 1.0.0
 */
@Component
public class TenantManager {

    public static final String TENANT_ROOT = "root";

    public static List<TenantDTO> queryTenantInfoList(DefaultApi defaultApi) {
        List<TenantDTO> tenantDTOList = null;
        try {
            Result result = defaultApi.queryTenantlistUsingGET();
            Object data = result.getData();
            tenantDTOList = GsonUtil.fromJsonString(GsonUtil.toJsonString(data), new TypeToken<List<TenantDTO>>() {
            }.getType());

        } catch (ApiException e) {
            e.printStackTrace();
        }
        return tenantDTOList;
    }

    public static TenantDTO queryTenantInfo(DefaultApi defaultApi) {
        List<TenantDTO> tenantDTOList = queryTenantInfoList(defaultApi);
        List<TenantDTO> tenantDTOs = tenantDTOList.stream().filter(tenantDTO -> tenantDTO.getTenantCode().equals(TENANT_ROOT)).collect(Collectors.toList());
        return tenantDTOs.get(0);
    }
}
