package com.qk.dm.dataquality.constant;

import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 通知状态
 *
 * @author wjq
 * @date 2021/11/12
 * @since 1.0.0
 */
public enum NotifyStateEnum {

    /**
     * 通知状态 "CLOSE":"关","OPEN":"开";
     */
    CLOSE("CLOSE", "关",false),
    OPEN("OPEN", "开",true);

    private String code;
    private String name;
    private Boolean state;

    NotifyStateEnum(String code, String name, Boolean state) {
        this.code = code;
        this.name = name;
        this.state = state;
    }

    public static Map<String, String> getAllValue() {
        Map<String, String> val = new HashMap<>();
        for (NotifyStateEnum enums : NotifyStateEnum.values()) {
            val.put(enums.code, enums.name);
        }
        return val;
    }

    /**
     * 数据库转换类型
     * @param state
     * @return
     */
    public static String conversionType(Boolean state){
        for (NotifyStateEnum enums : NotifyStateEnum.values()) {
            if (state.equals(enums.state)) {
                return enums.code;
            }
        }
        throw new IllegalArgumentException("Unexpected id '" + state + "'");
    }

    public static Boolean conversionType(String code){
        for (NotifyStateEnum enums : NotifyStateEnum.values()) {
            if (code.equals(enums.code)) {
                return enums.state;
            }
        }
        throw new IllegalArgumentException("Unexpected id '" + code + "'");
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
