package com.qk.dm.reptile.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dm.reptile.client.ClientUserInfo;
import com.qk.dm.reptile.client.CrawlerCall;
import com.qk.dm.reptile.constant.RptConstant;
import com.qk.dm.reptile.constant.RptRunStatusConstant;
import com.qk.dm.reptile.entity.RptBaseInfo;
import com.qk.dm.reptile.entity.RptConfigInfo;
import com.qk.dm.reptile.factory.ReptileServerFactory;
import com.qk.dm.reptile.mapstruct.mapper.RptConfigInfoMapper;
import com.qk.dm.reptile.params.dto.RptConfigDetailDTO;
import com.qk.dm.reptile.params.dto.RptConfigInfoDTO;
import com.qk.dm.reptile.params.dto.RptSelectorColumnInfoDTO;
import com.qk.dm.reptile.params.vo.RptAddConfigVO;
import com.qk.dm.reptile.params.vo.RptConfigInfoVO;
import com.qk.dm.reptile.params.vo.RptSelectorColumnInfoVO;
import com.qk.dm.reptile.params.vo.RptSelectorVO;
import com.qk.dm.reptile.repositories.RptBaseInfoRepository;
import com.qk.dm.reptile.repositories.RptConfigInfoRepository;
import com.qk.dm.reptile.service.RptConfigInfoService;
import com.qk.dm.reptile.service.RptSelectorColumnInfoService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
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
    private final CrawlerCall crawlerCall;

    public RptConfigInfoServiceImpl(RptConfigInfoRepository rptConfigInfoRepository,
                                    RptBaseInfoRepository rptBaseInfoRepository,
                                    RptSelectorColumnInfoService rptSelectorColumnInfoService, CrawlerCall crawlerCall){
        this.rptConfigInfoRepository = rptConfigInfoRepository;
        this.rptBaseInfoRepository = rptBaseInfoRepository;
        this.rptSelectorColumnInfoService = rptSelectorColumnInfoService;
        this.crawlerCall = crawlerCall;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RptAddConfigVO end(RptConfigInfoDTO rptConfigInfoDTO) {
        //删除所有子级数据
        deleteChildrenConfig(rptConfigInfoDTO);
        RptConfigInfo config = addConfigAndSelector(rptConfigInfoDTO);
        //修改基础信息表状态为爬虫
        updateBaseInfoStatus(rptConfigInfoDTO.getBaseInfoId());
        return buildRptAddConfigVO(config,rptConfigInfoDTO.getBaseInfoId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RptAddConfigVO complete(RptConfigInfoDTO rptConfigInfoDTO) {
        //删除当前级数据
        deleteConfig(rptConfigInfoDTO);
        RptConfigInfo config = addConfigAndSelector(rptConfigInfoDTO);
        //修改基础信息表状态为爬虫
        updateBaseInfoStatus(rptConfigInfoDTO.getBaseInfoId());
        return buildRptAddConfigVO(config,rptConfigInfoDTO.getBaseInfoId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long endAndStart(RptConfigInfoDTO rptConfigInfoDTO) {
        //删除所有子级数据
        deleteChildrenConfig(rptConfigInfoDTO);
        RptConfigInfo config = addConfigAndSelector(rptConfigInfoDTO);
       // 修改基础信息表状态为爬虫,调用爬虫接口
        start(rptConfigInfoDTO.getBaseInfoId(),
        ReptileServerFactory.manual(crawlerCall.getManualUrl(),rptList(config.getBaseInfoId())));
        return config.getId();
    }

    /**
     * 配置是否已存在，存在则将之前的删除(添加，浏览器回退，重新保存会导致数据重复)
     * @param rptConfigInfoDTO
     */
    private void deleteConfig(RptConfigInfoDTO rptConfigInfoDTO){
        //id为空则为添加,如果当前层已经存在数据则删除
        if(Objects.isNull(rptConfigInfoDTO.getId())){
            deleteConfig(rptConfigInfoDTO.getBaseInfoId(), rptConfigInfoDTO.getParentId());
        }
    }

    /**
     * 删除当前层级数据
     * @param baseInfoId
     * @param parentId
     */
    private void deleteConfig(Long baseInfoId,Long parentId){
        List<RptConfigInfo> list = rptConfigInfoRepository.findAllByBaseInfoIdAndParentId(baseInfoId,parentId);
        if (!CollectionUtils.isEmpty(list)) {
            rptConfigInfoRepository.deleteAll(list);
            List<Long> configIdList = list.stream().map(RptConfigInfo::getId).collect(Collectors.toList());
            rptSelectorColumnInfoService.deleteByConfigId(configIdList);
        }
    }

    private void deleteChildrenConfig(RptConfigInfoDTO rptConfigInfoDTO){
        List<RptConfigInfo> list = rptConfigInfoRepository.findAllByBaseInfoIdAndParentId(rptConfigInfoDTO.getBaseInfoId(),rptConfigInfoDTO.getParentId());
        if(CollectionUtils.isEmpty(list)){return;}

        if(Objects.isNull(rptConfigInfoDTO.getId())){
            rptConfigInfoRepository.deleteAll(list);
            List<Long> configIdList = list.stream().map(RptConfigInfo::getId).collect(Collectors.toList());
            rptSelectorColumnInfoService.deleteByConfigId(configIdList);
        }
        list.forEach(e->{deleteChildrenConfig(rptConfigInfoDTO.getBaseInfoId(),e.getId());});
    }

    /**
     * 删除所有的子层级数据
     * @param baseInfoId
     * @param parentId
     */
    private void deleteChildrenConfig(Long baseInfoId,Long parentId){
        List<RptConfigInfo> list = rptConfigInfoRepository.findAllByBaseInfoIdAndParentId(baseInfoId,parentId);
        if (!CollectionUtils.isEmpty(list)) {
            rptConfigInfoRepository.deleteAll(list);
            List<Long> configIdList = list.stream().map(RptConfigInfo::getId).collect(Collectors.toList());
            rptSelectorColumnInfoService.deleteByConfigId(configIdList);
            list.forEach(e->{deleteChildrenConfig(baseInfoId,e.getId());});
        }
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
        RptConfigInfo config = rptConfigInfoRepository.saveAndFlush(rptConfigInfo);
        //如果为编辑先将之前数据删除
        if(Objects.nonNull(rptConfigInfoDTO.getId())){
            rptSelectorColumnInfoService.deleteByConfigId(rptConfigInfoDTO.getId());
        }
        rptSelectorColumnInfoService.batchInset(selectorList.stream().peek(e -> e.setConfigId(config.getId()))
                .collect(Collectors.toList()));
        return config;
    }

    private void updateBaseInfoStatus(Long id) {
        RptBaseInfo rptBaseInfo = rptBaseInfoRepository.findById(id).orElse(null);
        if(Objects.isNull(rptBaseInfo)){
            throw new BizException("当前要修改的基础信息id为：" + id + " 的数据不存在！！！");
        }
          rptBaseInfo.setStatus(RptConstant.REPTILE);
          rptBaseInfo.setConfigName(ClientUserInfo.getUserName());
          rptBaseInfo.setConfigDate(new Date());
          rptBaseInfoRepository.saveAndFlush(rptBaseInfo);
    }

    private void configStatus(Long id) {
        RptBaseInfo rptBaseInfo = rptBaseInfoRepository.findById(id).orElse(null);
        if(Objects.isNull(rptBaseInfo)){
            throw new BizException("当前要修改的基础信息id为：" + id + " 的数据不存在！！！");
        }
        rptBaseInfo.setConfigStatus(true);
        rptBaseInfoRepository.saveAndFlush(rptBaseInfo);

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
        rptBaseInfo.setConfigName(ClientUserInfo.getUserName());
        rptBaseInfo.setConfigDate(new Date());
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
        rptConfigInfoRepository.saveAndFlush(rptConfigInfo);
        //修改选择器
//        rptSelectorColumnInfoService.batchUpdate(id,rptConfigInfoDTO.getSelectorList());
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

    private RptConfigInfoVO getDetailByParentId(Long parentId){
        RptConfigInfo rptConfigInfo = rptConfigInfoRepository.findByParentId(parentId);
        if(Objects.isNull(rptConfigInfo)){
            throw new BizException("当前要查询的配置信息parentId为：" + parentId + " 的数据不存在！！！");
        }
        RptConfigInfoVO rptConfigInfoVO = RptConfigInfoMapper.INSTANCE.useRptConfigInfoVO(rptConfigInfo);
        return transRptConfigInfoVO(rptConfigInfo,rptConfigInfoVO);
    }

    @Override
    public RptConfigInfoVO getDetailByBaseInfo(RptConfigDetailDTO rptConfigDetailDTO) {
        if(Objects.nonNull(rptConfigDetailDTO.getFirstFloor())){
            return getDetailByParentId(rptConfigDetailDTO.getId());
        }else {
            List<RptConfigInfo> list = rptConfigInfoRepository.findAllByBaseInfoIdOrderByIdAsc(rptConfigDetailDTO.getId());
            if(CollectionUtils.isEmpty(list)){ return null; }

            RptConfigInfoVO rptConfigVO = RptConfigInfoMapper.INSTANCE.useRptConfigInfoVO(list.get(0));
            transRptConfigInfoVO(list.get(0), rptConfigVO);
            return rptConfigVO;
        }
    }

    @Override
    public RptSelectorVO getSelectorInfo(Long configId) {
        RptConfigInfo configIdInfo = rptConfigInfoRepository.findByParentId(configId);
        List<RptSelectorColumnInfoVO> configInfoList = rptSelectorColumnInfoService.list(configId);
        return RptSelectorVO.builder().configId(configId)
                .selectorList(configInfoList)
                .next(Objects.nonNull(configIdInfo))
                .columnCodeList(Collections.EMPTY_LIST)
                .build();
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
        if(CollectionUtils.isEmpty(list)){return List.of();}
        return RptConfigInfoMapper.INSTANCE.of(list);
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

    public List<String> getColumnList(Long baseId){
        List<RptConfigInfo> list = rptConfigInfoRepository.findAllByBaseInfoIdOrderByIdAsc(baseId);
        if(!CollectionUtils.isEmpty(list)){
           return rptSelectorColumnInfoService.findByConfigIds(list.stream().map(RptConfigInfo::getId).collect(Collectors.toList()));
        }
        return List.of();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void copyConfig(Long sourceId, Long targetId) {
        List<RptConfigInfo> list = rptConfigInfoRepository.findAllByBaseInfoIdOrderByIdAsc(sourceId);
        if(!CollectionUtils.isEmpty(list)) {
            //删除目标原有配置
            deleteTargetConfig(targetId);
            AtomicReference<Long> parentId = new AtomicReference<>(Long.parseLong("0"));
            list.forEach(e -> {
                RptConfigInfo info = new RptConfigInfo();
                RptConfigInfoMapper.INSTANCE.of(e, info);
                info.setBaseInfoId(targetId);
                info.setParentId(parentId.get());
                //配置信息第一层的请求地址不复制
                if(parentId.get()==0){info.setRequestUrl(Strings.EMPTY);}
                RptConfigInfo rptConfigInfo = rptConfigInfoRepository.save(info);
                parentId.set(rptConfigInfo.getId());
                rptSelectorColumnInfoService.copyConfig(e.getId(), rptConfigInfo.getId());
            });
        }
        //修改配置状态为已经添加有配置
        configStatus(targetId);
    }

    /**
     * 删除原有的配置和字段
     * @param targetId
     */
    private void deleteTargetConfig(Long targetId){
        List<RptConfigInfo> targetList = rptConfigInfoRepository.findAllByBaseInfoIdOrderByIdAsc(targetId);
        rptConfigInfoRepository.deleteByBaseInfoId(targetId);
        if(!CollectionUtils.isEmpty(targetList)){
            List<Long> targetIdList = targetList.stream().map(RptConfigInfo::getId).collect(Collectors.toList());
            rptSelectorColumnInfoService.deleteByConfigId(targetIdList);
        }
    }

    private void transRptConfigInfo(RptConfigInfo rptConfigInfo, RptConfigInfoDTO rptConfigInfoDTO){
        if(Objects.nonNull(rptConfigInfoDTO.getCookies())){
            rptConfigInfo.setCookies(GsonUtil.toJsonString(rptConfigInfoDTO.getCookies()));
        }
        if(Objects.nonNull(rptConfigInfoDTO.getFormUrlencoded())){
            rptConfigInfo.setFormUrlencoded(GsonUtil.toJsonString(rptConfigInfoDTO.getFormUrlencoded()));
        }
        if(Objects.nonNull(rptConfigInfoDTO.getFormData())){
            rptConfigInfo.setFormData(GsonUtil.toJsonString(rptConfigInfoDTO.getFormData()));
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
        if(Objects.nonNull(rptConfigInfo.getFormData())){
            rptConfigInfoVO.setFormData(GsonUtil.fromJsonString(rptConfigInfo.getFormData(),Map.class));
        }
        if(Objects.nonNull(rptConfigInfo.getHeaders())){
            rptConfigInfoVO.setHeaders(GsonUtil.fromJsonString(rptConfigInfo.getHeaders(),Map.class));
        }
        if(Objects.nonNull(rptConfigInfo.getRaw())){
            rptConfigInfoVO.setRaw(GsonUtil.fromJsonString(rptConfigInfo.getRaw(),Map.class));
        }
        return rptConfigInfoVO;
    }

    private RptAddConfigVO buildRptAddConfigVO(RptConfigInfo config,Long baseInfoId){
        return RptAddConfigVO.builder()
                .configId(config.getId())
                .dimensionId(config.getDimensionId())
                .columnCodeList(getColumnCodeList(baseInfoId)).build();
    }

    private List<String> getColumnCodeList(Long baseInfoId){
        List<RptConfigInfo> configInfoList = rptConfigInfoRepository.findAllByBaseInfoIdOrderByIdDesc(baseInfoId);
        if(CollectionUtils.isEmpty(configInfoList)){
            return List.of();
        }
        return rptSelectorColumnInfoService.findByConfigIds(configInfoList.stream().map(RptConfigInfo::getId)
                .collect(Collectors.toList()));
    }

}
