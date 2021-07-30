package com.qk.dm.metadata.service.impl;


import com.qk.dam.commons.exception.BizException;
import com.qk.dm.metadata.entity.MtdLabels;
import com.qk.dm.metadata.entity.QMtdLabels;
import com.qk.dm.metadata.mapstruct.mapper.MtdLabelsMapper;
import com.qk.dm.metadata.repositories.MtdLabelsRepository;
import com.qk.dm.metadata.service.MtdLabelsService;
import com.qk.dm.metadata.vo.MtdLabelsListVO;
import com.qk.dm.metadata.vo.MtdLabelsVO;
import com.qk.dm.metadata.vo.PageResultVO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MtdLabelsServiceImpl implements MtdLabelsService {

    private JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;
    private final QMtdLabels qMtdLabels = QMtdLabels.mtdLabels;
    private final MtdLabelsRepository mtdLabelsRepository;

    public MtdLabelsServiceImpl(EntityManager entityManager, MtdLabelsRepository mtdLabelsRepository) {
        this.entityManager = entityManager;
        this.mtdLabelsRepository = mtdLabelsRepository;
    }

    @PostConstruct
    public void initFactory() {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public void insert(MtdLabelsVO mtdLabelsVO) {
        MtdLabels mtdLabels = MtdLabelsMapper.INSTANCE.useMtdLabels(mtdLabelsVO);
        Predicate predicate = qMtdLabels.name.eq(mtdLabels.getName());
        boolean exists = mtdLabelsRepository.exists(predicate);
        if (exists) {
            throw new BizException(
                    "当前要新增的标签名为："
                            + mtdLabels.getName()
                            + " 的数据，已存在！！！");
        }
        mtdLabelsRepository.save(mtdLabels);
    }

    @Override
    public void update(MtdLabelsVO mtdLabelsVO) {
        MtdLabels mtdLabels = MtdLabelsMapper.INSTANCE.useMtdLabels(mtdLabelsVO);
        Predicate predicate = qMtdLabels.name.eq(mtdLabels.getName());
        boolean exists = mtdLabelsRepository.exists(predicate);
        if (exists) {
            throw new BizException(
                    "当前要修改的标签名为："
                            + mtdLabels.getName()
                            + " 的数据，已存在！！！");
        }
        mtdLabelsRepository.saveAndFlush(mtdLabels);
    }

    @Override
    @Transactional
    public void delete(String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        Set<Integer> idSet = new HashSet<>();
        idList.forEach(id -> idSet.add(Integer.parseInt(id)));
        List<MtdLabels> basicInfoList = mtdLabelsRepository.findAllById(idSet);
        mtdLabelsRepository.deleteInBatch(basicInfoList);
    }

    @Override
    public PageResultVO<MtdLabelsVO> listByPage(MtdLabelsListVO mtdLabelsListVO) {
        Map<String, Object> map;
        try {
            map = queryMtdLabelsByParams(mtdLabelsListVO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("查询失败!!!");
        }
        List<MtdLabels> list = (List<MtdLabels>) map.get("list");
        long total = (long) map.get("total");

        List<MtdLabelsVO> mtdLabelsVOList =
                list.stream().map(mtd -> MtdLabelsMapper.INSTANCE.useMtdLabelsVO(mtd)).collect(Collectors.toList());

        return new PageResultVO<>(
                total,
                mtdLabelsListVO.getPagination().getPage(),
                mtdLabelsListVO.getPagination().getSize(),
                mtdLabelsVOList);

    }

    @Override
    public List<MtdLabelsVO> listByAll(MtdLabelsListVO mtdLabelsListVO) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        checkCondition(booleanBuilder, mtdLabelsListVO);
        List<MtdLabels> mtdLabelsList = jpaQueryFactory
                .select(qMtdLabels)
                .from(qMtdLabels)
                .where(booleanBuilder)
                .orderBy(qMtdLabels.id.asc())
                .fetch();
        return mtdLabelsList.stream().map(mtd -> MtdLabelsMapper.INSTANCE.useMtdLabelsVO(mtd))
                .collect(Collectors.toList());
    }

    private Map<String, Object> queryMtdLabelsByParams(MtdLabelsListVO mtdLabelsListVO) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        checkCondition(booleanBuilder, mtdLabelsListVO);
        Map<String, Object> result = new HashMap<>();
        long count = jpaQueryFactory
                .select(qMtdLabels.count())
                .from(qMtdLabels)
                .where(booleanBuilder)
                .fetchOne();
        List<MtdLabels> mtdLabelsList = jpaQueryFactory
                .select(qMtdLabels)
                .from(qMtdLabels)
                .where(booleanBuilder)
                .orderBy(qMtdLabels.id.asc())
                .offset((mtdLabelsListVO.getPagination().getPage() - 1) * mtdLabelsListVO.getPagination().getSize())
                .limit(mtdLabelsListVO.getPagination().getSize())
                .fetch();
        result.put("list", mtdLabelsList);
        result.put("total", count);
        return result;
    }


    public void checkCondition(BooleanBuilder booleanBuilder, MtdLabelsListVO mtdLabelsListVO) {
        if (!StringUtils.isEmpty(mtdLabelsListVO.getName())) {
            booleanBuilder.and(qMtdLabels.name.contains(mtdLabelsListVO.getName()));
        }
        if (!StringUtils.isEmpty(mtdLabelsListVO.getBeginDay()) && !StringUtils.isEmpty(mtdLabelsListVO.getEndDay())) {
            StringTemplate dateExpr = Expressions.stringTemplate("DATE_FORMAT({0},'%Y-%m-%d %H:%i:%S')", qMtdLabels.gmtCreate);
            booleanBuilder.and(dateExpr.between(mtdLabelsListVO.getBeginDay(), mtdLabelsListVO.getEndDay()));
        }
    }
}
