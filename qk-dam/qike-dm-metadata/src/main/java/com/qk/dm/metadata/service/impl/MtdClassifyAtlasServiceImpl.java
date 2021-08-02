package com.qk.dm.metadata.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.metadata.entity.MtdClassifyAtlas;
import com.qk.dm.metadata.entity.QMtdClassifyAtlas;
import com.qk.dm.metadata.mapstruct.mapper.MtdClassifyAtlasMapper;
import com.qk.dm.metadata.repositories.MtdClassifyAtlasRepository;
import com.qk.dm.metadata.service.MtdClassifyAtlasService;
import com.qk.dm.metadata.vo.MtdClassifyAtlasVO;
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
 * @date 2021/7/31 11:37
 * @since 1.0.0
 */
@Service
public class MtdClassifyAtlasServiceImpl implements MtdClassifyAtlasService {
    private JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;
    private final QMtdClassifyAtlas qMtdClassifyAtlas = QMtdClassifyAtlas.mtdClassifyAtlas;
    private final MtdClassifyAtlasRepository mtdClassifyAtlasRepository;

    @Autowired
    public MtdClassifyAtlasServiceImpl(EntityManager entityManager,MtdClassifyAtlasRepository mtdClassifyAtlasRepository){
        this.entityManager = entityManager;
        this.mtdClassifyAtlasRepository = mtdClassifyAtlasRepository;
    }

    @PostConstruct
    public void initFactory() {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public void insert(MtdClassifyAtlasVO mtdClassifyAtlasVO) {
        MtdClassifyAtlas mtdClassifyAtlas = MtdClassifyAtlasMapper.INSTANCE.useMtdClassifyAtlas(mtdClassifyAtlasVO);
        mtdClassifyAtlas.setGmtCreate(new Date());
        mtdClassifyAtlas.setGmtModified(new Date());
        Predicate predicate = qMtdClassifyAtlas.classify.eq(mtdClassifyAtlas.getClassify());
        boolean exists = mtdClassifyAtlasRepository.exists(predicate);
        if (exists) {
            throw new BizException(
                    "当前要新增的分类为："
                            + mtdClassifyAtlas.getClassify()
                            + " 的数据，已存在！！！");
        }
        mtdClassifyAtlasRepository.save(mtdClassifyAtlas);
    }

    @Override
    public void update(MtdClassifyAtlasVO mtdClassifyAtlasVO) {
        MtdClassifyAtlas mtdClassifyAtlas = MtdClassifyAtlasMapper.INSTANCE.useMtdClassifyAtlas(mtdClassifyAtlasVO);
        Predicate predicate = qMtdClassifyAtlas.classify.eq(mtdClassifyAtlas.getClassify());
        boolean exists = mtdClassifyAtlasRepository.exists(predicate);
        if (exists) {
            throw new BizException(
                    "当前要修改的分类为："
                            + mtdClassifyAtlas.getClassify()
                            + " 的数据，已存在！！！");
        }
        mtdClassifyAtlasRepository.saveAndFlush(mtdClassifyAtlas);
    }

    @Override
    public void delete(String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        Iterable<Long> idSet = idList.stream().map(i -> Long.valueOf(i)).collect(Collectors.toList());
        List<MtdClassifyAtlas> mtdClassifyAtlasList = mtdClassifyAtlasRepository.findAllById(idSet);
        mtdClassifyAtlasRepository.deleteInBatch(mtdClassifyAtlasList);
    }

    @Override
    public PageResultVO<MtdClassifyAtlasVO> listByPage(MtdClassifyAtlasVO mtdClassifyAtlasVO) {
        Map<String, Object> map;
        try {
            map = queryMtdClassifyAtlasByParams(mtdClassifyAtlasVO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("查询失败!!!");
        }
        List<MtdClassifyAtlas> list = (List<MtdClassifyAtlas>) map.get("list");
        long total = (long) map.get("total");

        List<MtdClassifyAtlasVO> mtdLabelsVOList =
                list.stream().map(mtd -> MtdClassifyAtlasMapper.INSTANCE.useMtdClassifyAtlasVO(mtd)).collect(Collectors.toList());

        return new PageResultVO<>(
                total,
                mtdClassifyAtlasVO.getPagination().getPage(),
                mtdClassifyAtlasVO.getPagination().getSize(),
                mtdLabelsVOList);
    }

    @Override
    public List<MtdClassifyAtlasVO> listByAll(MtdClassifyAtlasVO mtdClassifyAtlasVO) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        checkCondition(booleanBuilder, mtdClassifyAtlasVO);
        List<MtdClassifyAtlas> mtdLabelsList = jpaQueryFactory
                .select(qMtdClassifyAtlas)
                .from(qMtdClassifyAtlas)
                .where(booleanBuilder)
                .orderBy(qMtdClassifyAtlas.id.asc())
                .fetch();
        return mtdLabelsList.stream().map(mtd -> MtdClassifyAtlasMapper.INSTANCE.useMtdClassifyAtlasVO(mtd))
                .collect(Collectors.toList());
    }

    private Map<String, Object> queryMtdClassifyAtlasByParams(MtdClassifyAtlasVO mtdClassifyAtlasVO) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        checkCondition(booleanBuilder, mtdClassifyAtlasVO);
        Map<String, Object> result = new HashMap<>();
        long count = jpaQueryFactory
                .select(qMtdClassifyAtlas.count())
                .from(qMtdClassifyAtlas)
                .where(booleanBuilder)
                .fetchOne();
        List<MtdClassifyAtlas> mtdLabelsList = jpaQueryFactory
                .select(qMtdClassifyAtlas)
                .from(qMtdClassifyAtlas)
                .where(booleanBuilder)
                .orderBy(qMtdClassifyAtlas.id.asc())
                .offset((mtdClassifyAtlasVO.getPagination().getPage() - 1) * mtdClassifyAtlasVO.getPagination().getSize())
                .limit(mtdClassifyAtlasVO.getPagination().getSize())
                .fetch();
        result.put("list", mtdLabelsList);
        result.put("total", count);
        return result;
    }


    public void checkCondition(BooleanBuilder booleanBuilder, MtdClassifyAtlasVO mtdClassifyAtlasVO) {
        if (!StringUtils.isEmpty(mtdClassifyAtlasVO.getClassify())) {
            booleanBuilder.and(qMtdClassifyAtlas.classify.contains(mtdClassifyAtlasVO.getClassify()));
        }

    }
}
