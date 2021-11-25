package com.qk.dm.dataquality.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataquality.constant.DqcConstant;
import com.qk.dm.dataquality.constant.SchedulerOpenStateEnum;
import com.qk.dm.dataquality.constant.SchedulerStateEnum;
import com.qk.dm.dataquality.constant.schedule.InstanceStateTypeEnum;
import com.qk.dm.dataquality.dolphinapi.builder.InstanceData;
import com.qk.dm.dataquality.entity.DqcSchedulerBasicInfo;
import com.qk.dm.dataquality.mapstruct.mapper.DqcSchedulerBasicInfoMapper;
import com.qk.dm.dataquality.params.dto.DqcSchedulerBasicInfoReleaseDto;
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
    DqcSchedulerBasicInfo basicInfo =
        DqcSchedulerBasicInfoMapper.INSTANCE.userDqcSchedulerBasicInfo(dqcSchedulerBasicInfoVO);
    // todo 创建人
    basicInfo.setCreateUserid(1L);
    basicInfo.setSchedulerState(SchedulerStateEnum.NOT_STARTED.getCode());
    basicInfo.setDelFlag(DqcConstant.DEL_FLAG_RETAIN);
    basicInfo.setSchedulerOpenState(SchedulerOpenStateEnum.CLOSE.getCode());
    dqcSchedulerBasicInfoRepository.saveAndFlush(basicInfo);
  }

  @Override
  public void update(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO) {
    DqcSchedulerBasicInfo basicInfo = getInfoById(dqcSchedulerBasicInfoVO.getId());
    DqcSchedulerBasicInfoMapper.INSTANCE.toDqcSchedulerBasicInfo(
        dqcSchedulerBasicInfoVO, basicInfo);
    // todo 修改人
    basicInfo.setUpdateUserid(1L);
    dqcSchedulerBasicInfoRepository.saveAndFlush(basicInfo);
  }

  @Override
  public void deleteOne(Long id) {
    dqcSchedulerBasicInfoRepository.delete(getInfoById(id));
    //todo 删除 规则、配置、调度 是否在本处删除
  }

  @Override
  public void deleteBulk(String ids) {
    dqcSchedulerBasicInfoRepository.deleteAll(getInfoList(ids));
  }

  @Override
  public void release(DqcSchedulerBasicInfoReleaseDto infoReleaseDto) {
    DqcSchedulerBasicInfo basicInfo = getBasicInfo(infoReleaseDto.getId());
    basicInfo.setSchedulerState(infoReleaseDto.getSchedulerOpenState());
    basicInfo.setSchedulerOpenState(infoReleaseDto.getSchedulerOpenState());

    Integer processDefinitionId = 4;
    Integer scheduleId = null;
    if (basicInfo.getSchedulerOpenState().equals(SchedulerOpenStateEnum.OPEN.getCode())) {
      // todo 查询定时id
      dolphinScheduler.online(processDefinitionId, scheduleId);
    } else {
      dolphinScheduler.offline(processDefinitionId);
    }

    // todo 修改人
    basicInfo.setUpdateUserid(1L);
    dqcSchedulerBasicInfoRepository.saveAndFlush(basicInfo);
  }

  @Override
  public void runing(Long id) {
    DqcSchedulerBasicInfo basicInfo = getBasicInfo(id);
    basicInfo.setSchedulerState(SchedulerStateEnum.RUNING.getCode());

    Integer processDefinitionId = 4;
    dolphinScheduler.startInstance(processDefinitionId);
  }

  @Override
  public Object instanceDetailByList(Long id) {
    DqcSchedulerBasicInfo basicInfo = getBasicInfo(id);
    //获取到最近运行实例
    Integer processDefinitionId = DqcConstant.processDefinitionId;
    InstanceData instanceData = dolphinScheduler.detailByList(processDefinitionId);
    //
    InstanceStateTypeEnum instanceStateTypeEnum =
        InstanceStateTypeEnum.fromValue(instanceData.getState());
    if (instanceStateTypeEnum != null) {
      instanceData.setStateName(instanceStateTypeEnum.getNotes());
      basicInfo.setSchedulerState(instanceStateTypeEnum.getSchedulerState().getCode());
      // 判断开启调度
      if (basicInfo.getSchedulerOpenState().equals(SchedulerOpenStateEnum.OPEN.getCode())) {
        switch (instanceStateTypeEnum.getSchedulerState().getCode()){
          case 4:
            basicInfo.setSchedulerState(SchedulerStateEnum.SCHEDULING.getCode());
            break;
          case 3:
            basicInfo.setSchedulerOpenState(SchedulerOpenStateEnum.CLOSE.getCode());
            break;
          default:
        }
      }
      // 保存basic
      dqcSchedulerBasicInfoRepository.saveAndFlush(basicInfo);
    }
    return instanceData;
  }

  private List<Long> getIdList(String ids) {
    return Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toList());
  }

  private List<DqcSchedulerBasicInfo> getInfoList(String ids) {
    List<DqcSchedulerBasicInfo> infoList =
        dqcSchedulerBasicInfoRepository.findAllById(getIdList(ids));
    checkBasicInfo(ids, infoList);
    return infoList.stream().peek(this::checkState).collect(Collectors.toList());
  }

  private DqcSchedulerBasicInfo getInfoById(Long id) {
    DqcSchedulerBasicInfo info = getBasicInfo(id);
    checkState(info);
    return info;
  }

  private DqcSchedulerBasicInfo getBasicInfo(Long id) {
    DqcSchedulerBasicInfo info = dqcSchedulerBasicInfoRepository.findById(id).orElse(null);
    checkBasicInfo(id, info);
    return info;
  }

  private void checkState(DqcSchedulerBasicInfo info) {
    if (info.getSchedulerOpenState().equals(SchedulerOpenStateEnum.OPEN.getCode())) {
      throw new BizException("启动调度后不可进行此操作！！！");
    }
  }

  private void checkBasicInfo(String ids, List<DqcSchedulerBasicInfo> infoList) {
    if (CollectionUtils.isEmpty(infoList)) {
      throw new BizException("id为：" + ids + " 的任务，不存在！！！");
    }
  }

  private void checkBasicInfo(Long id, DqcSchedulerBasicInfo info) {
    if (info == null) {
      throw new BizException("id为：" + id + " 的任务，不存在！！！");
    }
  }
}
