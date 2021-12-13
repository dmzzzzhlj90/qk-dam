package com.qk.dm.dataquality.rest;

import com.google.common.reflect.TypeToken;
import com.qk.dam.commons.util.GsonUtil;

import java.util.List;

/**
 * @author wjq
 * @date 2021/12/10
 * @since 1.0.0
 */
public class test {
    public static void main(String[] args) {
        String ruleResult = "[[214216, 681026, 0.3145]]";
        List<List<Integer>> resultDataList = GsonUtil.fromJsonString(ruleResult, new TypeToken<List<List<Integer>>>() {
        }.getType());
        System.out.println(resultDataList);

    }
}
