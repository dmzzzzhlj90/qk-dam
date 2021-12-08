package com.qk.dm.dataquality.constant;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.datacenter.client.ApiException;
import com.qk.datacenter.model.Result;
import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * 数据质量_规则分类目录
 *
 * @author wjq
 * @date 2021/11/12
 * @since 1.0.0
 */
public class DqcConstant {
    public static final String format = "yyyy-MM-dd HH:mm:ss";
    /**
     * 目录顶级层级父级id
     */
    public static final String TREE_DIR_TOP_PARENT_ID = "-1";
    /**
     * 删除状态- 保留
     */
    public static final Integer DEL_FLAG_RETAIN = 0;
    /**
     * 删除状态 删除
     */
    public static final Integer DEL_FLAG_DEL = 1;
    /**
     * 调度返回code
     */
    public static final Integer RESULT_CODE = 0;
    /**
     * 日志分页
     */
    public static final int LIMIT = 100;

    /**
     * 拼装参数
     */
    private static final String SCHEDULE_TIME_START = "startTime";
    private static final String SCHEDULE_TIME_END = "endTime";
    private static final String SCHEDULE_CRON = "crontab";

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

    public static void verification(Result result, String output) {
        if (result.getCode() != null && !result.getCode().equals(DqcConstant.RESULT_CODE)) {
            throw new BizException(output + result.getMsg());
        }
    }

    public static void printException(ApiException e) {
        System.err.println("Exception when calling DefaultApi");
        System.err.println("Status code: " + e.getCode());
        System.err.println("Reason: " + e.getResponseBody());
        System.err.println("Response headers: " + e.getResponseHeaders());
        e.printStackTrace();
    }

    public static String schedule(Date effectiveTimeStart, Date effectiveTimeEnt, String cron) {
        JSONObject object = new JSONObject();
        object.put(SCHEDULE_TIME_START, DateUtil.format(effectiveTimeStart, format));
        object.put(SCHEDULE_TIME_END, DateUtil.format(effectiveTimeEnt, format));
        object.put(SCHEDULE_CRON, cron);
        return object.toJSONString();
    }

    public static List<String> jsonStrToList(String jsonStr) {
        if (!ObjectUtils.isEmpty(jsonStr)) {
            return GsonUtil.fromJsonString(jsonStr, new TypeToken<List<String>>() {}.getType());
        }
        return null;
    }

    public static void main(String[] args) {
        String a = "[\"MYSQL\",\"HIVE\"]";
        jsonStrToList(a);
    }
}
