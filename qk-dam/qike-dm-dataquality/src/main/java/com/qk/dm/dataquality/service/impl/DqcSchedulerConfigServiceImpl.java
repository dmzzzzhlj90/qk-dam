package com.qk.dm.dataquality.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataquality.constant.DqcConstant;
import com.qk.dm.dataquality.constant.SchedulerStateEnum;
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
    dqcSchedulerConfigRepository.save(config);
    //如果周期调度，需要调度下线并修改时间配置
  }

  @Override
  public void deleteOne(String taskId) {
    dqcSchedulerConfigRepository.delete(getInfoByTaskId(taskId));
  }

  @Override
  public void deleteBulk(List<String> taskIds) {
    dqcSchedulerConfigRepository.deleteAll(getInfoByTaskIds(taskIds));
  }

  @Override
  public DqcSchedulerConfigVO detail(String jobId) {
    return DqcSchedulerConfigMapper.INSTANCE.userDqcSchedulerConfigVO(
        checkConfigIsNullByTaskId(jobId));
  }

  private DqcSchedulerConfig getInfoById(Long id) {
    DqcSchedulerConfig info = dqcSchedulerConfigRepository.findById(id).orElse(null);
    if (info == null) {
      throw new BizException("id为：" + id + " 的配置，不存在！！！");
    }
    return info;
  }

  private void checkConfigIsNotNullByTaskId(String jobId) {
    DqcSchedulerConfig schedulerConfig = getInfoByTaskId(jobId);
    if (schedulerConfig != null) {
      throw new BizException("任务id为：" + jobId + " 的配置，已存在！！！");
    }
  }

  private DqcSchedulerConfig checkConfigIsNullByTaskId(String jobId) {
    DqcSchedulerConfig schedulerConfig = getInfoByTaskId(jobId);
    if (schedulerConfig == null) {
      throw new BizException("任务id为：" + jobId + " 的配置，不存在！！！");
    }
    return schedulerConfig;
  }

  private List<DqcSchedulerConfig> getInfoByTaskIds(List<String> jobIds) {
    return (List<DqcSchedulerConfig>)
        dqcSchedulerConfigRepository.findAll(qDqcSchedulerConfig.jobId.in(jobIds));
  }

  private DqcSchedulerConfig getInfoByTaskId(String jobId) {
    return dqcSchedulerConfigRepository.findOne(qDqcSchedulerConfig.jobId.eq(jobId)).orElse(null);
  }

  public void checkBasicInfoStateByTaskId(String jobId) {
    DqcSchedulerBasicInfo info =
        dqcSchedulerBasicInfoRepository
            .findOne(qDqcSchedulerBasicInfo.jobId.eq(jobId))
            .orElse(null);
    if (info == null) {
      throw new BizException("任务id为：" + jobId + " 的任务，不存在！！！");
    }
    if (!SchedulerStateEnum.checkout(SchedulerStateEnum.fromValue(info.getSchedulerState()))) {
      throw new BizException("启动调度后不可操作！！！");
    }
  }
}
