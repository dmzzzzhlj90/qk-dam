package com.qk.dm.metadata.service.impl;


import com.qk.dam.commons.exception.BizException;
import com.qk.dm.metadata.entity.MtdLabels;
import com.qk.dm.metadata.entity.QMtdLabels;
import com.qk.dm.metadata.mapstruct.mapper.MtdLabelsMapper;
import com.qk.dm.metadata.repositories.MtdLabelsRepository;
import com.qk.dm.metadata.service.MtdLabelsService;
import com.qk.dm.metadata.vo.MtdLabelsInfoVO;
import com.qk.dm.metadata.vo.MtdLabelsListVO;
import com.qk.dm.metadata.vo.MtdLabelsVO;
import com.qk.dm.metadata.vo.PageResultVO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        Predicate predicate = qMtdLabels.name.eq(mtdLabelsVO.getName());
        if (mtdLabelsRepository.exists(predicate)) {
            throw new BizException(
                    "当前要新增的标签名为："
                            + mtdLabelsVO.getName()
                            + " 的数据，已存在！！！");
        }
        MtdLabels mtdLabels = MtdLabelsMapper.INSTANCE.useMtdLabels(mtdLabelsVO);
        mtdLabelsRepository.save(mtdLabels);
    }

    @Override
    public void update(Long id, MtdLabelsVO mtdLabelsVO) {
        Predicate predicate = qMtdLabels.name.eq(mtdLabelsVO.getName()).and(qMtdLabels.id.ne(id));
        if (mtdLabelsRepository.exists(predicate)) {
            throw new BizException(
                    "当前要修改的标签名为："
                            + mtdLabelsVO.getName()
                            + " 的数据，已存在！！！");
        }
        MtdLabels mtdLabels = MtdLabelsMapper.INSTANCE.useMtdLabels(mtdLabelsVO);
        mtdLabels.setId(id);
        mtdLabelsRepository.saveAndFlush(mtdLabels);
    }

    @Override
    public void delete(String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        Iterable<Long> idSet = idList.stream().map(Long::valueOf).collect(Collectors.toList());
        List<MtdLabels> basicInfoList = mtdLabelsRepository.findAllById(idSet);
        //todo 查询是否存在绑定关系
        mtdLabelsRepository.deleteInBatch(basicInfoList);
    }

    @Override
    public PageResultVO<MtdLabelsInfoVO> listByPage(MtdLabelsListVO mtdLabelsListVO) {
        Map<String, Object> map;
        try {
            map = queryMtdLabelsByParams(mtdLabelsListVO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("查询失败!!!");
        }
        List<MtdLabels> list = (List<MtdLabels>) map.get("list");
        List<MtdLabelsInfoVO> mtdLabelsVOList =
                list.stream()
                        .map(MtdLabelsMapper.INSTANCE::useMtdLabelsInfoVO)
                        .collect(Collectors.toList());
        return new PageResultVO<>(
                (long) map.get("total"),
                mtdLabelsListVO.getPagination().getPage(),
                mtdLabelsListVO.getPagination().getSize(),
                mtdLabelsVOList);
    }

    @Override
    public List<MtdLabelsInfoVO> listByAll(MtdLabelsVO mtdLabelsVO) {
        Predicate predicate = qMtdLabels.name.contains(mtdLabelsVO.getName());
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        List<MtdLabels> mtdLabelsList = (List<MtdLabels>) mtdLabelsRepository.findAll(predicate, sort);
        return mtdLabelsList.stream()
                .map(MtdLabelsMapper.INSTANCE::useMtdLabelsInfoVO)
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
                .offset((long) (mtdLabelsListVO.getPagination().getPage() - 1) *
                        mtdLabelsListVO.getPagination().getSize())
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
