package com.qk.dm.dataingestion.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.datacenter.model.ProcessDefinition;
import com.qk.dm.dataingestion.datax.DataxDolphinClient;
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


@Service
public class DisBaseInfoServiceImpl implements DisBaseInfoService {

    private final QDisMigrationBaseInfo qDisMigrationBaseInfo = QDisMigrationBaseInfo.disMigrationBaseInfo;
    private final DisMigrationBaseInfoRepository disMigrationBaseInfoRepository;
    private final DataxDolphinClient dataxDolphinClient;

    public DisBaseInfoServiceImpl(DisMigrationBaseInfoRepository disMigrationBaseInfoRepository, DataxDolphinClient dataxDolphinClient) {
        this.disMigrationBaseInfoRepository = disMigrationBaseInfoRepository;
        this.dataxDolphinClient = dataxDolphinClient;
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
        rptBaseInfoList.forEach(e->{
            //下线
            dataxDolphinClient.dolphinProcessRelease(e.getTaskCode(),
                    ProcessDefinition.ReleaseStateEnum.OFFLINE);
            //删除流程定义
            dataxDolphinClient.deleteProcessDefinition(e.getTaskCode().toString());
        });
        disMigrationBaseInfoRepository.deleteAllById(ids);
    }

    @Override
    public Long update(DisMigrationBaseInfoVO disMigrationBaseInfoVO) {
        DisMigrationBaseInfo baseInfo = disMigrationBaseInfoRepository.findById(disMigrationBaseInfoVO.getId()).orElse(null);
        if (Objects.isNull(baseInfo)) {
            throw new BizException("当前要修改的基础信息id为：" + disMigrationBaseInfoVO.getId()+ " 的数据不存在！！！");
        }
        DisBaseInfoMapper.INSTANCE.of(disMigrationBaseInfoVO, baseInfo);
        disMigrationBaseInfoRepository.saveAndFlush(baseInfo);
        return baseInfo.getTaskCode();
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

    @Override
    public List<DisMigrationBaseInfoVO> list(List<Long> ids) {
       return DisBaseInfoMapper.INSTANCE.listVO(disMigrationBaseInfoRepository.findAllById(ids));
    }

}
