package com.qk.dm.dataquality.mapstruct.mapper;

import com.qk.dam.jpa.pojo.Pagination;
import com.qk.dm.dataquality.dolphinapi.dto.ProcessInstanceDTO;
import com.qk.dm.dataquality.dolphinapi.dto.ProcessInstanceSearchDTO;
import com.qk.dm.dataquality.dolphinapi.dto.ProcessTaskInstanceSearchDTO;
import com.qk.dm.dataquality.dolphinapi.dto.TaskInstanceDTO;
import com.qk.dm.dataquality.params.dto.DqcSchedulerInstanceParamsDTO;
import com.qk.dm.dataquality.params.dto.DqcSchedulerTaskInstanceParamsDTO;
import com.qk.dm.dataquality.vo.DqcProcessInstanceVO;
import com.qk.dm.dataquality.vo.DqcProcessTaskInstanceVO;
import com.qk.dm.dataquality.vo.TaskInstanceVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-30T20:19:50+0800",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.9.1.jar, environment: Java 11.0.13 (Azul Systems, Inc.)"
)
public class DqcProcessInstanceMapperImpl implements DqcProcessInstanceMapper {

    @Override
    public DqcProcessInstanceVO userDqcProcessInstanceVO(ProcessInstanceDTO processInstanceDTO) {
        if ( processInstanceDTO == null ) {
            return null;
        }

        DqcProcessInstanceVO dqcProcessInstanceVO = new DqcProcessInstanceVO();

        if ( processInstanceDTO.getState() != null ) {
            dqcProcessInstanceVO.setState( processInstanceDTO.getState() );
        }
        if ( processInstanceDTO.getId() != null ) {
            dqcProcessInstanceVO.setId( processInstanceDTO.getId() );
        }
        if ( processInstanceDTO.getProcessDefinitionCode() != null ) {
            dqcProcessInstanceVO.setProcessDefinitionCode( processInstanceDTO.getProcessDefinitionCode() );
        }
        if ( processInstanceDTO.getRecovery() != null ) {
            dqcProcessInstanceVO.setRecovery( processInstanceDTO.getRecovery() );
        }
        if ( processInstanceDTO.getStartTime() != null ) {
            dqcProcessInstanceVO.setStartTime( processInstanceDTO.getStartTime() );
        }
        if ( processInstanceDTO.getEndTime() != null ) {
            dqcProcessInstanceVO.setEndTime( processInstanceDTO.getEndTime() );
        }
        if ( processInstanceDTO.getRunTimes() != null ) {
            dqcProcessInstanceVO.setRunTimes( processInstanceDTO.getRunTimes() );
        }
        if ( processInstanceDTO.getName() != null ) {
            dqcProcessInstanceVO.setName( processInstanceDTO.getName() );
        }
        if ( processInstanceDTO.getHost() != null ) {
            dqcProcessInstanceVO.setHost( processInstanceDTO.getHost() );
        }
        if ( processInstanceDTO.getCommandType() != null ) {
            dqcProcessInstanceVO.setCommandType( processInstanceDTO.getCommandType() );
        }
        if ( processInstanceDTO.getExecutorName() != null ) {
            dqcProcessInstanceVO.setExecutorName( processInstanceDTO.getExecutorName() );
        }
        if ( processInstanceDTO.getDuration() != null ) {
            dqcProcessInstanceVO.setDuration( processInstanceDTO.getDuration() );
        }
        if ( processInstanceDTO.getDependenceScheduleTimes() != null ) {
            dqcProcessInstanceVO.setDependenceScheduleTimes( processInstanceDTO.getDependenceScheduleTimes() );
        }

        return dqcProcessInstanceVO;
    }

    @Override
    public List<DqcProcessInstanceVO> userDqcProcessInstanceVO(List<ProcessInstanceDTO> processInstanceDTO) {
        if ( processInstanceDTO == null ) {
            return null;
        }

        List<DqcProcessInstanceVO> list = new ArrayList<DqcProcessInstanceVO>( processInstanceDTO.size() );
        for ( ProcessInstanceDTO processInstanceDTO1 : processInstanceDTO ) {
            list.add( userDqcProcessInstanceVO( processInstanceDTO1 ) );
        }

        return list;
    }

    @Override
    public List<DqcProcessTaskInstanceVO> userDqcProcessTaskInstanceVO(List<TaskInstanceDTO> taskInstanceDTOS) {
        if ( taskInstanceDTOS == null ) {
            return null;
        }

        List<DqcProcessTaskInstanceVO> list = new ArrayList<DqcProcessTaskInstanceVO>( taskInstanceDTOS.size() );
        for ( TaskInstanceDTO taskInstanceDTO : taskInstanceDTOS ) {
            list.add( taskInstanceDTOToDqcProcessTaskInstanceVO( taskInstanceDTO ) );
        }

        return list;
    }

    @Override
    public ProcessTaskInstanceSearchDTO taskInstanceSearchDTO(DqcSchedulerTaskInstanceParamsDTO taskInstanceParamsDTO) {
        if ( taskInstanceParamsDTO == null ) {
            return null;
        }

        ProcessTaskInstanceSearchDTO processTaskInstanceSearchDTO = new ProcessTaskInstanceSearchDTO();

        Integer page = taskInstanceParamsDTOPaginationPage( taskInstanceParamsDTO );
        if ( page != null ) {
            processTaskInstanceSearchDTO.setPageNo( page );
        }
        Integer size = taskInstanceParamsDTOPaginationSize( taskInstanceParamsDTO );
        if ( size != null ) {
            processTaskInstanceSearchDTO.setPageSize( size );
        }
        if ( taskInstanceParamsDTO.getEndDate() != null ) {
            processTaskInstanceSearchDTO.setEndDate( taskInstanceParamsDTO.getEndDate() );
        }
        if ( taskInstanceParamsDTO.getExecutorName() != null ) {
            processTaskInstanceSearchDTO.setExecutorName( taskInstanceParamsDTO.getExecutorName() );
        }
        if ( taskInstanceParamsDTO.getProcessInstanceId() != null ) {
            processTaskInstanceSearchDTO.setProcessInstanceId( taskInstanceParamsDTO.getProcessInstanceId() );
        }
        if ( taskInstanceParamsDTO.getProcessInstanceName() != null ) {
            processTaskInstanceSearchDTO.setProcessInstanceName( taskInstanceParamsDTO.getProcessInstanceName() );
        }
        if ( taskInstanceParamsDTO.getSearchVal() != null ) {
            processTaskInstanceSearchDTO.setSearchVal( taskInstanceParamsDTO.getSearchVal() );
        }
        if ( taskInstanceParamsDTO.getStartDate() != null ) {
            processTaskInstanceSearchDTO.setStartDate( taskInstanceParamsDTO.getStartDate() );
        }
        if ( taskInstanceParamsDTO.getStateType() != null ) {
            processTaskInstanceSearchDTO.setStateType( taskInstanceParamsDTO.getStateType() );
        }
        if ( taskInstanceParamsDTO.getTaskName() != null ) {
            processTaskInstanceSearchDTO.setTaskName( taskInstanceParamsDTO.getTaskName() );
        }

        return processTaskInstanceSearchDTO;
    }

    @Override
    public ProcessInstanceSearchDTO instanceSearchDTO(DqcSchedulerInstanceParamsDTO instanceParamsDTO) {
        if ( instanceParamsDTO == null ) {
            return null;
        }

        ProcessInstanceSearchDTO processInstanceSearchDTO = new ProcessInstanceSearchDTO();

        Integer page = instanceParamsDTOPaginationPage( instanceParamsDTO );
        if ( page != null ) {
            processInstanceSearchDTO.setPageNo( page );
        }
        Integer size = instanceParamsDTOPaginationSize( instanceParamsDTO );
        if ( size != null ) {
            processInstanceSearchDTO.setPageSize( size );
        }
        if ( instanceParamsDTO.getEndDate() != null ) {
            processInstanceSearchDTO.setEndDate( instanceParamsDTO.getEndDate() );
        }
        if ( instanceParamsDTO.getExecutorName() != null ) {
            processInstanceSearchDTO.setExecutorName( instanceParamsDTO.getExecutorName() );
        }
        if ( instanceParamsDTO.getProcessDefineCode() != null ) {
            processInstanceSearchDTO.setProcessDefineCode( instanceParamsDTO.getProcessDefineCode() );
        }
        if ( instanceParamsDTO.getSearchVal() != null ) {
            processInstanceSearchDTO.setSearchVal( instanceParamsDTO.getSearchVal() );
        }
        if ( instanceParamsDTO.getStartDate() != null ) {
            processInstanceSearchDTO.setStartDate( instanceParamsDTO.getStartDate() );
        }
        if ( instanceParamsDTO.getStateType() != null ) {
            processInstanceSearchDTO.setStateType( instanceParamsDTO.getStateType() );
        }

        return processInstanceSearchDTO;
    }

    @Override
    public List<TaskInstanceVO> taskInstanceVO(List<TaskInstanceDTO> taskInstanceDTOs) {
        if ( taskInstanceDTOs == null ) {
            return null;
        }

        List<TaskInstanceVO> list = new ArrayList<TaskInstanceVO>( taskInstanceDTOs.size() );
        for ( TaskInstanceDTO taskInstanceDTO : taskInstanceDTOs ) {
            list.add( taskInstanceDTOToTaskInstanceVO( taskInstanceDTO ) );
        }

        return list;
    }

    protected DqcProcessTaskInstanceVO taskInstanceDTOToDqcProcessTaskInstanceVO(TaskInstanceDTO taskInstanceDTO) {
        if ( taskInstanceDTO == null ) {
            return null;
        }

        DqcProcessTaskInstanceVO dqcProcessTaskInstanceVO = new DqcProcessTaskInstanceVO();

        if ( taskInstanceDTO.getState() != null ) {
            dqcProcessTaskInstanceVO.setState( taskInstanceDTO.getState() );
        }
        if ( taskInstanceDTO.getId() != null ) {
            dqcProcessTaskInstanceVO.setId( taskInstanceDTO.getId() );
        }
        if ( taskInstanceDTO.getProcessInstanceId() != null ) {
            dqcProcessTaskInstanceVO.setProcessInstanceId( taskInstanceDTO.getProcessInstanceId() );
        }
        if ( taskInstanceDTO.getProcessInstanceName() != null ) {
            dqcProcessTaskInstanceVO.setProcessInstanceName( taskInstanceDTO.getProcessInstanceName() );
        }
        if ( taskInstanceDTO.getName() != null ) {
            dqcProcessTaskInstanceVO.setName( taskInstanceDTO.getName() );
        }
        if ( taskInstanceDTO.getExecutorName() != null ) {
            dqcProcessTaskInstanceVO.setExecutorName( taskInstanceDTO.getExecutorName() );
        }
        if ( taskInstanceDTO.getTaskType() != null ) {
            dqcProcessTaskInstanceVO.setTaskType( taskInstanceDTO.getTaskType() );
        }
        if ( taskInstanceDTO.getSubmitTime() != null ) {
            dqcProcessTaskInstanceVO.setSubmitTime( taskInstanceDTO.getSubmitTime() );
        }
        if ( taskInstanceDTO.getStartTime() != null ) {
            dqcProcessTaskInstanceVO.setStartTime( taskInstanceDTO.getStartTime() );
        }
        if ( taskInstanceDTO.getEndTime() != null ) {
            dqcProcessTaskInstanceVO.setEndTime( taskInstanceDTO.getEndTime() );
        }
        if ( taskInstanceDTO.getHost() != null ) {
            dqcProcessTaskInstanceVO.setHost( taskInstanceDTO.getHost() );
        }
        if ( taskInstanceDTO.getDuration() != null ) {
            dqcProcessTaskInstanceVO.setDuration( taskInstanceDTO.getDuration() );
        }
        if ( taskInstanceDTO.getRetryTimes() != null ) {
            dqcProcessTaskInstanceVO.setRetryTimes( taskInstanceDTO.getRetryTimes().intValue() );
        }

        return dqcProcessTaskInstanceVO;
    }

    private Integer taskInstanceParamsDTOPaginationPage(DqcSchedulerTaskInstanceParamsDTO dqcSchedulerTaskInstanceParamsDTO) {
        if ( dqcSchedulerTaskInstanceParamsDTO == null ) {
            return null;
        }
        Pagination pagination = dqcSchedulerTaskInstanceParamsDTO.getPagination();
        if ( pagination == null ) {
            return null;
        }
        int page = pagination.getPage();
        return page;
    }

    private Integer taskInstanceParamsDTOPaginationSize(DqcSchedulerTaskInstanceParamsDTO dqcSchedulerTaskInstanceParamsDTO) {
        if ( dqcSchedulerTaskInstanceParamsDTO == null ) {
            return null;
        }
        Pagination pagination = dqcSchedulerTaskInstanceParamsDTO.getPagination();
        if ( pagination == null ) {
            return null;
        }
        int size = pagination.getSize();
        return size;
    }

    private Integer instanceParamsDTOPaginationPage(DqcSchedulerInstanceParamsDTO dqcSchedulerInstanceParamsDTO) {
        if ( dqcSchedulerInstanceParamsDTO == null ) {
            return null;
        }
        Pagination pagination = dqcSchedulerInstanceParamsDTO.getPagination();
        if ( pagination == null ) {
            return null;
        }
        int page = pagination.getPage();
        return page;
    }

    private Integer instanceParamsDTOPaginationSize(DqcSchedulerInstanceParamsDTO dqcSchedulerInstanceParamsDTO) {
        if ( dqcSchedulerInstanceParamsDTO == null ) {
            return null;
        }
        Pagination pagination = dqcSchedulerInstanceParamsDTO.getPagination();
        if ( pagination == null ) {
            return null;
        }
        int size = pagination.getSize();
        return size;
    }

    protected TaskInstanceVO taskInstanceDTOToTaskInstanceVO(TaskInstanceDTO taskInstanceDTO) {
        if ( taskInstanceDTO == null ) {
            return null;
        }

        TaskInstanceVO taskInstanceVO = new TaskInstanceVO();

        if ( taskInstanceDTO.getId() != null ) {
            taskInstanceVO.setId( taskInstanceDTO.getId() );
        }
        if ( taskInstanceDTO.getState() != null ) {
            taskInstanceVO.setState( taskInstanceDTO.getState() );
        }
        if ( taskInstanceDTO.getProcessInstanceId() != null ) {
            taskInstanceVO.setProcessInstanceId( taskInstanceDTO.getProcessInstanceId() );
        }
        if ( taskInstanceDTO.getTaskCode() != null ) {
            taskInstanceVO.setTaskCode( taskInstanceDTO.getTaskCode() );
        }
        if ( taskInstanceDTO.getStartTime() != null ) {
            taskInstanceVO.setStartTime( taskInstanceDTO.getStartTime() );
        }
        if ( taskInstanceDTO.getEndTime() != null ) {
            taskInstanceVO.setEndTime( taskInstanceDTO.getEndTime() );
        }
        if ( taskInstanceDTO.getProcessInstanceName() != null ) {
            taskInstanceVO.setProcessInstanceName( taskInstanceDTO.getProcessInstanceName() );
        }

        return taskInstanceVO;
    }
}
