package com.qk.dm.datacollect.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;
import java.util.Map;

import static com.qk.dam.datasource.utils.ConnectInfoConvertUtils.objectMapper;

/**
 * 数据质量_规则分类目录
 *
 * @author wjq
 * @date 2021/11/12
 * @since 1.0.0
 */
public class DctConstant {
    /**
     * 拼装参数
     */
    private static final String SCHEDULE_TIME_START = "startTime";
    private static final String SCHEDULE_TIME_END = "endTime";
    private static final String SCHEDULE_CRON = "crontab";

    public static <T> T changeObjectToClass(Object data, Class<T> clazz) throws JsonProcessingException {
        return objectMapper.readValue(objectMapper.writeValueAsString(data), clazz);
    }

    public static String schedule(Date effectiveTimeStart, Date effectiveTimeEnt, String cron) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> m = Map.of(
                SCHEDULE_TIME_START,
                DateUtil.toStr(effectiveTimeStart),
                SCHEDULE_TIME_END,
                DateUtil.toStr(effectiveTimeEnt),
                SCHEDULE_CRON,
                cron,
                "timezoneId",
                "Asia/Shanghai");
        try {
            return objectMapper.writeValueAsString(m);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
