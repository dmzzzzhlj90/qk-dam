package com.qk.dm.datacollect.service.impl;

import com.qk.dm.datacollect.dto.ProcessDefinitionDTO;
import com.qk.dm.datacollect.service.DctDolphinService;
import com.qk.dm.datacollect.service.DolphinProcessService;
import com.qk.dm.datacollect.service.DolphinScheduleService;
import com.qk.dm.datacollect.service.ProcessInstanceService;
import com.qk.dm.datacollect.vo.DctSchedulerBasicInfoVO;
import com.qk.dm.datacollect.vo.HttpParamsVO;
import org.springframework.stereotype.Service;

/**
 * @author shenpj
 * @date 2022/4/21 16:57
 * @since 1.0.0
 */
@Service
public class DctDolphinServiceImpl implements DctDolphinService {

    private final DolphinProcessService dolphinProcessService;
    private final DolphinScheduleService dolphinScheduleService;
    private final ProcessInstanceService processInstanceService;

    public DctDolphinServiceImpl(DolphinProcessService dolphinProcessService,
                                 DolphinScheduleService dolphinScheduleService,
                                 ProcessInstanceService processInstanceService) {
        this.dolphinProcessService = dolphinProcessService;
        this.dolphinScheduleService = dolphinScheduleService;
        this.processInstanceService = processInstanceService;
    }

    @Override
    public void insert(DctSchedulerBasicInfoVO dctSchedulerBasicInfoVO) {
        Long projectId = 5262418498656L;
        String name = dctSchedulerBasicInfoVO.getJobName();
        String url = "http://10.0.18.48:7001/#/metadatacollection/dataoverview";
        Object httpParams = HttpParamsVO.createList(dctSchedulerBasicInfoVO);
        String httpMethod = "POST";
        String description = dctSchedulerBasicInfoVO.getDescription();

        ProcessDefinitionDTO processDefinition = dolphinProcessService.createProcessDefinition(projectId, name, url, httpParams, httpMethod, description);
        System.out.println("processDefinition:"+processDefinition);


        //创建完流程实例后保存到本地，方便根据分类查询
        String dirId = dctSchedulerBasicInfoVO.getDirId();
    }

    @Override
    public void delete() {
        Long projectId = 3908755011744L;

    }




}
