package com.qk.dm.reptile.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dm.reptile.constant.RptConstant;
import com.qk.dm.reptile.entity.QRptConfigInfo;
import com.qk.dm.reptile.entity.RptBaseInfo;
import com.qk.dm.reptile.entity.RptConfigInfo;
import com.qk.dm.reptile.mapstruct.mapper.RptConfigInfoMapper;
import com.qk.dm.reptile.params.dto.RptConfigInfoDTO;
import com.qk.dm.reptile.params.dto.RptSelectorColumnInfoDTO;
import com.qk.dm.reptile.params.vo.RptConfigInfoVO;
import com.qk.dm.reptile.repositories.RptBaseInfoRepository;
import com.qk.dm.reptile.repositories.RptConfigInfoRepository;
import com.qk.dm.reptile.service.RptBaseInfoService;
import com.qk.dm.reptile.service.RptConfigInfoService;
import com.qk.dm.reptile.service.RptSelectorColumnInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
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

    private final RptBaseInfoRepository rptBaseInfoRepository;

    private final RptSelectorColumnInfoService rptSelectorColumnInfoService;

    private final QRptConfigInfo qRptConfigInfo = QRptConfigInfo.rptConfigInfo;

    public RptConfigInfoServiceImpl(RptConfigInfoRepository rptConfigInfoRepository,
                                    RptBaseInfoRepository rptBaseInfoRepository,
                                    RptSelectorColumnInfoService rptSelectorColumnInfoService){
        this.rptConfigInfoRepository = rptConfigInfoRepository;
        this.rptBaseInfoRepository = rptBaseInfoRepository;
        this.rptSelectorColumnInfoService = rptSelectorColumnInfoService;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long insert(RptConfigInfoDTO rptConfigInfoDTO) {
        RptConfigInfo rptConfigInfo = RptConfigInfoMapper.INSTANCE.useRptConfigInfo(rptConfigInfoDTO);
        transRptConfigInfo(rptConfigInfo,rptConfigInfoDTO);
        List<RptSelectorColumnInfoDTO> selectorList = rptConfigInfoDTO.getSelectorList();
        RptConfigInfo config = rptConfigInfoRepository.save(rptConfigInfo);
        //添加选择器
        if(!CollectionUtils.isEmpty(selectorList)){
            selectorList.forEach(e->e.setConfigId(config.getId()));
            rptSelectorColumnInfoService.batchInset(selectorList);
        }
        //修改基础信息表状态为爬虫
        updateBaseInfoStatus(rptConfigInfoDTO.getBaseInfoId());
        return config.getId();
    }
    private void updateBaseInfoStatus(Long id) {
        RptBaseInfo rptBaseInfo = rptBaseInfoRepository.findById(id).orElse(null);
        if(Objects.isNull(rptBaseInfo)){
            throw new BizException("当前要修改的基础信息id为：" + id + " 的数据不存在！！！");
        }
        rptBaseInfo.setStatus( RptConstant.REPTILE);
        rptBaseInfoRepository.saveAndFlush(rptBaseInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long id, RptConfigInfoDTO rptConfigInfoDTO) {
        RptConfigInfo rptConfigInfo = rptConfigInfoRepository.findById(id).orElse(null);
        if (Objects.isNull(rptConfigInfo)) {
            throw new BizException("当前要修改的配置信息id为：" + id + " 的数据不存在！！！");
        }
        RptConfigInfoMapper.INSTANCE.of(rptConfigInfoDTO, rptConfigInfo);
        transRptConfigInfo(rptConfigInfo, rptConfigInfoDTO);
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
        RptConfigInfoVO rptConfigInfoVO = RptConfigInfoMapper.INSTANCE.useRptConfigInfoVO(rptConfigInfo.get());
        return transRptConfigInfoVO(rptConfigInfo.get(),rptConfigInfoVO);
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
        List<RptConfigInfo> list = rptConfigInfoRepository.findAllByBaseInfoIdOrderByIdDesc(baseInfoId);
        if(!CollectionUtils.isEmpty(list)){
           return RptConfigInfoMapper.INSTANCE.of(list);
        }
        return null;
    }

    @Override
    public List<RptConfigInfoVO> rptList(Long baseId) {
        List<RptConfigInfo> list = rptConfigInfoRepository.findAllByBaseInfoIdOrderByIdAsc(baseId);
        List<RptConfigInfoVO> rptConfigInfoVOList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(list)){
            list.forEach(e->{
                RptConfigInfoVO rptConfigVO = RptConfigInfoMapper.INSTANCE.useRptConfigInfoVO(e);
                transRptConfigInfoVO(e,rptConfigVO);
                rptConfigInfoVOList.add(rptConfigVO);
            });
        }
        return rptConfigInfoVOList;
    }

    private RptConfigInfo transRptConfigInfo(RptConfigInfo rptConfigInfo,RptConfigInfoDTO rptConfigInfoDTO){
        if(Objects.nonNull(rptConfigInfoDTO.getCookies())){
            rptConfigInfo.setCookies(GsonUtil.toJsonString(rptConfigInfoDTO.getCookies()));
        }
        if(Objects.nonNull(rptConfigInfoDTO.getFormUrlencoded())){
            rptConfigInfo.setFormUrlencoded(GsonUtil.toJsonString(rptConfigInfoDTO.getFormUrlencoded()));
        }
        if(Objects.nonNull(rptConfigInfoDTO.getFromData())){
            rptConfigInfo.setFromData(GsonUtil.toJsonString(rptConfigInfoDTO.getFromData()));
        }
        if(Objects.nonNull(rptConfigInfoDTO.getHeaders())){
            rptConfigInfo.setHeaders(GsonUtil.toJsonString(rptConfigInfoDTO.getHeaders()));
        }
        if(Objects.nonNull(rptConfigInfoDTO.getRaw())){
            rptConfigInfo.setRaw(GsonUtil.toJsonString(rptConfigInfoDTO.getRaw()));
        }
        return rptConfigInfo;
    }

    private RptConfigInfoVO transRptConfigInfoVO(RptConfigInfo rptConfigInfo,RptConfigInfoVO rptConfigInfoVO){
        if(Objects.nonNull(rptConfigInfo.getCookies())){
            rptConfigInfoVO.setCookies(GsonUtil.fromJsonString(GsonUtil.toJsonString(rptConfigInfo.getCookies()), Map.class));
        }
        if(Objects.nonNull(rptConfigInfo.getFormUrlencoded())){
            rptConfigInfoVO.setFormUrlencoded(GsonUtil.fromJsonString(rptConfigInfo.getFormUrlencoded(),Map.class));
        }
        if(Objects.nonNull(rptConfigInfo.getFromData())){
            rptConfigInfoVO.setFromData(GsonUtil.fromJsonString(rptConfigInfo.getFromData(),Map.class));
        }
        if(Objects.nonNull(rptConfigInfo.getHeaders())){
            rptConfigInfoVO.setHeaders(GsonUtil.fromJsonString(rptConfigInfo.getHeaders(),Map.class));
        }
        if(Objects.nonNull(rptConfigInfo.getRaw())){
            rptConfigInfoVO.setRaw(GsonUtil.fromJsonString(rptConfigInfo.getRaw(),Map.class));
        }
        return rptConfigInfoVO;
    }


}
