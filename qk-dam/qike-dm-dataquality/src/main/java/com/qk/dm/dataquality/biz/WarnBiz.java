package com.qk.dm.dataquality.biz;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dm.dataquality.entity.DqcSchedulerResult;
import com.qk.dm.dataquality.service.DqcSchedulerResultDataService;
import com.qk.dm.dataquality.service.DqcSchedulerRulesService;
import com.qk.dm.dataquality.vo.DqcProcessInstanceVO;
import com.qk.dm.dataquality.vo.statistics.JobInfoVO;
import com.qk.dm.dataquality.vo.statistics.WarnTrendVO;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author shenpj
 * @date 2022/1/6 2:44 下午
 * @since 1.0.0
 */
@Component
public class WarnBiz {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private final DqcSchedulerRulesService dqcSchedulerRulesService;
    private final RedisBiz redisBiz;
    private final DqcSchedulerResultDataService dqcSchedulerResultDataService;

    public WarnBiz(DqcSchedulerRulesService dqcSchedulerRulesService,
                   RedisBiz redisBiz,
                   DqcSchedulerResultDataService dqcSchedulerResultDataService) {
        this.dqcSchedulerRulesService = dqcSchedulerRulesService;
        this.redisBiz = redisBiz;
        this.dqcSchedulerResultDataService = dqcSchedulerResultDataService;
    }

    /**
     * 实例列表
     *
     * @return
     */
    private List<DqcProcessInstanceVO> getDolphinInstanceList() {
        return GsonUtil.fromJsonString(redisBiz.redisInstanceList(null, null), new TypeToken<List<DqcProcessInstanceVO>>() {
        }.getType());
    }

    /**
     * 所有警告列表
     *
     * @return
     */
    public List<DqcSchedulerResult> warnResultList() {
        //todo 直接从数据库中查询所有告警数据或者从查询出的流程结果中查询
        return dqcSchedulerResultDataService.getSchedulerResultListByWarn(RedisBiz.warnResult);
//        List<DqcProcessInstanceVO> dqcProcessInstanceVOS = getDolphinInstanceList();
//        //传入实例摘取警告列表
//        return GsonUtil.fromJsonString(redisHandler.redisWarnResultList(dqcProcessInstanceVOS), new TypeToken<List<DqcSchedulerResult>>() {}.getType());
    }

    /**
     * 警告统计数据
     *
     * @return
     */
    public JobInfoVO warnStatistics() {
        List<DqcSchedulerResult> list = warnResultList();
        Set<Long> taskCodeSet = list.stream().map(DqcSchedulerResult::getTaskCode).collect(Collectors.toSet());
        return JobInfoVO.builder()
                .count(list.size())
                .tableCount(dqcSchedulerRulesService.getTableSet(taskCodeSet))
                .fieldCount(dqcSchedulerRulesService.getFieldSet(taskCodeSet))
                .build();
    }

    /**
     * 根据时间查询警告列表
     *
     * @param date
     * @return
     */
    public List<DqcSchedulerResult> warnResultList(Date date) {
        List<DqcSchedulerResult> list = warnResultList();
        //过滤出今日警告列表
        Date startTime = DateUtil.beginOfDay(date);
        Date endTime = DateUtil.endOfDay(date);
        return list.stream().filter(it -> it.getGmtCreate().compareTo(startTime) >= 0 && it.getGmtCreate().compareTo(endTime) <= 0).collect(Collectors.toList());
    }


    public List<WarnTrendVO> warnTrendStatistics() {
        Date date = new Date();
        //查询一周内的所有告警
        DateTime beginOfDay = DateUtil.offsetDay(DateUtil.beginOfDay(date), -6);
        DateTime endOfDay = DateUtil.endOfDay(date);
        List<DqcSchedulerResult> schedulerResultListByWarnTrend = dqcSchedulerResultDataService.getSchedulerResultListByWarnTrend(RedisBiz.warnResult, beginOfDay, endOfDay);
        //获取最近七天数据
        Map<String, Long> warnTrendCountMap = schedulerResultListByWarnTrend.stream().collect(Collectors.groupingBy(item -> sdf.format(item.getGmtCreate()), Collectors.counting()));
        return getDatesByDay(beginOfDay, endOfDay).keySet().stream().map(integer -> {
            return WarnTrendVO
                    .builder()
                    .Date(integer)
                    .scales(warnTrendCountMap.get(integer) != null ? warnTrendCountMap.get(integer).intValue() : 0)
                    .build();
        }).collect(Collectors.toList());
    }

    /**
     * 获取两个日期之间的所有日期
     *
     * @return
     */
    public static Map<String, Integer> getDatesByDay(Date date, Date dateEnd) {
        //保存日期的集合 
        Map<String, Integer> map = new LinkedHashMap<>();
        //用Calendar 进行日期比较判断
        Calendar cd = Calendar.getInstance();
        while (date.getTime() <= dateEnd.getTime()) {
            map.put(sdf.format(date), 0);
            cd.setTime(date);
            //增加一天 放入集合
            cd.add(Calendar.DATE, 1);
            date = cd.getTime();
        }
        return map;
    }
}
