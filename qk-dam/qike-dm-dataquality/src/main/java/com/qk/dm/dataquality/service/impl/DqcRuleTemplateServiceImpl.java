package com.qk.dm.dataquality.service.impl;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dam.jpa.pojo.Pagination;
import com.qk.dam.sqlbuilder.sqlparser.SqlParserFactory;
import com.qk.dm.dataquality.constant.*;
import com.qk.dm.dataquality.constant.ruletemplate.DimensionTypeEnum;
import com.qk.dm.dataquality.constant.ruletemplate.PublishStateEnum;
import com.qk.dm.dataquality.constant.ruletemplate.TempTypeEnum;
import com.qk.dm.dataquality.entity.DqcRuleTemplate;
import com.qk.dm.dataquality.entity.QDqcRuleTemplate;
import com.qk.dm.dataquality.mapstruct.mapper.DqcRuleTemplateMapper;
import com.qk.dm.dataquality.params.dto.DqcRuleTemplatePageDTO;
import com.qk.dm.dataquality.params.dto.DqcRuleTemplateReleaseDTO;
import com.qk.dm.dataquality.repositories.DqcRuleTemplateRepository;
import com.qk.dm.dataquality.service.DqcRuleTemplateService;
import com.qk.dm.dataquality.service.DqcSchedulerRulesService;
import com.qk.dm.dataquality.vo.DqcRuleTemplateInfoVO;
import com.qk.dm.dataquality.vo.DqcRuleTemplateVO;
import com.qk.dm.dataquality.vo.RuleTemplateConstantsVO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author shenpj
 * @date 2021/11/8 7:46 下午
 * @since 1.0.0
 */
@Service
public class DqcRuleTemplateServiceImpl implements DqcRuleTemplateService {
    private static final Log LOG = LogFactory.get("规则模版操作");
    private final DqcRuleTemplateRepository dqcRuleTemplateRepository;
    private JPAQueryFactory jpaQueryFactory;
    private final EntityManager entityManager;
    private final QDqcRuleTemplate qDqcRuleTemplate = QDqcRuleTemplate.dqcRuleTemplate;
    private final DqcSchedulerRulesService dqcSchedulerRulesService;

    public DqcRuleTemplateServiceImpl(DqcRuleTemplateRepository dqcRuleTemplateRepository,
                                      EntityManager entityManager,
                                      DqcSchedulerRulesService dqcSchedulerRulesService) {
        this.dqcRuleTemplateRepository = dqcRuleTemplateRepository;
        this.entityManager = entityManager;
        this.dqcSchedulerRulesService = dqcSchedulerRulesService;
    }

    @PostConstruct
    public void initFactory() {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public void insert(DqcRuleTemplateVO dqcRuleTemplateVo) {
        //判断sql是否适用与引擎
        checkEngine(dqcRuleTemplateVo.getEngineType(), dqcRuleTemplateVo.getTempSql());
        DqcRuleTemplate dqcRuleTemplate = DqcRuleTemplateMapper.INSTANCE.userDqcRuleTemplate(dqcRuleTemplateVo);
        //todo 需要更改为前端传入
//        dqcRuleTemplate.setEngineType(DqcConstant.ENGINE_TYPE);
//        dqcRuleTemplate.setTempType(TempTypeEnum.CUSTOM.getCode());
        dqcRuleTemplate.setPublishState(PublishStateEnum.OUTLINE.getCode());
        // todo 添加创建人
        dqcRuleTemplate.setCreateUserid("admin");
        dqcRuleTemplate.setDelFlag(0);
        dqcRuleTemplateRepository.save(dqcRuleTemplate);
    }

    @Override
    public void update(DqcRuleTemplateVO dqcRuleTemplateVo) {
        DqcRuleTemplate dqcRuleTemplate = getInfoById(dqcRuleTemplateVo.getId());
        checkPublishState(dqcRuleTemplate, "上线规则模版不支持修改！！！");
        DqcRuleTemplateMapper.INSTANCE.userDqcRuleTemplate(dqcRuleTemplateVo, dqcRuleTemplate);
        // todo 添加修改人
        dqcRuleTemplate.setUpdateUserid("admin");
        dqcRuleTemplateRepository.save(dqcRuleTemplate);
    }

    @Override
    public void release(DqcRuleTemplateReleaseDTO dqcRuleTemplateReleaseDto) {
        DqcRuleTemplate dqcRuleTemplate = getInfoById(dqcRuleTemplateReleaseDto.getId());
        // 判断是否有引用
        checkRulesIsQuote(dqcRuleTemplateReleaseDto.getPublishState(), dqcRuleTemplate.getId());
        dqcRuleTemplate.setPublishState(dqcRuleTemplateReleaseDto.getPublishState());
        // todo 添加修改人
        dqcRuleTemplate.setUpdateUserid("admin");
        dqcRuleTemplateRepository.save(dqcRuleTemplate);
    }

    @Override
    public void deleteOne(Long id) {
        DqcRuleTemplate dqcRuleTemplate = getInfoById(id);
        checkPublishState(dqcRuleTemplate, "上线规则模版不支持删除");
        dqcRuleTemplate.setDelFlag(DqcConstant.DEL_FLAG_DEL);
        dqcRuleTemplateRepository.save(dqcRuleTemplate);
    }

    @Override
    public void deleteBulk(String ids) {
        Iterable<Long> idList =
                Arrays.stream(ids.split(","))
                        .map(Long::valueOf)
                        .collect(Collectors.toList());
        List<DqcRuleTemplate> ruleTemplates = dqcRuleTemplateRepository.findAllById(idList);
        checkInfo(ids, ruleTemplates);
        ruleTemplates.stream()
                .peek(
                        ruleTemplate -> {
                            checkPublishState(ruleTemplate, "上线规则模版不支持删除");
                            ruleTemplate.setDelFlag(DqcConstant.DEL_FLAG_DEL);
                        })
                .collect(Collectors.toList());
        dqcRuleTemplateRepository.saveAll(ruleTemplates);
    }

    @Override
    public DqcRuleTemplateInfoVO detail(Long id) {
        return DqcRuleTemplateMapper.INSTANCE.userDqcRuleTemplateInfoVo(getInfoById(id));
    }



    @Override
    public List<DqcRuleTemplateInfoVO> search(DqcRuleTemplatePageDTO dqcRuleTemplatePageDto) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        checkCondition(dqcRuleTemplatePageDto, booleanBuilder);
        booleanBuilder.and(qDqcRuleTemplate.publishState.eq(PublishStateEnum.RELEASE.getCode()));
        List<DqcRuleTemplate> list =
                jpaQueryFactory
                        .select(qDqcRuleTemplate)
                        .from(qDqcRuleTemplate)
                        .where(booleanBuilder)
                        .orderBy(qDqcRuleTemplate.tempType.desc(), qDqcRuleTemplate.gmtModified.desc())
                        .fetch();
        return DqcRuleTemplateMapper.INSTANCE.userDqcRuleTemplateInfoVo(list);
    }

    @Override
    public PageResultVO<DqcRuleTemplateInfoVO> searchPageList(
            DqcRuleTemplatePageDTO dqcRuleTemplatePageDto) {
        Map<String, Object> map = queryParams(dqcRuleTemplatePageDto);
        List<DqcRuleTemplate> list = (List<DqcRuleTemplate>) map.get("list");
        List<DqcRuleTemplateInfoVO> voList =
                DqcRuleTemplateMapper.INSTANCE.userDqcRuleTemplateInfoVo(list);
        return new PageResultVO<>(
                (long) map.get("total"),
                dqcRuleTemplatePageDto.getPagination().getPage(),
                dqcRuleTemplatePageDto.getPagination().getSize(),
                voList);
    }

    @Override
    public RuleTemplateConstantsVO getRuLeTemplateConstants() {
        RuleTemplateConstantsVO.RuleTemplateConstantsVOBuilder builder = RuleTemplateConstantsVO.builder();
        builder.engineTypeEnum(EngineTypeEnum.getAllValue())
                .dimensionTypeEnum(DimensionTypeEnum.getAllValue())
                .publishStateEnum(PublishStateEnum.getAllValue())
                .tempTypeEnum(TempTypeEnum.getAllValue())
                .ruleTypeEnum(RuleTypeEnum.getAllValue());
        return builder.build();
    }

    private Map<String, Object> queryParams(DqcRuleTemplatePageDTO dqcRuleTemplateVo) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        checkCondition(dqcRuleTemplateVo, booleanBuilder);
        Map<String, Object> result = new HashMap<>(2);
        result.put("list", getTemplateList(dqcRuleTemplateVo.getPagination(), booleanBuilder));
        result.put("total", getCount(booleanBuilder));
        return result;
    }

    private long getCount(BooleanBuilder booleanBuilder) {
        try {
            return jpaQueryFactory
                    .select(qDqcRuleTemplate.count())
                    .from(qDqcRuleTemplate)
                    .where(booleanBuilder)
                    .fetchOne();
        } catch (Exception e) {
            LOG.debug("查询总数量出错{}", e.getMessage());
            throw new BizException("查询失败");
        }
    }

    private List<DqcRuleTemplate> getTemplateList(
            Pagination pagination, BooleanBuilder booleanBuilder) {
        try {
            return jpaQueryFactory
                    .select(qDqcRuleTemplate)
                    .from(qDqcRuleTemplate)
                    .where(booleanBuilder)
                    .orderBy(qDqcRuleTemplate.tempType.desc(), qDqcRuleTemplate.gmtModified.desc())
                    .offset((pagination.getPage() - 1) * pagination.getSize())
                    .limit(pagination.getSize())
                    .fetch();
        } catch (Exception e) {
            LOG.debug("查询列表出错{}", e.getMessage());
            throw new BizException("查询失败!!!");
        }
    }

    public void checkCondition(DqcRuleTemplatePageDTO dqcRuleTemplateVo, BooleanBuilder booleanBuilder) {
        if (dqcRuleTemplateVo.getDirId() != null) {
            booleanBuilder.and(qDqcRuleTemplate.dirId.eq(dqcRuleTemplateVo.getDirId()));
        }
        if (dqcRuleTemplateVo.getTempName() != null) {
            booleanBuilder.and(qDqcRuleTemplate.tempName.contains(dqcRuleTemplateVo.getTempName()));
        }
        if (dqcRuleTemplateVo.getDimensionType() != null) {
            booleanBuilder.and(qDqcRuleTemplate.dimensionType.eq(dqcRuleTemplateVo.getDimensionType()));
        }
        if (dqcRuleTemplateVo.getEngineType() != null) {
            booleanBuilder.and(qDqcRuleTemplate.engineType.contains(dqcRuleTemplateVo.getEngineType()));
        }
        if (dqcRuleTemplateVo.getRuleType() != null) {
            booleanBuilder.and(qDqcRuleTemplate.ruleType.contains(dqcRuleTemplateVo.getRuleType()));
        }
    }

    private DqcRuleTemplate getInfoById(Long id) {
        DqcRuleTemplate info = dqcRuleTemplateRepository.findById(id).orElse(null);
        checkInfo(id, info);
        return info;
    }

    private void checkInfo(Long id, DqcRuleTemplate info) {
        if (info == null) {
            throw new BizException("id为：" + id + " 的模版不存在!");
        }
    }

    private void checkInfo(String ids, List<DqcRuleTemplate> idcTimeLimitList) {
        if (CollectionUtils.isEmpty(idcTimeLimitList)) {
            throw new BizException("当前要删除的id为：" + ids + " 的数据，不存在!");
        }
    }

    private void checkPublishState(DqcRuleTemplate dqcRuleTemplate, String s) {
        if (Objects.equals(dqcRuleTemplate.getPublishState(), PublishStateEnum.RELEASE.getCode())) {
            throw new BizException(s);
        }
    }

    private void checkEngine(String engineType, String sql) {
        Arrays.stream(engineType.split(","))
                .peek(item -> checkSql(sql, EngineTypeEnum.fromValue(item)))
                .collect(Collectors.toList());
    }

    private void checkSql(String sql, EngineTypeEnum dataSourceEnums) {
        if (!SqlParserFactory.parseStatements(sql, dataSourceEnums.getDbType())) {
            throw new BizException("本sql " + dataSourceEnums.getCode() + " 不适用!");
        }
    }

    private void checkRulesIsQuote(String publishState, Long id) {
        if (publishState.equals(PublishStateEnum.OFFLINE.getCode()) && dqcSchedulerRulesService.checkRuleTemp(id)) {
            throw new BizException("有规则引用不允许下线!");
        }
    }
}
