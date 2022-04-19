package com.qk.dm.dataquality.constant;

import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 结果集数据格式_数值类型
 *
 * @author wjq
 * @date 2021/11/12
 * @since 1.0.0
 */
public enum NumberIndexEnum {

    VACANCY_RATE("空值率");

    private String value;

    NumberIndexEnum(String value) {
        this.value = value;
    }

    public static NumberIndexEnum fromValue(String value) {
        if (!ObjectUtils.isEmpty(value)) {
            for (NumberIndexEnum indexEnum : NumberIndexEnum.values()) {
                if (indexEnum.value.equals(value)) {
                    return indexEnum;
                }
            }
        }
        throw new IllegalArgumentException("Unexpected id '" + value + "'");
    }

    public static List<String> getAllValue() {
        List<String> val = new ArrayList<>();
        for (NumberIndexEnum enums : NumberIndexEnum.values()) {
            val.add(enums.value);
        }
        return val;
    }

    public String getValue() {
        return value;
    }
}
