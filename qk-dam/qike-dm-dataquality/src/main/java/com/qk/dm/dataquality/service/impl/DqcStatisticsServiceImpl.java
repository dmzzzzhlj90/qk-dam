package com.qk.dm.dataquality.service.impl;

import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dm.dataquality.constant.schedule.InstanceStateTypeEnum;
import com.qk.dm.dataquality.service.DqcStatisticsService;
import com.qk.dm.dataquality.utils.RedisUtil;
import com.qk.dm.dataquality.vo.statistics.DataSummaryVO;
import com.qk.dm.dataquality.vo.statistics.DimensionVO;
import com.qk.dm.dataquality.vo.statistics.RuleDirVO;
import com.qk.dm.dataquality.vo.statistics.handler.InstanceHandler;
import com.qk.dm.dataquality.vo.statistics.handler.JobInfoHandler;
import com.qk.dm.dataquality.vo.statistics.handler.RuleTemplateHandler;
import com.qk.dm.dataquality.vo.statistics.handler.TaskInstanceHandler;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author shenpj
 * @date 2021/12/21 5:31 下午
 * @since 1.0.0
 */
@Service
public class DqcStatisticsServiceImpl implements DqcStatisticsService {
    private final TaskInstanceHandler taskInstanceHandler;
    private final RuleTemplateHandler ruleTemplateHandler;
    private final JobInfoHandler jobInfoHandler;
    private final InstanceHandler instanceHandler;
    private final RedisUtil redisUtil;

    public DqcStatisticsServiceImpl(TaskInstanceHandler taskInstanceHandler,
                                    RuleTemplateHandler ruleTemplateHandler,
                                    JobInfoHandler jobInfoHandler,
                                    InstanceHandler instanceHandler, RedisUtil redisUtil) {
        this.taskInstanceHandler = taskInstanceHandler;
        this.ruleTemplateHandler = ruleTemplateHandler;
        this.jobInfoHandler = jobInfoHandler;
        this.instanceHandler = instanceHandler;
        this.redisUtil = redisUtil;
    }

    private static final String DATA_SUMMARY_NAME = "quality-data-summary";
    private static final String DATA_DIMENSION_NAME = "quality-data-dimension";
    private static final String DATA_DIR_NAME = "quality-data-dir";
    private static final Integer TIMEOUT = 16;

    @Override
    public void timeToReis(){
        try {
            Date date = new Date();
            //统计总揽
            DataSummaryVO dataSummary = getDataSummary();
            redisUtil.setEx(DATA_SUMMARY_NAME, GsonUtil.toJsonString(dataSummary),TIMEOUT, TimeUnit.MINUTES);
            //纬度统计
            List<DimensionVO> dimensionVOList = taskInstanceHandler.dimensionStatistics(date);
            redisUtil.setEx(DATA_DIMENSION_NAME, GsonUtil.toJsonString(dimensionVOList),TIMEOUT, TimeUnit.MINUTES);
            //分类统计
            List<RuleDirVO> ruleDirVOList = instanceHandler.dirStatistics(date);
            redisUtil.setEx(DATA_DIR_NAME, GsonUtil.toJsonString(ruleDirVOList),TIMEOUT, TimeUnit.MINUTES);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private DataSummaryVO getDataSummary() {
        //实例状态统计
        return DataSummaryVO.builder()
                //规则模版统计数据
                .ruleTemplate(ruleTemplateHandler.ruleTemplateStatistics())
                //作业统计数据
                .jobInfo(jobInfoHandler.basicInfoStatistics())
                //实例统计数据
                .instance(instanceHandler.instanceStatistics())
                //实例状态统计
                .successsState(taskInstanceHandler.instanceStateStatistics(InstanceStateTypeEnum.SUCCESS.getCode()))
                //实例状态统计
                .failureState(taskInstanceHandler.instanceStateStatistics(InstanceStateTypeEnum.FAILURE.getCode()))
                .build();
    }

    @Override
    public DataSummaryVO statistics() {
        return GsonUtil.fromJsonString(redisUtil.get(DATA_SUMMARY_NAME), new TypeToken<DataSummaryVO>() {}.getType());
    }

    @Override
    public List<DimensionVO> dimensionStatistics() {
        return GsonUtil.fromJsonString(redisUtil.get(DATA_DIMENSION_NAME), new TypeToken<List<DimensionVO>>() {}.getType());
    }

    @Override
    public List<RuleDirVO> dirStatistics() {
        return GsonUtil.fromJsonString(redisUtil.get(DATA_DIR_NAME), new TypeToken<List<RuleDirVO>>() {}.getType());
    }
}
