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

        disSchedulerConfigVO.setCron(CronUtil.createCron(disSchedulerConfigVO));
        DisSchedulerConfig disSchedulerConfig = DisSchedulerConfigMapper.INSTANCE.of(disSchedulerConfigVO);

        disSchedulerConfigRepository.save(disSchedulerConfig);
    }

    @Override
    public void delete(List<Long> baseIdList) {
        baseIdList.forEach(disSchedulerConfigRepository::deleteByBaseInfoId);
    }

    @Override
    public void update(Long baseInfoId,DisSchedulerConfigVO disSchedulerConfigVO) {
        DisSchedulerConfig disSchedulerConfig = disSchedulerConfigRepository.findByBaseInfoId(baseInfoId);
        DisSchedulerConfigMapper.INSTANCE.of(disSchedulerConfigVO,disSchedulerConfig);
        disSchedulerConfigRepository.saveAndFlush(disSchedulerConfig);
    }

    @Override
    public void update(Long baseInfoId, Integer schedulerId) {
        if(Objects.isNull(schedulerId)){ return; }
        DisSchedulerConfig disSchedulerConfig = disSchedulerConfigRepository.findByBaseInfoId(baseInfoId);
        if(Objects.isNull(disSchedulerConfig)){
            throw new BizException("当前作业不存在定时:"+baseInfoId);
        }
        disSchedulerConfig.setSchedulerId(schedulerId);
        disSchedulerConfigRepository.saveAndFlush(disSchedulerConfig);
    }

    @Override
    public DisSchedulerConfigVO detail(Long baseId) {

        return DisSchedulerConfigMapper.INSTANCE.of(disSchedulerConfigRepository.findByBaseInfoId(baseId));
    }

}
