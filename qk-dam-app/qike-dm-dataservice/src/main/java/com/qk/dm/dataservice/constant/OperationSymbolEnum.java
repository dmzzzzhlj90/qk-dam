package com.qk.dm.dataservice.constant;

import org.springframework.util.StringUtils;

import java.util.LinkedList;

/**
 * 查询条件匹配操作符号
 *
 * @author wjq
 * @date 2022/03/07
 * @since 1.0.0
 */
public enum OperationSymbolEnum {

    /**
     * 等号 =
     */
    EQUAL("="),

    /**
     * 不大于小于 <>
     */
    NO_GREATER_LESS_THAN("<>"),

    /**
     * 大于 >
     */
    MORE_THAN(">"),

    /**
     * 大于等于 >=
     */
    GREATER_EQUAL(">="),


    /**
     * 小于 <
     */
    LESS_THAN("<"),

    /**
     * 小于等于 <=
     */
    LESS_THAN_EQUAL("<="),

    /**
     * %like%
     */
    ALL_LIKE("%like%"),

    /**
     * %like
     */
    LEFT_LIKE("like%");


    private String value;

    OperationSymbolEnum(String value) {
        this.value = value;
    }

    public static OperationSymbolEnum getVal(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        for (OperationSymbolEnum enums : OperationSymbolEnum.values()) {
            if (value.equals(enums.value)) {
                return enums;
            }
        }
        return null;
    }

    public static LinkedList<String> getAllValue() {
        LinkedList<String> val = new LinkedList<>();

        for (OperationSymbolEnum enums : OperationSymbolEnum.values()) {
            val.add(enums.value);
        }
        return val;
    }

    public String getValue() {
        return value;
    }

}
