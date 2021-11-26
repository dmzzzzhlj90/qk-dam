package com.qk.dm.dataquality.dolphinapi.manager;

import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.datacenter.api.DefaultApi;
import com.qk.datacenter.model.Result;
import com.qk.dm.dataquality.dolphinapi.constant.ResourceType;
import com.qk.dm.dataquality.dolphinapi.dto.ResourceDTO;
import com.qk.dm.dataquality.dolphinapi.dto.ResourceComponentDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DolphinScheduler 文件资源信息
 *
 * @author wjq
 * @date 2021/11/22
 * @since 1.0.0
 */
@Component
public class ResourceFileManager {

    public static final int PARENT_PID = -1;
    public static final String FULL_NAME_MYSQL = "qk_dam_dataquality/mysql/mysql_script.py";

    public static List<ResourceComponentDTO> queryResourceList(DefaultApi defaultApi) {
        List<ResourceComponentDTO> childFileDataList = null;
        try {
            Result result = defaultApi.queryResourceListUsingGET(ResourceType.FILE);
            childFileDataList = new ArrayList<>();
            Object data = result.getData();
            List<ResourceComponentDTO> resourceComponentList = GsonUtil.fromJsonString(GsonUtil.toJsonString(data), new TypeToken<List<ResourceComponentDTO>>() {
            }.getType());
            fileList(resourceComponentList, PARENT_PID, childFileDataList);
            return childFileDataList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return childFileDataList;
    }

    private static void fileList(List<ResourceComponentDTO> resourceComponentList, Integer pid, List<ResourceComponentDTO> childFileDataList) {
        for (ResourceComponentDTO resourceComponent : resourceComponentList) {
            if (pid == resourceComponent.getPid() && !resourceComponent.isDirctory()) {
                childFileDataList.add(resourceComponent);
            }
            if (null != resourceComponent.getChildren()) {
                fileList(resourceComponent.getChildren(), resourceComponent.getId(), childFileDataList);
            }
        }

    }

    public static ResourceDTO queryMySqlScriptResource(DefaultApi defaultApi) {
        List<ResourceComponentDTO> resourceComponentList = queryResourceList(defaultApi);
        List<ResourceComponentDTO> resourceComponents = resourceComponentList.stream()
                .filter(resourceComponent ->
                        resourceComponent.getFullName().equals(FULL_NAME_MYSQL))
                .collect(Collectors.toList());

        ResourceComponentDTO resourceComponent = resourceComponents.get(0);

        return ResourceDTO.builder()
                .id(resourceComponent.getId())
                .name(resourceComponent.getName())
                .res(resourceComponent.getFullName())
                .build();
    }

}
