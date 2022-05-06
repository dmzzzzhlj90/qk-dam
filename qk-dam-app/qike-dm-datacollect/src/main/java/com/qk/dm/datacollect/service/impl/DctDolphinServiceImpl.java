package com.qk.dm.datacollect.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.BeanMapUtils;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datacollect.service.DctDolphinService;
import com.qk.dm.datacollect.vo.*;
import com.qk.dm.dolphin.common.dto.ProcessDefinitionResultDTO;
import com.qk.dm.dolphin.common.dto.ScheduleDTO;
import com.qk.dm.dolphin.common.enums.ReleaseStateEnum;
import com.qk.dm.dolphin.common.enums.SchedulerTypeEnum;
import com.qk.dm.dolphin.common.service.DolphinProcessService;
import com.qk.dm.dolphin.common.service.DolphinScheduleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author shenpj
 * @date 2022/4/21 16:57
 * @since 1.0.0
 */
@Service
public class DctDolphinServiceImpl implements DctDolphinService {

    private final DolphinProcessService dolphinProcessService;
    private final DolphinScheduleService dolphinScheduleService;

    public DctDolphinServiceImpl(DolphinProcessService dolphinProcessService, DolphinScheduleService dolphinScheduleService) {
        this.dolphinProcessService = dolphinProcessService;
        this.dolphinScheduleService = dolphinScheduleService;
    }

    @Override
    public void delete(Long processDefinitionCode) {
        dolphinProcessService.delete(processDefinitionCode);
    }

    @Override
    public void release(DctSchedulerReleaseVO dctSchedulerReleaseVO) {
        dolphinProcessService.release(dctSchedulerReleaseVO.getCode(), dctSchedulerReleaseVO.getReleaseState());
        //更新定时
        updateScheduler(dctSchedulerReleaseVO);
    }

    private void updateScheduler(DctSchedulerReleaseVO dctSchedulerReleaseVO) {
        try {
            //如果是上线，更新定时器
            if (Objects.equals(dctSchedulerReleaseVO.getReleaseState(), ReleaseStateEnum.ONLINE.getValue())) {
                //查询流程定义详情
                DctSchedulerBasicInfoVO detail = detail(dctSchedulerReleaseVO.getCode());
                DctSchedulerConfigVO schedulerConfig = detail.getSchedulerConfig();
                ScheduleDTO scheduleDTO = dolphinScheduleService.detail(dctSchedulerReleaseVO.getCode());
                if (Objects.equals(schedulerConfig.getSchedulerType(), SchedulerTypeEnum.CYCLE.getCode()) && schedulerConfig.getCron() != null) {
                    if (scheduleDTO != null) {
                        dolphinScheduleService.update(scheduleDTO.getId(),
                                schedulerConfig.getEffectiveTimeStart(), schedulerConfig.getEffectiveTimeEnt(), schedulerConfig.getCron());
                    } else {
                        dolphinScheduleService.insert(dctSchedulerReleaseVO.getCode(),
                                schedulerConfig.getEffectiveTimeStart(), schedulerConfig.getEffectiveTimeEnt(), schedulerConfig.getCron());
                    }
                    //定时器上线
                    dolphinScheduleService.execute(scheduleDTO.getId(), ReleaseStateEnum.ONLINE.getValue());
                } else {
                    //todo 暂时不删除
                }
            }
        } catch (Exception e) {
            //下线
            dolphinProcessService.release(dctSchedulerReleaseVO.getCode(), ReleaseStateEnum.OFFLINE.getValue());
            throw new BizException("流程定时更新失败");
        }
    }

    @Override
    public void runing(Long processDefinitionCode) {
        dolphinProcessService.runing(processDefinitionCode, null);
    }

    @Override
    public PageResultVO<DctSchedulerInfoVO> searchPageList(DctSchedulerInfoParamsVO schedulerInfoParamsVO) {
        //更改查询条件
        DctSchedulerInfoParamsVO.changeSearchValAndDir(schedulerInfoParamsVO);
        ProcessDefinitionResultDTO processDefinitionResultDTO =
                dolphinProcessService.pageList(schedulerInfoParamsVO.getName(),
                        schedulerInfoParamsVO.getPagination().getPage(), schedulerInfoParamsVO.getPagination().getSize());
        List<Map<String, Object>> processDefinitionListMap = BeanMapUtils.changeBeansToList(processDefinitionResultDTO.getTotalList());
        List<DctSchedulerInfoVO> basicInfoList = BeanMapUtils.changeListToBeans(processDefinitionListMap, DctSchedulerInfoVO.class);
        //把name中的分类去除
        DctSchedulerInfoVO.changeName(basicInfoList);
        return new PageResultVO<>(
                processDefinitionResultDTO.getTotal(),
                schedulerInfoParamsVO.getPagination().getPage(),
                schedulerInfoParamsVO.getPagination().getSize(),
                basicInfoList);
    }

    @Override
    public DctSchedulerBasicInfoVO detail(Long code) {
        Object detail = dolphinProcessService.detail(code);
        return DctSchedulerBasicInfoVO.getDctSchedulerBasicInfoVO(detail);
    }
}
