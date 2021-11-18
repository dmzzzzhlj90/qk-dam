package com.qk.dm.dataquality.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataquality.constant.DqcConstant;
import com.qk.dm.dataquality.entity.DqcSchedulerBasicInfo;
import com.qk.dm.dataquality.entity.DqcSchedulerConfig;
import com.qk.dm.dataquality.entity.QDqcSchedulerBasicInfo;
import com.qk.dm.dataquality.entity.QDqcSchedulerConfig;
import com.qk.dm.dataquality.mapstruct.mapper.DqcSchedulerConfigMapper;
import com.qk.dm.dataquality.repositories.DqcSchedulerBasicInfoRepository;
import com.qk.dm.dataquality.repositories.DqcSchedulerConfigRepository;
import com.qk.dm.dataquality.service.DqcSchedulerConfigService;
import com.qk.dm.dataquality.utils.CronUtil;
import com.qk.dm.dataquality.vo.DqcSchedulerConfigVO;
import com.qk.dm.dataquality.vo.DqcSchedulerInfoParamsVO;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author wjq
 * @date 2021/11/10
 * @since 1.0.0
 */
@Service
public class DqcSchedulerConfigServiceImpl implements DqcSchedulerConfigService {
  private final DqcSchedulerConfigRepository dqcSchedulerConfigRepository;
  private final QDqcSchedulerConfig qDqcSchedulerConfig = QDqcSchedulerConfig.dqcSchedulerConfig;

  private final DqcSchedulerBasicInfoRepository dqcSchedulerBasicInfoRepository;
  private final QDqcSchedulerBasicInfo qDqcSchedulerBasicInfo =
      QDqcSchedulerBasicInfo.dqcSchedulerBasicInfo;

  public DqcSchedulerConfigServiceImpl(
      DqcSchedulerConfigRepository dqcSchedulerConfigRepository,
      DqcSchedulerBasicInfoRepository dqcSchedulerBasicInfoRepository) {
    this.dqcSchedulerConfigRepository = dqcSchedulerConfigRepository;
    this.dqcSchedulerBasicInfoRepository = dqcSchedulerBasicInfoRepository;
  }

  @Override
  public PageResultVO<DqcSchedulerConfigVO> searchPageList(
      DqcSchedulerInfoParamsVO dsdSchedulerAllParamsVO) {
    return null;
  }

  @Override
  public void insert(DqcSchedulerConfigVO dqcSchedulerConfigVO) {
    checkConfigIsNotNullByTaskId(dqcSchedulerConfigVO.getJobId());
    DqcSchedulerConfig config =
        DqcSchedulerConfigMapper.INSTANCE.userDqcSchedulerConfig(dqcSchedulerConfigVO);
    if (Objects.equals(dqcSchedulerConfigVO.getRunType(), DqcConstant.RUN_TYPE)) {
      config.setCron(CronUtil.createCron(dqcSchedulerConfigVO));
    }
    // todo 创建人
    config.setCreateUserid(1L);
    config.setGmtCreate(new Date());
    config.setGmtModified(new Date());
    config.setDelFlag(0);
    dqcSchedulerConfigRepository.saveAndFlush(config);
  }

  @Override
  public void update(DqcSchedulerConfigVO dqcSchedulerConfigVO) {
    DqcSchedulerConfig config = getInfoById(dqcSchedulerConfigVO.getId());
    // 判断调度规则为停止
    checkBasicInfoStateByTaskId(config.getJobId());
    if (Objects.equals(dqcSchedulerConfigVO.getRunType(), DqcConstant.RUN_TYPE)) {
      config.setCron(CronUtil.createCron(dqcSchedulerConfigVO));
    }
    DqcSchedulerConfigMapper.INSTANCE.userDqcSchedulerConfig(dqcSchedulerConfigVO, config);
    // todo 修改人
    config.setUpdateUserid(1L);
    config.setGmtModified(new Date());
    config.setDelFlag(0);
    dqcSchedulerConfigRepository.save(config);
  }

  @Override
  public void delete(String taskId) {
    dqcSchedulerConfigRepository.delete(getInfoByTaskId(taskId));
  }

  @Override
  public void deleteBulk(List<String> taskIds) {
    dqcSchedulerConfigRepository.deleteAll(getInfoByTaskIds(taskIds));
  }

  private DqcSchedulerConfig getInfoById(Long id) {
    DqcSchedulerConfig info = dqcSchedulerConfigRepository.findById(id).orElse(null);
    if (info == null) {
      throw new BizException("id为：" + id + " 的配置，不存在！！！");
    }
    return info;
  }

  private void checkConfigIsNotNullByTaskId(String taskId) {
    DqcSchedulerConfig schedulerConfig = getInfoByTaskId(taskId);
    if (schedulerConfig != null) {
      throw new BizException("任务id为：" + taskId + " 的配置，已存在！！！");
    }
  }

  private List<DqcSchedulerConfig> getInfoByTaskIds(List<String> taskIds) {
    return (List<DqcSchedulerConfig>)
        dqcSchedulerConfigRepository.findAll(qDqcSchedulerConfig.jobId.in(taskIds));
  }

  private DqcSchedulerConfig getInfoByTaskId(String taskId) {
    return dqcSchedulerConfigRepository.findOne(qDqcSchedulerConfig.jobId.eq(taskId)).orElse(null);
  }

  public void checkBasicInfoStateByTaskId(String taskId) {
    DqcSchedulerBasicInfo info =
        dqcSchedulerBasicInfoRepository
            .findOne(qDqcSchedulerBasicInfo.jobId.eq(taskId))
            .orElse(null);
    if (info == null) {
      throw new BizException("id为：" + taskId + " 的任务，不存在！！！");
    }
    if (!Objects.equals(info.getSchedulerState(), DqcConstant.INIT_STATE)
        && !Objects.equals(info.getSchedulerState(), DqcConstant.STOP_STATE)) {
      throw new BizException("启动调度后不可操作！！！");
    }
  }
}
