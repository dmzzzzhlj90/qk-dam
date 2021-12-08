package com.qk.dm.reptile.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.reptile.entity.QRptBaseInfo;
import com.qk.dm.reptile.entity.RptBaseInfo;
import com.qk.dm.reptile.mapstruct.mapper.RptBaseInfoMapper;
import com.qk.dm.reptile.params.dto.RptBaseInfoDTO;
import com.qk.dm.reptile.params.vo.RptBaseInfoVO;
import com.qk.dm.reptile.repositories.RptBaseInfoRepository;
import com.qk.dm.reptile.service.RptBaseInfoService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

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

    @PostConstruct
    public void initFactory() {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    public RptBaseInfoServiceImpl(RptBaseInfoRepository rptBaseInfoRepository,
                                  EntityManager entityManager){
        this.rptBaseInfoRepository = rptBaseInfoRepository;
        this.entityManager = entityManager;
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
        rptBaseInfoRepository.deleteAllById(idSet);
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
                        .orderBy(qRptBaseInfo.id.asc())
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
        if (!StringUtils.isEmpty(rptBaseInfoDTO.getCnName())) {
            booleanBuilder.and(qRptBaseInfo.cnName.contains(rptBaseInfoDTO.getCnName()));
        }
        if (!StringUtils.isEmpty(rptBaseInfoDTO.getConfigName())) {
            booleanBuilder.and(qRptBaseInfo.configName.contains(rptBaseInfoDTO.getConfigName()));
        }
        if(Objects.nonNull(rptBaseInfoDTO.getStatus())){
            booleanBuilder.and(qRptBaseInfo.status.eq(rptBaseInfoDTO.getStatus()));
        }
    }
}
