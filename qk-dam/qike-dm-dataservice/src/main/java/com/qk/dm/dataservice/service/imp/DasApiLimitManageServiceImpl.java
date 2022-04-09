package com.qk.dm.dataservice.service.imp;

import com.google.common.collect.Maps;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.dataservice.spi.pojo.RouteData;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.dataservice.biz.ApiSixProcessService;
import com.qk.dm.dataservice.constant.TimeUnitTypeEnum;
import com.qk.dm.dataservice.entity.DasApiLimitBindInfo;
import com.qk.dm.dataservice.entity.DasApiLimitInfo;
import com.qk.dm.dataservice.entity.QDasApiLimitBindInfo;
import com.qk.dm.dataservice.entity.QDasApiLimitInfo;
import com.qk.dm.dataservice.mapstruct.mapper.DasApiLimitInfoMapper;
import com.qk.dm.dataservice.repositories.DasApiLimitBindInfoRepository;
import com.qk.dm.dataservice.repositories.DasApiLimitInfoRepository;
import com.qk.dm.dataservice.service.DasApiLimitManageService;
import com.qk.dm.dataservice.vo.*;
import com.qk.plugin.dataservice.apisix.route.ApiSixRouteInfo;
import com.qk.plugin.dataservice.apisix.route.constant.ApiSixConstant;
import com.qk.plugin.dataservice.apisix.route.entity.PluginLimitCount;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

/**
 * 数据服务_服务流控管理
 *
 * @author wjq
 * @date 2022/03/16
 * @since 1.0.0
 */
@Service
public class DasApiLimitManageServiceImpl implements DasApiLimitManageService {

    private static final QDasApiLimitInfo qDasApiLimitInfo = QDasApiLimitInfo.dasApiLimitInfo;
    private static final QDasApiLimitBindInfo qDasApiLimitBindInfo = QDasApiLimitBindInfo.dasApiLimitBindInfo;

    private final ApiSixProcessService apiSixProcessService;
    private final DasApiLimitInfoRepository dasApiLimitInfoRepository;
    private final DasApiLimitBindInfoRepository dasApiLimitBindInfoRepository;
    private final JPAQueryFactory jpaQueryFactory;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public DasApiLimitManageServiceImpl(ApiSixProcessService apiSixProcessService,
                                        DasApiLimitInfoRepository dasApiLimitInfoRepository,
                                        DasApiLimitBindInfoRepository dasApiLimitBindInfoRepository,
                                        JPAQueryFactory jpaQueryFactory) {

        this.apiSixProcessService = apiSixProcessService;
        this.dasApiLimitInfoRepository = dasApiLimitInfoRepository;
        this.dasApiLimitBindInfoRepository = dasApiLimitBindInfoRepository;
        this.jpaQueryFactory = jpaQueryFactory;
    }


    @Override
    public PageResultVO<DasApiLimitInfoVO> searchList(DasApiLimitManageParamsVO dasApiLimitManageParamsVO) {
        List<DasApiLimitInfoVO> dasApiLimitInfoVOList = new ArrayList<>();
        Map<String, Object> map = null;
        try {
            map = queryDasApiLimitInfoVOByParams(dasApiLimitManageParamsVO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("查询失败!!!");
        }
        List<DasApiLimitInfo> list = (List<DasApiLimitInfo>) map.get("list");
        long total = (long) map.get("total");

        list.forEach(
                dasApiLimitInfo -> {
                    DasApiLimitInfoVO dasApiLimitInfoVO = transformToVO(dasApiLimitInfo);
                    dasApiLimitInfoVOList.add(dasApiLimitInfoVO);
                });
        return new PageResultVO<>(
                total,
                dasApiLimitManageParamsVO.getPagination().getPage(),
                dasApiLimitManageParamsVO.getPagination().getSize(),
                dasApiLimitInfoVOList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(DasApiLimitInfoVO dasApiLimitInfoVO) {
        Optional<DasApiLimitInfo> optionalDasApiLimitInfo = checkExistApiLimitInfo(dasApiLimitInfoVO);
        if (optionalDasApiLimitInfo.isPresent()) {
            DasApiLimitInfo dasApiLimitInfo = optionalDasApiLimitInfo.get();
            throw new BizException("当前要新增的流控策略名称为:" + dasApiLimitInfo.getLimitName() + " 的数据，已存在！！！");
        }
        DasApiLimitInfo dasApiLimitInfo = transformToEntity(dasApiLimitInfoVO);
        dasApiLimitInfo.setGmtCreate(new Date());
        dasApiLimitInfo.setGmtModified(new Date());
        dasApiLimitInfo.setCreateUserid("admin");
        dasApiLimitInfo.setDelFlag(0);
        dasApiLimitInfoRepository.save(dasApiLimitInfo);
    }

    public Optional<DasApiLimitInfo> checkExistApiLimitInfo(DasApiLimitInfoVO dasApiLimitInfoVO) {
        Predicate predicate = qDasApiLimitInfo.limitName.eq(dasApiLimitInfoVO.getLimitName());
        return dasApiLimitInfoRepository.findOne(predicate);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DasApiLimitInfoVO dasApiLimitInfoVO) {
        checkUpdateParams(dasApiLimitInfoVO);
        Predicate predicate = qDasApiLimitInfo.id.eq(dasApiLimitInfoVO.getId());
        Optional<DasApiLimitInfo> optionalDasApiLimitInfo = dasApiLimitInfoRepository.findOne(predicate);
        if (optionalDasApiLimitInfo.isEmpty()) {
            throw new BizException("当前要编辑的流控策略ID为:" + dasApiLimitInfoVO.getId() + " 的数据，不存在！！！");
        }

        DasApiLimitInfo dasApiLimitInfo = transformToEntity(dasApiLimitInfoVO);
        dasApiLimitInfo.setGmtModified(new Date());
        dasApiLimitInfo.setCreateUserid("admin");
        dasApiLimitInfo.setDelFlag(0);
        dasApiLimitInfoRepository.saveAndFlush(dasApiLimitInfo);
    }

    private void checkUpdateParams(DasApiLimitInfoVO dasApiLimitInfoVO) {
        if (ObjectUtils.isEmpty(dasApiLimitInfoVO.getId())) {
            throw new BizException("流控策略信息,ID参数为空,编辑失败!!!");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBulk(String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        Set<Long> idSet = new HashSet<>();
        idList.forEach(id -> idSet.add(Long.valueOf(id)));
        List<DasApiLimitInfo> apiLimitInfoList = dasApiLimitInfoRepository.findAllById(idSet);
        // 批量删除流控信息
        dasApiLimitInfoRepository.deleteAllInBatch(apiLimitInfoList);

        // 批量删除绑定信息
        Iterable<DasApiLimitBindInfo> apiLimitBindInfos = dasApiLimitBindInfoRepository.findAll(qDasApiLimitBindInfo.limitId.in(idSet));
        dasApiLimitBindInfoRepository.deleteAllInBatch(apiLimitBindInfos);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bind(DasApiLimitBindParamsVO apiLimitBindParamsVO) {
        List<DasApiLimitBindInfo> dasApiLimitBindInfos = Lists.newArrayList();
        //参数信息
        Long limitId = apiLimitBindParamsVO.getLimitId();
        List<String> routeIds = apiLimitBindParamsVO.getRouteIds();
        // 查询流控策略信息
        Optional<DasApiLimitInfo> optionalDasApiLimitInfo = dasApiLimitInfoRepository.findById(limitId);
        if (optionalDasApiLimitInfo.isPresent()) {
            DasApiLimitInfo dasApiLimitInfo = optionalDasApiLimitInfo.get();
            // 清除绑定路由
            clearApiLimitBindRoutes(limitId, routeIds);
            for (String routeId : routeIds) {
                // 更新路由插件信息
                updateRoutePlugins(dasApiLimitBindInfos, dasApiLimitInfo, routeId);
            }
            // 批量更新绑定信息
            bulkUpdateApiLimitBindInfo(dasApiLimitBindInfos);
        }
    }

    @Override
    public List<DasApiGroupRouteVO> routes() {
        List<DasApiGroupRouteVO> apiGroupRouteVOList = new LinkedList<>();
        //获取路由信息
        List<RouteData> routeInfoList = apiSixProcessService.getRouteInfo(apiSixProcessService.buildRouteContext());
        routeInfoList.forEach(routeData -> {
            DasApiGroupRouteVO dasApiGroupRouteVO =
                    DasApiGroupRouteVO.builder()
                            .routeId(routeData.getId())
                            .routeName(routeData.getName())
                            .routeUrl(routeData.getUri())
                            .build();
            apiGroupRouteVOList.add(dasApiGroupRouteVO);
        });
        return apiGroupRouteVOList;
    }

    @Override
    public Map<String, String> timeUnit() {
        return TimeUnitTypeEnum.getAllValue();
    }

    @Override
    public List<DasApiLimitBindInfoVO> searchBindInfo(Long limitId) {
        List<DasApiLimitBindInfoVO> limitBindInfoVOS = Lists.newArrayList();
        Iterable<DasApiLimitBindInfo> limitBindInfos = dasApiLimitBindInfoRepository.findAll(qDasApiLimitBindInfo.limitId.eq(limitId));
        limitBindInfos.forEach(bindInfo -> {
            limitBindInfoVOS.add(DasApiLimitInfoMapper.INSTANCE.useDasApiLimitBindInfoVO(bindInfo));
        });
        return limitBindInfoVOS;
    }

    private DasApiLimitInfoVO transformToVO(DasApiLimitInfo dasApiLimitInfo) {
        return DasApiLimitInfoMapper.INSTANCE.useDasApiLimitInfoVO(dasApiLimitInfo);
    }

    private DasApiLimitInfo transformToEntity(DasApiLimitInfoVO dasApiLimitInfoVO) {
        return DasApiLimitInfoMapper.INSTANCE.useDasApiLimitInfo(dasApiLimitInfoVO);
    }

    /**
     * 更新路由插件信息
     *
     * @param dasApiLimitBindInfos
     * @param dasApiLimitInfo
     * @param routeId
     */
    private void updateRoutePlugins(List<DasApiLimitBindInfo> dasApiLimitBindInfos, DasApiLimitInfo dasApiLimitInfo, String routeId) {
        // 根据路由ID查询路由信息
        ApiSixRouteInfo routeInfo = (ApiSixRouteInfo) apiSixProcessService.getRouteInfoById(routeId);
        // 路由插件信息(old)
        Map<String, Object> plugins = routeInfo.getPlugins();
        if (plugins == null) {
            plugins = Maps.newHashMap();
        }
        // 设置 路由插件limit-count信息
        PluginLimitCount pluginLimitCount = PluginLimitCount.builder()
                .count(dasApiLimitInfo.getApiLimitCount())
                .key_type(ApiSixConstant.PLUGINS_LIMIT_COUNT_KEY_TYPE)
                .key(ApiSixConstant.PLUGINS_LIMIT_COUNT_REMOTE_ADDR)
                .policy(ApiSixConstant.PLUGINS_LIMIT_COUNT_LOCAL)
                .time_window(calTimeWindow(dasApiLimitInfo))
                .rejected_code(ApiSixConstant.PLUGINS_LIMIT_COUNT_REJECTED_CODE)
                .build();

        plugins.put(ApiSixConstant.PLUGINS_LIMIT_COUNT_KEY, pluginLimitCount);
        routeInfo.setPlugins(plugins);
        // 添加 路由插件limit-count
        apiSixProcessService.updateRoutePlugins(routeInfo);
        // 设置绑定对象信息
        dasApiLimitBindInfos.add(setApiLimitBindInfo(dasApiLimitInfo, routeInfo));
    }

    /**
     * 批量更新绑定信息
     *
     * @param dasApiLimitBindInfos
     */
    @Transactional(rollbackFor = Exception.class)
    public void bulkUpdateApiLimitBindInfo(List<DasApiLimitBindInfo> dasApiLimitBindInfos) {
        for (DasApiLimitBindInfo dasApiLimitBindInfo : dasApiLimitBindInfos) {
            entityManager.persist(dasApiLimitBindInfo);
        }
        entityManager.flush();
        entityManager.clear();
    }

    /**
     * 清除绑定路由
     *
     * @param limitId
     * @param routeIds
     */
    private void clearApiLimitBindRoutes(Long limitId, List<String> routeIds) {
        Predicate predicate = qDasApiLimitBindInfo.limitId.eq(limitId)
                .and(qDasApiLimitBindInfo.routeId.in(routeIds));
        Iterable<DasApiLimitBindInfo> oldLimitBindInfos = dasApiLimitBindInfoRepository.findAll(predicate);
        dasApiLimitBindInfoRepository.deleteAllInBatch(oldLimitBindInfos);
    }

    /**
     * 设置绑定对象信息
     *
     * @param dasApiLimitInfo
     * @param routeInfo
     * @return
     */
    private DasApiLimitBindInfo setApiLimitBindInfo(DasApiLimitInfo dasApiLimitInfo, ApiSixRouteInfo routeInfo) {
        DasApiLimitBindInfo dasApiLimitBindInfo = new DasApiLimitBindInfo();
        dasApiLimitBindInfo.setLimitId(dasApiLimitInfo.getId());
        dasApiLimitBindInfo.setRouteId(routeInfo.getId());
        dasApiLimitBindInfo.setApiGroupRouteName(routeInfo.getName());
        dasApiLimitBindInfo.setApiGroupRoutePath(routeInfo.getUri());

        dasApiLimitBindInfo.setGmtCreate(new Date());
        dasApiLimitBindInfo.setGmtModified(new Date());
        dasApiLimitBindInfo.setCreateUserid("admin");
        dasApiLimitBindInfo.setDelFlag(0);
        return dasApiLimitBindInfo;
    }

    /**
     * 根据时间单位计算限制具体时长
     *
     * @param dasApiLimitInfo
     * @return
     */
    private int calTimeWindow(DasApiLimitInfo dasApiLimitInfo) {
        int timeWindow = 0;

        Integer limitTime = dasApiLimitInfo.getLimitTime();
        String limitTimeUnit = dasApiLimitInfo.getLimitTimeUnit();
        TimeUnitTypeEnum timeUnitTypeEnum = TimeUnitTypeEnum.getVal(limitTimeUnit);
        switch (Objects.requireNonNull(timeUnitTypeEnum)) {
            case MS:
                timeWindow = limitTime;
                break;
            case MIN:
                timeWindow = limitTime * 60;
                break;
            case H:
                timeWindow = limitTime * 60 * 60;
                break;
            case D:
                timeWindow = limitTime * 60 * 60 * 24;
                break;
            default:
                break;
        }
        return timeWindow;
    }

    /**
     * 多条件查询流控策略信息列表
     *
     * @param dasApiLimitManageParamsVO
     * @return
     */
    public Map<String, Object> queryDasApiLimitInfoVOByParams(DasApiLimitManageParamsVO dasApiLimitManageParamsVO) {
        Map<String, Object> result = Maps.newHashMap();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        checkCondition(booleanBuilder, qDasApiLimitInfo, dasApiLimitManageParamsVO);
        long count =
                jpaQueryFactory
                        .select(qDasApiLimitInfo.count())
                        .from(qDasApiLimitInfo)
                        .where(booleanBuilder)
                        .fetchOne();
        List<DasApiLimitInfo> dasApiBasicInfoList =
                jpaQueryFactory
                        .select(qDasApiLimitInfo)
                        .from(qDasApiLimitInfo)
                        .where(booleanBuilder)
                        .orderBy(qDasApiLimitInfo.limitName.asc())
                        .offset(
                                (dasApiLimitManageParamsVO.getPagination().getPage() - 1)
                                        * dasApiLimitManageParamsVO.getPagination().getSize())
                        .limit(dasApiLimitManageParamsVO.getPagination().getSize())
                        .fetch();
        result.put("list", dasApiBasicInfoList);
        result.put("total", count);
        return result;
    }

    /**
     * 条件信息
     *
     * @param dasApiLimitManageParamsVO
     * @return
     */
    public void checkCondition(
            BooleanBuilder booleanBuilder,
            QDasApiLimitInfo qDasApiLimitInfo,
            DasApiLimitManageParamsVO dasApiLimitManageParamsVO) {

        // 策略名称
        if (!ObjectUtils.isEmpty(dasApiLimitManageParamsVO.getLimitName())) {
            booleanBuilder.and(qDasApiLimitInfo.limitName.contains(dasApiLimitManageParamsVO.getLimitName()));
        }
        // 开始时间--结束时间
        if (!ObjectUtils.isEmpty(dasApiLimitManageParamsVO.getBeginDay())
                && !ObjectUtils.isEmpty(dasApiLimitManageParamsVO.getEndDay())) {
            StringTemplate dateExpr =
                    Expressions.stringTemplate(
                            "DATE_FORMAT({0},'%Y-%m-%d %H:%i:%S')", qDasApiLimitInfo.gmtModified);
            booleanBuilder.and(
                    dateExpr.between(
                            dasApiLimitManageParamsVO.getBeginDay(), dasApiLimitManageParamsVO.getEndDay()));
        }
    }
}
