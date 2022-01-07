package com.qk.dm.dataquality.vo.statistics.handler;

import cn.hutool.core.date.DateUtil;
import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dm.dataquality.entity.DqcSchedulerResult;
import com.qk.dm.dataquality.service.DqcSchedulerResultDataService;
import com.qk.dm.dataquality.service.DqcSchedulerRulesService;
import com.qk.dm.dataquality.vo.DqcProcessInstanceVO;
import com.qk.dm.dataquality.vo.statistics.JobInfoVO;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author shenpj
 * @date 2022/1/6 2:44 下午
 * @since 1.0.0
 */
@Component
public class WarnHandler {

    private final DqcSchedulerRulesService dqcSchedulerRulesService;
    private final RedisHandler redisHandler;
    private final DqcSchedulerResultDataService dqcSchedulerResultDataService;

    public WarnHandler(DqcSchedulerRulesService dqcSchedulerRulesService,
                       RedisHandler redisHandler,
                       DqcSchedulerResultDataService dqcSchedulerResultDataService) {
        this.dqcSchedulerRulesService = dqcSchedulerRulesService;
        this.redisHandler = redisHandler;
        this.dqcSchedulerResultDataService = dqcSchedulerResultDataService;
    }

    /**
     * 实例列表
     * @return
     */
    private List<DqcProcessInstanceVO> getDolphinInstanceList() {
        return GsonUtil.fromJsonString(redisHandler.redisInstanceList(null, null), new TypeToken<List<DqcProcessInstanceVO>>() {}.getType());
    }

    /**
     * 所有警告列表
     * @return
     */
    public List<DqcSchedulerResult> warnResultList() {
        //todo 直接从数据库中查询所有告警数据或者从查询出的流程结果中查询
        return dqcSchedulerResultDataService.getSchedulerResultListByWarn();
//        List<DqcProcessInstanceVO> dqcProcessInstanceVOS = getDolphinInstanceList();
//        //传入实例摘取警告列表
//        return GsonUtil.fromJsonString(redisHandler.redisWarnResultList(dqcProcessInstanceVOS), new TypeToken<List<DqcSchedulerResult>>() {}.getType());
    }

    /**
     * 警告统计数据
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
}
