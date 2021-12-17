package com.qk.dm.reptile.service.impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.reptile.constant.RptConstant;
import com.qk.dm.reptile.constant.RptRunStatusConstant;
import com.qk.dm.reptile.entity.QRptBaseInfo;
import com.qk.dm.reptile.entity.RptBaseInfo;
import com.qk.dm.reptile.mapstruct.mapper.RptBaseInfoMapper;
import com.qk.dm.reptile.params.builder.RptParaBuilder;
import com.qk.dm.reptile.params.dto.RptBaseInfoDTO;
import com.qk.dm.reptile.params.vo.RptBaseInfoVO;
import com.qk.dm.reptile.repositories.RptBaseInfoRepository;
import com.qk.dm.reptile.service.RptBaseInfoService;
import com.qk.dm.reptile.service.RptConfigInfoService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 爬虫数据采集基础信息
 * @author wangzp
 * @date 2021/12/8 14:32
 * @since 1.0.0
 */
@Service
public class RptBaseInfoServiceImpl implements RptBaseInfoService {

    private JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;
    private final QRptBaseInfo qRptBaseInfo = QRptBaseInfo.rptBaseInfo;
    private final RptBaseInfoRepository rptBaseInfoRepository;
    private final RptConfigInfoService rptConfigInfoService;

    @PostConstruct
    public void initFactory() {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    public RptBaseInfoServiceImpl(RptBaseInfoRepository rptBaseInfoRepository,
                                  EntityManager entityManager,
                                  RptConfigInfoService rptConfigInfoService){
        this.rptBaseInfoRepository = rptBaseInfoRepository;
        this.entityManager = entityManager;
        this.rptConfigInfoService = rptConfigInfoService;
    }

    @Override
    public void insert(RptBaseInfoDTO rptBaseInfoDTO) {
        RptBaseInfo rptBaseInfo = RptBaseInfoMapper.INSTANCE.userRtpBaseInfo(rptBaseInfoDTO);
        rptBaseInfoRepository.save(rptBaseInfo);
    }

    @Override
    public void update(Long id, RptBaseInfoDTO rptBaseInfoDTO) {
        RptBaseInfo rptBaseInfo = rptBaseInfoRepository.findById(id).orElse(null);
        if (Objects.isNull(rptBaseInfo)) {
            throw new BizException("当前要修改的基础信息id为：" + id + " 的数据不存在！！！");
        }
        RptBaseInfoMapper.INSTANCE.of(rptBaseInfoDTO, rptBaseInfo);
        rptBaseInfoRepository.saveAndFlush(rptBaseInfo);

    }

    @Override
    public void updateRunStatus(Long id, Integer runStatus) {
        RptBaseInfo rptBaseInfo = rptBaseInfoRepository.findById(id).orElse(null);
        if (Objects.isNull(rptBaseInfo)) {
            throw new BizException("当前要修改的基础信息id为：" + id + " 的数据不存在！！！");
        }
        rptBaseInfo.setRunStatus(runStatus);
        rptBaseInfoRepository.saveAndFlush(rptBaseInfo);
    }

    @Override
    public RptBaseInfoVO detail(Long id) {
        Optional<RptBaseInfo> rptBaseInfo = rptBaseInfoRepository.findById(id);
        if(rptBaseInfo.isEmpty()){
            throw new BizException("当前要查询的基础信息id为：" + id + " 的数据不存在！！！");
        }
        return RptBaseInfoMapper.INSTANCE.userRtpBaseInfoVO(rptBaseInfo.get());
    }

    @Override
    public void delete(String ids) {
        Iterable<Long> idSet = Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toList());
        List<RptBaseInfo> rptBaseInfoList = rptBaseInfoRepository.findAllById(idSet);
        if(rptBaseInfoList.isEmpty()){
            throw new BizException("当前要删除的基础信息id为：" + ids + " 的数据不存在！！！");
        }
        rptBaseInfoList.forEach(e->e.setStatus(RptConstant.HISTORY));
        rptBaseInfoRepository.saveAllAndFlush(rptBaseInfoList);

    }

    @Override
    public PageResultVO<RptBaseInfoVO> listByPage(RptBaseInfoDTO rptBaseInfoDTO) {
        Map<String, Object> map;
        try {
            map = queryByParams(rptBaseInfoDTO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("查询失败!!!");
        }
        List<RptBaseInfo> list = (List<RptBaseInfo>) map.get("list");
        List<RptBaseInfoVO> voList = RptBaseInfoMapper.INSTANCE.of(list);
        return new PageResultVO<>(
                (long) map.get("total"),
                rptBaseInfoDTO.getPagination().getPage(),
                rptBaseInfoDTO.getPagination().getSize(),
                voList);
    }

    @Override
    public void updateStatus(Long id,Integer status) {
        RptBaseInfo rptBaseInfo = rptBaseInfoRepository.findById(id).orElse(null);
        if(Objects.isNull(rptBaseInfo)){
            throw new BizException("当前要修改的基础信息id为：" + id + " 的数据不存在！！！");
        }
        rptBaseInfo.setStatus(status);
        rptBaseInfoRepository.saveAndFlush(rptBaseInfo);
    }

    @Override
    public void timedExecution() {
    List<RptBaseInfo> list = rptBaseInfoRepository.findAllByRunStatus(RptRunStatusConstant.START);
        if(!CollectionUtils.isEmpty(list)){
            list.forEach(e->{
                RptParaBuilder.rptConfigInfoList(rptConfigInfoService.rptList(e.getId()));
            });
        }
    }

    @Override
    public void execution(Long id) {
        RptBaseInfo rptBaseInfo = rptBaseInfoRepository.findById(id).orElse(null);
        if(Objects.isNull(rptBaseInfo)){
            throw new BizException("当前要修改的基础信息id为：" + id + " 的数据不存在！！！");
        }
        RptParaBuilder.rptConfigInfoList(rptConfigInfoService.rptList(rptBaseInfo.getId()));
    }

    private Map<String, Object> queryByParams(RptBaseInfoDTO rptBaseInfoDTO) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        checkCondition(booleanBuilder, rptBaseInfoDTO);
        Map<String, Object> result = new HashMap<>();
        long count =
                jpaQueryFactory.select(qRptBaseInfo.count()).from(qRptBaseInfo).where(booleanBuilder).fetchOne();
        List<RptBaseInfo> mtdLabelsList =
                jpaQueryFactory
                        .select(qRptBaseInfo)
                        .from(qRptBaseInfo)
                        .where(booleanBuilder)
                        .orderBy(qRptBaseInfo.id.desc())
                        .offset(
                                (long) (rptBaseInfoDTO.getPagination().getPage() - 1)
                                        * rptBaseInfoDTO.getPagination().getSize())
                        .limit(rptBaseInfoDTO.getPagination().getSize())
                        .fetch();
        result.put("list", mtdLabelsList);
        result.put("total", count);
        return result;
    }

    public void checkCondition(BooleanBuilder booleanBuilder, RptBaseInfoDTO rptBaseInfoDTO) {
        if (!StringUtils.isEmpty(rptBaseInfoDTO.getWebsiteName())) {
            booleanBuilder.and(qRptBaseInfo.websiteName.contains(rptBaseInfoDTO.getWebsiteName()));
        }
        if (!StringUtils.isEmpty(rptBaseInfoDTO.getConfigName())) {
            booleanBuilder.and(qRptBaseInfo.configName.contains(rptBaseInfoDTO.getConfigName()));
        }
        if (!StringUtils.isEmpty(rptBaseInfoDTO.getWebsiteUrl())) {
           booleanBuilder.and(qRptBaseInfo.websiteUrl.contains(rptBaseInfoDTO.getWebsiteUrl()));
        }
        if (!StringUtils.isEmpty(rptBaseInfoDTO.getCreateUsername())) {
            booleanBuilder.and(qRptBaseInfo.createUsername.contains(rptBaseInfoDTO.getCreateUsername()));
        }
        if(Objects.nonNull(rptBaseInfoDTO.getStatus())){
            booleanBuilder.and(qRptBaseInfo.status.eq(rptBaseInfoDTO.getStatus()));
        }
    }
}
