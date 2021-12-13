package com.qk.dm.dataquality.service.impl;

import com.qk.dm.dataquality.constant.SchedulerStateEnum;
import com.qk.dm.dataquality.dolphinapi.constant.SchedulerConstant;
import com.qk.dm.dataquality.dolphinapi.dto.*;
import com.qk.dm.dataquality.dolphinapi.service.ProcessDefinitionApiService;
import com.qk.dm.dataquality.dolphinapi.service.ProcessInstanceService;
import com.qk.dm.dataquality.dolphinapi.service.ScheduleApiService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @author shenpj
 * @date 2021/11/22 3:00 下午
 * @since 1.0.0
 */
@Service
public class DolphinScheduler {
    private final ProcessDefinitionApiService processDefinitionApiService;
    private final ScheduleApiService scheduleApiService;
    private final ProcessInstanceService processInstanceService;

    public DolphinScheduler(ProcessDefinitionApiService processDefinitionApiService,
                            ScheduleApiService scheduleApiService,
                            ProcessInstanceService processInstanceService) {
        this.processDefinitionApiService = processDefinitionApiService;
        this.scheduleApiService = scheduleApiService;
        this.processInstanceService = processInstanceService;
    }

    /*****************************流程定义**开始*************************************************/

    /**
     * 流程定义上线
     *
     * @param processDefinitionId
     */
    public void online(Integer processDefinitionId) {
        processDefinitionApiService.release(processDefinitionId, SchedulerStateEnum.ONLINE.getState());
    }

    /**
     * 流程定义下线
     *
     * @param processDefinitionId
     */
    public void offline(Integer processDefinitionId) {
        processDefinitionApiService.release(processDefinitionId, SchedulerStateEnum.OFFLINE.getState());
    }

    /**
     * 运行
     *
     * @param processDefinitionId
     */
    public void startInstance(Integer processDefinitionId) {
        processDefinitionApiService.startCheck(processDefinitionId);
        processDefinitionApiService.startInstance(processDefinitionId);
    }

    /*****************************流程实例**开始*************************************************/

    /**
     * 操作实例
     *
     * @param processDefinitionId
     */
    public void execute(Integer processDefinitionId,String executeType) {
        processInstanceService.execute(processDefinitionId, executeType);
    }

    /**
     * 查询实例列表
     *
     * @param instanceSearchDTO
     * @return
     */
    public ProcessInstanceResultDTO instanceList(ProcessInstanceSearchDTO instanceSearchDTO) {
        return processInstanceService.search(instanceSearchDTO);
    }

    /**
     * 删除实例
     * @param processInstanceId
     */
    public void deleteOne(Integer processInstanceId) {
        processInstanceService.deleteOne(processInstanceId);
    }

    /**
     * 批量删除实例
     * @param processInstanceIds
     */
    public void deleteBulk(String processInstanceIds) {
        processInstanceService.deleteBulk(ProcessInstanceDeleteDTO.builder().processInstanceIds(processInstanceIds).build());
    }

    /**
     * 查询任务实例列表
     * @param instanceSearchDTO
     * @return
     */
    public ProcessTaskInstanceResultDTO taskInstanceList(ProcessTaskInstanceSearchDTO instanceSearchDTO) {
        return processInstanceService.searchTask(instanceSearchDTO);
    }

    /**
     * 查询任务实例日志
     * @param taskInstanceId
     * @param limit
     * @param skipLineNum
     * @return
     */
    public String taskLog(Integer taskInstanceId, Integer limit, Integer skipLineNum){
        return processInstanceService.taskLog(taskInstanceId,limit,skipLineNum);
    }

    /**
     * 下载任务实例日志
     * @param taskInstanceId
     * @return
     */
    public String taskLogDownload(Integer taskInstanceId){
        return processInstanceService.taskLogDownload(taskInstanceId);
    }

    /*****************************定时操作**开始*************************************************/

    /**
     * 新增定时器
     *
     * @param processDefinitionId
     * @param effectiveTimeStart
     * @param effectiveTimeEnt
     * @param cron
     * @return
     */
    public Integer createSchedule(
            Integer processDefinitionId, Date effectiveTimeStart, Date effectiveTimeEnt, String cron) {
        // 创建定时
        scheduleApiService.create(processDefinitionId, effectiveTimeStart, effectiveTimeEnt, cron);
        // 查询定时
        ScheduleSearchDTO scheduleSearchDTO =
                ScheduleSearchDTO.builder()
                        .processDefinitionId(processDefinitionId)
                        .pageNo(SchedulerConstant.PAGE_NO)
                        .pageSize(SchedulerConstant.SCHEDULER_PAGE_SIZE)
                        .build();
        ScheduleResultDTO search = scheduleApiService.search(scheduleSearchDTO);
        List<ScheduleDTO> totalList = search.getTotalList();
        return CollectionUtils.isEmpty(totalList) ? null : totalList.get(0).getId();
    }

    /**
     * 修改定时器
     *
     * @param scheduleId
     * @param effectiveTimeStart
     * @param effectiveTimeEnt
     * @param cron
     */
    public void updateSchedule(Integer scheduleId, Date effectiveTimeStart, Date effectiveTimeEnt, String cron) {
        scheduleApiService.update(scheduleId, effectiveTimeStart, effectiveTimeEnt, cron);
    }

    /**
     * 删除定时器
     *
     * @param scheduleId
     */
    public void deleteSchedule(Integer scheduleId) {
        scheduleApiService.deleteOne(ScheduleDeleteDTO.builder().scheduleId(scheduleId).build());
    }

    /**
     * 上线定时器
     *
     * @param scheduleId
     */
    public void onlineSchedule(Integer scheduleId) {
        scheduleApiService.online(scheduleId);
    }

    /**
     * 下线定时器
     *
     * @param scheduleId
     */
    public void offlineSchedule(Integer scheduleId) {
        scheduleApiService.offline(scheduleId);
    }

    /**
     * 查询定时器
     *
     * @param processDefinitionId
     */
    public ScheduleDTO searchSchedule(Integer processDefinitionId) {
        // 查询定时
        ScheduleSearchDTO scheduleSearchDTO =
                ScheduleSearchDTO.builder()
                        .processDefinitionId(processDefinitionId)
                        .pageNo(SchedulerConstant.PAGE_NO)
                        .pageSize(SchedulerConstant.SCHEDULER_PAGE_SIZE)
                        .build();
        ScheduleResultDTO search = scheduleApiService.search(scheduleSearchDTO);
        List<ScheduleDTO> totalList = search.getTotalList();
        return CollectionUtils.isEmpty(totalList) ? null : totalList.get(0);
    }
}
