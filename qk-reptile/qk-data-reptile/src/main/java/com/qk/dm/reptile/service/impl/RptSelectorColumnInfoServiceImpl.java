package com.qk.dm.reptile.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.reptile.entity.RptSelectorColumnInfo;
import com.qk.dm.reptile.mapstruct.mapper.RptSelectorColumnInfoMapper;
import com.qk.dm.reptile.params.dto.RptSelectorColumnInfoDTO;
import com.qk.dm.reptile.params.vo.RptSelectorColumnInfoVO;
import com.qk.dm.reptile.repositories.RptSelectorColumnInfoRepository;
import com.qk.dm.reptile.service.RptSelectorColumnInfoService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 选择器信息
 * @author wangzp
 * @date 2021/12/10 13:35
 * @since 1.0.0
 */
@Service
public class RptSelectorColumnInfoServiceImpl implements RptSelectorColumnInfoService {
    private final RptSelectorColumnInfoRepository rptSelectorColumnInfoRepository;

    public RptSelectorColumnInfoServiceImpl(RptSelectorColumnInfoRepository rptSelectorColumnInfoRepository){
        this.rptSelectorColumnInfoRepository = rptSelectorColumnInfoRepository;
    }
    @Override
    public void insert(RptSelectorColumnInfoDTO rptSelectorColumnInfoDTO) {
        RptSelectorColumnInfo rptSelectorColumnInfo = RptSelectorColumnInfoMapper.INSTANCE.useRptSelectorColumnInfo(rptSelectorColumnInfoDTO);
        rptSelectorColumnInfoRepository.save(rptSelectorColumnInfo);
    }

    @Override
    public void batchInset(List<RptSelectorColumnInfoDTO> rptSelectorColumnInfoDTOList) {
        if(!CollectionUtils.isEmpty(rptSelectorColumnInfoDTOList)){
            List<RptSelectorColumnInfo> list = RptSelectorColumnInfoMapper.INSTANCE.ofList(rptSelectorColumnInfoDTOList);
            rptSelectorColumnInfoRepository.saveAll(list);
        }

    }

    @Override
    public void update(Long id, RptSelectorColumnInfoDTO rptSelectorColumnInfoDTO) {
        RptSelectorColumnInfo rptSelectorColumnInfo = rptSelectorColumnInfoRepository.findById(id).orElse(null);
        if (Objects.isNull(rptSelectorColumnInfo)) {
            throw new BizException("当前要修改的选择器信息id为：" + id + " 的数据不存在！！！");
        }
        RptSelectorColumnInfoMapper.INSTANCE.of(rptSelectorColumnInfoDTO, rptSelectorColumnInfo);
        rptSelectorColumnInfoRepository.saveAndFlush(rptSelectorColumnInfo);
    }

    @Override
    public void batchUpdate(Long configId, List<RptSelectorColumnInfoDTO> rptSelectorColumnInfoDTOList) {
        rptSelectorColumnInfoRepository.deleteAllByConfigId(configId);
        if(!CollectionUtils.isEmpty(rptSelectorColumnInfoDTOList)){
            List<RptSelectorColumnInfo> list = RptSelectorColumnInfoMapper.INSTANCE.ofList(rptSelectorColumnInfoDTOList);
            rptSelectorColumnInfoRepository.saveAll(list.stream().peek(e->e.setConfigId(configId)).collect(Collectors.toList()));
        }
    }

    @Override
    public RptSelectorColumnInfoVO detail(Long id) {
        Optional<RptSelectorColumnInfo> rptSelectorColumnInfo = rptSelectorColumnInfoRepository.findById(id);
        if(rptSelectorColumnInfo.isEmpty()){
            throw new BizException("当前要查询的选择器信息id为：" + id + " 的数据不存在！！！");
        }
        return RptSelectorColumnInfoMapper.INSTANCE.useRptSelectorColumnInfoVO(rptSelectorColumnInfo.get());
    }

    @Override
    public void delete(String ids) {
        Iterable<Long> idSet = Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toList());
        List<RptSelectorColumnInfo> rptSelectorColumnInfoList = rptSelectorColumnInfoRepository.findAllById(idSet);
        if(rptSelectorColumnInfoList.isEmpty()){
            throw new BizException("当前要删除的选择器信息id为：" + ids + " 的数据不存在！！！");
        }
        rptSelectorColumnInfoRepository.deleteAllById(idSet);
    }

    @Override
    public void deleteByConfigId(Long configId) {
        List<RptSelectorColumnInfo> configList = rptSelectorColumnInfoRepository.findAllByConfigId(configId);
        if(!CollectionUtils.isEmpty(configList)){
            rptSelectorColumnInfoRepository.deleteAllByConfigId(configId);
        }

    }

    @Override
    public void deleteByConfigId(List<Long> configIdList) {
        rptSelectorColumnInfoRepository.deleteAllByConfigIdIn(configIdList);
    }

    @Override
    public List<RptSelectorColumnInfoVO> list(Long configId) {
        List<RptSelectorColumnInfo> list = rptSelectorColumnInfoRepository.findAllByConfigId(configId);
        if(CollectionUtils.isEmpty(list)){return Collections.emptyList(); }
        return RptSelectorColumnInfoMapper.INSTANCE.of(list);
    }

    @Override
    public void copyConfig(Long sourceId, Long targetId) {
        List<RptSelectorColumnInfo> list = rptSelectorColumnInfoRepository.findAllByConfigId(sourceId);
        List<RptSelectorColumnInfo> rptSelectorColumnList = list.stream().map(e->{
            RptSelectorColumnInfo rptSelectorColumnInfo = new RptSelectorColumnInfo();
            RptSelectorColumnInfoMapper.INSTANCE.of(e, rptSelectorColumnInfo);
            rptSelectorColumnInfo.setConfigId(targetId);
            return rptSelectorColumnInfo;
        }).collect(Collectors.toList());
        rptSelectorColumnInfoRepository.saveAll(rptSelectorColumnList);
    }

    @Override
    public List<String> findByConfigIds(List<Long> configIds) {
        List<RptSelectorColumnInfo> rptSelectorColumnInfoList = rptSelectorColumnInfoRepository.findAllByConfigIdIn(configIds);
        if(CollectionUtils.isEmpty(rptSelectorColumnInfoList)){ return Collections.emptyList(); }
        return rptSelectorColumnInfoList.stream().map(RptSelectorColumnInfo::getColumnCode).collect(Collectors.toList());
    }
}
