package com.qk.dm.indicator.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.indicator.common.property.IdcState;
import com.qk.dam.indicator.common.sqlbuilder.SqlBuilder;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.indicator.entity.IdcDerived;
import com.qk.dm.indicator.entity.QIdcDerived;
import com.qk.dm.indicator.mapstruct.mapper.IdcDerivedMapper;
import com.qk.dm.indicator.params.dto.IdcDerivedDTO;
import com.qk.dm.indicator.params.dto.IdcDerivedPageDTO;
import com.qk.dm.indicator.params.vo.IdcAtomVO;
import com.qk.dm.indicator.params.vo.IdcDerivedVO;
import com.qk.dm.indicator.repositories.IdcDerivedRepository;
import com.qk.dm.indicator.service.IdcAtomService;
import com.qk.dm.indicator.service.IdcDerivedService;
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

@Service
public class IdcDerivedServiceImpl implements IdcDerivedService {

    private JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;
    private final QIdcDerived qIdcDerived = QIdcDerived.idcDerived;
    private final IdcDerivedRepository idcDerivedRepository;
    private final IdcAtomService idcAtomService;
    @Autowired
    public IdcDerivedServiceImpl(EntityManager entityManager,IdcDerivedRepository idcDerivedRepository,
                                 IdcAtomService idcAtomService){
        this.entityManager = entityManager;
        this.idcDerivedRepository = idcDerivedRepository;
        this.idcAtomService = idcAtomService;
    }

    @PostConstruct
    public void initFactory() {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public void insert(IdcDerivedDTO idcDerivedDTO) {
        IdcDerived idcDerived = IdcDerivedMapper.INSTANCE.useIdcDerived(idcDerivedDTO);
        IdcAtomVO idcAtomVO = idcAtomService.getDetailByCode(idcDerived.getAtomIndicatorCode());
        String sql = SqlBuilder.derived(idcAtomVO.getExpression(),idcAtomVO.getDataSheet(),
                idcDerived.getGeneralLimit());
        idcDerived.setSqlSentence(sql);
        idcDerivedRepository.save(idcDerived);
    }

    @Override
    public void update(Long id, IdcDerivedDTO idcDerivedDTO) {
        IdcDerived idcDerived = idcDerivedRepository.findById(id).orElse(null);
        if (idcDerived == null) {
            throw new BizException("当前要修改的衍生指标id为：" + id + " 的数据不存在！！！");
        }
        IdcDerivedMapper.INSTANCE.useIdcDerived(idcDerivedDTO,idcDerived);

        IdcAtomVO idcAtomVO = idcAtomService.getDetailByCode(idcDerived.getAtomIndicatorCode());
        String sql = SqlBuilder.derived(idcAtomVO.getExpression(),idcAtomVO.getDataSheet(),
                idcDerived.getGeneralLimit());
        idcDerived.setSqlSentence(sql);
        idcDerivedRepository.saveAndFlush(idcDerived);
    }

    @Override
    public void delete(String ids) {
        Iterable<Long> idList =
                Arrays.stream(ids.split(",")).map(Long::valueOf).collect(Collectors.toList());
        List<IdcDerived> idcTimeLimitList = idcDerivedRepository.findAllById(idList);
        if (idcTimeLimitList.isEmpty()) {
            throw new BizException("当前要删除的衍生指标id为：" + ids + " 的数据，不存在！！！");
        }
        idcDerivedRepository.deleteAll(idcTimeLimitList);
    }

    @Override
    public IdcDerivedVO detail(Long id) {
        Predicate predicate =
                qIdcDerived
                        .id
                        .eq(id);
        IdcDerived idcDerived = idcDerivedRepository.findOne(predicate).orElse(null);
        if (Objects.isNull(idcDerived)) {
            throw new BizException("当前衍生指标id：" + id + " 的数据不存在！！！");
        }
        return IdcDerivedMapper.INSTANCE.useIdcDerivedVO(idcDerived);
    }

    @Override
    public void publish(Long id) {
        IdcDerived idcDerived = idcDerivedRepository.findById(id).orElse(null);
        if (idcDerived == null) {
            throw new BizException("当前要上线的衍生指标id为：" + id + " 的数据不存在！！！");
        }
        idcDerived.setIndicatorStatus(IdcState.ONLINE);
        idcDerivedRepository.saveAndFlush(idcDerived);
    }

    @Override
    public void offline(Long id) {
        IdcDerived idcDerived = idcDerivedRepository.findById(id).orElse(null);
        if (idcDerived == null) {
            throw new BizException("当前要下线的衍生指标id为：" + id + " 的数据不存在！！！");
        }
        idcDerived.setIndicatorStatus(IdcState.OFFLINE);
        idcDerivedRepository.saveAndFlush(idcDerived);
    }

    @Override
    public PageResultVO<IdcDerivedVO> findListPage(IdcDerivedPageDTO idcDerivedPageDTO) {
        Map<String, Object> map;
        try {
            map = queryIdcDerivedVOByParams(idcDerivedPageDTO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("查询失败!!!");
        }
        List<IdcDerived> list = (List<IdcDerived>) map.get("list");
        List<IdcDerivedVO> idcDerivedVOList = IdcDerivedMapper.INSTANCE.userIdcDerivedListVO(list);
        return new PageResultVO<>(
                (long) map.get("total"),
                idcDerivedPageDTO.getPagination().getPage(),
                idcDerivedPageDTO.getPagination().getSize(),
                idcDerivedVOList);
    }
    private Map<String, Object> queryIdcDerivedVOByParams(IdcDerivedPageDTO idcDerivedPageDTO) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        checkCondition(booleanBuilder, idcDerivedPageDTO);
        Map<String, Object> result = new HashMap<>();
        long count =
                jpaQueryFactory
                        .select(qIdcDerived.count())
                        .from(qIdcDerived)
                        .where(booleanBuilder)
                        .fetchOne();

        List<IdcDerived> idcDerivedList = jpaQueryFactory
                .select(qIdcDerived)
                .from(qIdcDerived)
                .where(booleanBuilder)
                .orderBy(qIdcDerived.id.asc())
                .offset(
                        (long) (idcDerivedPageDTO.getPagination().getPage() - 1)
                                * idcDerivedPageDTO.getPagination().getSize())
                .limit(idcDerivedPageDTO.getPagination().getSize())
                .fetch();
        result.put("list", idcDerivedList);
        result.put("total", count);
        return result;
    }

    public void checkCondition(BooleanBuilder booleanBuilder, IdcDerivedPageDTO idcDerivedPageDTO) {
        if (!StringUtils.isEmpty(idcDerivedPageDTO.getDerivedIndicatorName())) {
            booleanBuilder.and(qIdcDerived.derivedIndicatorName.contains(idcDerivedPageDTO.getDerivedIndicatorName()));
        }
        if (!StringUtils.isEmpty(idcDerivedPageDTO.getStartTime())
                && !StringUtils.isEmpty(idcDerivedPageDTO.getEndTime())) {
            StringTemplate dateExpr =
                    Expressions.stringTemplate(
                            "DATE_FORMAT({0},'%Y-%m-%d %H:%i:%S')", qIdcDerived.gmtCreate);
            booleanBuilder.and(
                    dateExpr.between(idcDerivedPageDTO.getStartTime(), idcDerivedPageDTO.getEndTime()));
        }
        booleanBuilder.and(qIdcDerived.delFlag.eq(0));
    }
}
