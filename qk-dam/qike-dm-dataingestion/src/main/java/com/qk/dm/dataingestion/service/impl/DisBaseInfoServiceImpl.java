package com.qk.dm.dataingestion.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.dataingestion.entity.DisMigrationBaseInfo;
import com.qk.dm.dataingestion.mapstruct.mapper.DisBaseInfoMapper;
import com.qk.dm.dataingestion.repositories.DisMigrationBaseInfoRepository;
import com.qk.dm.dataingestion.service.DisBaseInfoService;
import com.qk.dm.dataingestion.vo.DisMigrationBaseInfoVO;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class DisBaseInfoServiceImpl implements DisBaseInfoService {

    private final DisMigrationBaseInfoRepository disMigrationBaseInfoRepository;

    public DisBaseInfoServiceImpl(DisMigrationBaseInfoRepository disMigrationBaseInfoRepository) {
        this.disMigrationBaseInfoRepository = disMigrationBaseInfoRepository;
    }

    @Override
    public Long add(DisMigrationBaseInfoVO ddgMigrationBaseInfoVO) {
        DisMigrationBaseInfo ddgMigrationBaseInfo = disMigrationBaseInfoRepository.save(DisBaseInfoMapper.
                INSTANCE.of(ddgMigrationBaseInfoVO));
        return ddgMigrationBaseInfo.getId();
    }

    @Override
    public void delete(List<Long> ids) {
        List<DisMigrationBaseInfo> rptBaseInfoList = disMigrationBaseInfoRepository.findAllById(ids);
        if(rptBaseInfoList.isEmpty()){
            throw new BizException("当前要删除的作业信息id为：" + ids + " 的数据不存在！！！");
        }
        disMigrationBaseInfoRepository.saveAllAndFlush(rptBaseInfoList.stream().peek(e->{
                    e.setDelFlag(1);
                }
        ).collect(Collectors.toList()));
    }

    @Override
    public void update(DisMigrationBaseInfoVO disMigrationBaseInfoVO) {
        if(Objects.isNull(disMigrationBaseInfoVO.getId())){
            throw new BizException("修改作业时id不能为空！！！");
        }
        disMigrationBaseInfoRepository.saveAndFlush(DisBaseInfoMapper.INSTANCE.of(disMigrationBaseInfoVO));
    }

    @Override
    public DisMigrationBaseInfoVO detail(Long id) {
        DisMigrationBaseInfo ddgMigrationBaseInfo = disMigrationBaseInfoRepository.getById(id);
        return DisBaseInfoMapper.INSTANCE.of(ddgMigrationBaseInfo);
    }

}
