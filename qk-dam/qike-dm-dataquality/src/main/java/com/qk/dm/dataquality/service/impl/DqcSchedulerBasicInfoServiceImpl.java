package com.qk.dm.dataquality.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataquality.constant.DqcConstant;
import com.qk.dm.dataquality.constant.SchedulerInstanceStateEnum;
import com.qk.dm.dataquality.constant.SchedulerStateEnum;
import com.qk.dm.dataquality.entity.DqcSchedulerBasicInfo;
import com.qk.dm.dataquality.mapstruct.mapper.DqcSchedulerBasicInfoMapper;
import com.qk.dm.dataquality.repositories.DqcSchedulerBasicInfoRepository;
import com.qk.dm.dataquality.service.DqcSchedulerBasicInfoService;
import com.qk.dm.dataquality.service.DqcSchedulerConfigService;
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
        DqcSchedulerBasicInfoMapper.INSTANCE.toDqcSchedulerBasicInfo(dqcSchedulerBasicInfoVO, basicInfo);
        // todo 修改人
        basicInfo.setUpdateUserid("admin");
        dqcSchedulerBasicInfoRepository.saveAndFlush(basicInfo);
    }

    @Override
    public void update(DqcSchedulerBasicInfo dqcSchedulerBasicInfo) {
        // todo 修改人
        dqcSchedulerBasicInfo.setUpdateUserid("admin");
        dqcSchedulerBasicInfoRepository.saveAndFlush(dqcSchedulerBasicInfo);
    }

    @Override
    public void deleteOne(Long id) {
        //todo 需要判断状态
        DqcSchedulerBasicInfo infoById = getInfoById(id);
        dqcSchedulerBasicInfoRepository.delete(infoById);
//        dqcSchedulerBasicInfoRepository.deleteById(id);
    }

    @Override
    public void deleteBulk(List<DqcSchedulerBasicInfo> schedulerBasicInfoList) {
        dqcSchedulerBasicInfoRepository.deleteAll(schedulerBasicInfoList);
    }

    @Override
    public DqcSchedulerBasicInfo getInfoById(Long id) {
        DqcSchedulerBasicInfo info = getBasicInfo(id);
        checkState(info);
        return info;
    }

    @Override
    public DqcSchedulerBasicInfo getBasicInfo(Long id) {
        DqcSchedulerBasicInfo info = dqcSchedulerBasicInfoRepository.findById(id).orElse(null);
        checkBasicInfo(id, info);
        return info;
    }

    private void checkState(DqcSchedulerBasicInfo info) {
        if (info.getSchedulerState().equals(SchedulerStateEnum.SCHEDULING.getCode())) {
            throw new BizException("启动调度后不可进行此操作！");
        }
    }

    private void checkBasicInfo(Long id, DqcSchedulerBasicInfo info) {
        if (info == null) {
            throw new BizException("id为：" + id + " 的任务，不存在！");
        }
    }

    @Override
    public List<DqcSchedulerBasicInfo> getInfoList(String ids) {
        List<DqcSchedulerBasicInfo> infoList = dqcSchedulerBasicInfoRepository.findAllById(getIdList(ids));
        checkBasicInfo(ids, infoList);
        return infoList.stream().peek(this::checkState).collect(Collectors.toList());
    }

    private List<Long> getIdList(String ids) {
        return Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toList());
    }

    private void checkBasicInfo(String ids, List<DqcSchedulerBasicInfo> infoList) {
        if (CollectionUtils.isEmpty(infoList)) {
            throw new BizException("id为：" + ids + " 的任务，不存在！");
        }
    }
}
