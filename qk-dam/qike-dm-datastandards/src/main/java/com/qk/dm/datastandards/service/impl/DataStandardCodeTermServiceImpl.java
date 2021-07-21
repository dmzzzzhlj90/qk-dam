package com.qk.dm.datastandards.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.datastandards.constant.DsdConstant;
import com.qk.dm.datastandards.entity.DsdCodeTerm;
import com.qk.dm.datastandards.entity.QDsdCodeTerm;
import com.qk.dm.datastandards.mapstruct.mapper.DsdCodeTermMapper;
import com.qk.dm.datastandards.repositories.DsdCodeTermRepository;
import com.qk.dm.datastandards.service.DataStandardCodeDirService;
import com.qk.dm.datastandards.service.DataStandardCodeTermService;
import com.qk.dm.datastandards.vo.DsdCodeTermVO;
import com.qk.dm.datastandards.vo.PageResultVO;
import com.qk.dm.datastandards.vo.params.DsdCodeTermParamsVO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wjq
 * @date 20210604
 * @since 1.0.0 数据标准标准代码术语接口实现类
 */
@Service
public class DataStandardCodeTermServiceImpl implements DataStandardCodeTermService {
    private final DsdCodeTermRepository dsdCodeTermRepository;
    private final DataStandardCodeDirService dataStandardCodeDirService;
    private final EntityManager entityManager;
    private JPAQueryFactory jpaQueryFactory;

    @Autowired
    public DataStandardCodeTermServiceImpl(DsdCodeTermRepository dsdCodeTermRepository, DataStandardCodeDirService dataStandardCodeDirService, EntityManager entityManager) {
        this.dsdCodeTermRepository = dsdCodeTermRepository;
        this.dataStandardCodeDirService = dataStandardCodeDirService;
        this.entityManager = entityManager;
    }

    @PostConstruct
    public void initFactory() {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public PageResultVO<DsdCodeTermVO> getDsdCodeTerm(DsdCodeTermParamsVO dsdCodeTermParamsVO) {
        List<DsdCodeTermVO> dsdCodeTermVOList = new ArrayList<DsdCodeTermVO>();

        Map<String, Object> map = null;
        try {
            map = queryDsdCodeTermByParams(dsdCodeTermParamsVO);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new BizException("查询失败!!!");
        }
        List<DsdCodeTerm> list = (List<DsdCodeTerm>) map.get("list");
        long total = (long) map.get("total");

        list.forEach(dsdCodeTerm -> {
            DsdCodeTermVO dsdCodeTermVO = DsdCodeTermMapper.INSTANCE.usDsdCodeTermVO(dsdCodeTerm);
            dsdCodeTermVOList.add(dsdCodeTermVO);
        });
        return new PageResultVO<>(total, dsdCodeTermParamsVO.getPagination().getPage(),
                dsdCodeTermParamsVO.getPagination().getSize(), dsdCodeTermVOList);
    }

    @Override
    public void bulkAddDsdCodeTerm(List<DsdCodeTermVO> dsdCodeTermVOList) {
        Set<String> codeIds = dsdCodeTermVOList.stream().map(dsdCodeTermVO -> dsdCodeTermVO.getCodeId()).collect(Collectors.toSet());
        List<DsdCodeTerm> searchResult = (List<DsdCodeTerm>) dsdCodeTermRepository.findAll(QDsdCodeTerm.dsdCodeTerm.codeId.in(codeIds));

        if (searchResult.size() > 0) {
            throw new BizException("当前要新增的码表编码ID为：" + searchResult.get(0).getCodeId()
                    + "码表名称名称为:" + searchResult.get(0).getCodeName() + " 的数据，已存在！！！");
        }

        List<DsdCodeTerm> dsdCodeTermList = new ArrayList<>();
        dsdCodeTermVOList.forEach(dsdCodeTermVO -> {
            DsdCodeTerm dsdCodeTerm = DsdCodeTermMapper.INSTANCE.useDsdCodeTerm(dsdCodeTermVO);
            dsdCodeTerm.setGmtCreate(new Date());
            dsdCodeTerm.setGmtModified(new Date());
            dsdCodeTermList.add(dsdCodeTerm);
        });
        dsdCodeTermRepository.saveAll(dsdCodeTermList);
    }

    @Override
    public DsdCodeTermVO getDsdCodeTermById(Integer id) {
        DsdCodeTermVO dsdCodeTermVO = null;
        Optional<DsdCodeTerm> dsdCodeTerm = dsdCodeTermRepository.findById(id);
        if (dsdCodeTerm.isPresent()) {
            dsdCodeTermVO = DsdCodeTermMapper.INSTANCE.usDsdCodeTermVO(dsdCodeTerm.get());
        }
        return dsdCodeTermVO;
    }

    @Override
    public void bulkUpdateDsdCodeTerm(List<DsdCodeTermVO> dsdCodeTermVOList) {
        dsdCodeTermVOList.forEach(dsdCodeTermVO -> updateDsdCodeTerm(dsdCodeTermVO));
    }

    @Override
    public void addDsdCodeTerm(DsdCodeTermVO dsdCodeTermVO) {
        DsdCodeTerm dsdCodeTerm = DsdCodeTermMapper.INSTANCE.useDsdCodeTerm(dsdCodeTermVO);
        dsdCodeTerm.setGmtCreate(new Date());
        dsdCodeTerm.setGmtModified(new Date());
        Predicate predicate = QDsdCodeTerm.dsdCodeTerm.id.eq(dsdCodeTerm.getId());
        boolean exists = dsdCodeTermRepository.exists(predicate);
        if (exists) {
            throw new BizException("当前要新增的码表编码ID为：" + dsdCodeTerm.getCodeId() +
                    "码表名称名称为:" + dsdCodeTerm.getCodeName() + " 的数据，已存在！！！");
        }
        dsdCodeTermRepository.save(dsdCodeTerm);
    }

    @Override
    public void updateDsdCodeTerm(DsdCodeTermVO dsdCodeTermVO) {
        DsdCodeTerm dsdCodeTerm = DsdCodeTermMapper.INSTANCE.useDsdCodeTerm(dsdCodeTermVO);
        dsdCodeTerm.setGmtModified(new Date());
        Predicate predicate = QDsdCodeTerm.dsdCodeTerm.id.eq(dsdCodeTerm.getId());
        boolean exists = dsdCodeTermRepository.exists(predicate);
        if (exists) {
            dsdCodeTermRepository.saveAndFlush(dsdCodeTerm);
        } else {
            throw new BizException("当前要新增的码表编码ID为：" + dsdCodeTerm.getCodeId() +
                    "码表名称名称为:" + dsdCodeTerm.getCodeName() + " 的数据，不存在！！！");
        }
    }

    @Override
    public void deleteDsdCodeTerm(Integer delId) {
        boolean exists = dsdCodeTermRepository.exists(QDsdCodeTerm.dsdCodeTerm.id.eq(delId));
        if (exists) {
            dsdCodeTermRepository.deleteById(delId);
        }
    }

    @Transactional
    @Override
    public void bulkDeleteDsdTerm(String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        Set<Integer> idSet = new HashSet<>();
        idList.forEach(id -> idSet.add(Integer.parseInt(id)));
        Iterable<DsdCodeTerm> dsdCodeTerms = dsdCodeTermRepository.findAll(QDsdCodeTerm.dsdCodeTerm.id.in(idSet));
        dsdCodeTermRepository.deleteInBatch(dsdCodeTerms);
    }

    public Map<String, Object> queryDsdCodeTermByParams(DsdCodeTermParamsVO dsdCodeTermParamsVO) throws ParseException {
        QDsdCodeTerm qDsdCodeTerm = QDsdCodeTerm.dsdCodeTerm;
        HashMap<String, Object> result = new HashMap<>();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        checkCondition(booleanBuilder, qDsdCodeTerm, dsdCodeTermParamsVO);
        long count = jpaQueryFactory.select(qDsdCodeTerm.count()).from(qDsdCodeTerm).where(booleanBuilder).fetchOne();
        List<DsdCodeTerm> dsdCodeTermList = jpaQueryFactory.select(qDsdCodeTerm).
                from(qDsdCodeTerm).
                where(booleanBuilder).
                orderBy(qDsdCodeTerm.codeId.asc()).
                offset((dsdCodeTermParamsVO.getPagination().getPage() - 1) * dsdCodeTermParamsVO.getPagination().getSize()).
                limit(dsdCodeTermParamsVO.getPagination().getSize()).
                fetch();
        result.put("list", dsdCodeTermList);
        result.put("total", count);
        return result;
    }

    public void checkCondition(BooleanBuilder booleanBuilder, QDsdCodeTerm qDsdCodeTerm, DsdCodeTermParamsVO dsdCodeTermParamsVO) {
        if (!StringUtils.isEmpty(dsdCodeTermParamsVO.getCodeDirId()) && !DsdConstant.TREE_DIR_TOP_PARENT_ID.equals(dsdCodeTermParamsVO.getCodeDirId())) {
            Set<String> codeDirSet = new HashSet<>();
            dataStandardCodeDirService.getCodeDirId(codeDirSet, dsdCodeTermParamsVO.getCodeDirId());
            booleanBuilder.and(qDsdCodeTerm.codeDirId.in(codeDirSet));
        }
        if (!StringUtils.isEmpty(dsdCodeTermParamsVO.getCodeName())) {
            booleanBuilder.and(qDsdCodeTerm.codeName.contains(dsdCodeTermParamsVO.getCodeName()));
        }
        if (!StringUtils.isEmpty(dsdCodeTermParamsVO.getCodeId())) {
            booleanBuilder.and(qDsdCodeTerm.codeId.contains(dsdCodeTermParamsVO.getCodeId()));
        }
        if (!StringUtils.isEmpty(dsdCodeTermParamsVO.getBeginDay()) && !StringUtils.isEmpty(dsdCodeTermParamsVO.getEndDay())) {
            StringTemplate dateExpr = Expressions.stringTemplate("DATE_FORMAT({0},'%Y-%m-%d %H:%i:%S')", qDsdCodeTerm.gmtModified);
            booleanBuilder.and(dateExpr.between(dsdCodeTermParamsVO.getBeginDay(), dsdCodeTermParamsVO.getEndDay()));
        }
    }
}
