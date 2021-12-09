package com.qk.dm.dataquality.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataquality.constant.DqcConstant;
import com.qk.dm.dataquality.entity.DqcSchedulerRules;
import com.qk.dm.dataquality.entity.QDqcSchedulerRules;
import com.qk.dm.dataquality.mapstruct.mapper.DqcSchedulerRulesMapper;
import com.qk.dm.dataquality.repositories.DqcSchedulerRulesRepository;
import com.qk.dm.dataquality.service.DqcRuleSqlBuilderService;
import com.qk.dm.dataquality.service.DqcSchedulerRulesService;
import com.qk.dm.dataquality.vo.DqcSchedulerRulesParamsVO;
import com.qk.dm.dataquality.vo.DqcSchedulerRulesVO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.*;

/**
 * 数据质量_规则调度_规则信息
 *
 * @author wjq
 * @date 2021/11/10
 * @since 1.0.0
 */
@Service
public class DqcSchedulerRulesServiceImpl implements DqcSchedulerRulesService {
    private final QDqcSchedulerRules qDqcSchedulerRules = QDqcSchedulerRules.dqcSchedulerRules;
    private final DqcSchedulerRulesRepository dqcSchedulerRulesRepository;
    private final DqcRuleSqlBuilderService dqcRuleSqlBuilderService;


    private final EntityManager entityManager;
    private JPAQueryFactory jpaQueryFactory;

    public DqcSchedulerRulesServiceImpl(DqcSchedulerRulesRepository dqcSchedulerRulesRepository,
                                        DqcRuleSqlBuilderService dqcRuleSqlBuilderService, EntityManager entityManager) {
        this.dqcSchedulerRulesRepository = dqcSchedulerRulesRepository;
        this.dqcRuleSqlBuilderService = dqcRuleSqlBuilderService;
        this.entityManager = entityManager;
    }


    @PostConstruct
    public void initFactory() {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public PageResultVO<DqcSchedulerRulesVO> searchPageList(DqcSchedulerRulesParamsVO dqcSchedulerRulesParamsVO) {
        List<DqcSchedulerRulesVO> dqcSchedulerRulesVOList = new ArrayList<>();
        Map<String, Object> map = null;
        long total;
        try {
            map = querySchedulerRulesByParams(dqcSchedulerRulesParamsVO);
            List<DqcSchedulerRules> schedulerRulesList = (List<DqcSchedulerRules>) map.get("list");
            total = (long) map.get("total");

            schedulerRulesList.forEach(
                    schedulerRules -> {
                        DqcSchedulerRulesVO dqcSchedulerRulesVO = DqcSchedulerRulesMapper.INSTANCE.userDqcSchedulerRulesVO(schedulerRules);
                        dqcSchedulerRulesVOList.add(dqcSchedulerRulesVO);
                    });
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("查询失败!!!");
        }
        return new PageResultVO<>(
                total,
                dqcSchedulerRulesParamsVO.getPagination().getPage(),
                dqcSchedulerRulesParamsVO.getPagination().getSize(),
                dqcSchedulerRulesVOList);
    }

    @Override
    public void insert(DqcSchedulerRulesVO dqcSchedulerRulesVO) {
        DqcSchedulerRules dqcSchedulerRules = DqcSchedulerRulesMapper.INSTANCE.userDqcSchedulerRules(dqcSchedulerRulesVO);

        dqcSchedulerRules.setTables(GsonUtil.toJsonString(dqcSchedulerRulesVO.getTableList()));
        dqcSchedulerRules.setFields(GsonUtil.toJsonString(dqcSchedulerRulesVO.getFieldList()));
        // todo 创建人
        dqcSchedulerRules.setCreateUserid("admin");
        dqcSchedulerRules.setDelFlag(DqcConstant.DEL_FLAG_RETAIN);
        dqcSchedulerRulesRepository.save(dqcSchedulerRules);
    }

    @Override
    public List<DqcSchedulerRulesVO> insertBulk(List<DqcSchedulerRulesVO> dqcSchedulerRulesVOList, String jobId) {
        List<DqcSchedulerRulesVO> executorRuleList = new ArrayList<>();
        //TODO 数据量比较少,暂时循环保存,后期修改为jpa批量操作
        for (DqcSchedulerRulesVO dqcSchedulerRulesVO : dqcSchedulerRulesVOList) {
            dqcSchedulerRulesVO.setJobId(jobId);
            //生成执行Sql;
            String executorSql = getExecutorSql(dqcSchedulerRulesVO);
            dqcSchedulerRulesVO.setExecuteSql(executorSql);
            //回填basicVo进行实例生成;
            executorRuleList.add(dqcSchedulerRulesVO);
            insert(dqcSchedulerRulesVO);
        }
        return executorRuleList;
    }

    @Override
    public void update(DqcSchedulerRulesVO dqcSchedulerRulesVO) {
        DqcSchedulerRules dqcSchedulerRules = DqcSchedulerRulesMapper.INSTANCE.userDqcSchedulerRules(dqcSchedulerRulesVO);

        dqcSchedulerRules.setTables(GsonUtil.toJsonString(dqcSchedulerRulesVO.getTableList()));
        dqcSchedulerRules.setFields(GsonUtil.toJsonString(dqcSchedulerRulesVO.getFieldList()));
        // todo 修改人
        dqcSchedulerRules.setUpdateUserid("admin");
        if(dqcSchedulerRules.getId() == null){
            // todo 创建人
            dqcSchedulerRules.setCreateUserid("admin");
        }
        dqcSchedulerRules.setDelFlag(DqcConstant.DEL_FLAG_RETAIN);
        dqcSchedulerRulesRepository.saveAndFlush(dqcSchedulerRules);

    }

    @Override
    public List<DqcSchedulerRulesVO> updateBulk(List<DqcSchedulerRulesVO> dqcSchedulerRulesVOList, String jobId) {
        List<DqcSchedulerRulesVO> executorRuleList = new ArrayList<>();
        //TODO 数据量比较少,暂时循环保存,后期修改为jpa批量操作
        for (DqcSchedulerRulesVO dqcSchedulerRulesVO : dqcSchedulerRulesVOList) {
            dqcSchedulerRulesVO.setJobId(jobId);
            //生成执行Sql;
            String executorSql = getExecutorSql(dqcSchedulerRulesVO);
            dqcSchedulerRulesVO.setExecuteSql(executorSql);
            //回填basicVo进行实例生成;
            executorRuleList.add(dqcSchedulerRulesVO);
            update(dqcSchedulerRulesVO);
        }
        return executorRuleList;
    }

    @Override
    public void deleteOne(Long id) {
        boolean exists = dqcSchedulerRulesRepository.exists(qDqcSchedulerRules.id.eq(id));
        if (exists) {
            dqcSchedulerRulesRepository.deleteById(id);
        }
    }

    @Override
    public void deleteBulk(String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        Set<Long> idSet = new HashSet<>();
        idList.forEach(id -> idSet.add(Long.parseLong(id)));
        dqcSchedulerRulesRepository.deleteAllByIdInBatch(idSet);
    }

    public Map<String, Object> querySchedulerRulesByParams(DqcSchedulerRulesParamsVO dqcSchedulerRulesParamsVO) {
        Map<String, Object> result = new HashMap<>(16);
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        checkCondition(booleanBuilder, qDqcSchedulerRules, dqcSchedulerRulesParamsVO);
        long count =
                jpaQueryFactory
                        .select(qDqcSchedulerRules.count())
                        .from(qDqcSchedulerRules)
                        .where(booleanBuilder)
                        .fetchOne();
        List<DqcSchedulerRules> dsdBasicInfoList =
                jpaQueryFactory
                        .select(qDqcSchedulerRules)
                        .from(qDqcSchedulerRules)
                        .where(booleanBuilder)
                        .orderBy(qDqcSchedulerRules.gmtModified.asc())
                        .offset((dqcSchedulerRulesParamsVO.getPagination().getPage() - 1) * dqcSchedulerRulesParamsVO.getPagination().getSize())
                        .limit(dqcSchedulerRulesParamsVO.getPagination().getSize())
                        .fetch();

        result.put("list", dsdBasicInfoList);
        result.put("total", count);
        return result;
    }

    public void checkCondition(BooleanBuilder booleanBuilder,
                               QDqcSchedulerRules qDqcSchedulerRules,
                               DqcSchedulerRulesParamsVO dqcSchedulerRulesParamsVO) {
        if (!ObjectUtils.isEmpty(dqcSchedulerRulesParamsVO.getJobId())) {
            booleanBuilder.and(qDqcSchedulerRules.jobId.eq(dqcSchedulerRulesParamsVO.getJobId()));
        }

        if (!ObjectUtils.isEmpty(dqcSchedulerRulesParamsVO.getEngineType())) {
            booleanBuilder.and(qDqcSchedulerRules.engineType.eq(dqcSchedulerRulesParamsVO.getEngineType()));
        }

        if (!ObjectUtils.isEmpty(dqcSchedulerRulesParamsVO.getDatabaseName())) {
            booleanBuilder.and(qDqcSchedulerRules.databaseName.eq(dqcSchedulerRulesParamsVO.getDatabaseName()));
        }

        if (!ObjectUtils.isEmpty(dqcSchedulerRulesParamsVO.getBeginDay())
                && !StringUtils.isEmpty(dqcSchedulerRulesParamsVO.getEndDay())) {
            StringTemplate dateExpr =
                    Expressions.stringTemplate(
                            "DATE_FORMAT({0},'%Y-%m-%d %H:%i:%S')", qDqcSchedulerRules.gmtModified);
            booleanBuilder.and(
                    dateExpr.between(dqcSchedulerRulesParamsVO.getBeginDay(), dqcSchedulerRulesParamsVO.getEndDay()));
        }
    }

    @Override
    public Boolean checkRuleTemp(Long id) {
        return dqcSchedulerRulesRepository.exists(qDqcSchedulerRules.ruleTempId.eq(id));
    }

    @Override
    public void deleteByJobId(String jobId) {
        //根据JobId查询对应规则信息
        Iterable<DqcSchedulerRules> dqcSchedulerRules = dqcSchedulerRulesRepository.findAll(qDqcSchedulerRules.jobId.eq(jobId));
        //执行批量删除
        dqcSchedulerRulesRepository.deleteAllInBatch(dqcSchedulerRules);
    }

    @Override
    public void deleteBulkByJobIds(List<String> jobIds) {
        Iterable<DqcSchedulerRules> dqcSchedulerRules = dqcSchedulerRulesRepository.findAll(qDqcSchedulerRules.jobId.in(jobIds));
        dqcSchedulerRulesRepository.deleteAllInBatch(dqcSchedulerRules);
    }

    private String getExecutorSql(DqcSchedulerRulesVO dqcSchedulerRulesVO) {
        return dqcRuleSqlBuilderService.getExecuteSql(dqcSchedulerRulesVO);
    }

}
