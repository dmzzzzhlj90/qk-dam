package com.qk.dm.reptile.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.reptile.constant.RptConstant;
import com.qk.dm.reptile.entity.RptConfigInfo;
import com.qk.dm.reptile.entity.RptSelectorColumnInfo;
import com.qk.dm.reptile.mapstruct.mapper.RptConfigInfoMapper;
import com.qk.dm.reptile.params.dto.RptConfigInfoDTO;
import com.qk.dm.reptile.params.dto.RptSelectorColumnInfoDTO;
import com.qk.dm.reptile.params.vo.RptConfigInfoVO;
import com.qk.dm.reptile.repositories.RptConfigInfoRepository;
import com.qk.dm.reptile.service.RptBaseInfoService;
import com.qk.dm.reptile.service.RptConfigInfoService;
import com.qk.dm.reptile.service.RptSelectorColumnInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 配置信息
 * @author wangzp
 * @date 2021/12/8 14:32
 * @since 1.0.0
 */
@Service
public class RptConfigInfoServiceImpl implements RptConfigInfoService {

    private final RptConfigInfoRepository rptConfigInfoRepository;

    private final RptBaseInfoService rptBaseInfoService;

    private final RptSelectorColumnInfoService rptSelectorColumnInfoService;

    public RptConfigInfoServiceImpl(RptConfigInfoRepository rptConfigInfoRepository,
                                    RptBaseInfoService rptBaseInfoService,
                                    RptSelectorColumnInfoService rptSelectorColumnInfoService){
        this.rptConfigInfoRepository = rptConfigInfoRepository;
        this.rptBaseInfoService = rptBaseInfoService;
        this.rptSelectorColumnInfoService = rptSelectorColumnInfoService;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long insert(RptConfigInfoDTO rptConfigInfoDTO) {
        RptConfigInfo rptConfigInfo = RptConfigInfoMapper.INSTANCE.useRptConfigInfo(rptConfigInfoDTO);
        List<RptSelectorColumnInfoDTO> selectorList = rptConfigInfoDTO.getSelectorList();
        RptConfigInfo config = rptConfigInfoRepository.save(rptConfigInfo);
        //添加选择器
        if(!CollectionUtils.isEmpty(selectorList)){
            selectorList.forEach(e->e.setConfigId(config.getId()));
            rptSelectorColumnInfoService.batchInset(selectorList);
        }
        //修改基础信息表状态为爬虫
        rptBaseInfoService.updateStatus(rptConfigInfoDTO.getBaseInfoId(), RptConstant.REPTILE);
        return config.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long id, RptConfigInfoDTO rptConfigInfoDTO) {
        RptConfigInfo rptConfigInfo = rptConfigInfoRepository.findById(id).orElse(null);
        if (Objects.isNull(rptConfigInfo)) {
            throw new BizException("当前要修改的配置信息id为：" + id + " 的数据不存在！！！");
        }
        RptConfigInfoMapper.INSTANCE.of(rptConfigInfoDTO, rptConfigInfo);
        List<RptSelectorColumnInfoDTO> selectorList = rptConfigInfoDTO.getSelectorList();
        rptConfigInfoRepository.saveAndFlush(rptConfigInfo);
        //修改选择器
        rptSelectorColumnInfoService.batchUpdate(id,selectorList);
    }

    @Override
    public RptConfigInfoVO detail(Long id) {
        Optional<RptConfigInfo> rptConfigInfo = rptConfigInfoRepository.findById(id);
        if(rptConfigInfo.isEmpty()){
            throw new BizException("当前要查询的配置信息id为：" + id + " 的数据不存在！！！");
        }
        return RptConfigInfoMapper.INSTANCE.useRptConfigInfoVO(rptConfigInfo.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String ids) {
        Iterable<Long> idSet = Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toList());
        List<RptConfigInfo> rptConfigInfoList = rptConfigInfoRepository.findAllById(idSet);
        if(rptConfigInfoList.isEmpty()){
            throw new BizException("当前要删除的配置信息id为：" + ids + " 的数据不存在！！！");
        }
        rptConfigInfoRepository.deleteAllById(idSet);
    }

    @Override
    public List<RptConfigInfoVO> list(Long baseInfoId) {
        List<RptConfigInfo> list = rptConfigInfoRepository.findAllByBaseInfoId(baseInfoId);
        if(!CollectionUtils.isEmpty(list)){
           return RptConfigInfoMapper.INSTANCE.of(list);
        }
        return null;
    }


}
