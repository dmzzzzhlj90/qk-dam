package com.qk.dm.dataingestion.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.dataingestion.entity.DisMigrationBaseInfo;
import com.qk.dm.dataingestion.entity.QDisMigrationBaseInfo;
import com.qk.dm.dataingestion.mapstruct.mapper.DisBaseInfoMapper;
import com.qk.dm.dataingestion.repositories.DisMigrationBaseInfoRepository;
import com.qk.dm.dataingestion.service.DisBaseInfoService;
import com.qk.dm.dataingestion.vo.DisMigrationBaseInfoVO;
import com.querydsl.core.types.Predicate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class DisBaseInfoServiceImpl implements DisBaseInfoService {

    private final QDisMigrationBaseInfo qDisMigrationBaseInfo = QDisMigrationBaseInfo.disMigrationBaseInfo;
    private final DisMigrationBaseInfoRepository disMigrationBaseInfoRepository;

    public DisBaseInfoServiceImpl(DisMigrationBaseInfoRepository disMigrationBaseInfoRepository) {
        this.disMigrationBaseInfoRepository = disMigrationBaseInfoRepository;
    }

    @Override
    public Long add(DisMigrationBaseInfoVO disMigrationBaseInfoVO) {
        DisMigrationBaseInfo disMigrationBaseInfo = disMigrationBaseInfoRepository.save(DisBaseInfoMapper.
                INSTANCE.of(disMigrationBaseInfoVO));
        return disMigrationBaseInfo.getId();
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
        DisMigrationBaseInfo disMigrationBaseInfo = disMigrationBaseInfoRepository.getById(id);
        return DisBaseInfoMapper.INSTANCE.of(disMigrationBaseInfo);
    }

    @Override
    public Boolean exists(DisMigrationBaseInfoVO disMigrationBaseInfoVO) {

        Predicate predicate = qDisMigrationBaseInfo.jobName.eq(disMigrationBaseInfoVO.getJobName());
        return disMigrationBaseInfoRepository.exists(predicate);
    }

    @Override
    public Boolean sourceExists(DisMigrationBaseInfoVO disMigrationBaseInfoVO) {
        Predicate predicate = qDisMigrationBaseInfo.sourceConnect.eq(disMigrationBaseInfoVO.getSourceConnect())
                .and(qDisMigrationBaseInfo.sourceConnectType.eq(disMigrationBaseInfoVO.getSourceConnectType()))
                .and(qDisMigrationBaseInfo.sourceDatabase.eq(disMigrationBaseInfoVO.getSourceDatabase()))
                .and(qDisMigrationBaseInfo.sourceTable.eq(disMigrationBaseInfoVO.getSourceTable()))
                .and(qDisMigrationBaseInfo.id.eq(disMigrationBaseInfoVO.getId()));
          return disMigrationBaseInfoRepository.exists(predicate);
    }

    @Override
    public Boolean targetExists(DisMigrationBaseInfoVO disMigrationBaseInfoVO) {
        Predicate predicate = qDisMigrationBaseInfo.targetConnect.eq(disMigrationBaseInfoVO.getTargetConnect())
                .and(qDisMigrationBaseInfo.targetConnectType.eq(disMigrationBaseInfoVO.getTargetConnectType()))
                .and(qDisMigrationBaseInfo.targetDatabase.eq(disMigrationBaseInfoVO.getTargetDatabase()))
                .and(qDisMigrationBaseInfo.targetTable.eq(disMigrationBaseInfoVO.getTargetTable()))
                .and(qDisMigrationBaseInfo.id.eq(disMigrationBaseInfoVO.getId()));

        return disMigrationBaseInfoRepository.exists(predicate);
    }

}