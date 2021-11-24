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

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
  public void insert(DqcSchedulerConfigVO dqcSchedulerConfigVO) {
    DqcSchedulerConfig config =
        DqcSchedulerConfigMapper.INSTANCE.userDqcSchedulerConfig(dqcSchedulerConfigVO);
    saveCron(dqcSchedulerConfigVO, config);
    // todo 创建人
    config.setCreateUserid(1L);
    config.setDelFlag(DqcConstant.DEL_FLAG_RETAIN);
    dqcSchedulerConfigRepository.saveAndFlush(config);
  }

  @Override
  public void update(DqcSchedulerConfigVO dqcSchedulerConfigVO) {
    DqcSchedulerConfig config = getInfoById(dqcSchedulerConfigVO.getId());
    DqcSchedulerConfigMapper.INSTANCE.userDqcSchedulerConfig(dqcSchedulerConfigVO, config);
    saveCron(dqcSchedulerConfigVO, config);
    // todo 修改人
    config.setUpdateUserid(1L);
    dqcSchedulerConfigRepository.save(config);
  }

  @Override
  public void deleteOne(Long id) {
    boolean exists = dqcSchedulerConfigRepository.exists(qDqcSchedulerConfig.id.eq(id));
    if (exists) {
      dqcSchedulerConfigRepository.deleteById(id);
    }
  }

  @Override
  public void deleteBulk(String ids) {
    dqcSchedulerConfigRepository.deleteAllByIdInBatch(getIdList(ids));
  }

  @Override
  public void deleteByJobId(String jobId) {
    dqcSchedulerConfigRepository.delete(checkConfigIsNullByTaskId(jobId));
  }

  @Override
  public void deleteBulkByJobIds(List<String> jobIds) {
    dqcSchedulerConfigRepository.deleteAll(getInfoByTaskIds(jobIds));
  }

  @Override
  public DqcSchedulerConfigVO detail(String jobId) {
    return DqcSchedulerConfigMapper.INSTANCE.userDqcSchedulerConfigVO(
        checkConfigIsNullByTaskId(jobId));
  }

  private DqcSchedulerConfig getInfoById(Long id) {
    DqcSchedulerConfig info = dqcSchedulerConfigRepository.findById(id).orElse(null);
    checkConfig(id, info);
    return info;
  }

  private DqcSchedulerConfig checkConfigIsNullByTaskId(String jobId) {
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

  private List<Long> getIdList(String ids) {
    return Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toList());
  }

  private List<DqcSchedulerConfig> getInfoByTaskIds(List<String> jobIds) {
    return (List<DqcSchedulerConfig>)
        dqcSchedulerConfigRepository.findAll(qDqcSchedulerConfig.jobId.in(jobIds));
  }

  private DqcSchedulerConfig getInfoByTaskId(String jobId) {
    return dqcSchedulerConfigRepository.findOne(qDqcSchedulerConfig.jobId.eq(jobId)).orElse(null);
  }

  private void saveCron(DqcSchedulerConfigVO dqcSchedulerConfigVO, DqcSchedulerConfig config) {
    if (Objects.equals(dqcSchedulerConfigVO.getRunType(), DqcConstant.RUN_TYPE)) {
      config.setCron(CronUtil.createCron(dqcSchedulerConfigVO));
    }
  }
}
