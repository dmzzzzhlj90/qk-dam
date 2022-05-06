package com.qk.dm.dataingestion.service.impl;

import com.qk.dm.dataingestion.mapstruct.mapper.DisColumnInfoMapper;
import com.qk.dm.dataingestion.repositories.DisColumnInfoRepository;

import com.qk.dm.dataingestion.service.DisColumnInfoService;
import com.qk.dm.dataingestion.vo.DisColumnInfoVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class DisColumnInfoServiceImpl implements DisColumnInfoService {
    private final DisColumnInfoRepository disColumnInfoRepository;

    public DisColumnInfoServiceImpl(DisColumnInfoRepository disColumnInfoRepository) {
        this.disColumnInfoRepository = disColumnInfoRepository;
    }

    @Override
    public void batchAdd(List<DisColumnInfoVO> disColumnInfoList) {
        disColumnInfoRepository.saveAllAndFlush(DisColumnInfoMapper.INSTANCE.list(disColumnInfoList));
    }

    @Override
    public void delete(List<Long> baseIdList) {
        baseIdList.forEach(disColumnInfoRepository::deleteByBaseInfoId);
    }

    @Override
    public void update(Long baseId, List<DisColumnInfoVO> disColumnInfoList) {
        //先将之前的字段数据删除
        disColumnInfoRepository.deleteByBaseInfoId(baseId);
        disColumnInfoList.forEach(e->e.setBaseInfoId(baseId));
        disColumnInfoRepository.saveAllAndFlush(DisColumnInfoMapper.INSTANCE.list(disColumnInfoList));
    }

    @Override
    public List<DisColumnInfoVO> list(Long baseInfoId) {
        if(Objects.nonNull(baseInfoId)) {
            return DisColumnInfoMapper.INSTANCE.listVO(disColumnInfoRepository.findByBaseInfoId(baseInfoId));
        }
        return List.of();
    }
}
