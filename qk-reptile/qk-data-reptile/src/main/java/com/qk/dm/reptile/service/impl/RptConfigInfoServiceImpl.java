package com.qk.dm.reptile.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dm.reptile.constant.RptConstant;
import com.qk.dm.reptile.constant.RptRunStatusConstant;
import com.qk.dm.reptile.entity.RptBaseInfo;
import com.qk.dm.reptile.entity.RptConfigInfo;
import com.qk.dm.reptile.mapstruct.mapper.RptConfigInfoMapper;
import com.qk.dm.reptile.params.builder.RptParaBuilder;
import com.qk.dm.reptile.params.dto.RptConfigInfoDTO;
import com.qk.dm.reptile.params.dto.RptSelectorColumnInfoDTO;
import com.qk.dm.reptile.params.vo.RptAddConfigVO;
import com.qk.dm.reptile.params.vo.RptConfigInfoVO;
import com.qk.dm.reptile.params.vo.RptSelectorColumnInfoVO;
import com.qk.dm.reptile.repositories.RptBaseInfoRepository;
import com.qk.dm.reptile.repositories.RptConfigInfoRepository;
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

    public RptConfigInfoServiceImpl(RptConfigInfoRepository rptConfigInfoRepository,
                                    RptBaseInfoRepository rptBaseInfoRepository,
                                    RptSelectorColumnInfoService rptSelectorColumnInfoService){
        this.rptConfigInfoRepository = rptConfigInfoRepository;
        this.rptBaseInfoRepository = rptBaseInfoRepository;
        this.rptSelectorColumnInfoService = rptSelectorColumnInfoService;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RptAddConfigVO insert(RptConfigInfoDTO rptConfigInfoDTO) {
        RptConfigInfo config = addConfigAndSelector(rptConfigInfoDTO);
        //修改基础信息表状态为爬虫
        updateBaseInfoStatus(rptConfigInfoDTO.getBaseInfoId());
        return buildRptAddConfigVO(config);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long endAndStart(RptConfigInfoDTO rptConfigInfoDTO) {
        RptConfigInfo config = addConfigAndSelector(rptConfigInfoDTO);
        String result = RptParaBuilder.rptConfigInfoList(rptList(config.getBaseInfoId()));
        //修改基础信息表状态为爬虫
        start(rptConfigInfoDTO.getBaseInfoId(),result);
        return config.getId();
    }

    /**
     * 保存配置信息和选择器信息
     * @param rptConfigInfoDTO
     * @return
     */
    private RptConfigInfo addConfigAndSelector(RptConfigInfoDTO rptConfigInfoDTO){
        RptConfigInfo rptConfigInfo = RptConfigInfoMapper.INSTANCE.useRptConfigInfo(rptConfigInfoDTO);
        transRptConfigInfo(rptConfigInfo,rptConfigInfoDTO);
        List<RptSelectorColumnInfoDTO> selectorList = rptConfigInfoDTO.getSelectorList();
        RptConfigInfo config = rptConfigInfoRepository.save(rptConfigInfo);
        //添加选择器
        if(!CollectionUtils.isEmpty(selectorList)){
            selectorList.forEach(e->e.setConfigId(config.getId()));
            rptSelectorColumnInfoService.batchInset(selectorList);
        }
        return config;
    }

    private void updateBaseInfoStatus(Long id) {
        RptBaseInfo rptBaseInfo = rptBaseInfoRepository.findById(id).orElse(null);
        if(Objects.isNull(rptBaseInfo)){
            throw new BizException("当前要修改的基础信息id为：" + id + " 的数据不存在！！！");
        }
       if (!Objects.equals(rptBaseInfo.getStatus(), RptConstant.REPTILE)) {
          rptBaseInfo.setStatus(RptConstant.REPTILE);
          rptBaseInfoRepository.saveAndFlush(rptBaseInfo);
        }
    }

    private void start(Long id,String result) {
        RptBaseInfo rptBaseInfo = rptBaseInfoRepository.findById(id).orElse(null);
        if(Objects.isNull(rptBaseInfo)){
            throw new BizException("当前要修改的基础信息id为：" + id + " 的数据不存在！！！");
        }
        Map<String,String> map = GsonUtil.fromJsonString(result, Map.class);
        rptBaseInfo.setJobId(map.get(RptConstant.JOBID));
        rptBaseInfo.setStatus(RptConstant.REPTILE);
        rptBaseInfo.setRunStatus(RptRunStatusConstant.START);
        rptBaseInfo.setGmtFunction(new Date());
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
        return list.stream().map(e->{
            RptConfigInfoVO rptConfigVO = RptConfigInfoMapper.INSTANCE.useRptConfigInfoVO(e);
            transRptConfigInfoVO(e,rptConfigVO);
            rptConfigVO.setSelectorList(rptSelectorColumnInfoService.list(rptConfigVO.getId()));
            return rptConfigVO;
        }).collect(Collectors.toList());
    }

    private void transRptConfigInfo(RptConfigInfo rptConfigInfo, RptConfigInfoDTO rptConfigInfoDTO){
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
    }

    private RptConfigInfoVO transRptConfigInfoVO(RptConfigInfo rptConfigInfo,RptConfigInfoVO rptConfigInfoVO){
        if(Objects.nonNull(rptConfigInfo.getCookies())){
            rptConfigInfoVO.setCookies(GsonUtil.fromJsonString(rptConfigInfo.getCookies(), Map.class));
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

    private RptAddConfigVO buildRptAddConfigVO(RptConfigInfo config){
        return RptAddConfigVO.builder()
                .configId(config.getId())
                .dimensionId(config.getDimensionId())
                .columnCodeList(getColumnCodeList(config.getId())).build();
    }

    private List<String> getColumnCodeList(Long configId){
        List<RptSelectorColumnInfoVO> rptSelectorColumnInfoList = rptSelectorColumnInfoService.list(configId);
        if(CollectionUtils.isEmpty(rptSelectorColumnInfoList)){
            return null;
        }
        return rptSelectorColumnInfoList.stream().map(RptSelectorColumnInfoVO::getColumnCode).collect(Collectors.toList());
    }

}
