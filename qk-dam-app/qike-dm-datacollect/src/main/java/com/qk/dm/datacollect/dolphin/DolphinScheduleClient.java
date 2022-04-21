//package com.qk.dm.datacollect.dolphin.schedule;
//
//import com.dolphinscheduler.client.DolphinschedulerManager;
//import com.qk.datacenter.client.ApiException;
//import com.qk.datacenter.model.Result;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//
///**
// * @author shenpj
// * @date 2022/4/19 17:46
// * @since 1.0.0
// */
//@Service
//public class DolphinScheduleClient {
//    private final DolphinschedulerManager dolphinschedulerManager;
//
//    public DolphinScheduleClient(DolphinschedulerManager dolphinschedulerManager) {
//        this.dolphinschedulerManager = dolphinschedulerManager;
//    }
//
//    /**
//     * 创建定时
//     * @param processDefinitionCode
//     * @param projectCode
//     * @param effectiveTimeStart
//     * @param effectiveTimeEnt
//     * @param cron
//     * @throws ApiException
//     */
//    public void schedule_create(Long processDefinitionCode,
//                               Long projectCode,
//                               Date effectiveTimeStart,
//                               Date effectiveTimeEnt,
//                               String cron) throws ApiException {
//        DolphinScheduleDefinition dolphinScheduleDefinition = new DolphinScheduleDefinition(effectiveTimeStart, effectiveTimeEnt, cron);
//        Result result =
//                dolphinschedulerManager.defaultApi().createScheduleUsingPOST(
//                        processDefinitionCode,
//                        projectCode,
//                        dolphinScheduleDefinition.getEnvironmentCode(),
//                        dolphinScheduleDefinition.getFailureStrategy(),
//                        dolphinScheduleDefinition.getProcessInstancePriority(),
//                        dolphinScheduleDefinition.getSchedule(),
//                        dolphinScheduleDefinition.getWarningGroupId(),
//                        dolphinScheduleDefinition.getWarningType(),
//                        dolphinScheduleDefinition.getWorkerGroup());
//        if (Boolean.TRUE.equals(result.getFailed())) {
//            throw new ApiException(400, result.getMsg());
//        }
//    }
//
//    public void schedule_update(Integer scheduleId,
//                               Long projectCode,
//                               Date effectiveTimeStart,
//                               Date effectiveTimeEnt,
//                               String cron) throws ApiException {
//        DolphinScheduleDefinition dolphinScheduleDefinition = new DolphinScheduleDefinition(effectiveTimeStart, effectiveTimeEnt, cron);
//        Result result =
//                dolphinschedulerManager.defaultApi().updateScheduleUsingPUT(
//                        scheduleId,
//                        projectCode,
//                        dolphinScheduleDefinition.getEnvironmentCode(),
//                        dolphinScheduleDefinition.getFailureStrategy(),
//                        dolphinScheduleDefinition.getProcessInstancePriority(),
//                        dolphinScheduleDefinition.getSchedule(),
//                        dolphinScheduleDefinition.getWarningGroupId(),
//                        dolphinScheduleDefinition.getWarningType(),
//                        dolphinScheduleDefinition.getWorkerGroup());
//        if (Boolean.TRUE.equals(result.getFailed())) {
//            throw new ApiException(400, result.getMsg());
//        }
//
//    }
//
//    public void schedule_online(Integer scheduleId, Long projectCode) throws ApiException {
//        Result result = dolphinschedulerManager.defaultApi().onlineUsingPOST(scheduleId, projectCode);
//        if (Boolean.TRUE.equals(result.getFailed())) {
//            throw new ApiException(400, result.getMsg());
//        }
//    }
//
//    public void schedule_offline(Integer scheduleId, Long projectCode) throws ApiException {
//        Result result = dolphinschedulerManager.defaultApi().offlineUsingPOST(scheduleId, projectCode);
//        if (Boolean.TRUE.equals(result.getFailed())) {
//            throw new ApiException(400, result.getMsg());
//        }
//    }
//
//    public void schedule_delete(Integer scheduleId, Long projectCode) throws ApiException {
//        Result result =
//                dolphinschedulerManager.defaultApi().deleteScheduleByIdUsingDELETE(
//                        scheduleId,
//                        projectCode,
//                        null,
//                        null,
//                        null,
//                        null,
//                        null,
//                        null,
//                        null,
//                        null,
//                        null,
//                        null,
//                        null,
//                        null,
//                        null);
//        if (Boolean.TRUE.equals(result.getFailed())) {
//            throw new ApiException(400, result.getMsg());
//        }
//    }
//
//    public Result schedule_search(Long processDefinitionCode,
//                                             Long projectCode,
//                                             Integer pageNo,
//                                             Integer pageSize) throws ApiException {
//        Result result =
//                dolphinschedulerManager.defaultApi().queryScheduleListPagingUsingGET(
//                        processDefinitionCode,
//                        projectCode,
//                        pageNo,
//                        pageSize,
//                        null);
//        if (Boolean.TRUE.equals(result.getFailed())) {
//            throw new ApiException(400, result.getMsg());
//        }
////        return DctConstant.changeObjectToClass(result.getData(), ScheduleResultDTO.class);
//        return result;
//    }
//}
