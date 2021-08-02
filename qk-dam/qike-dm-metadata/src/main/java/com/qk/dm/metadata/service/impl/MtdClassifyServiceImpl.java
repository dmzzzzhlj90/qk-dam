package com.qk.dm.metadata.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.metadata.entity.MtdClassify;
import com.qk.dm.metadata.entity.QMtdClassify;
import com.qk.dm.metadata.mapstruct.mapper.MtdClassifyMapper;
import com.qk.dm.metadata.repositories.MtdClassifyRepository;
import com.qk.dm.metadata.service.MtdClassifyService;
import com.qk.dm.metadata.vo.MtdClassifyVO;
import com.qk.dm.metadata.vo.PageResultVO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wangzp
 * @date 2021/7/31 13:10
 * @since 1.0.0
 */
@Service
public class MtdClassifyServiceImpl implements MtdClassifyService {
    private JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;
    private final QMtdClassify qMtdClassify = QMtdClassify.mtdClassify;
    private final MtdClassifyRepository mtdClassifyRepository;

    @Autowired
    public MtdClassifyServiceImpl(EntityManager entityManager,MtdClassifyRepository mtdClassifyRepository){
        this.entityManager = entityManager;
        this.mtdClassifyRepository = mtdClassifyRepository;
    }
    @PostConstruct
    public void initFactory() {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }
    @Override
    public void insert(MtdClassifyVO mtdClassifyVO) {
        MtdClassify mtdClassify = MtdClassifyMapper.INSTANCE.useMtdClassify(mtdClassifyVO);
        mtdClassify.setGmtCreate(new Date());
        mtdClassify.setGmtModified(new Date());
        Predicate predicate = qMtdClassify.name.eq(mtdClassify.getName());
        boolean exists = mtdClassifyRepository.exists(predicate);
        if (exists) {
            throw new BizException(
                    "当前要新增的分类为："
                            + mtdClassifyVO.getName()
                            + " 的数据，已存在！！！");
        }
        mtdClassifyRepository.save(mtdClassify);
    }

    @Override
    public void update(MtdClassifyVO mtdClassifyVO) {
        MtdClassify mtdClassify = MtdClassifyMapper.INSTANCE.useMtdClassify(mtdClassifyVO);
        Predicate predicate = qMtdClassify.name.eq(mtdClassify.getName());
        boolean exists = mtdClassifyRepository.exists(predicate);
        if (exists) {
            throw new BizException(
                    "当前要修改的分类为："
                            + mtdClassify.getName()
                            + " 的数据，已存在！！！");
        }
        mtdClassifyRepository.saveAndFlush(mtdClassify);
    }

    @Override
    public void delete(String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        Iterable<Long> idSet = idList.stream().map(i -> Long.valueOf(i)).collect(Collectors.toList());
        List<MtdClassify> mtdClassifyList = mtdClassifyRepository.findAllById(idSet);
        mtdClassifyRepository.deleteInBatch(mtdClassifyList);
    }

    @Override
    public PageResultVO<MtdClassifyVO> listByPage(MtdClassifyVO mtdClassifyVO) {
        Map<String, Object> map;
        try {
            map = queryMtdClassifyByParams(mtdClassifyVO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("查询失败!!!");
        }
        List<MtdClassify> list = (List<MtdClassify>) map.get("list");
        long total = (long) map.get("total");

        List<MtdClassifyVO> mtdLabelsVOList =
                list.stream().map(mtd -> MtdClassifyMapper.INSTANCE.useMtdClassifyVO(mtd)).collect(Collectors.toList());

        return new PageResultVO<>(
                total,
                mtdClassifyVO.getPagination().getPage(),
                mtdClassifyVO.getPagination().getSize(),
                mtdLabelsVOList);
    }

    @Override
    public List<MtdClassifyVO> listByAll(MtdClassifyVO mtdClassifyVO) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        checkCondition(booleanBuilder, mtdClassifyVO);
        List<MtdClassify> mtdLabelsList = jpaQueryFactory
                .select(qMtdClassify)
                .from(qMtdClassify)
                .where(booleanBuilder)
                .orderBy(qMtdClassify.id.asc())
                .fetch();
        return mtdLabelsList.stream().map(mtd -> MtdClassifyMapper.INSTANCE.useMtdClassifyVO(mtd))
                .collect(Collectors.toList());
    }


    private Map<String, Object> queryMtdClassifyByParams(MtdClassifyVO mtdClassifyVO) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        checkCondition(booleanBuilder, mtdClassifyVO);
        Map<String, Object> result = new HashMap<>();
        long count = jpaQueryFactory
                .select(qMtdClassify.count())
                .from(qMtdClassify)
                .where(booleanBuilder)
                .fetchOne();
        List<MtdClassify> mtdLabelsList = jpaQueryFactory
                .select(qMtdClassify)
                .from(qMtdClassify)
                .where(booleanBuilder)
                .orderBy(qMtdClassify.id.asc())
                .offset((mtdClassifyVO.getPagination().getPage() - 1) * mtdClassifyVO.getPagination().getSize())
                .limit(mtdClassifyVO.getPagination().getSize())
                .fetch();
        result.put("list", mtdLabelsList);
        result.put("total", count);
        return result;
    }


    public void checkCondition(BooleanBuilder booleanBuilder,MtdClassifyVO mtdClassifyVO) {
        if (!StringUtils.isEmpty(mtdClassifyVO.getName())) {
            booleanBuilder.and(qMtdClassify.name.contains(mtdClassifyVO.getName()));
        }

    }

}
