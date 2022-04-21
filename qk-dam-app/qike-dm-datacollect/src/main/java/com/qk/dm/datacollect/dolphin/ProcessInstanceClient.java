//package com.qk.dm.datacollect.dolphin.processinstance;
//
//import com.dolphinscheduler.client.DolphinschedulerManager;
//import com.qk.datacenter.client.ApiException;
//import com.qk.datacenter.model.ProcessInstance;
//import com.qk.datacenter.model.Result;
//import com.qk.datacenter.model.Resultstring;
//
///**
// * @author shenpj
// * @date 2022/4/19 17:46
// * @since 1.0.0
// */
//public class ProcessInstanceClient {
//    private final DolphinschedulerManager dolphinschedulerManager;
//
//    public ProcessInstanceClient(DolphinschedulerManager dolphinschedulerManager) {
//        this.dolphinschedulerManager = dolphinschedulerManager;
//    }
//
//    /**
//     * execute
//     * executeType 执行类型 (required)
//     * processInstanceId 流程实例ID (required)
//     * projectCode PROJECT_CODE (required)
//     */
//    public void instance_execute(Integer processInstanceId,
//                                 Long projectCode,
//                                 ProcessInstance.CmdTypeIfComplementEnum executeType) throws ApiException {
//        Result result =
//                dolphinschedulerManager.defaultApi().executeUsingPOST(
//                        executeType,
//                        processInstanceId,
//                        projectCode
//                );
//        if (Boolean.TRUE.equals(result.getFailed())) {
//            throw new ApiException(400, result.getMsg());
//        }
//    }
//
//    public Result instance_search(Long projectCode, ProcessInstanceSearchDTO instanceSearchDTO) throws ApiException {
//        Result result = null;
//        result =
//                dolphinschedulerManager.defaultApi().queryProcessInstanceListUsingGET(
//                        instanceSearchDTO.getPageNo(),
//                        instanceSearchDTO.getPageSize(),
//                        projectCode,
//                        instanceSearchDTO.getEndDate(),
//                        instanceSearchDTO.getExecutorName(),
//                        instanceSearchDTO.getHost(),
//                        instanceSearchDTO.getProcessDefineCode(),
//                        instanceSearchDTO.getSearchVal(),
//                        instanceSearchDTO.getStartDate(),
//                        instanceSearchDTO.getStateType()
//                );
//        if (Boolean.TRUE.equals(result.getFailed())) {
//            throw new ApiException(400, result.getMsg());
//        }
////        return DctConstant.changeObjectToClass(result.getData(), ProcessInstanceResultDTO.class);
//        return result;
//    }
//
//    public Result instance_detail(Integer processInstanceId, Long projectCode) throws ApiException {
//        Result result = null;
//        result = dolphinschedulerManager.defaultApi().queryProcessInstanceByIdUsingGET(
//                processInstanceId,
//                projectCode
//        );
//        if (Boolean.TRUE.equals(result.getFailed())) {
//            throw new ApiException(400, result.getMsg());
//        }
////        return DctConstant.changeObjectToClass(result.getData(), ProcessInstanceDTO.class);
//        return result;
//    }
//
//    public Result task_by_ProcessId(Integer processInstanceId, Long projectCode) throws ApiException {
//        Result result =
//                dolphinschedulerManager.defaultApi().queryTaskListByProcessIdUsingGET(
//                        processInstanceId,
//                        projectCode
//                );
//        if (Boolean.TRUE.equals(result.getFailed())) {
//            throw new ApiException(400, result.getMsg());
//        }
//        return result;
//    }
//
//    public Resultstring taskLog(Integer taskInstanceId, Integer limit, Integer skipLineNum) throws ApiException {
//        Resultstring result =
//                dolphinschedulerManager.defaultApi().queryLogUsingGET(
//                        limit,
//                        skipLineNum,
//                        taskInstanceId
//                );
//        if (Boolean.TRUE.equals(result.getFailed())) {
//            throw new ApiException(400, result.getMsg());
//        }
//        return result;
//    }
//}
