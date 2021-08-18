package com.qk.dm.dataservice.service.imp;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataservice.entity.DasFlowStrategy;
import com.qk.dm.dataservice.entity.QDasFlowStrategy;
import com.qk.dm.dataservice.mapstruct.mapper.DasFlowStrategyMapper;
import com.qk.dm.dataservice.repositories.DasFlowStrategyRepository;
import com.qk.dm.dataservice.service.DasFlowStrategyService;
import com.qk.dm.dataservice.vo.DasFlowStrategyParamsVO;
import com.qk.dm.dataservice.vo.DasFlowStrategyVO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.*;

/**
 * @author zys
 * @date 2021/8/18
 * @since 1.0.0
 */
@Service
public class DasFlowStrategyServiceImpl implements DasFlowStrategyService {
    private static final QDasFlowStrategy qDasFlowStrategy =QDasFlowStrategy.dasFlowStrategy;
    private final DasFlowStrategyRepository dasFlowStrategyRepository;
    private final EntityManager entityManager;
    private JPAQueryFactory jpaQueryFactory;
    @Autowired
    public DasFlowStrategyServiceImpl(DasFlowStrategyRepository dasFlowStrategyRepository, EntityManager entityManager) {
        this.dasFlowStrategyRepository = dasFlowStrategyRepository;
        this.entityManager = entityManager;
    }
    @PostConstruct
    public void initFactory() {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }
    @Override
    public void addDasFlowStrategy(DasFlowStrategyVO dasFlowStrategyVO) {
        DasFlowStrategy dasFlowStrategy = DasFlowStrategyMapper.INSTANCE.useDasFlowStrategy(dasFlowStrategyVO);
        dasFlowStrategy.setGmtCreate(new Date());
        dasFlowStrategy.setGmtModified(new Date());
        BooleanExpression predicate = qDasFlowStrategy.strategyName.eq(dasFlowStrategy.getStrategyName());
        boolean exists = dasFlowStrategyRepository.exists(predicate);
        if (exists){
            throw  new BizException("当前新增服务流控名称为"+dasFlowStrategy.getStrategyName()+"的数据已经存在");
        }else {
            dasFlowStrategyRepository.save(dasFlowStrategy);
        }
    }

    @Override
    public void updateDasFlowStrategy(DasFlowStrategyVO dasFlowStrategyVO) {
        DasFlowStrategy dasFlowStrategy = DasFlowStrategyMapper.INSTANCE.useDasFlowStrategy(dasFlowStrategyVO);
        dasFlowStrategy.setGmtCreate(new Date());
        dasFlowStrategy.setGmtModified(new Date());
        BooleanExpression predicate = qDasFlowStrategy.strategyName.eq(dasFlowStrategy.getStrategyName());
        boolean exists = dasFlowStrategyRepository.exists(predicate);
        if (exists){
            dasFlowStrategyRepository.saveAndFlush(dasFlowStrategy);
        }else {
            throw  new BizException("当前修改服务流控名称为"+dasFlowStrategy.getStrategyName()+"的数据不存在");
        }
    }

    @Override
    public void deleteDasFlowStrategy(Long id) {
        boolean exists = dasFlowStrategyRepository.exists(qDasFlowStrategy.id.eq(id));
        if (exists) {
            dasFlowStrategyRepository.deleteById(id);
        }else {
            throw new BizException("当前要删除id为"+id+"的服务流控数据不存在");
        }
    }

    @Override
    public void bulkDeleteDasFlowStrategy(String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        Set<Long> idSet = new HashSet<>();
        idList.forEach(id -> idSet.add(Long.valueOf(id)));
        List<DasFlowStrategy> apiDasFlowStrategyList = dasFlowStrategyRepository.findAllById(idSet);
        dasFlowStrategyRepository.deleteInBatch(apiDasFlowStrategyList);
    }

    @Override
    public PageResultVO<DasFlowStrategyVO> getDasFlowStrategy(DasFlowStrategyParamsVO dasFlowStrategyParamsVO) {
        List<DasFlowStrategyVO> dasFlowStrategyVOList = new ArrayList<>();
        Map<String, Object> map = null;
        try {
            map = queryDasFlowStrategyParams(dasFlowStrategyParamsVO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("查询失败!!!");
        }
        List<DasFlowStrategy> list = (List<DasFlowStrategy>) map.get("list");
        long total = (long) map.get("total");
        list.forEach(
                dasFlowStrategy -> {
                    DasFlowStrategyVO dasFlowStrategyVO = DasFlowStrategyMapper.INSTANCE.useDasFlowStrategyVO(dasFlowStrategy);
                    dasFlowStrategyVOList.add(dasFlowStrategyVO);
                });
        return new PageResultVO<>(
                total,
                dasFlowStrategyParamsVO.getPagination().getPage(),
                dasFlowStrategyParamsVO.getPagination().getSize(),
                dasFlowStrategyVOList);
    }

    private Map<String, Object> queryDasFlowStrategyParams(DasFlowStrategyParamsVO dasFlowStrategyParamsVO) {
        Map<String, Object> result = new HashMap<>();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        checkCondition(booleanBuilder, qDasFlowStrategy, dasFlowStrategyParamsVO);
        long count =
                jpaQueryFactory
                        .select(qDasFlowStrategy.count())
                        .from(qDasFlowStrategy)
                        .where(booleanBuilder)
                        .fetchOne();

        List<DasFlowStrategy> dasFlowStrategyList = jpaQueryFactory
                .select(qDasFlowStrategy)
                .from(qDasFlowStrategy)
                .where(booleanBuilder)
                .orderBy(qDasFlowStrategy.strategyName.asc())
                .offset(
                        (dasFlowStrategyParamsVO.getPagination().getPage() - 1)
                                * dasFlowStrategyParamsVO.getPagination().getSize())
                .limit(dasFlowStrategyParamsVO.getPagination().getSize())
                .fetch();
        result.put("list", dasFlowStrategyList);
        result.put("total", count);
        return result;
    }

    private void checkCondition(BooleanBuilder booleanBuilder, QDasFlowStrategy qDasFlowStrategy, DasFlowStrategyParamsVO dasFlowStrategyParamsVO) {
        if (!StringUtils.isEmpty(dasFlowStrategyParamsVO.getStrategyName())) {
            booleanBuilder.and(qDasFlowStrategy.strategyName.contains(dasFlowStrategyParamsVO.getStrategyName()));
        }
        if (!StringUtils.isEmpty(dasFlowStrategyParamsVO.getBeginDay())
                && !StringUtils.isEmpty(dasFlowStrategyParamsVO.getEndDay())) {
            StringTemplate dateExpr =
                    Expressions.stringTemplate(
                            "DATE_FORMAT({0},'%Y-%m-%d %H:%i:%S')", qDasFlowStrategy.gmtModified);
            booleanBuilder.and(
                    dateExpr.between(
                            dasFlowStrategyParamsVO.getBeginDay(), dasFlowStrategyParamsVO.getEndDay()));
        }
    }

}