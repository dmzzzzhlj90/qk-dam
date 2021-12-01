package com.qk.dm.dataquality.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataquality.constant.DqcConstant;
import com.qk.dm.dataquality.entity.DqcSchedulerConfig;
import com.qk.dm.dataquality.entity.QDqcSchedulerConfig;
import com.qk.dm.dataquality.mapstruct.mapper.DqcSchedulerConfigMapper;
import com.qk.dm.dataquality.repositories.DqcSchedulerConfigRepository;
import com.qk.dm.dataquality.service.DqcSchedulerConfigService;
import com.qk.dm.dataquality.utils.CronUtil;
import com.qk.dm.dataquality.vo.DqcSchedulerConfigVO;
import com.qk.dm.dataquality.vo.DqcSchedulerInfoParamsVO;
import org.springframework.stereotype.Service;

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

  public DqcSchedulerConfigServiceImpl(DqcSchedulerConfigRepository dqcSchedulerConfigRepository) {
    this.dqcSchedulerConfigRepository = dqcSchedulerConfigRepository;
  }

  @Override
  public PageResultVO<DqcSchedulerConfigVO> searchPageList(
      DqcSchedulerInfoParamsVO dsdSchedulerAllParamsVO) {
    return null;
  }

  @Override
  public void insert(DqcSchedulerConfigVO dqcSchedulerConfigVO, String jobId) {
    dqcSchedulerConfigVO.setJobId(jobId);
    DqcSchedulerConfig config =
        DqcSchedulerConfigMapper.INSTANCE.userDqcSchedulerConfig(dqcSchedulerConfigVO);
    String cron = generateCron(dqcSchedulerConfigVO);
    config.setCron(cron);
    dqcSchedulerConfigVO.setCron(cron);
    // todo 创建人
    config.setCreateUserid("admin");
    config.setDelFlag(DqcConstant.DEL_FLAG_RETAIN);
    dqcSchedulerConfigRepository.saveAndFlush(config);
  }

  @Override
  public void update(DqcSchedulerConfigVO dqcSchedulerConfigVO) {
    DqcSchedulerConfig config = getInfoById(dqcSchedulerConfigVO.getId());
    DqcSchedulerConfigMapper.INSTANCE.userDqcSchedulerConfig(dqcSchedulerConfigVO, config);
    String cron = generateCron(dqcSchedulerConfigVO);
    config.setCron(cron);
    dqcSchedulerConfigVO.setCron(cron);
    // todo 修改人
    config.setUpdateUserid("admin");
    dqcSchedulerConfigRepository.save(config);
  }

  @Override
  public void update(DqcSchedulerConfig dqcSchedulerConfig) {
    // todo 修改人
    dqcSchedulerConfig.setUpdateUserid("admin");
    dqcSchedulerConfigRepository.saveAndFlush(dqcSchedulerConfig);
  }

  @Override
  public void update(String jobId, Integer schedulerId) {
    DqcSchedulerConfig config = checkConfigIsNotNullByTaskId(jobId);
    config.setSchedulerId(schedulerId);
    // todo 修改人
    config.setUpdateUserid("admin");
    dqcSchedulerConfigRepository.save(config);
  }

  @Override
  public void deleteOne(DqcSchedulerConfig config) {
    dqcSchedulerConfigRepository.delete(config);
  }

  @Override
  public void deleteBulk(List<DqcSchedulerConfig> dqcSchedulerConfigs) {
    dqcSchedulerConfigRepository.deleteAll(dqcSchedulerConfigs);
  }

  @Override
  public void deleteByJobId(String jobId) {
    dqcSchedulerConfigRepository.delete(checkConfigIsNotNullByTaskId(jobId));
  }

  @Override
  public void deleteBulkByJobIds(List<String> jobIds) {
    dqcSchedulerConfigRepository.deleteAll(getInfoByTaskIds(jobIds));
  }

  @Override
  public DqcSchedulerConfig getConfig(String jobId) {
    return checkConfigIsNotNullByTaskId(jobId);
  }

  @Override
  public List<DqcSchedulerConfig> getConfigList(List<String> jobIds) {
    return getInfoByTaskIds(jobIds);
  }

  private DqcSchedulerConfig getInfoById(Long id) {
    DqcSchedulerConfig info = dqcSchedulerConfigRepository.findById(id).orElse(null);
    checkConfig(id, info);
    return info;
  }

  private DqcSchedulerConfig checkConfigIsNotNullByTaskId(String jobId) {
    DqcSchedulerConfig schedulerConfig = getInfoByTaskId(jobId);
    checkConfig(jobId, schedulerConfig);
    return schedulerConfig;
  }

  private void checkConfig(Long id, DqcSchedulerConfig info) {
    if (info == null) {
      throw new BizException("id为：" + id + " 的配置，不存在！！！");
    }
  }

  private void checkConfig(String jobId, DqcSchedulerConfig schedulerConfig) {
    if (schedulerConfig == null) {
      throw new BizException("任务id为：" + jobId + " 的配置，不存在！！！");
    }
  }

  private List<DqcSchedulerConfig> getInfoByTaskIds(List<String> jobIds) {
    return (List<DqcSchedulerConfig>)
        dqcSchedulerConfigRepository.findAll(qDqcSchedulerConfig.jobId.in(jobIds));
  }

  private DqcSchedulerConfig getInfoByTaskId(String jobId) {
    return dqcSchedulerConfigRepository.findOne(qDqcSchedulerConfig.jobId.eq(jobId)).orElse(null);
  }

  private String generateCron(DqcSchedulerConfigVO dqcSchedulerConfigVO) {
    if (Objects.equals(dqcSchedulerConfigVO.getRunType(), DqcConstant.RUN_TYPE)) {
      return CronUtil.createCron(dqcSchedulerConfigVO);
    }
    return null;
  }
}
