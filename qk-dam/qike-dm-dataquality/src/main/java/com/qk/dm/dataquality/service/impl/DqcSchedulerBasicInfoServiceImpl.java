package com.qk.dm.dataquality.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataquality.constant.DqcConstant;
import com.qk.dm.dataquality.entity.DqcSchedulerBasicInfo;
import com.qk.dm.dataquality.entity.QDqcSchedulerBasicInfo;
import com.qk.dm.dataquality.mapstruct.mapper.DqcSchedulerBasicInfoMapper;
import com.qk.dm.dataquality.repositories.DqcSchedulerBasicInfoRepository;
import com.qk.dm.dataquality.service.DqcSchedulerBasicInfoService;
import com.qk.dm.dataquality.service.DqcSchedulerConfigService;
import com.qk.dm.dataquality.vo.DqcSchedulerBasicInfoVO;
import com.qk.dm.dataquality.vo.DqcSchedulerInfoParamsVO;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
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
  private final QDqcSchedulerBasicInfo qDqcSchedulerBasicInfo =
      QDqcSchedulerBasicInfo.dqcSchedulerBasicInfo;

  public DqcSchedulerBasicInfoServiceImpl(
      DqcSchedulerBasicInfoRepository dqcSchedulerBasicInfoRepository,
      DqcSchedulerConfigService dqcSchedulerConfigService) {
    this.dqcSchedulerBasicInfoRepository = dqcSchedulerBasicInfoRepository;
    this.dqcSchedulerConfigService = dqcSchedulerConfigService;
  }

  @Override
  public PageResultVO<DqcSchedulerBasicInfoVO> searchPageList(
      DqcSchedulerInfoParamsVO dsdSchedulerAllParamsVO) {
    return null;
  }

  @Override
  public String insert(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO) {
    DqcSchedulerBasicInfo basicInfo =
        DqcSchedulerBasicInfoMapper.INSTANCE.userDqcSchedulerBasicInfo(dqcSchedulerBasicInfoVO);
    basicInfo.setJobId(UUID.randomUUID().toString().replaceAll("-", ""));
    // todo 创建人
    basicInfo.setCreateUserid(1L);
    dqcSchedulerBasicInfoRepository.saveAndFlush(basicInfo);
    return basicInfo.getJobId();
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
  public void publish(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO) {
    DqcSchedulerBasicInfo basicInfo = getBasicInfo(dqcSchedulerBasicInfoVO.getId());
    // 更改各种状态
    // todo 如果状态为发布状态，需要判断规则、时间是否配置
    basicInfo.setSchedulerState(dqcSchedulerBasicInfoVO.getSchedulerState());
    // todo 修改人
    basicInfo.setUpdateUserid(1L);
    dqcSchedulerBasicInfoRepository.saveAndFlush(basicInfo);
  }

  @Override
  @Transient
  public void delete(Long id) {
    DqcSchedulerBasicInfo info = getInfoById(id);
    dqcSchedulerConfigService.delete(info.getJobId());
    dqcSchedulerBasicInfoRepository.delete(info);
  }

  @Override
  @Transient
  public void deleteBulk(String ids) {
    List<Long> idList =
        Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toList());
    List<DqcSchedulerBasicInfo> infoList = getInfoList(idList);
    List<String> taskIds =
        infoList.stream().map(DqcSchedulerBasicInfo::getJobId).collect(Collectors.toList());
    dqcSchedulerConfigService.deleteBulk(taskIds);
    dqcSchedulerBasicInfoRepository.deleteAll(infoList);
  }

  private List<DqcSchedulerBasicInfo> getInfoList(List<Long> idList) {
    List<DqcSchedulerBasicInfo> infoList = dqcSchedulerBasicInfoRepository.findAllById(idList);
    if (CollectionUtils.isEmpty(infoList)) {
      throw new BizException("id为：" + idList + " 的任务，不存在！！！");
    }
    if (infoList.stream()
        .anyMatch(
            i ->
                !Objects.equals(i.getSchedulerState(), DqcConstant.INIT_STATE)
                    && !Objects.equals(i.getSchedulerState(), DqcConstant.STOP_STATE))) {
      throw new BizException("启动调度后不可操作！！！");
    }
    return infoList;
  }

  private DqcSchedulerBasicInfo getInfoById(Long id) {
    DqcSchedulerBasicInfo info = getBasicInfo(id);
    if (!Objects.equals(info.getSchedulerState(), DqcConstant.INIT_STATE)
        && !Objects.equals(info.getSchedulerState(), DqcConstant.STOP_STATE)) {
      throw new BizException("启动调度后不可操作！！！");
    }
    return info;
  }

  private DqcSchedulerBasicInfo getBasicInfo(Long id) {
    DqcSchedulerBasicInfo info = dqcSchedulerBasicInfoRepository.findById(id).orElse(null);
    if (info == null) {
      throw new BizException("id为：" + id + " 的任务，不存在！！！");
    }
    return info;
  }
}
