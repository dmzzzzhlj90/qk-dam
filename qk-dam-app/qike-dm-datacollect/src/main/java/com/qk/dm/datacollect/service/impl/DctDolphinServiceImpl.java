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
import com.qk.dm.dolphin.common.manager.DolphinProcessManager;
import com.qk.dm.dolphin.common.manager.DolphinScheduleManager;
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

    private final DolphinProcessManager dolphinProcessManager;
    private final DolphinScheduleManager dolphinScheduleManager;

    public DctDolphinServiceImpl(DolphinProcessManager dolphinProcessManager, DolphinScheduleManager dolphinScheduleManager) {
        this.dolphinProcessManager = dolphinProcessManager;
        this.dolphinScheduleManager = dolphinScheduleManager;
    }

    @Override
    public void delete(Long processDefinitionCode) {
        dolphinProcessManager.delete(processDefinitionCode);
    }

    @Override
    public void release(DctSchedulerReleaseVO dctSchedulerReleaseVO) {
        dolphinProcessManager.release(dctSchedulerReleaseVO.getCode(), dctSchedulerReleaseVO.getReleaseState());
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
                ScheduleDTO scheduleDTO = dolphinScheduleManager.detail(dctSchedulerReleaseVO.getCode());
                if (Objects.equals(schedulerConfig.getSchedulerType(), SchedulerTypeEnum.CYCLE.getCode()) && schedulerConfig.getCron() != null) {
                    if (scheduleDTO != null) {
                        dolphinScheduleManager.update(scheduleDTO.getId(),
                                schedulerConfig.getEffectiveTimeStart(), schedulerConfig.getEffectiveTimeEnt(), schedulerConfig.getCron());
                    } else {
                        dolphinScheduleManager.insert(dctSchedulerReleaseVO.getCode(),
                                schedulerConfig.getEffectiveTimeStart(), schedulerConfig.getEffectiveTimeEnt(), schedulerConfig.getCron());
                    }
                    //定时器上线
                    dolphinScheduleManager.execute(scheduleDTO.getId(), ReleaseStateEnum.ONLINE.getValue());
                } else {
                    //todo 暂时不删除
                }
            }
        } catch (Exception e) {
            //下线
            dolphinProcessManager.release(dctSchedulerReleaseVO.getCode(), ReleaseStateEnum.OFFLINE.getValue());
            throw new BizException("流程定时更新失败");
        }
    }

    @Override
    public void runing(Long processDefinitionCode) {
        dolphinProcessManager.runing(processDefinitionCode, null);
    }

    @Override
    public PageResultVO<DctSchedulerInfoVO> searchPageList(DctSchedulerInfoParamsVO schedulerInfoParamsVO) {
        //更改查询条件
        DctSchedulerInfoParamsVO.changeSearchValAndDir(schedulerInfoParamsVO);
        ProcessDefinitionResultDTO processDefinitionResultDTO =
                dolphinProcessManager.pageList(schedulerInfoParamsVO.getName(),
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
        Object detail = dolphinProcessManager.detail(code);
        return DctSchedulerBasicInfoVO.getDctSchedulerBasicInfoVO(detail);
    }
}
