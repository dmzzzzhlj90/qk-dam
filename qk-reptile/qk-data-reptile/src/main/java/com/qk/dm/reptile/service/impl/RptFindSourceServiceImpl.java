package com.qk.dm.reptile.service.impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.google.common.collect.Maps;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.reptile.client.ClientUserInfo;
import com.qk.dm.reptile.entity.QRptFindSource;
import com.qk.dm.reptile.entity.RptFindSource;
import com.qk.dm.reptile.factory.ReptileServerFactory;
import com.qk.dm.reptile.mapstruct.mapper.RptFindSourceMapper;
import com.qk.dm.reptile.params.dto.RptFindSourceDTO;
import com.qk.dm.reptile.params.vo.RptFindSourceVO;
import com.qk.dm.reptile.repositories.RptFindSourceRepository;
import com.qk.dm.reptile.service.RptFindSourceService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 站点找源
 * @author wangzp
 * @date 2021/5/09 15:05
 * @since 1.0.0
 */
@Slf4j
@Service
public class RptFindSourceServiceImpl implements RptFindSourceService {
    private JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;
    private final QRptFindSource qRptFindSource = QRptFindSource.rptFindSource;
    private final RptFindSourceRepository rptFindSourceRepository;

    public RptFindSourceServiceImpl(EntityManager entityManager, RptFindSourceRepository rptFindSourceRepository) {
        this.entityManager = entityManager;
        this.rptFindSourceRepository = rptFindSourceRepository;
    }
    @PostConstruct
    public void initFactory() {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public void requestCrawler(RptFindSourceDTO rptFindSourceDTO) {
        /**
         * 调用爬虫接口
         */
        ReptileServerFactory.grabData(rptFindSourceDTO);
    }

    @Override
    public void insert(RptFindSourceDTO rptFindSourceDTO) {
        RptFindSource rptFindSource = RptFindSourceMapper.INSTANCE.of(rptFindSourceDTO);
        //判断数据是否存在
        Boolean result = ReptileServerFactory.dataCheck(rptFindSourceDTO.getTitle(),
                rptFindSourceDTO.getPublishTime());
        rptFindSource.setStatus(result?1:2);
        rptFindSourceRepository.save(rptFindSource);
    }

    @Override
    public void dataContrast(Integer status) {
        List<RptFindSource> list = rptFindSourceRepository.findAllByStatus(status);
        Optional.ofNullable(list).orElse(List.of()).forEach(e->{
            Boolean result = ReptileServerFactory.dataCheck(e.getTitle(),
                    e.getPublishTime());
            if(result){
                e.setStatus(1);
                rptFindSourceRepository.saveAndFlush(e);
            }else {
                if(e.getStatus()==0){
                    e.setStatus(2);
                    rptFindSourceRepository.saveAndFlush(e);
                }
            }
        });
    }


    @Override
    public void update(Long id, RptFindSourceDTO rptFindSourceDTO) {
        RptFindSource rptFindSource = rptFindSourceRepository.findById(id).orElse(null);
        if (Objects.isNull(rptFindSource)) {
            throw new BizException("当前要修改的找源信息id为：" + id + " 的数据不存在！！！");
        }
        RptFindSourceMapper.INSTANCE.of(rptFindSourceDTO,rptFindSource);
        rptFindSourceRepository.saveAndFlush(rptFindSource);
    }

    @Override
    public void delete(String ids) {
        Iterable<Long> idSet = Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toList());
        List<RptFindSource> rptFindSourceList = rptFindSourceRepository.findAllById(idSet);
        if(rptFindSourceList.isEmpty()){
            throw new BizException("当前要删除的找源信息id为：" + ids + " 的数据不存在！！！");
        }
        rptFindSourceRepository.deleteAll(rptFindSourceList);
    }

    @Override
    public PageResultVO<RptFindSourceVO> list(RptFindSourceDTO rptFindSourceDTO) {
        Map<String, Object> map;
        try {
            map = queryByParams(rptFindSourceDTO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("查询失败!!!");
        }
        List<RptFindSource> list = (List<RptFindSource>) map.get("list");
        List<RptFindSourceVO> voList = RptFindSourceMapper.INSTANCE.of(list);
        return new PageResultVO<>(
                (long) map.get("total"),
                rptFindSourceDTO.getPagination().getPage(),
                rptFindSourceDTO.getPagination().getSize(),
                voList);
    }

    private Map<String, Object> queryByParams(RptFindSourceDTO rptFindSourceDTO) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        checkCondition(booleanBuilder, rptFindSourceDTO);
        Map<String, Object> result = Maps.newHashMap();
        if(StringUtils.isBlank(ClientUserInfo.getUserName())){
            result.put("list", Collections.emptyList());
            result.put("total", (long)0);
            return result;
        }
        long count = jpaQueryFactory.select(qRptFindSource.count()).from(qRptFindSource).where(booleanBuilder).fetchOne();
        List<RptFindSource> rptBaseInfoList = jpaQueryFactory
                .select(qRptFindSource)
                .from(qRptFindSource)
                .where(booleanBuilder)
                .orderBy(qRptFindSource.id.desc())
                .offset((long) (rptFindSourceDTO.getPagination().getPage() - 1)
                        * rptFindSourceDTO.getPagination().getSize())
                .limit(rptFindSourceDTO.getPagination().getSize()).fetch();
        result.put("list", rptBaseInfoList);
        result.put("total", count);
        return result;
    }

    public void checkCondition(BooleanBuilder booleanBuilder, RptFindSourceDTO rptFindSourceDTO) {
        if (Objects.nonNull(rptFindSourceDTO.getStatus())) {
             booleanBuilder.and(qRptFindSource.status.eq(rptFindSourceDTO.getStatus()));
        }
        if(Objects.nonNull(rptFindSourceDTO.getTitle())){
            booleanBuilder.and(qRptFindSource.title.contains(rptFindSourceDTO.getTitle()));
        }
        if(Objects.nonNull(rptFindSourceDTO.getKeywords())){
            booleanBuilder.and(qRptFindSource.title.contains(rptFindSourceDTO.getKeywords()));
        }
        if(Objects.nonNull(rptFindSourceDTO.getInfoType())){
            booleanBuilder.and(qRptFindSource.infoType.contains(rptFindSourceDTO.getInfoType()));
        }
        if(Objects.nonNull(rptFindSourceDTO.getProvinceCode())){
            booleanBuilder.and(qRptFindSource.provinceCode.in(rptFindSourceDTO.getProvinceCode().split(",")));
        }
        if(Objects.nonNull(rptFindSourceDTO.getCityCode())){
            booleanBuilder.and(qRptFindSource.cityCode.in(rptFindSourceDTO.getCityCode().split(",")));
        }
    }
}
