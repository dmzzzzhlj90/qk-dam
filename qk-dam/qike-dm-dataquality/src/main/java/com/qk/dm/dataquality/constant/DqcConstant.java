package com.qk.dm.dataquality.constant;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.datacenter.model.Result;
import com.qk.datacenter.model.ResultProcessInstance;
import com.qk.datacenter.model.Resultstring;
import com.qk.dm.dataquality.utils.DateUtil;
import org.springframework.util.ObjectUtils;

import java.util.*;

import static com.qk.dam.datasource.utils.ConnectInfoConvertUtils.objectMapper;

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

    public static void verification(Resultstring result, String output) {
        if (result.getCode() != null && !result.getCode().equals(DqcConstant.RESULT_CODE)) {
            throw new BizException(output + result.getMsg());
        }
    }

    public static void verification(ResultProcessInstance result, String output) {
        if (result.getCode() != null && !result.getCode().equals(DqcConstant.RESULT_CODE)) {
            throw new BizException(output + result.getMsg());
        }
    }

    public static void printException(Exception e) {
        e.printStackTrace();
        throw new BizException("dolphin出错{}," + e.getMessage());
    }

    public static <T> T changeObjectToClass(Object data, Class<T> clazz) throws JsonProcessingException {
        return objectMapper.readValue(objectMapper.writeValueAsString(data), clazz);
    }

    public static String schedule(Date effectiveTimeStart, Date effectiveTimeEnt, String cron) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> m = Map.of(SCHEDULE_TIME_START, DateUtil.toStr(effectiveTimeStart),
                SCHEDULE_TIME_END, DateUtil.toStr(effectiveTimeEnt),
                SCHEDULE_CRON, cron,
                "timezoneId", "Asia/Shanghai");
        try {
            return objectMapper.writeValueAsString(m);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> jsonStrToList(String jsonStr) {
        List<String> list = new ArrayList<>();
        //todo 临时加null 此处需要优化
        if (!"null".equals(jsonStr) && !ObjectUtils.isEmpty(jsonStr)) {
            list = GsonUtil.fromJsonString(jsonStr, new TypeToken<List<String>>() {
            }.getType());
        }
        return list;
    }

    public static void main(String[] args) {
        String a = "[\"MYSQL\",\"HIVE\"]";
        jsonStrToList(a);
    }
}
