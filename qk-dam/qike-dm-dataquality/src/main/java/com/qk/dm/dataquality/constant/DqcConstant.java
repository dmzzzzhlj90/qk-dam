package com.qk.dm.dataquality.constant;

import java.util.*;

/**
 * 数据质量_规则分类目录
 *
 * @author wjq
 * @date 2021/11/12
 * @since 1.0.0
 */
public class DqcConstant {
    /**
     * 目录顶级层级父级id
     */
    public static final String TREE_DIR_TOP_PARENT_ID = "-1";

    public static final Integer INIT_STATE = 0;
    public static final Integer STOP_STATE = 3;
    public static final Integer RUN_TYPE = 2;

    /**
     * 提示级别
     */
    public static Map<String, String> getNotifyLevelMap() {
        LinkedHashMap<String, String> notifyLevelMap = new LinkedHashMap<>();
        notifyLevelMap.put("0", "提示");
        notifyLevelMap.put("1", "一般");
        notifyLevelMap.put("2", "严重");
        notifyLevelMap.put("3", "致命");
        return notifyLevelMap;
    }

    /**
     * 通知状态
     */
    public static Map<String, String> getNotifyStateMap() {
        LinkedHashMap<String, String> notifyStateMap = new LinkedHashMap<>();
        notifyStateMap.put("0", "关");
        notifyStateMap.put("1", "开");
        return notifyStateMap;
    }

    // API类型
    public static List<Map<String, String>> getApiType() {
        List<Map<String, String>> apiTypeList = new ArrayList();
        Map<String, String> registerTypeMap = new HashMap<>();
        registerTypeMap.put("code", "REGISTER-API");
        registerTypeMap.put("value", "注册API");
        apiTypeList.add(registerTypeMap);

        Map<String, String> dmSourceMap = new HashMap<>();
        dmSourceMap.put("code", "CREATE-API");
        dmSourceMap.put("value", "新建API");
        apiTypeList.add(dmSourceMap);
        return apiTypeList;
    }

    // 新建API_字段操作符号
    public static List<String> getDasApiCreateParasCompareSymbol() {
        LinkedList<String> compareSymbolList = new LinkedList<>();
        compareSymbolList.add("=");
        compareSymbolList.add("<>");
        compareSymbolList.add(">");
        compareSymbolList.add(">=");
        compareSymbolList.add("<");
        compareSymbolList.add("<=");
        compareSymbolList.add("%like%");
        compareSymbolList.add("%like");
        return compareSymbolList;
    }

}
