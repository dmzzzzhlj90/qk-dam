package com.qk.dm.indicator.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.indicator.entity.IdcTimeLimit;
import com.qk.dm.indicator.entity.QIdcTimeLimit;
import com.qk.dm.indicator.mapstruct.mapper.IdcTimeLimitMapper;
import com.qk.dm.indicator.params.dto.IdcTimeLimitDTO;
import com.qk.dm.indicator.params.dto.IdcTimeLimitPageDTO;
import com.qk.dm.indicator.params.vo.IdcTimeLimitVO;
import com.qk.dm.indicator.repositories.IdcTimeLimitRepository;
import com.qk.dm.indicator.service.IdcTimeLimitService;
import com.querydsl.core.BooleanBuilder;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
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
 * @date 2021/9/1 15:53
 * @since 1.0.0
 */
@Service
public class IdcTimeLimitServiceImpl implements IdcTimeLimitService {
    private JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;
    private final QIdcTimeLimit qIdcTimeLimit = QIdcTimeLimit.idcTimeLimit;
    private final IdcTimeLimitRepository idcTimeLimitRepository;

    @Autowired
    public IdcTimeLimitServiceImpl(EntityManager entityManager, IdcTimeLimitRepository idcTimeLimitRepository) {
        this.entityManager = entityManager;
        this.idcTimeLimitRepository = idcTimeLimitRepository;
    }

    @PostConstruct
    public void initFactory() {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public void insert(IdcTimeLimitDTO idcTimeLimitDTO) {
        IdcTimeLimit idcTimeLimit = IdcTimeLimitMapper.INSTANCE.useIdcTimeLimit(idcTimeLimitDTO);
        idcTimeLimitRepository.save(idcTimeLimit);
    }

    @Override
    public void update(Long id, IdcTimeLimitDTO idcTimeLimitDTO) {
        Predicate predicate =
                qIdcTimeLimit
                        .id
                        .eq(id);
        IdcTimeLimit idcTimeLimit = idcTimeLimitRepository.findOne(predicate).orElse(null);
        if (Objects.isNull(idcTimeLimit)) {
            throw new BizException("当前时间限定：" + id + " 的数据不存在！！！");
        }
        idcTimeLimitRepository.saveAndFlush(idcTimeLimit);
    }

    @Override
    public void delete(String ids) {
        Iterable<Long> idList =
                Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toList());
        List<IdcTimeLimit> idcTimeLimitList = idcTimeLimitRepository.findAllById(idList);
        if (idcTimeLimitList.isEmpty()) {
            throw new BizException("当前要删除的通用限定id为：" + ids + " 的数据，不存在！！！");
        }
        idcTimeLimitRepository.deleteAll(idcTimeLimitList);
    }

    @Override
    public IdcTimeLimitVO detail(Long idcTimeLimitId) {
        Predicate predicate =
                qIdcTimeLimit
                        .id
                        .eq(idcTimeLimitId);
        IdcTimeLimit idcTimeLimit = idcTimeLimitRepository.findOne(predicate).orElse(null);
        if (Objects.isNull(idcTimeLimit)) {
            throw new BizException("当前时间限定：" + idcTimeLimitId + " 的数据不存在！！！");
        }
        return IdcTimeLimitMapper.INSTANCE.useIdcTimeLimitVO(idcTimeLimit);
    }

    @Override
    public List<IdcTimeLimitVO> findAll() {
        List<IdcTimeLimit> list = idcTimeLimitRepository.findAllByDelFlag(0);
        return IdcTimeLimitMapper.INSTANCE.userIdcTimeLimitListVO(list);
    }

    @Override
    public PageResultVO<IdcTimeLimitVO> findListPage(IdcTimeLimitPageDTO idcTimeLimitPageDTO) {

        Map<String, Object> map;
        try {
            map = queryIdcTimeLimitByParams(idcTimeLimitPageDTO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("查询失败!!!");
        }
        List<IdcTimeLimit> list = (List<IdcTimeLimit>) map.get("list");
        List<IdcTimeLimitVO> idcTimeLimitVOList = IdcTimeLimitMapper.INSTANCE.userIdcTimeLimitListVO(list);
        return new PageResultVO<>(
                (long) map.get("total"),
                idcTimeLimitPageDTO.getPagination().getPage(),
                idcTimeLimitPageDTO.getPagination().getSize(),
                idcTimeLimitVOList);
    }

    private Map<String, Object> queryIdcTimeLimitByParams(IdcTimeLimitPageDTO idcTimeLimitPageDTO) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        checkCondition(booleanBuilder, idcTimeLimitPageDTO);
        Map<String, Object> result = new HashMap<>();
        long count =
                jpaQueryFactory
                        .select(qIdcTimeLimit.count())
                        .from(qIdcTimeLimit)
                        .where(booleanBuilder)
                        .fetchOne();

        List<IdcTimeLimit> idcTimeLimitList = jpaQueryFactory
                .select(qIdcTimeLimit)
                .from(qIdcTimeLimit)
                .where(booleanBuilder)
                .orderBy(qIdcTimeLimit.id.asc())
                .offset(
                        (long) (idcTimeLimitPageDTO.getPagination().getPage() - 1)
                                * idcTimeLimitPageDTO.getPagination().getSize())
                .limit(idcTimeLimitPageDTO.getPagination().getSize())
                .fetch();
        result.put("list", idcTimeLimitList);
        result.put("total", count);
        return result;
    }

    public void checkCondition(BooleanBuilder booleanBuilder, IdcTimeLimitPageDTO idcTimeLimitPageDTO) {
        if (!StringUtils.isEmpty(idcTimeLimitPageDTO.getLimitName())) {
            booleanBuilder.and(qIdcTimeLimit.limitName.contains(idcTimeLimitPageDTO.getLimitName()));
        }
        if (!StringUtils.isEmpty(idcTimeLimitPageDTO.getStartTime())
                && !StringUtils.isEmpty(idcTimeLimitPageDTO.getEndTime())) {
            StringTemplate dateExpr =
                    Expressions.stringTemplate(
                            "DATE_FORMAT({0},'%Y-%m-%d %H:%i:%S')", qIdcTimeLimit.gmtCreate);
            booleanBuilder.and(
                    dateExpr.between(idcTimeLimitPageDTO.getStartTime(), idcTimeLimitPageDTO.getEndTime()));
        }
        booleanBuilder.and(qIdcTimeLimit.delFlag.eq(0));
    }
}
