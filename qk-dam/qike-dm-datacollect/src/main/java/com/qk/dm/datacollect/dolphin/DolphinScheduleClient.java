package com.qk.dm.datacollect.dolphin;

import com.dolphinscheduler.client.DolphinschedulerManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qk.datacenter.client.ApiException;
import com.qk.datacenter.model.Result;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author shenpj
 * @date 2022/4/19 17:46
 * @since 1.0.0
 */
public class DolphinScheduleClient {
    private final DolphinschedulerManager dolphinschedulerManager;

    public DolphinScheduleClient(DolphinschedulerManager dolphinschedulerManager) {
        this.dolphinschedulerManager = dolphinschedulerManager;
    }



    /**
     * 创建定时
     */
    public void create(Long processDefinitionCode,
                       Long projectCode,
                       Date effectiveTimeStart,
                       Date effectiveTimeEnt,
                       String cron) throws ApiException {
        Result result = null;
        result =
                dolphinschedulerManager.defaultApi().createScheduleUsingPOST(
                        processDefinitionCode,
                        projectCode,
                        null,
                        "CONTINUE",
                        "MEDIUM",
                        // 拼接定时时间
                        schedule(effectiveTimeStart, effectiveTimeEnt, cron),
                        0,
                        "NONE",
                        "default");
        if (Boolean.TRUE.equals(result.getFailed())){
            throw new ApiException(400, result.getMsg());
        }
    }

    /**
     * 拼装参数
     */
    private static final String SCHEDULE_TIME_START = "startTime";
    private static final String SCHEDULE_TIME_END = "endTime";
    private static final String SCHEDULE_CRON = "crontab";
    public static String schedule(Date effectiveTimeStart, Date effectiveTimeEnt, String cron) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> m = Map.of(SCHEDULE_TIME_START, toStr(effectiveTimeStart),
                SCHEDULE_TIME_END, toStr(effectiveTimeEnt),
                SCHEDULE_CRON, cron,
                "timezoneId", "Asia/Shanghai");
        try {
            return objectMapper.writeValueAsString(m);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Date 转 String
     *
     * @param time
     * @return
     */
    public static String toStr(Date time) {
        if (time == null) {
            return null;
        }
        return toStr(time, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 根据格式，Date 转 string
     *
     * @param time
     * @param format
     * @return
     */
    public static String toStr(Date time, String format) {
        SimpleDateFormat df = null;
        if (null == format) {
            df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        } else {
            df = new SimpleDateFormat(format);
        }
        try {
            return df.format(time);
        } catch (Exception e) {
            return null;
        }
    }
}
