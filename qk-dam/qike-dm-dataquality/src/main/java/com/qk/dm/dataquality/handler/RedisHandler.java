package com.qk.dm.dataquality.handler;

import cn.hutool.core.date.DateUtil;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dm.dataquality.constant.schedule.InstanceStateTypeEnum;
import com.qk.dm.dataquality.dolphinapi.dto.ProcessInstanceResultDTO;
import com.qk.dm.dataquality.dolphinapi.dto.ProcessInstanceSearchDTO;
import com.qk.dm.dataquality.dolphinapi.dto.ProcessTaskInstanceResultDTO;
import com.qk.dm.dataquality.dolphinapi.dto.ProcessTaskInstanceSearchDTO;
import com.qk.dm.dataquality.entity.DqcSchedulerBasicInfo;
import com.qk.dm.dataquality.entity.DqcSchedulerResult;
import com.qk.dm.dataquality.mapstruct.mapper.DqcProcessInstanceMapper;
import com.qk.dm.dataquality.service.DqcSchedulerBasicInfoService;
import com.qk.dm.dataquality.service.DqcSchedulerResultDataService;
import com.qk.dm.dataquality.service.impl.DolphinScheduler;
import com.qk.dm.dataquality.vo.DqcProcessInstanceVO;
import com.qk.dm.dataquality.vo.TaskInstanceVO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author shenpj
 * @date 2021/12/29 12:22 下午
 * @since 1.0.0
 */
@Component
public class RedisHandler {
    private static final int pageNo = 1;
    private static final int listPageSize = 10;
    private static final String warnResult = "True";

    private final DolphinScheduler dolphinScheduler;
    private final DqcSchedulerResultDataService dqcSchedulerResultDataService;
    private final DqcSchedulerBasicInfoService dqcSchedulerBasicInfoService;

    public RedisHandler(DolphinScheduler dolphinScheduler, DqcSchedulerResultDataService dqcSchedulerResultDataService, DqcSchedulerBasicInfoService dqcSchedulerBasicInfoService) {
        this.dolphinScheduler = dolphinScheduler;
        this.dqcSchedulerResultDataService = dqcSchedulerResultDataService;
        this.dqcSchedulerBasicInfoService = dqcSchedulerBasicInfoService;
    }

    /************************所有实例列表*****************************/

    private ProcessInstanceSearchDTO getInstanceSearchDTO(int pageSize, String failure, Date date) {
        return ProcessInstanceSearchDTO.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .stateType(failure)
                .startDate(date != null ? DateUtil.formatDateTime(DateUtil.beginOfDay(date)) : null)
                .endDate(date != null ? DateUtil.formatDateTime(DateUtil.endOfDay(date)) : null)
                .build();
    }

    private ProcessInstanceResultDTO getProcessInstanceResult(ProcessInstanceSearchDTO processInstanceSearchDTO) {
        return dolphinScheduler.instanceList(processInstanceSearchDTO);
    }

    private void getInstanceList(ProcessInstanceSearchDTO instanceSearchDTO, List<DqcProcessInstanceVO> totalList) {
        ProcessInstanceResultDTO instanceResultDTO = getProcessInstanceResult(instanceSearchDTO);
        totalList.addAll(DqcProcessInstanceMapper.INSTANCE.userDqcProcessInstanceVO(instanceResultDTO.getTotalList()));
        if (instanceResultDTO.getTotalPage() > instanceResultDTO.getCurrentPage()) {
            instanceSearchDTO.setPageNo(instanceSearchDTO.getPageNo() + 1);
            getInstanceList(instanceSearchDTO, totalList);
        }
    }

    private List<DqcProcessInstanceVO> getInstanceList(String failure, Date date) {
        ProcessInstanceSearchDTO instanceSearchDTO = getInstanceSearchDTO(listPageSize, failure, date);
        //查询出所有实例
        List<DqcProcessInstanceVO> totalList = new ArrayList<>();
        getInstanceList(instanceSearchDTO, totalList);
        return totalList;
    }

    @Cacheable(cacheManager = "dynamicTtlCacheManager", cacheNames = "statistics", key = "#root.methodName", unless = "#result == '[]'")
    public String redisInstanceList(String failure, Date date) {
        //根据条件缓存所有实例
        return GsonUtil.toJsonString(getInstanceList(failure, date));
    }

    /*********************所有警告列表*************************************************/

    private List<DqcProcessInstanceVO> getInstanceGroupByState(List<DqcProcessInstanceVO> dqcProcessInstanceVOS, InstanceStateTypeEnum stateType) {
        //根据状态筛选实例列表
        return dqcProcessInstanceVOS.stream().filter(it -> stateType.getCode().equals(it.getState())).collect(Collectors.toList());
    }

    private List<DqcSchedulerResult> getSchedulerList(List<DqcProcessInstanceVO> dqcProcessInstanceVOS) {
        //根据成功流程查询运行结果
        List<DqcProcessInstanceVO> successList = getInstanceGroupByState(dqcProcessInstanceVOS, InstanceStateTypeEnum.SUCCESS);
        Set<Long> codeSet = successList.stream().map(DqcProcessInstanceVO::getProcessDefinitionCode).collect(Collectors.toSet());
        List<DqcSchedulerBasicInfo> basicInfoList = dqcSchedulerBasicInfoService.getBasicInfoList(codeSet);
        Set<String> jobIdSet = basicInfoList.stream().map(DqcSchedulerBasicInfo::getJobId).collect(Collectors.toSet());
        return dqcSchedulerResultDataService.getSchedulerResultList(jobIdSet);
    }

    private List<DqcSchedulerResult> warnResultList(List<DqcProcessInstanceVO> dqcProcessInstanceVOS){
        return getSchedulerList(dqcProcessInstanceVOS)
                .stream()
                .filter(it -> warnResult.equals(it.getWarnResult()))
                .collect(Collectors.toList());
    }

    @Cacheable(cacheManager = "dynamicTtlCacheManager", cacheNames = "statistics", key = "#root.methodName", unless = "#result == '[]'")
    public String redisWarnResultList(List<DqcProcessInstanceVO> dqcProcessInstanceVOS){
        return GsonUtil.toJsonString(warnResultList(dqcProcessInstanceVOS));
    }

    /*********************所有任务实例列表*************************************************/

    private ProcessTaskInstanceSearchDTO getProcessTaskInstanceSearchDTO(Date date, String stateType) {
        return ProcessTaskInstanceSearchDTO.builder()
                .pageNo(pageNo)
                .pageSize(listPageSize)
                .startDate(date != null ? DateUtil.formatDateTime(DateUtil.beginOfDay(date)) : null)
                .endDate(date != null ? DateUtil.formatDateTime(DateUtil.endOfDay(date)) : null)
                .stateType(stateType)
                .build();
    }

    private ProcessTaskInstanceResultDTO getTaskInstanceResult(ProcessTaskInstanceSearchDTO taskInstanceSearchDTO) {
        return dolphinScheduler.taskInstanceList(taskInstanceSearchDTO);
    }

    private void getTaskInstanceList(ProcessTaskInstanceSearchDTO taskInstanceSearchDTO, List<TaskInstanceVO> totalList) {
        ProcessTaskInstanceResultDTO taskInstanceResultDTO = getTaskInstanceResult(taskInstanceSearchDTO);
        totalList.addAll(DqcProcessInstanceMapper.INSTANCE.taskInstanceVO(taskInstanceResultDTO.getTotalList()));
        if (taskInstanceResultDTO.getTotalPage() > taskInstanceResultDTO.getCurrentPage()) {
            taskInstanceSearchDTO.setPageNo(taskInstanceSearchDTO.getPageNo() + 1);
            getTaskInstanceList(taskInstanceSearchDTO, totalList);
        }
    }

    private List<TaskInstanceVO> getTaskInstanceList(Date date, String stateType) {
        ProcessTaskInstanceSearchDTO taskInstanceSearchDTO = getProcessTaskInstanceSearchDTO(date, stateType);
        List<TaskInstanceVO> totalList = new ArrayList<>();
        //查询所有的任务实例
        getTaskInstanceList(taskInstanceSearchDTO, totalList);
        return totalList;
    }

    @Cacheable(cacheManager = "dynamicTtlCacheManager", cacheNames = "statistics", key = "#root.methodName", unless = "#result == '[]'")
    public String redisTaskInstanceList(Date date, String stateType) {
        return GsonUtil.toJsonString(getTaskInstanceList(date,stateType));
    }
}
