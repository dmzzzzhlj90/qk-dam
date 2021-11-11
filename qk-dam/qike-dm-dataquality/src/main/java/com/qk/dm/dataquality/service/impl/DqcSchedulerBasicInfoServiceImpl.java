package com.qk.dm.dataquality.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataquality.entity.DqcSchedulerBasicInfo;
import com.qk.dm.dataquality.mapstruct.mapper.DqcSchedulerBasicInfoMapper;
import com.qk.dm.dataquality.repositories.DqcSchedulerBasicInfoRepository;
import com.qk.dm.dataquality.service.DqcSchedulerBasicInfoService;
import com.qk.dm.dataquality.service.DqcSchedulerConfigService;
import com.qk.dm.dataquality.vo.DqcSchedulerBasicInfoVO;
import com.qk.dm.dataquality.vo.DqcSchedulerInfoParamsVO;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
    basicInfo.setTaskId(UUID.randomUUID().toString().replaceAll("-", ""));
    // todo 创建人
    basicInfo.setCreateUserid(1L);
    dqcSchedulerBasicInfoRepository.saveAndFlush(basicInfo);
    return basicInfo.getTaskId();
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
  @Transient
  public void delete(Long id) {
    DqcSchedulerBasicInfo info = getInfoById(id);
    dqcSchedulerConfigService.delete(info.getTaskId());
    dqcSchedulerBasicInfoRepository.delete(info);
  }

  @Override
  @Transient
  public void deleteBulk(String ids) {
    List<Long> idList =
        Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toList());
    List<DqcSchedulerBasicInfo> infoList = dqcSchedulerBasicInfoRepository.findAllById(idList);
    List<String> taskIds =
        infoList.stream().map(DqcSchedulerBasicInfo::getTaskId).collect(Collectors.toList());
    dqcSchedulerConfigService.deleteBulk(taskIds);
    dqcSchedulerBasicInfoRepository.deleteAll(infoList);
  }

  private DqcSchedulerBasicInfo getInfoById(Long id) {
    Optional<DqcSchedulerBasicInfo> info = dqcSchedulerBasicInfoRepository.findById(id);
    if (info.isEmpty()) {
      throw new BizException("id为：" + id + " 的任务不存在！！！");
    }
    return info.get();
  }
}
