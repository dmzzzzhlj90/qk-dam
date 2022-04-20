package com.qk.dm.dataservice.constant;

import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * 新建API_请求参数表头信息
 *
 * @author wjq
 * @date 20220221
 * @since 1.0.0
 */
public enum CreateRequestParamHeaderInfoEnum {

    /**
     * 绑定参数
     */
    PARA_NAME("paraName", "绑定参数",true),

    /**
     * 绑定字段
     */
    MAPPING_NAME("mappingName", "绑定字段",true),

    /**
     * 操作符
     */
    CONDITION_TYPE("conditionType", "操作符",true),

    /**
     * 后端参数
     */
    BACKEND_PARA_NAME("backendParaName", "后端参数",true),

    /**
     * 后端参数位置
     */
    BACKEND_PARA_POSITION("backendParaPosition", "后端参数位置",true);


    private String key;
    private String value;
    private boolean required;

    CreateRequestParamHeaderInfoEnum(String key, String value, boolean required) {
        this.key = key;
        this.value = value;
        this.required = required;
    }

    public static CreateRequestParamHeaderInfoEnum getVal(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        for (CreateRequestParamHeaderInfoEnum enums : CreateRequestParamHeaderInfoEnum.values()) {
            if (value.equals(enums.value)) {
                return enums;
            }
        }
        return null;
    }

    public static LinkedList<Map<String, Object>> getAllValue() {
        LinkedList<Map<String, Object>> valList = new LinkedList<>();

        for (CreateRequestParamHeaderInfoEnum enums : CreateRequestParamHeaderInfoEnum.values()) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put(DasConstant.PARAM_KEY, enums.key);
            map.put(DasConstant.PARAM_VALUE, enums.value);
            map.put(DasConstant.PARAM_REQUIRED, enums.required);
            valList.add(map);
        }
        return valList;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public boolean isRequired() {
        return required;
    }
}
