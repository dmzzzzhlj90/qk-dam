package com.qk.dm.dataingestion.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.dataingestion.mapstruct.mapper.DisSchedulerConfigMapper;
import com.qk.dm.dataingestion.repositories.DisSchedulerConfigRepository;
import com.qk.dm.dataingestion.service.DisSchedulerConfigService;
import com.qk.dm.dataingestion.vo.DisSchedulerConfigVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class DisSchedulerConfigServiceImpl implements DisSchedulerConfigService {

    private final DisSchedulerConfigRepository disSchedulerConfigRepository;

    public DisSchedulerConfigServiceImpl(DisSchedulerConfigRepository disSchedulerConfigRepository) {
        this.disSchedulerConfigRepository = disSchedulerConfigRepository;
    }

    @Override
    public void add(DisSchedulerConfigVO disSchedulerConfigVO) {
        disSchedulerConfigRepository.save(DisSchedulerConfigMapper.INSTANCE.of(disSchedulerConfigVO));
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
}
