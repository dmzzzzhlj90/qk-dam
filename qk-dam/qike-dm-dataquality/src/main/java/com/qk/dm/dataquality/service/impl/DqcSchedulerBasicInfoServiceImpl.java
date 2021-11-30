package com.qk.dm.dataquality.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataquality.constant.DqcConstant;
import com.qk.dm.dataquality.constant.SchedulerInstanceStateEnum;
import com.qk.dm.dataquality.constant.SchedulerStateEnum;
import com.qk.dm.dataquality.constant.schedule.InstanceStateTypeEnum;
import com.qk.dm.dataquality.entity.DqcSchedulerBasicInfo;
import com.qk.dm.dataquality.mapstruct.mapper.DqcSchedulerBasicInfoMapper;
import com.qk.dm.dataquality.params.dto.DqcSchedulerBasicInfoReleaseDTO;
import com.qk.dm.dataquality.params.dto.DqcSchedulerBasicInfoRuningDTO;
import com.qk.dm.dataquality.repositories.DqcSchedulerBasicInfoRepository;
import com.qk.dm.dataquality.service.DqcSchedulerBasicInfoService;
import com.qk.dm.dataquality.service.DqcSchedulerConfigService;
import com.qk.dm.dataquality.vo.DqcProcessInstanceVO;
import com.qk.dm.dataquality.vo.DqcSchedulerBasicInfoVO;
import com.qk.dm.dataquality.vo.DqcSchedulerInfoParamsVO;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wjq
 * @date 2021/11/10
 * @since 1.0.0
 */
@Service
public class DqcSchedulerBasicInfoServiceImpl implements DqcSchedulerBasicInfoService {
    private final DqcSchedulerBasicInfoRepository dqcSchedulerBasicInfoRepository;
    private final DqcSchedulerConfigService dqcSchedulerConfigService;
    private final DolphinScheduler dolphinScheduler;

    public DqcSchedulerBasicInfoServiceImpl(
            DqcSchedulerBasicInfoRepository dqcSchedulerBasicInfoRepository,
            DqcSchedulerConfigService dqcSchedulerConfigService,
            DolphinScheduler dolphinScheduler) {
        this.dqcSchedulerBasicInfoRepository = dqcSchedulerBasicInfoRepository;
        this.dqcSchedulerConfigService = dqcSchedulerConfigService;
        this.dolphinScheduler = dolphinScheduler;
    }

    @Override
    public PageResultVO<DqcSchedulerBasicInfoVO> searchPageList(
            DqcSchedulerInfoParamsVO dsdSchedulerAllParamsVO) {
        return null;
    }

    @Override
    public void insert(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO) {
        DqcSchedulerBasicInfo basicInfo = DqcSchedulerBasicInfoMapper.INSTANCE.userDqcSchedulerBasicInfo(dqcSchedulerBasicInfoVO);
        // todo 创建人
        basicInfo.setCreateUserid("admin");
        basicInfo.setSchedulerState(SchedulerStateEnum.NOT_STARTED.getCode());
        basicInfo.setDelFlag(DqcConstant.DEL_FLAG_RETAIN);
        basicInfo.setRunInstanceState(SchedulerInstanceStateEnum.INIT.getCode());
        dqcSchedulerBasicInfoRepository.saveAndFlush(basicInfo);
    }

    @Override
    public void update(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO) {
        DqcSchedulerBasicInfo basicInfo = getInfoById(dqcSchedulerBasicInfoVO.getId());
        DqcSchedulerBasicInfoMapper.INSTANCE.toDqcSchedulerBasicInfo(
                dqcSchedulerBasicInfoVO, basicInfo);
        // todo 修改人
        basicInfo.setUpdateUserid("admin");
        dqcSchedulerBasicInfoRepository.saveAndFlush(basicInfo);
    }

    @Override
    public void deleteOne(Long id) {
        dqcSchedulerBasicInfoRepository.deleteById(id);
    }

    @Override
    public void deleteBulk(List<DqcSchedulerBasicInfo> schedulerBasicInfoList) {
        dqcSchedulerBasicInfoRepository.deleteAll(schedulerBasicInfoList);
    }

    @Override
    public void release(DqcSchedulerBasicInfoReleaseDTO infoReleaseDto) {
        DqcSchedulerBasicInfo basicInfo = getBasicInfo(infoReleaseDto.getId());
        Integer processDefinitionId = basicInfo.getProcessDefinitionId();
        Integer scheduleId = null;
        if (infoReleaseDto.getSchedulerState().equals(SchedulerStateEnum.SCHEDULING.getCode())) {
            // todo 查询定时id，根据是否是手动触发
            dolphinScheduler.online(processDefinitionId, scheduleId);
        } else {
            dolphinScheduler.offline(processDefinitionId);
        }
        basicInfo.setSchedulerState(infoReleaseDto.getSchedulerState());
        // todo 修改人
        basicInfo.setUpdateUserid("admin");
        dqcSchedulerBasicInfoRepository.saveAndFlush(basicInfo);
    }

    @Override
    public void runing(DqcSchedulerBasicInfoRuningDTO basicInfoRuningDTO) {
        DqcSchedulerBasicInfo basicInfo = getBasicInfo(basicInfoRuningDTO.getId());
        if (basicInfoRuningDTO.getRunInstanceState().equals(SchedulerInstanceStateEnum.RUNING.getCode())) {
            dolphinScheduler.startInstance(basicInfo.getProcessDefinitionId());
        } else {
            if(basicInfoRuningDTO.getInstanceId() == null){
                throw new BizException("停止实例需要实例id");
            }
            dolphinScheduler.stop(basicInfoRuningDTO.getInstanceId());
        }
        basicInfo.setRunInstanceState(basicInfoRuningDTO.getRunInstanceState());
        // todo 修改人
        basicInfo.setUpdateUserid("admin");
        dqcSchedulerBasicInfoRepository.saveAndFlush(basicInfo);
        // todo 启动异步查询运行状态，直到成功或失败
    }

    @Override
    public DqcProcessInstanceVO instanceDetailByList(Long id) {
        DqcSchedulerBasicInfo basicInfo = getBasicInfo(id);
        // 获取到最近运行实例
        DqcProcessInstanceVO instanceData = dolphinScheduler.detailByList(basicInfo.getProcessDefinitionId());
        //保存状态
        InstanceStateTypeEnum instanceStateTypeEnum = InstanceStateTypeEnum.fromValue(instanceData.getState());
        instanceData.setStateName(instanceStateTypeEnum.getSchedulerInstanceStateEnum().getValue());
        basicInfo.setRunInstanceState(instanceStateTypeEnum.getSchedulerInstanceStateEnum().getCode());
        // todo 修改人
        basicInfo.setUpdateUserid("admin");
        // 保存basic
        dqcSchedulerBasicInfoRepository.saveAndFlush(basicInfo);
        return instanceData;
    }

    private List<Long> getIdList(String ids) {
        return Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toList());
    }

    @Override
    public DqcSchedulerBasicInfo getInfoById(Long id) {
        DqcSchedulerBasicInfo info = getBasicInfo(id);
        checkState(info);
        return info;
    }

    @Override
    public List<DqcSchedulerBasicInfo> getInfoList(String ids) {
        List<DqcSchedulerBasicInfo> infoList = dqcSchedulerBasicInfoRepository.findAllById(getIdList(ids));
        checkBasicInfo(ids, infoList);
        return infoList.stream().peek(this::checkState).collect(Collectors.toList());
    }

    private DqcSchedulerBasicInfo getBasicInfo(Long id) {
        DqcSchedulerBasicInfo info = dqcSchedulerBasicInfoRepository.findById(id).orElse(null);
        checkBasicInfo(id, info);
        return info;
    }

    private void checkBasicInfo(Long id, DqcSchedulerBasicInfo info) {
        if (info == null) {
            throw new BizException("id为：" + id + " 的任务，不存在！！！");
        }
    }

    private void checkState(DqcSchedulerBasicInfo info) {
        if (info.getSchedulerState().equals(SchedulerStateEnum.SCHEDULING.getCode())) {
            throw new BizException("启动调度后不可进行此操作！！！");
        }
    }

    private void checkBasicInfo(String ids, List<DqcSchedulerBasicInfo> infoList) {
        if (CollectionUtils.isEmpty(infoList)) {
            throw new BizException("id为：" + ids + " 的任务，不存在！！！");
        }
    }
}
