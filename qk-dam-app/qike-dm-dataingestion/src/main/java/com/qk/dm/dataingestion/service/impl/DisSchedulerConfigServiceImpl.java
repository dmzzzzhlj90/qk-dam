package com.qk.dm.dataingestion.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.dataingestion.entity.DisSchedulerConfig;
import com.qk.dm.dataingestion.mapstruct.mapper.DisSchedulerConfigMapper;
import com.qk.dm.dataingestion.repositories.DisSchedulerConfigRepository;
import com.qk.dm.dataingestion.service.DisSchedulerConfigService;
import com.qk.dm.dataingestion.util.CronUtil;
import com.qk.dm.dataingestion.vo.DisSchedulerConfigVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 作业任务配置
 * @author wangzp
 * @date 2022/04/07 14:27
 * @since 1.0.0
 */
@Service
public class DisSchedulerConfigServiceImpl implements DisSchedulerConfigService {

    private final DisSchedulerConfigRepository disSchedulerConfigRepository;

    public DisSchedulerConfigServiceImpl(DisSchedulerConfigRepository disSchedulerConfigRepository) {
        this.disSchedulerConfigRepository = disSchedulerConfigRepository;
    }

    @Override
    public void add(DisSchedulerConfigVO disSchedulerConfigVO) {
        DisSchedulerConfig disSchedulerConfig = DisSchedulerConfigMapper.INSTANCE.of(disSchedulerConfigVO);
        disSchedulerConfig.setCron(createCron(disSchedulerConfig));
        disSchedulerConfigRepository.save(disSchedulerConfig);
    }

    @Override
    public void delete(List<Long> baseIdList) {
        baseIdList.forEach(disSchedulerConfigRepository::deleteByBaseInfoId);
    }

    @Override
    public void update(DisSchedulerConfigVO disSchedulerConfigVO) {
        if(Objects.isNull(disSchedulerConfigVO.getId())){
            throw new BizException("修改任务配置时id不能为空！！！");
        }
        disSchedulerConfigRepository.saveAndFlush(DisSchedulerConfigMapper.INSTANCE.of(disSchedulerConfigVO));
    }

    @Override
    public DisSchedulerConfigVO detail(Long baseId) {

        return DisSchedulerConfigMapper.INSTANCE.of(disSchedulerConfigRepository.findByBaseInfoId(baseId));
    }
    /**
     * 方法摘要：构建Cron表达式
     */
    public static String createCron(DisSchedulerConfig disSchedulerConfig) {

        return CronUtil.createCron(disSchedulerConfig.getSchedulerCycle(),
                disSchedulerConfig.getSchedulerIntervalTime(),
                disSchedulerConfig.getSchedulerTime());
    }
}
