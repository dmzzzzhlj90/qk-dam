package com.qk.dm.dataquality.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataquality.entity.DqcSchedulerConfig;
import com.qk.dm.dataquality.entity.QDqcSchedulerConfig;
import com.qk.dm.dataquality.mapstruct.mapper.DqcSchedulerConfigMapper;
import com.qk.dm.dataquality.repositories.DqcSchedulerConfigRepository;
import com.qk.dm.dataquality.service.DqcSchedulerConfigService;
import com.qk.dm.dataquality.vo.DqcSchedulerConfigVO;
import com.qk.dm.dataquality.vo.DqcSchedulerInfoParamsVO;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

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
    getInfoByTaskIdIsNotNull(dqcSchedulerConfigVO.getTaskId());
    DqcSchedulerConfig config =
        DqcSchedulerConfigMapper.INSTANCE.userDqcSchedulerConfig(dqcSchedulerConfigVO);
    // todo 创建人
    config.setCreateUserid(1L);
    dqcSchedulerConfigRepository.saveAndFlush(config);
  }

  @Override
  public void update(DqcSchedulerConfigVO dqcSchedulerConfigVO) {
    DqcSchedulerConfig config = getInfoById(dqcSchedulerConfigVO.getId());
    DqcSchedulerConfigMapper.INSTANCE.userDqcSchedulerConfig(dqcSchedulerConfigVO, config);
    // todo 修改人
    config.setUpdateUserid(1L);
    dqcSchedulerConfigRepository.saveAndFlush(config);
  }

  @Override
  public void delete(String taskId) {
    dqcSchedulerConfigRepository.delete(getInfoByTaskIdIsNull(taskId));
  }

  @Override
  public void deleteBulk(List<String> taskIds) {
    dqcSchedulerConfigRepository.deleteAll(getInfoByTaskId(taskIds));
  }

  private DqcSchedulerConfig getInfoById(Long id) {
    DqcSchedulerConfig info = dqcSchedulerConfigRepository.findById(id).orElse(null);
    if (info == null) {
      throw new BizException("id为：" + id + " 的配置，不存在！！！");
    }
    return info;
  }

  private List<DqcSchedulerConfig> getInfoByTaskId(List<String> taskIds) {
    List<DqcSchedulerConfig> configs = getInfoByTaskIds(taskIds);
    if (CollectionUtils.isEmpty(configs)) {
      throw new BizException("任务id为：" + taskIds + " 的配置，不存在！！！");
    }
    return configs;
  }

  private DqcSchedulerConfig getInfoByTaskIdIsNull(String taskId) {
    DqcSchedulerConfig schedulerConfig = getInfoByTaskId(taskId);
    if (schedulerConfig == null) {
      throw new BizException("任务id为：" + taskId + " 的配置，不存在！！！");
    }
    return schedulerConfig;
  }

  private DqcSchedulerConfig getInfoByTaskIdIsNotNull(String taskId) {
    DqcSchedulerConfig schedulerConfig = getInfoByTaskId(taskId);
    if (schedulerConfig != null) {
      throw new BizException("任务id为：" + taskId + " 的配置，已存在！！！");
    }
    return schedulerConfig;
  }

  private List<DqcSchedulerConfig> getInfoByTaskIds(List<String> taskIds) {
    return (List<DqcSchedulerConfig>)
        dqcSchedulerConfigRepository.findAll(qDqcSchedulerConfig.taskId.in(taskIds));
  }

  private DqcSchedulerConfig getInfoByTaskId(String taskId) {
    return dqcSchedulerConfigRepository.findOne(qDqcSchedulerConfig.taskId.eq(taskId)).orElse(null);
  }
}
